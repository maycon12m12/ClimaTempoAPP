package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> city = new MutableLiveData<>("Toledo, PR"); // Definindo um valor inicial padrão.

    // Metodo para definir a cidade
    public void setCity(String city) {
        this.city.setValue(city);
    }

    // Metodo para obter a cidade observável
    public LiveData<String> getCity() {
        return city;
    }
}
