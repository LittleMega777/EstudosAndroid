package com.example.passardadosactivities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SegundaActivity extends AppCompatActivity {

    private TextView recebeNome;
    private TextView recebeIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        recebeNome = findViewById(R.id.viewRecebeNome);
        recebeIdade = findViewById(R.id.viewRecebeIdade);

        //Recuperar Dados
        Bundle dados = getIntent().getExtras();

        String nome = dados.getString("nome");
        String idade = dados.getString("idade");
        Usuario usuario = (Usuario) dados.getSerializable("objeto");

        recebeNome.setText(usuario.getEmail());
        recebeIdade.setText(usuario.getNome());


    }
}