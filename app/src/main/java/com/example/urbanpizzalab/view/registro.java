package com.example.urbanpizzalab.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.controller.UsuarioController;
import com.example.urbanpizzalab.model.Usuario;

import java.util.ArrayList;

public class registro extends AppCompatActivity {

    UsuarioController usuarioController;
    EditText nomR, apeR, dnoR, emailR, passR;
    Spinner discR;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nomR = findViewById(R.id.txt_nombres_register);
        apeR = findViewById(R.id.txt_apellidos_register);
        dnoR = findViewById(R.id.txt_dni);
        emailR = findViewById(R.id.txt_email_register);
        passR = findViewById(R.id.txt_contraseña_register);

        btnRegistrar = findViewById(R.id.btn_register);

        usuarioController = new UsuarioController(this);

        // Lógica de registro
        btnRegistrar.setOnClickListener(v -> {
            String nombre = nomR.getText().toString().trim();
            String apellido = apeR.getText().toString().trim();
            String dniStr = dnoR.getText().toString().trim();
            String email = emailR.getText().toString().trim();
            String password = passR.getText().toString().trim();

            // Validación básica
            if (nombre.isEmpty() || apellido.isEmpty() || dniStr.isEmpty() ||
                    email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int dni;
            try {
                dni = Integer.parseInt(dniStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "DNI inválido", Toast.LENGTH_SHORT).show();
                return;
            }


            // Crear objeto usuario (el ID es 0 ya que es autogenerado)
            Usuario nuevoUsuario = new Usuario(0, nombre, apellido, email, password, dni);

            // Insertar en BD
            boolean success = usuarioController.insertarUsuario(nuevoUsuario);
            if (success) {
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                // Mostrar diálogo de confirmación
                new AlertDialog.Builder(this)
                        .setTitle("Registro exitoso")
                        .setMessage("¿Deseas iniciar sesión ahora?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            Intent intent = new Intent(registro.this, login.class);
                            startActivity(intent);
                            finish(); // Cierra Register
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            Intent intent = new Intent(registro.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Cierra Register
                        })
                        .setCancelable(false)
                        .show();
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Limpiar campos luego de registrar
    private void limpiarCampos() {
        nomR.setText("");
        apeR.setText("");
        dnoR.setText("");
        emailR.setText("");
        passR.setText("");
        discR.setSelection(0);
    }

}
