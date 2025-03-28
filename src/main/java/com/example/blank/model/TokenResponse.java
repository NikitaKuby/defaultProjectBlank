package com.example.blank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenResponse {
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("aud")
    private List<String> aud;
    @JsonProperty("exp")
    private long exp;
    @JsonProperty("iat")
    private long iat;
    @JsonProperty("iss")
    private String iss;
    @JsonProperty("jti")
    private String jti;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nbf")
    private long nbf;
    @JsonProperty("preferred_username")
    private String preferredUsername;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("updated_at")
    private long updatedAt;
    @JsonProperty("urn:zitadel:iam:user:resourceowner:id")
    private String userResourceOwnerId;
    @JsonProperty("urn:zitadel:iam:user:resourceowner:name")
    private String userResourceOwnerName;
    @JsonProperty("urn:zitadel:iam:user:resourceowner:primary_domain")
    private String userResourceOwnerPrimaryDomain;
    @JsonProperty("username")
    private String username;

    @Override
    public String toString() {
        return "TokenResponse{" +
                "active=" + active +
                ", aud=" + aud +
                ", exp=" + exp +
                ", iat=" + iat +
                ", iss='" + iss + '\'' +
                ", jti='" + jti + '\'' +
                ", name='" + name + '\'' +
                ", nbf=" + nbf +
                ", preferredUsername='" + preferredUsername + '\'' +
                ", scope='" + scope + '\'' +
                ", sub='" + sub + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", updatedAt=" + updatedAt +
                ", userResourceOwnerId='" + userResourceOwnerId + '\'' +
                ", userResourceOwnerName='" + userResourceOwnerName + '\'' +
                ", userResourceOwnerPrimaryDomain='" + userResourceOwnerPrimaryDomain + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
