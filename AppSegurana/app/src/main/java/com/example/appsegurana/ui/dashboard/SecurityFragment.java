package com.example.appsegurana.ui.dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appsegurana.R;
import com.example.appsegurana.databinding.FragmentSecurityBinding;
import com.example.appsegurana.models.DBHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SecurityFragment extends Fragment {

    private FragmentSecurityBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private AlertDialog alertDialog;
    private boolean isAlarmOn = false;  // <- Controle do estado do alarme
    private Button btnAlarm;
    private Button btnSalvarUltimaLocalizacao;
    private Button btnGoodPratices;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security, container, false);

        btnAlarm = view.findViewById(R.id.btnAlarm);
        btnGoodPratices = view.findViewById(R.id.btnGoodPratices);
        btnSalvarUltimaLocalizacao = view.findViewById(R.id.btnGetLocation);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        dbHelper = new DBHelper(getContext());

        btnAlarm.setOnClickListener(v -> {
            if (!isAlarmOn) {
                startAlarm();
            } else {
                stopAlarm();
            }
        });

        btnSalvarUltimaLocalizacao.setOnClickListener(v -> {
            salvarUltimaLocalizacao();

        });

        btnGoodPratices.setOnClickListener(v -> openBoasPraticasPdf());

        return view;
    }

    private void openBoasPraticasPdf() {
        // Nome do ficheiro PDF como está em res/raw
        String pdfFileName = "boas_praticas_seguranca.pdf";
        File pdfFileInCacheDir = new File(requireContext().getCacheDir() + "/pdfs/" + pdfFileName);

        // Cria a subpasta 'pdfs' no diretório de cache se não existir
        File pdfsDir = new File(requireContext().getCacheDir(), "pdfs");
        if (!pdfsDir.exists()) {
            pdfsDir.mkdirs();
        }

        try {
            // Copia o ficheiro de res/raw para o diretório de cache
            InputStream inputStream = requireContext().getResources().openRawResource(R.raw.cartilha_boas_praticas);
            OutputStream outputStream = new FileOutputStream(pdfFileInCacheDir);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            // Obter o URI do ficheiro usando FileProvider
            // A autoridade DEVE corresponder à que você definiu no AndroidManifest.xml
            Uri pdfUri = FileProvider.getUriForFile(requireContext(),
                    requireContext().getPackageName() + ".fileprovider", // Ou a autoridade exata que você usou
                    pdfFileInCacheDir);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Concede permissão de leitura
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // Opcional: não mantém o visualizador de PDF no histórico de atividades

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "Nenhuma aplicação encontrada para abrir PDF.", Toast.LENGTH_LONG).show();
                Log.e("SecurityFragment", "Error opening PDF: No PDF viewer found", e);
            }

        } catch (IOException e) {
            Toast.makeText(getContext(), "Erro ao abrir o ficheiro PDF.", Toast.LENGTH_LONG).show();
            Log.e("SecurityFragment", "Error copying or opening PDF", e);
        }
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

    private void salvarUltimaLocalizacao() {
        // 1. Verificar permissões
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // 2. Pedir permissões se não concedidas
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // 3. Obter localização (se permissão concedida)
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // 4. Salvar no banco de dados
                            boolean sucesso = dbHelper.saveLocation(latitude, longitude);
                            if (sucesso) {
                                Toast.makeText(getContext(), "Localização salva: Lat: " + latitude + ", Lon: " + longitude, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Erro ao salvar localização.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Não foi possível obter a localização. Verifique se o GPS está ativo.", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(requireActivity(), e -> {
                    Toast.makeText(getContext(), "Erro ao obter localização: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    // 5. Lidar com o resultado do pedido de permissão
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, tentar salvar novamente
                salvarUltimaLocalizacao();
            } else {
                Toast.makeText(getContext(), "Permissão de localização negada.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}