package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class SecondScreenActivity extends AppCompatActivity {

    private ActivityResultLauncher<ScanOptions> qrCodeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        // Encontrando as views
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Configurando o ViewPager com um Adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Associando TabLayout com ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Previsão");
                    break;
                case 1:
                    tab.setText("Mapa");
                    break;
                case 2:
                    tab.setText("Sobre");
                    break;
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
    }

    private void sendCityToFragment(String city) {
        // Aqui você pode implementar a lógica para enviar a cidade ao FirstFragment
        // Isso pode ser feito via SharedViewModel, SharedPreferences, ou outros meios de comunicação entre Activity e Fragment
        // Por enquanto, apenas uma mensagem de log
        Log.d("SecondScreenActivity", "Cidade recebida: " + city);
    }
}
