package com.example.primeirojavaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void sortearNumero(View view){
        Random random = new Random();
        int sort_range = 11;
        int sort_number = random.nextInt(sort_range);

        TextView textoSorteio = findViewById(R.id.TextSorteio);
        String finalText = String.format("Numero sorteado = %d", sort_number);
        textoSorteio.setText(finalText);

    }
}