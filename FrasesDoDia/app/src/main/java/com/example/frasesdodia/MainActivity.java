package com.example.frasesdodia;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generatesNewPhrase(View view){
        Random random = new Random();

        TextView texto = findViewById(R.id.dayPhrase);
        String[] phraseList = {"Phrase1", "Phrase2", "Phrase3", "Phrase4"};
        texto.setText(phraseList[random.nextInt(phraseList.length)]);

    }
}