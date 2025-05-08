package br.edu.unifaj.mobile.fajbank.feature.user.singup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.edu.unifaj.mobile.fajbank.R;
import br.edu.unifaj.mobile.fajbank.data.model.AuthenticateResponse;
import br.edu.unifaj.mobile.fajbank.data.model.SignUpRequest;
import br.edu.unifaj.mobile.fajbank.data.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingUpActiivity extends AppCompatActivity {
    private TextInputEditText editTextFirstName;
    private TextInputEditText editTextSecondName;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextConfirmPassword;
    private TextInputLayout inputLayoutPassword;
    private TextInputLayout inputLayoutConfirmPassword;
    private androidx.appcompat.widget.AppCompatButton buttonCriar;
    private ProgressBar progressBar;
    private View overlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        editTextFirstName = findViewById(R.id.singup_inputEditText_firstName);
        editTextSecondName = findViewById(R.id.singup_inputEditText_secondName);
        editTextEmail = findViewById(R.id.singup_inputEditText_email);
        editTextPassword = findViewById(R.id.singup_inputEditText_password);
        editTextConfirmPassword = findViewById(R.id.singup_inputEditText_confirmPassword);
        inputLayoutPassword = findViewById(R.id.singup_inputLayout_password);
        inputLayoutConfirmPassword = findViewById(R.id.singup_inputLayout_confirmPassword);
        buttonCriar = findViewById(R.id.singup_button_criar);
        progressBar = findViewById(R.id.singup_progressBar);
        overlay = findViewById(R.id.singup_overlay);
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

        editTextFirstName.addTextChangedListener(textWatcher);
        editTextSecondName.addTextChangedListener(textWatcher);
        editTextEmail.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);
        editTextConfirmPassword.addTextChangedListener(textWatcher);

        buttonCriar.setOnClickListener(v -> performSignUp());
    }

    private void validateInputs() {
        String firstName = editTextFirstName.getText().toString().trim();
        String secondName = editTextSecondName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Reset error states
        inputLayoutPassword.setError(null);
        inputLayoutConfirmPassword.setError(null);

        boolean isValid = !firstName.isEmpty() && !secondName.isEmpty() && 
                !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

        // Password validation
        if (isValid) {
            if (password.length() < 6) {
                inputLayoutPassword.setError("A senha deve ter pelo menos 6 caracteres");
                isValid = false;
            } else if (!password.equals(confirmPassword)) {
                inputLayoutConfirmPassword.setError("As senhas não coincidem");
                isValid = false;
            }
        }

        buttonCriar.setEnabled(isValid);
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        overlay.setVisibility(show ? View.VISIBLE : View.GONE);
        buttonCriar.setEnabled(!show);
        editTextFirstName.setEnabled(!show);
        editTextSecondName.setEnabled(!show);
        editTextEmail.setEnabled(!show);
        editTextPassword.setEnabled(!show);
        editTextConfirmPassword.setEnabled(!show);
    }

    private void performSignUp() {
        String firstName = editTextFirstName.getText().toString().trim();
        String secondName = editTextSecondName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        showLoading(true);
        SignUpRequest request = new SignUpRequest(email, password, firstName, secondName);
        
        RetrofitClient.getInstance()
                .getAuthApi()
                .signup(request)
                .enqueue(new Callback<AuthenticateResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                        showLoading(false);
                        if (response.isSuccessful() && response.body() != null) {
                            AuthenticateResponse authResponse = response.body();
                            handleSuccessfulSignUp(authResponse);
                        } else {
                            handleSignUpError("Erro ao criar conta. Tente novamente.");
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                        showLoading(false);
                        handleSignUpError("Erro de conexão. Tente novamente mais tarde.");
                    }
                });
    }

    private void handleSuccessfulSignUp(AuthenticateResponse response) {
        String token = response.getToken();
        
        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
        finish(); // Return to previous screen
    }

    private void handleSignUpError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
