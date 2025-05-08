package br.edu.unifaj.mobile.fajbank.data.model;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("second_name")
    private String second_name;

    public SignUpRequest(String email, String password, String first_name, String second_name) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.second_name = second_name;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }
} 