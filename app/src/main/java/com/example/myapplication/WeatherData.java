package com.example.myapplication;

public class WeatherData {
    private String day;
    private String temperature;
    private String condition;

    public WeatherData(String day, String temperature, String condition) {
        this.day = day;
        this.temperature = temperature;
        this.condition = condition;
    }

    public String getDay() {
        return day;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }
}
