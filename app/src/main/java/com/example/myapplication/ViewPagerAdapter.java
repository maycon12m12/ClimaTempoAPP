package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FirstFragment(); // Fragmento que exibe a previsão do tempo
            case 1:
                return new MapFragment(); // Fragmento que exibe o mapa
            case 2:
                return new AboutFragment(); // Fragmento "Sobre" com as informações pessoais
            default:
                return new FirstFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Número de abas
    }
}
