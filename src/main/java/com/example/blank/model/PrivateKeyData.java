package com.example.blank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public class PrivateKeyData {


    @JsonProperty("type")
    private String type;

    @JsonProperty("keyId")
    private String keyId;

    @JsonProperty("key")
    private String key;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("clientId")
    private String clientId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    @Override
    public String toString() {
        return "PrivateKeyData{" +
                "type='" + type + '\'' +
                ", keyId='" + keyId + '\'' +
                ", key='" + key + '\'' +
                ", appId='" + appId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}