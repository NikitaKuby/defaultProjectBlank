package com.example.blank.model;

import lombok.Data;
import java.util.List;

@Data // Автоматически генерирует геттеры, сеттеры, toString, equals, hashCode
public class TokenIntrospectionResponse {
    private boolean active;
    private List<String> aud;
    private long exp;
    private long iat;
    private String iss;
    private String jti;
    private String name;
    private long nbf;
    private String preferred_username;
    private String scope;
    private String sub;
    private String token_type;
    private long updated_at;

    private String urn_zitadel_iam_user_resourceowner_id;

    private String urn_zitadel_iam_user_resourceowner_name;

    private String urn_zitadel_iam_user_resourceowner_primary_domain;

    private String username;
}