package com.example.blank.service;

import com.example.blank.model.PrivateKeyData;
import com.example.blank.model.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@PropertySource(value = "/application.properties")
public class IntrospectService {

    @Value("${auth.introspection_url}")
    private String introspectionUrl;
    @Value("${auth.basic_client_id}")
    private String clientId;
    @Value("${auth.basic_client_secret}")
    private String clientSecret;
    @Value("${auth.key_algo}")
    private String jwtKeyAlgo;
    @Value("${auth.domain}")
    private String domain;
    private final RestTemplate restTemplate = new RestTemplate();

    private final PrivateKeyData privateKeyData;

    public IntrospectService() throws IOException{
        // Используем ClassPathResource для доступа к файлу в resources
        ClassPathResource resource = new ClassPathResource("jwt-private-key.json");
        File file = resource.getFile();

        // Читаем файл и преобразуем его в объект
        ObjectMapper objectMapper = new ObjectMapper();
        this.privateKeyData = objectMapper.readValue(file, PrivateKeyData.class);
    }


    public void validateIntro(TokenResponse intro) {
        if (intro == null) {
            throw new RuntimeException("Token is missing");
        }
        if (!intro.isActive()) {
            throw new RuntimeException("Token is invalid");
        }
        if (intro.getExp() < System.currentTimeMillis() / 1000) {
            throw new RuntimeException("Token has expired");
        }
    }


    public TokenResponse introspectTokenViaBasicAuth(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        // Подготовка данных для запроса
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("token", token);
        data.add("token_type_hint", "access_token");
        data.add("scope", "openid");

        // Настройка заголовков
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret); // Basic Authentication

        // Создание HTTP-запроса
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);

        // Выполнение POST-запроса
        TokenResponse response = restTemplate.postForObject(introspectionUrl, request, TokenResponse.class);
        log.info(response.toString());
        // Возврат результата
        return response;
    }


    public TokenResponse introspectTokenViaJWTAuth(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        // Генерация JWT для аутентификации клиента
        String appJWT = genAppAuthJWT(); // 1 час
        // Подготовка данных для запроса
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        data.add("client_assertion", appJWT);
        data.add("token", token);

        // Настройка заголовков
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Создание HTTP-запроса
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);

        // Выполнение POST-запроса
        TokenResponse tokenResponse = restTemplate.postForObject(introspectionUrl, request, TokenResponse.class);
        log.info(tokenResponse.toString());

        // Возврат результата
        return tokenResponse;
    }

    private String genAppAuthJWT() {
        // Преобразуем приватный ключ в объект PrivateKey
        PrivateKey privateKey = getPrivateKeyFromString(privateKeyData.getKey());

        // Полезная нагрузка (payload)
        Map<String, Object> payload = new HashMap<>();
        payload.put("iss", privateKeyData.getClientId()); // Issuer
        payload.put("sub", privateKeyData.getClientId()); // Subject
        payload.put("aud", domain);      // Audience
        payload.put("exp", new Date(System.currentTimeMillis() + (long) 3600 * 1000)); // Expiration
        payload.put("iat", new Date()); // Issued At

        // Заголовки (headers)
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", jwtKeyAlgo); // Алгоритм подписи
        headers.put("kid", privateKeyData.getKeyId());   // Key ID

        // Генерация JWT
        return Jwts.builder()
                .setHeader(headers) // Устанавливаем заголовки
                .setClaims(payload) // Устанавливаем полезную нагрузку
                .signWith(privateKey, SignatureAlgorithm.forName(jwtKeyAlgo)) // Подписываем JWT
                .compact(); // Преобразуем в строку
    }

    // Метод для преобразования строки в PrivateKey
    private PrivateKey getPrivateKeyFromString(String key) {
        try {
            // Удаляем лишние символы из PEM-формата
            String cleanedKey = key.replaceAll("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replaceAll("-----END RSA PRIVATE KEY-----", "")
                    .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", ""); // Удаляем пробелы и символы новой строки

            // Декодируем Base64
            byte[] keyBytes = Base64.getDecoder().decode(cleanedKey);

            // Если ключ в формате PKCS#1, преобразуем его в PKCS#8
            if (key.contains("BEGIN RSA PRIVATE KEY")) {
                // Используем BouncyCastle для преобразования PKCS#1 в PKCS#8
                try (PEMParser pemParser = new PEMParser(new StringReader(key))) {
                    Object object = pemParser.readObject();
                    if (object == null) {
                        throw new IllegalArgumentException("Failed to parse PEM key: the key is null or invalid.");
                    }

                    // Если объект является PEMKeyPair, извлекаем приватный ключ
                    if (object instanceof PEMKeyPair pemKeyPair) {
                        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                        return converter.getPrivateKey(pemKeyPair.getPrivateKeyInfo());
                    }
                    // Если объект является RSAPrivateKey (PKCS#1)
                    else if (object instanceof RSAPrivateKey rsaPrivateKey) {
                        PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(
                                new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption),
                                rsaPrivateKey
                        );
                        keyBytes = privateKeyInfo.getEncoded();
                    } else {
                        throw new IllegalArgumentException("Unsupported key format: " + object.getClass().getName());
                    }
                }
            }

            // Создаем объект PrivateKey
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert string to PrivateKey", e);
        }
    }
}
