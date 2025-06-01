package com.example.appsegurana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsegurana.R;
import com.example.appsegurana.models.DBHelper;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginButton, registerButton;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerPageButton);

        db = new DBHelper(this);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String senha = passwordInput.getText().toString();

            if (db.verifyLogin(email, senha)) {
                Utils.makeToast(this, "Login bem-sucedido!");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Utils.makeToast(this, "E-mail ou senha incorretos!");
            }
        });

        registerButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

    }

}