package com.example.componentesbasicos;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailLogin;
    private EditText passwordLogin;
    private TextView printTextView;
    private CheckBox rememberPassword;
    private RadioButton sexLike;
    private RadioButton sexDontLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        printTextView = findViewById(R.id.printTextView);
        rememberPassword = findViewById(R.id.checkRememberPassword);
        sexLike = findViewById(R.id.radioLike);
        sexDontLike = findViewById(R.id.radioDontLike);
    }

    public void checkBox(){
        boolean passwordChecked = rememberPassword.isChecked();
        printTextView.setText("Lembrar senha = " + passwordChecked);

    }

    public void radioButton(){
        String definitiveSex = "";
        if(sexLike.isChecked()){
             definitiveSex = "Like";
         }
        else if (sexDontLike.isChecked()){
            definitiveSex = "DontLike";
        }
        printTextView.setText(definitiveSex);
    }

    public void login(View view){
        radioButton();
        // checkBox();
        /*String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();
        printTextView.setText("Já existe um Usuario com esse email e senha");
         */

    }

    public void clean(View view){
        printTextView.setText("");
        emailLogin.setText("");
        passwordLogin.setText("");
    }
}