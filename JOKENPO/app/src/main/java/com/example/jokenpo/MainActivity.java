package com.example.jokenpo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void stoneOption(View view) {
        this.optionSelected("Stone");
    }
    public void paperOption(View view) {
        this.optionSelected("Paper");
    }
    public void scissorOption(View view) {
        this.optionSelected("Scissor");
    }

    public void optionSelected(String optionByPlayer){
        ImageView resultImage = findViewById(R.id.mainImage);
        int number = new Random().nextInt(3);
        String[] options = {"Stone", "Paper", "Scissor"};
        String optionByMachine = options[number];

        switch (optionByMachine){
            case "Stone":
                resultImage.setImageResource(R.drawable.pedra);
                break;
            case "Paper":
                resultImage.setImageResource(R.drawable.papel);
                break;
            case "Scissor":
                resultImage.setImageResource(R.drawable.tesoura);
                break;
        }
        System.out.println("Item Selected = " + optionByPlayer);
        System.out.println("Item Selected Randomly = " + optionByMachine);

        if(
                optionByMachine == "Scissor" && optionByPlayer == "Paper" ||
                optionByMachine == "Paper" && optionByPlayer == "Stone" ||
                optionByMachine == "Stone" && optionByPlayer == "Scissor"
        ){
            System.out.println("The app wins");
        }
        else if (
                optionByPlayer == "Scissor" && optionByMachine == "Paper" ||
                optionByPlayer == "Paper" && optionByMachine == "Stone" ||
                optionByPlayer == "Stone" && optionByMachine == "Scissor"
        ){ // jogador ganha
            System.out.println("The Player wins");
        }
        else{ // empate
            System.out.println("Draw Game");
        }






    }

}