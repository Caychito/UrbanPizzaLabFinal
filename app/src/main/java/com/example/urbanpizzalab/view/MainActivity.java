package com.example.urbanpizzalab.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.adapter.BusquedaProductoAdapter;
import com.example.urbanpizzalab.controller.ProductoController;
import com.example.urbanpizzalab.database.bdUrban;
import com.example.urbanpizzalab.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button Inicio;

    CardView pizzas, bebidas, platos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bebidas = findViewById(R.id.cv_bebidas);
        pizzas = findViewById(R.id.cv_pizzas);
        platos = findViewById(R.id.cv_platos);

        bebidas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, categoria.class);
            intent.putExtra("idCategoria", 3);
            startActivity(intent);
        });

        pizzas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, categoria.class);
            intent.putExtra("idCategoria", 1);
            startActivity(intent);
        });

        platos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, categoria.class);
            intent.putExtra("idCategoria", 2);
            startActivity(intent);
        });


        AutoCompleteTextView txtBuscar = findViewById(R.id.txt_buscar);

        // 1. Traer productos de la BD
        ProductoController controller = new ProductoController(this);
        List<Producto> listaProductos = controller.ListarProductos(); // puedes ignorar el ID_Categoria aquí

        // 2. Crear adaptador personalizado
        BusquedaProductoAdapter adapter = new BusquedaProductoAdapter(this, listaProductos);

        // 3. Asignar adaptador al buscador
        txtBuscar.setAdapter(adapter);

        // 4. Opcional: manejar selección de un producto
        txtBuscar.setOnItemClickListener((parent, view, position, id) -> {
            Producto seleccionado = adapter.getItem(position);
            if (seleccionado != null) {
                txtBuscar.setText(seleccionado.getNombre());

                Intent intent = new Intent(MainActivity.this, detalle_producto.class);
                intent.putExtra("idProducto", seleccionado.getID_Producto());
                startActivity(intent);

            }
        });

    }
}