package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout manualmente
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Log para verificar se o método está sendo chamado corretamente
        Log.d(TAG, "onViewCreated chamado - Verificando o layout e o botão");

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        if (recyclerView == null) {
            Log.e(TAG, "RecyclerView não encontrado! Certifique-se de que o ID esteja correto.");
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            weatherDataList = new ArrayList<>();
            weatherAdapter = new WeatherAdapter(weatherDataList);
            recyclerView.setAdapter(weatherAdapter);

            // Adicionar dados estáticos para teste
            weatherDataList.add(new WeatherData("Monday", "20°C", "Clear Sky"));
            weatherDataList.add(new WeatherData("Tuesday", "22°C", "Cloudy"));
            weatherAdapter.notifyDataSetChanged();
        }

        // Carregar dados da API
        loadWeatherData("Toledo, PR");

        // Configurar o FloatingActionButton para ler o QR Code
        FloatingActionButton fab = view.findViewById(R.id.fabScanQrCode);
        if (fab == null) {
            Log.e(TAG, "FloatingActionButton não encontrado! Certifique-se de que o ID esteja correto no layout.");
        } else {
            Log.d(TAG, "FloatingActionButton encontrado, adicionando listener.");
            fab.setOnClickListener(v -> {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(FirstFragment.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan QR Code to change the city");
                integrator.setCameraId(0); // Use a câmera traseira
                integrator.setBeepEnabled(true);
                integrator.initiateScan();
            });
        }
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
                } else {
                    Log.e(TAG, "onResponse: Falha ao obter dados da API");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                // Quando um QR Code é lido, use o conteúdo para carregar os dados do tempo
                String scannedCity = result.getContents();
                loadWeatherData(scannedCity);
                Toast.makeText(getContext(), "Cidade alterada para: " + scannedCity, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Leitura do QR Code cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
