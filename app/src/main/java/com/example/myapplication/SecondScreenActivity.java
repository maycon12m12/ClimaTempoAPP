package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.journeyapps.barcodescanner.ScanOptions;
import com.journeyapps.barcodescanner.ScanContract;

public class SecondScreenActivity extends AppCompatActivity {

    private ActivityResultLauncher<ScanOptions> qrCodeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen); // Certifique-se de que o nome do layout está correto

        // Encontrando as views
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        FloatingActionButton fabScanQrCode = findViewById(R.id.fabScanQrCode);

        // Configurando o ViewPager com um Adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Associando TabLayout com ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Aba 1");
            } else if (position == 1) {
                tab.setText("Aba 2");
            }
        }).attach();

        // Configurar o QR Code Scanner
        qrCodeLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                // Cidade obtida do QR Code
                String city = result.getContents();
                Toast.makeText(this, "Cidade selecionada: " + city, Toast.LENGTH_SHORT).show();
                // Passar a cidade para o FirstFragment ou tratar como necessário
                sendCityToFragment(city);
            }
        });

        // Configurar o clique do FloatingActionButton para escanear o QR Code
        fabScanQrCode.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Escaneie o QR Code da cidade");
            options.setBeepEnabled(true);
            options.setBarcodeImageEnabled(true);
            qrCodeLauncher.launch(options);
        });
    }

    private void sendCityToFragment(String city) {
        // Aqui você pode implementar a lógica para enviar a cidade ao FirstFragment
        // Isso pode ser feito via SharedViewModel, SharedPreferences, ou outros meios de comunicação entre Activity e Fragment
        // Por enquanto, apenas uma mensagem de log
        Log.d("SecondScreenActivity", "Cidade recebida: " + city);
    }
}
