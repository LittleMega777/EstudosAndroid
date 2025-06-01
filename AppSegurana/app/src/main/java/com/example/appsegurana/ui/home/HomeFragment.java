package com.example.appsegurana.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appsegurana.R;

import java.io.File;

public class HomeFragment extends Fragment {

    // Declarando os TextViews
    private TextView txtDeviceModel, txtManufacturer, txtAndroidVersion, txtDeviceId, txtStorage, txtBattery, txtNetwork;

    public HomeFragment() {
        // Construtor vazio obrigatório
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflando o layout do fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vinculando os TextViews
        txtDeviceModel = view.findViewById(R.id.txtDeviceModel);
        txtManufacturer = view.findViewById(R.id.txtManufacturer);
        txtAndroidVersion = view.findViewById(R.id.txtAndroidVersion);
        txtDeviceId = view.findViewById(R.id.txtDeviceId);
        txtStorage = view.findViewById(R.id.txtStorage);
        txtBattery = view.findViewById(R.id.txtBattery);
        txtNetwork = view.findViewById(R.id.txtNetwork);

        // Carregando as informações
        carregarDados();
    }

    @SuppressLint("SetTextI18n")
    private void carregarDados() {
        Context context = requireContext();

        // Modelo e Fabricante
        txtDeviceModel.setText("\uD83D\uDCF1 Modelo: " + Build.MODEL);
        txtManufacturer.setText("\t\uD83C\uDFED Fabricante: " + Build.MANUFACTURER);

        // Versão Android
        txtAndroidVersion.setText("\t\uD83E\uDD16 Versão do Android: " + Build.VERSION.RELEASE);

        // Android ID
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        txtDeviceId.setText("\t\uD83C\uDD94 Android ID: " + androidId);

        // Armazenamento
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long availableBlocks = stat.getAvailableBlocksLong();

        long total = (blockSize * totalBlocks) / (1024 * 1024); // em MB
        long free = (blockSize * availableBlocks) / (1024 * 1024); // em MB

        txtStorage.setText(" \t\uD83D\uDCBE Armazenamento: " + free + "MB livre de " + total + "MB");

        // Bateria
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        int batteryPct = (int) ((level / (float) scale) * 100);
        txtBattery.setText("\t\uD83D\uDD0B Bateria: " + batteryPct + "%");

        // Conexão
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        String status;
        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wi-Fi";
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Dados móveis";
            } else {
                status = "Outra conexão";
            }
        } else {
            status = "Sem conexão";
        }

        txtNetwork.setText("\uD83D\uDCF6 Conexão: " + status);
    }
}
