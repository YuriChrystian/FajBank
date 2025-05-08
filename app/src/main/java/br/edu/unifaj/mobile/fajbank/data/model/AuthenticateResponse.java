package br.edu.unifaj.mobile.fajbank.data.model;

import com.google.gson.annotations.SerializedName;

public class AuthenticateResponse {
    @SerializedName("token")
    private String token;

    public AuthenticateResponse(String tokene) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
} 