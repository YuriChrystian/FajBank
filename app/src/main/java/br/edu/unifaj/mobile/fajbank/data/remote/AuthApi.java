package br.edu.unifaj.mobile.fajbank.data.remote;

import br.edu.unifaj.mobile.fajbank.data.model.AuthenticateRequest;
import br.edu.unifaj.mobile.fajbank.data.model.AuthenticateResponse;
import br.edu.unifaj.mobile.fajbank.data.model.SignUpRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("v1/signin")
    Call<AuthenticateResponse> signin(@Body AuthenticateRequest request);

    @POST("v1/signup")
    Call<AuthenticateResponse> signup(@Body SignUpRequest request);
} 