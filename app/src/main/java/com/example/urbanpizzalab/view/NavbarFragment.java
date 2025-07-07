package com.example.urbanpizzalab.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urbanpizzalab.R;

public class NavbarFragment extends Fragment {
    public NavbarFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_navbar, container, false);

        ImageButton btnInicio = view.findViewById(R.id.btnInicio);
        ImageButton btnPerfil = view.findViewById(R.id.btnPerfil);
        View fabCarrito = view.findViewById(R.id.fabCarrito);

        btnInicio.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
        });

        fabCarrito.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), carrito.class);
            startActivity(intent);
        });

        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), perfil.class);
            startActivity(intent);
        });

        return view;
    }

}
