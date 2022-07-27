package com.example.talk5login.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Authentication extends RealmObject {

    @SerializedName("accessCode")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("access_token")
    private String token;

    public Authentication(String username, String password, String grantType) {
        this.username = username;
        this.password = password;
        this.grantType = grantType;
    }

    public Authentication() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getGrantType() {
        return grantType;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", grantType='" + grantType + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
