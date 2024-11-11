package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class SecondScreenActivity extends AppCompatActivity {

    private ActivityResultLauncher<ScanOptions> qrCodeLauncher;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        // Inicializando o SharedViewModel para compartilhar dados entre atividades e fragments
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Encontrando as views
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Configurando o ViewPager com um Adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Associando TabLayout com ViewPager e definindo títulos das abas
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Previsão");
                    break;
                case 1:
                    tab.setText("Mapa");
                    break;
                case 2:
                    tab.setIcon(R.drawable.menu_3); // Usa o VectorDrawable preto para o ícone de três pontinhos
                    if (tab.getIcon() != null) {
                        tab.getIcon().setTint(ContextCompat.getColor(this, android.R.color.black)); // Define o ícone como preto
                    }
                    break;
            }
        }).attach();



        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, android.R.color.black),         // Cor das abas inativas
                ContextCompat.getColor(this, android.R.color.black)          // Cor da aba ativa
        );
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, android.R.color.black)); // Cor do indicador

        qrCodeLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                // Cidade obtida do QR Code
                String city = result.getContents();
                Toast.makeText(this, "Cidade selecionada: " + city, Toast.LENGTH_SHORT).show();
                // Atualizar a cidade no SharedViewModel
                sharedViewModel.setCity(city);
            } else {
                Toast.makeText(this, "Falha ao escanear o QR Code ou leitura cancelada", Toast.LENGTH_SHORT).show();
            }
        });

        // Pode ser adicionado um exemplo de chamada ao QR Code aqui, caso necessário para teste:
        // startQrCodeScanner(); // Lembre-se de remover em produção.
    }

    // Método auxiliar para iniciar a leitura do QR Code
    private void startQrCodeScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Escaneie o QR Code da cidade");
        options.setBeepEnabled(true);
        options.setBarcodeImageEnabled(true);
        qrCodeLauncher.launch(options);
    }
}
