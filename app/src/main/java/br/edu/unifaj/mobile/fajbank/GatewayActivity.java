package br.edu.unifaj.mobile.fajbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.unifaj.mobile.fajbank.feature.user.signin.SignInActivity;
import br.edu.unifaj.mobile.fajbank.feature.user.singup.SingUpActiivity;

public class GatewayActivity extends AppCompatActivity {
    Button login;
    Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);
        login = findViewById(R.id.main_button_login);
        loginButton(login);
        register = findViewById(R.id.main_button_cadastrar);
        registerButton(register);
    }

    public void loginButton(Button login){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GatewayActivity.this, SignInActivity.class));
            }
        });
    }
    public void registerButton(Button register){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GatewayActivity.this, SingUpActiivity.class));
            }
        });
    }
}
