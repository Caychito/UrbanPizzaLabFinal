package com.example.urbanpizzalab.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.controller.UsuarioController;

public class recuperarcontra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperarcontra);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText txtDni = findViewById(R.id.txt_dni_rec_contra);
        EditText txtNuevaContra = findViewById(R.id.txt_nueva_contra);
        Button btnRecuperar = findViewById(R.id.btn_rec_contra);

        btnRecuperar.setOnClickListener(v -> {
            String dni = txtDni.getText().toString().trim();
            String nuevaContra = txtNuevaContra.getText().toString().trim();

            if (dni.isEmpty() || nuevaContra.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            UsuarioController controller = new UsuarioController(this);
            boolean exito = controller.actualizarContrasenia(dni, nuevaContra);

            if (exito) {
                Toast.makeText(this, "Contraseña actualizada con éxito", Toast.LENGTH_SHORT).show();
                finish(); // o redirige al login
            } else {
                Toast.makeText(this, "DNI no encontrado", Toast.LENGTH_SHORT).show();
            }
        });

    }
}