package com.example.appsegurana.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsegurana.R;
import com.example.appsegurana.models.DBHelper;
import com.example.appsegurana.models.SecurityUtils;

public class RegisterActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button registerButton;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);
        db = new DBHelper(this);

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Utils.makeToast(this, "Preencha todos os campos");
                return;
            }

            if (db.checkUserExists(email)) {
                Utils.makeToast(this, "Email já está cadastrado !");
                return;
            }

            String hash = SecurityUtils.sha256(password);
            boolean success = db.registerUser(email, hash);

            if (success) {
                Utils.makeToast(this, "Cadastro realizado com sucesso !");
                finish();
            } else {
                Utils.makeToast(this, "Erro ao cadastrar");
            }
        });
    }
}