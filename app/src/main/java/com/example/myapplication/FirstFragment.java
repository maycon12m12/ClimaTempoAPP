package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstFragment extends Fragment {

    private static final String TAG = "FirstFragment";
    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private List<WeatherData> weatherDataList;
    private String apiKey = "b60daae2"; // Chave da API para consultar o clima.
    private SharedViewModel sharedViewModel;
    private TextView textViewCityName;

    // Registrando o launcher para ler o QR Code
    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    // Atualiza a cidade no ViewModel
                    String scannedCity = result.getContents();
                    sharedViewModel.setCity(scannedCity);
                    Toast.makeText(getContext(), "Cidade alterada para: " + scannedCity, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Leitura do QR Code cancelada", Toast.LENGTH_SHORT).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar o ViewModel compartilhado
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Inicializar o TextView do nome da cidade
        textViewCityName = view.findViewById(R.id.textViewCityName);

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        weatherDataList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(weatherDataList);
        recyclerView.setAdapter(weatherAdapter);

        // Observar mudanças na cidade
        sharedViewModel.getCity().observe(getViewLifecycleOwner(), city -> {
            if (city != null && !city.isEmpty()) {
                Log.d(TAG, "Cidade observada: " + city);

                // Atualizar o nome da cidade no TextView
                textViewCityName.setText("Cidade: " + city);

                // Carregar os dados do clima para a cidade observada
                loadWeatherData(city);
            } else {
                // Caso a cidade não esteja definida, definir uma cidade padrão
                String defaultCity = "Toledo, PR";
                sharedViewModel.setCity(defaultCity);
                Log.d(TAG, "Cidade padrão definida: " + defaultCity);
            }
        });

        // Configurar o FloatingActionButton para ler o QR Code
        FloatingActionButton fab = view.findViewById(R.id.fabScanQrCode);
        fab.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Escaneie o QR Code da cidade");
            options.setBeepEnabled(true);
            options.setBarcodeImageEnabled(true);
            qrCodeLauncher.launch(options);
        });
    }

    private void loadWeatherData(String city) {
        // Inicializando Retrofit com a base da API HG Brasil
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.hgbrasil.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService apiService = retrofit.create(WeatherApiService.class);

        // Fazendo a chamada da API com Retrofit
        Call<WeatherResponse> call = apiService.getWeatherForecast(apiKey, city);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherDataList.clear();
                    for (WeatherResponse.Results.Forecast forecast : response.body().getResults().getForecast()) {
                        weatherDataList.add(new WeatherData(
                                forecast.getDate() + " (" + forecast.getWeekday() + ")",
                                forecast.getMax() + "°C / " + forecast.getMin() + "°C",
                                forecast.getDescription()
                        ));
                    }
                    weatherAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Dados de previsão do tempo carregados com sucesso para: " + city);
                } else {
                    Log.e(TAG, "onResponse: Falha ao obter dados da API - Código de resposta: " + response.code());
                    Toast.makeText(getContext(), "Falha ao obter dados da API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: Erro ao chamar a API", t);
                Toast.makeText(getContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
