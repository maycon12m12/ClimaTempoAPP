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
                return new FirstFragment(); // Fragmento da aba 1 que exibe previsões do tempo
            case 1:
                return new MapFragment(); // Substitua pelo fragmento do mapa
            default:
                return new FirstFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Número de abas
    }
}
