package com.example.blank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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