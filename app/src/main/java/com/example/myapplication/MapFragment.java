package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate o layout para este fragmento
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        // Verificar se o GoogleMap foi inicializado corretamente
        if (googleMap != null) {
            // Defina a posição da cidade (exemplo com Toledo, PR)
            LatLng toledo = new LatLng(-24.7244, -53.7412);
            googleMap.addMarker(new MarkerOptions().position(toledo).title("Toledo, PR"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toledo, 10));
        } else {
            Toast.makeText(getContext(), "Erro ao inicializar o mapa.", Toast.LENGTH_SHORT).show();
        }
    }
}
