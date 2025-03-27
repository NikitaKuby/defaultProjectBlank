package com.example.blank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

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

    public boolean isActive() {
        return active;
    }

    public List<String> getAud() {
        return aud;
    }

    public long getExp() {
        return exp;
    }

    public long getIat() {
        return iat;
    }

    public String getIss() {
        return iss;
    }

    public String getJti() {
        return jti;
    }

    public String getName() {
        return name;
    }

    public long getNbf() {
        return nbf;
    }

    public String getPreferredUsername() {
        return preferredUsername;
    }

    public String getScope() {
        return scope;
    }

    public String getSub() {
        return sub;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getUserResourceOwnerId() {
        return userResourceOwnerId;
    }

    public String getUserResourceOwnerName() {
        return userResourceOwnerName;
    }

    public String getUserResourceOwnerPrimaryDomain() {
        return userResourceOwnerPrimaryDomain;
    }

    public String getUsername() {
        return username;
    }
}
