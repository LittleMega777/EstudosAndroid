package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private ToggleButton togglePassword;
    private Switch switchPassword;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        togglePassword = findViewById(R.id.togglePassword);
        switchPassword = findViewById(R.id.switchPassword);
        result = findViewById(R.id.textResult);
    }

    public void send(View view){
        if (switchPassword.isChecked()){
            result.setText("Switch ON");
        } else {
            result.setText("Switch OFF");
        }

    }
}