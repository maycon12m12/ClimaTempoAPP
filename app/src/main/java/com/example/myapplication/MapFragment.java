package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate o layout para este fragmento
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar o ViewModel compartilhado
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Obtendo o fragmento do mapa para inicializá-lo
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getContext(), "Erro ao carregar o mapa. Verifique o fragmento do mapa.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Observar mudanças na cidade
        sharedViewModel.getCity().observe(getViewLifecycleOwner(), city -> {
            if (googleMap != null) {
                // Atualiza a posição no mapa para a nova cidade
                updateMapLocation(city);
            }
        });

        // Inicialmente, defina a posição para Toledo, PR
        updateMapLocation("Toledo, PR");
    }

    private void updateMapLocation(String city) {
        LatLng location;
        switch (city.toLowerCase()) {
            case "formosa, pr":
            case "formosa do oeste, pr":
                location = new LatLng(-24.3022, -53.3113); // Coordenadas de Formosa do Oeste, PR
                break;
            case "toledo, pr":
                location = new LatLng(-24.7199, -53.7433); // Coordenadas de Toledo, PR
                break;
            default:
                // Caso não encontre a cidade, defina um valor padrão
                Toast.makeText(getContext(), "Cidade não encontrada. Exibindo Toledo, PR por padrão.", Toast.LENGTH_SHORT).show();
                location = new LatLng(-24.7244, -53.7412);
                break;
        }

        if (googleMap != null) {
            googleMap.clear(); // Remove os marcadores antigos
            googleMap.addMarker(new MarkerOptions().position(location).title(city));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        }
    }
}
