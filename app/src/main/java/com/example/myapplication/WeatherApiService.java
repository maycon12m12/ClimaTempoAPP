package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather") // Verifique se o endpoint está correto.
    Call<WeatherResponse> getWeatherForecast(
            @Query("key") String apiKey,  // API key para autorização
            @Query("city_name") String city // Confirme que o nome do parâmetro corresponde ao que a API espera
    );
}

