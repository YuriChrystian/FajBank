package br.edu.unifaj.mobile.fajbank.data.model;

import com.google.gson.annotations.SerializedName;

public class AuthenticateRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public AuthenticateRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
} 