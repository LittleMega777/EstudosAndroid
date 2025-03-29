package com.example.progressbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int progress = 0;
    private ProgressBar progressHorizontal;
    private ProgressBar progressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressHorizontal = findViewById(R.id.progressHorizontal);
        progressCircle = findViewById(R.id.progressCircle);

        progressCircle.setVisibility(View.GONE);
    }

    public void loadingProgressBar(View view){
        System.out.println("Entrei na funcao");
        this.progress = this.progress + 1;
        progressHorizontal.setProgress(this.progress);
        progressCircle.setVisibility(View.VISIBLE);

        if (this.progress == 10){
            progressCircle.setVisibility(View.GONE);
        }

    }
}