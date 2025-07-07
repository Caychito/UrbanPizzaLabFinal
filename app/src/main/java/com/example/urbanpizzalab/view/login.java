package com.example.urbanpizzalab.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.controller.UsuarioController;

public class login extends AppCompatActivity {
    EditText Lemail, Lpass;
    Button login;
    TextView regis, recuperarcontra;
    UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Lemail = findViewById(R.id.txt_email_login);
        Lpass = findViewById(R.id.txt_contraseña_login);
        login = findViewById(R.id.btn_login);
        recuperarcontra = findViewById(R.id.btn_recuperarcontra);
        regis = findViewById(R.id.text_button_Registrate_login);

        // Inicializar controlador
        usuarioController = new UsuarioController(this);
        // Acción al presionar "Iniciar Sesión"
        login.setOnClickListener(v -> {
            String email = Lemail.getText().toString().trim();
            String pass = Lpass.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean valido = usuarioController.validarLogin(email, pass);

            if (valido) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cierra el Login

            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        // Acción para ir a la pantalla de registro
        regis.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, registro.class);
            startActivity(intent);
        });

        // Acción para ir a recuperar contraseña
        recuperarcontra.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, recuperarcontra.class);
            startActivity(intent);
        });

    }
}
