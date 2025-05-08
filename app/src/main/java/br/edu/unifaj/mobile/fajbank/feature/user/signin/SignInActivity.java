package br.edu.unifaj.mobile.fajbank.feature.user.signin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import br.edu.unifaj.mobile.fajbank.R;
import br.edu.unifaj.mobile.fajbank.data.model.AuthenticateRequest;
import br.edu.unifaj.mobile.fajbank.data.model.AuthenticateResponse;
import br.edu.unifaj.mobile.fajbank.data.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private androidx.appcompat.widget.AppCompatButton buttonAcessar;
    private ProgressBar progressBar;
    private View overlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        editTextEmail = findViewById(R.id.singin_inputEditText_login);
        editTextPassword = findViewById(R.id.singin_inputEditText_password);
        buttonAcessar = findViewById(R.id.main_button_acessar);
        progressBar = findViewById(R.id.singin_progressBar);
        overlay = findViewById(R.id.singin_overlay);
    }

    private void setupListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateInputs();
            }
        };

        editTextEmail.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        buttonAcessar.setOnClickListener(v -> performSignIn());
    }

    private void validateInputs() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        boolean isValid = !email.isEmpty() && !password.isEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

        buttonAcessar.setEnabled(isValid);
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        overlay.setVisibility(show ? View.VISIBLE : View.GONE);
        buttonAcessar.setEnabled(!show);
        editTextEmail.setEnabled(!show);
        editTextPassword.setEnabled(!show);
    }

    private void performSignIn() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        showLoading(true);
        AuthenticateRequest request = new AuthenticateRequest(email, password);
        
        RetrofitClient.getInstance()
                .getAuthApi()
                .signin(request)
                .enqueue(new Callback<AuthenticateResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                        showLoading(false);
                        if (response.isSuccessful() && response.body() != null) {
                            AuthenticateResponse authResponse = response.body();
                            handleSuccessfulSignIn(authResponse);
                        } else {
                            handleSignInError("Erro ao fazer login. Verifique suas credenciais.");
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                        showLoading(false);
                        handleSignInError("Erro de conexão. Tente novamente mais tarde.");
                    }
                });
    }

    private void handleSuccessfulSignIn(AuthenticateResponse response) {
        String token = response.getToken();
        
        Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void handleSignInError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
