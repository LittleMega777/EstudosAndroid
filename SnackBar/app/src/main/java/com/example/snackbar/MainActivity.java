package com.example.snackbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button buttonSnackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSnackBar = findViewById(R.id.buttonSnack);

        buttonSnackBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(
                        view,
                        "Tem certeza ?",
                        Snackbar.LENGTH_INDEFINITE
                        ).setAction("Confirmar", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                buttonSnackBar.setText("Confirmado");
                            }
                        }).show();
            }
        });

    }
}