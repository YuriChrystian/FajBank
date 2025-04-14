package br.edu.unifaj.mobile.fajbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.unifaj.mobile.fajbank.feature.user.signin.SignInActivity;

public class GatewayActivity extends AppCompatActivity {
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);
        login = findViewById(R.id.main_button_login);
        loginButton(login);
    }

    public void loginButton(Button login){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GatewayActivity.this, SignInActivity.class));
            }
        });
    }
}
