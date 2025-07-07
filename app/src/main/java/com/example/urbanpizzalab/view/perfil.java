package com.example.urbanpizzalab.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.controller.UsuarioController;
import com.example.urbanpizzalab.model.CarritoGlobal;
import com.example.urbanpizzalab.model.ItemCarrito;
import com.example.urbanpizzalab.model.Producto;
import com.example.urbanpizzalab.model.Usuario;

public class perfil extends AppCompatActivity {

    String Email;
    EditText Nombre, Apellido, E_mail, DNI;
    UsuarioController uc;
    Usuario usuario;
    Button cambcont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navbarContainer, new NavbarFragment())
                .commit();

        Email = getIntent().getStringExtra("Email");

        Nombre = findViewById(R.id.etNombre);
        Apellido = findViewById(R.id.etApellido);
        E_mail = findViewById(R.id.etEmail);
        DNI = findViewById(R.id.etDNI);
        cambcont =  findViewById(R.id.btnCambiarContrasenia);

        uc  = new UsuarioController(this);
        usuario = uc.obtenerUsuarioPorEmail(Email);

        Nombre.setText(usuario.getNombre());
        Apellido.setText(usuario.getApellido());
        E_mail.setText(usuario.getEmail());
        DNI.setText(String.valueOf(usuario.getDNI()));

        cambcont.setOnClickListener(v -> {
            Intent intent = new Intent(perfil.this, recuperarcontra.class);
            startActivity(intent);
        });

    }


}