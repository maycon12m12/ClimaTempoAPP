package com.example.myapplication;

import java.util.List;

public class WeatherResponse {
    private Results results;

    // Método para obter o objeto de resultados
    public Results getResults() {
        return results;
    }

    // Classe interna Results que armazena a previsão do tempo e outros dados
    public static class Results {
        private String temp;
        private String description;
        private String city_name;
        private List<Forecast> forecast;

        // Método getter para obter a previsão
        public List<Forecast> getForecast() {
            return forecast;
        }

        // Outros getters opcionais
        public String getTemp() {
            return temp;
        }

        public String getDescription() {
            return description;
        }

        public String getCityName() {
            return city_name;
        }

        // Classe interna Forecast que armazena os detalhes da previsão
        public static class Forecast {
            private String date;
            private String weekday;
            private int max;
            private int min;
            private String description;

            // Getters para os campos da previsão
            public String getDate() {
                return date;
            }

            public String getWeekday() {
                return weekday;
            }

            public int getMax() {
                return max;
            }

            public int getMin() {
                return min;
            }

            public String getDescription() {
                return description;
            }
        }
    }
}
