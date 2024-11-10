package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherData> weatherDataList;

    public WeatherAdapter(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherData weatherData = weatherDataList.get(position);
        holder.bind(weatherData);
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {

        private final TextView dayTextView;
        private final TextView temperatureTextView;
        private final TextView conditionTextView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            conditionTextView = itemView.findViewById(R.id.conditionTextView);
        }

        public void bind(WeatherData weatherData) {
            dayTextView.setText(weatherData.getDay());
            temperatureTextView.setText(weatherData.getTemperature());
            conditionTextView.setText(weatherData.getCondition());
        }
    }
}
