package com.example.fragments.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.fragments.R;
import com.example.fragments.fragment.ContatosFragment;
import com.example.fragments.fragment.ConversasFragment;

public class MainActivity extends AppCompatActivity {

    private Button buttonConversa, buttonContato;
    private ConversasFragment conversasFragment;
    private ContatosFragment contatosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonContato = findViewById(R.id.buttonContatos);
        buttonConversa = findViewById(R.id.buttonConversa);

        // Configurar Objeto para o fragmento
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.frameConteudo, conversasFragment);
//
//        transaction.commit();

        buttonConversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conversasFragment = new ConversasFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.frameConteudo, conversasFragment);

                transaction.commit();

            }
        });

        buttonContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contatosFragment = new ContatosFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.frameConteudo, contatosFragment);

                transaction.commit();

            }
        });

    }
}