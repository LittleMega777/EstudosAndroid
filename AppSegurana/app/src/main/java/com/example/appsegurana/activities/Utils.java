package com.example.appsegurana.activities;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void makeToast(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }
}
