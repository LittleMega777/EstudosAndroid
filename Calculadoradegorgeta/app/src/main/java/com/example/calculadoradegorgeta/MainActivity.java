package com.example.calculadoradegorgeta;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textPercent;
    private TextView textTotalTip;
    private TextView textTotalBill;
    private EditText editTextValue;
    private SeekBar  percentSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPercent = findViewById(R.id.textPercent);
        textTotalTip = findViewById(R.id.textTotalTip);
        textTotalBill = findViewById(R.id.textTotalBill);
        editTextValue = findViewById(R.id.editTextValue);
        percentSeekBar = findViewById(R.id.percentSeekBar);
        seekBarListener();

    }

    public void seekBarListener(){
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String textValue = getValueEditText(editTextValue);
                float decimalValue = Float.parseFloat(textValue);
                // int percent  = getValueSeekBar(percentSeekBar);

                Float totalTip = tipCalculator(progress, decimalValue);

                System.out.println("Total Tip:" + totalTip);
                textPercent.setText(progress + "%");

                textTotalTip.setText(String.format("R$%.2f" ,totalTip ));
                textTotalBill.setText(String.format("R$%.2f" ,totalTip + decimalValue ));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public Float tipCalculator(int percent, float value){
        return value / 100 * percent;
    }

    public String getValueEditText(EditText editText){
        return editText.getText().toString();
    }

    public int getValueSeekBar(SeekBar seekbar){
        return seekbar.getProgress();
    }
}