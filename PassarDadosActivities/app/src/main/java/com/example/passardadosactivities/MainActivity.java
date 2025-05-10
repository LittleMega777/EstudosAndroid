package com.example.passardadosactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button buttonEnviar;
    private TextView textNome;
    private TextView textIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNome = findViewById(R.id.nomeText);
        textIdade = findViewById(R.id.idadeText);
        buttonEnviar = findViewById(R.id.botaoEnviar);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dadoNome = textNome.getText().toString();
                String dadoIdade = textIdade.getText().toString();

                Intent intent = new Intent(getApplicationContext(), SegundaActivity.class);

                Usuario usuario = new Usuario("Jean", "jean@gmail.com");

                // Passar dados
                intent.putExtra("nome", dadoNome);
                intent.putExtra("idade", dadoIdade);
                intent.putExtra("objeto", usuario);

                startActivity(intent);


            }
        });
    }
}