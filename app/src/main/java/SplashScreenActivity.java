package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Espera por 3 segundos e navega para a SecondScreenActivity
        new Handler().postDelayed(() -> {
            try {
                Intent intent = new Intent(SplashScreenActivity.this, SecondScreenActivity.class);
                startActivity(intent);
                finish(); // Fecha a SplashScreenActivity para evitar que ela seja acessada com o botão "voltar"
            } catch (Exception e) {
                Log.e(TAG, "Erro ao iniciar a SecondScreenActivity: ", e);
            }
        }, 3000); // 3000 ms = 3 segundos
    }
}
