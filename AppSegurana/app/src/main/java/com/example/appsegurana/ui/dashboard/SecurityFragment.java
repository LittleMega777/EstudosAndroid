package com.example.appsegurana.ui.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appsegurana.R;
import com.example.appsegurana.databinding.FragmentSecurityBinding;

public class SecurityFragment extends Fragment {

    private FragmentSecurityBinding binding;

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private AlertDialog alertDialog;
    private boolean isAlarmOn = false;  // <- Controle do estado do alarme
    private Button btnAlarm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security, container, false);

        btnAlarm = view.findViewById(R.id.btnAlarm);

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        btnAlarm.setOnClickListener(v -> {
            if (!isAlarmOn) {
                startAlarm();
            } else {
                stopAlarm();
            }
        });

        return view;
    }

    private void startAlarm() {
        isAlarmOn = true;
        btnAlarm.setText("🛑 Parar Alarme");  // Troca texto do botão

        // Som
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarme);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // Vibração
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(
                    new long[]{0, 500, 1000}, 0));
        } else {
            vibrator.vibrate(new long[]{0, 500, 1000}, 0);
        }

        // AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("🚨 Atenção!")
                .setMessage("Movimentação suspeita detectada.\nA polícia foi acionada!\nPor favor, permaneça no local.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void stopAlarm() {
        isAlarmOn = false;
        btnAlarm.setText("🚨 Acionar Alarme");  // Troca texto do botão de volta

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        vibrator.cancel();

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}