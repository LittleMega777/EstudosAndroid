package com.example.toast;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button toastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toastButton = findViewById(R.id.toastButton);
    }

    public void openDialog(View view){
        // Instanciar o alert dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        // configurando titulo e msg
        dialog.setTitle("Confirmacao");
        dialog.setMessage("Voce tem certeza do toast ?");
        // configurando acoes para sim e nao
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendToast(view);
            }
        });
        dialog.setNegativeButton("Nao", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        // cria com as config e mostra
        dialog.create();
        dialog.show();
    }

    public void sendToast(View view){
        Toast.makeText(getApplicationContext(), "Feito com Sucesso", Toast.LENGTH_LONG).show();

    }
}