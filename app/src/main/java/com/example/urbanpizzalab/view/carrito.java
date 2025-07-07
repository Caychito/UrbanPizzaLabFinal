package com.example.urbanpizzalab.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.adapter.CarritoAdapter;
import com.example.urbanpizzalab.model.CarritoGlobal;
import com.example.urbanpizzalab.model.ItemCarrito;
import com.example.urbanpizzalab.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class carrito extends AppCompatActivity {

    private RecyclerView recyclerCarrito;
    private CarritoAdapter carritoAdapter;
    private TextView tvTotal;
    private Button btnPagar;
    private TextView btnBack;

    private List<ItemCarrito> listaCarrito = new ArrayList<>(); // Puedes cargarla desde SQLite o algo fijo para pruebas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrito);

        recyclerCarrito = findViewById(R.id.recyclerCarrito);
        tvTotal = findViewById(R.id.tvTotal);
        btnPagar = findViewById(R.id.btnPagar);
        btnBack = findViewById(R.id.btnBack);

        // 🔙 Botón de volver
        btnBack.setOnClickListener(v -> finish());

        // ⚡ Lista de prueba
        cargarItemsPrueba();

        // ⚡ Configurar Adapter
        listaCarrito = CarritoGlobal.listaCarrito;
        carritoAdapter = new CarritoAdapter(listaCarrito, this::calcularTotal);
        recyclerCarrito.setLayoutManager(new LinearLayoutManager(this));
        recyclerCarrito.setAdapter(carritoAdapter);

        calcularTotal();

        // 🟠 Botón de pagar
        btnPagar.setOnClickListener(v -> {
            // Aquí iría tu lógica para procesar el pago
            // Por ejemplo: enviar datos, vaciar carrito, mostrar mensaje, etc.
        });
    }

    private void calcularTotal() {
        double total = 0;
        for (ItemCarrito item : listaCarrito) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        tvTotal.setText("Total: S/ " + String.format("%.2f", total));
    }

    private void cargarItemsPrueba() {
        Producto pizza1 = new Producto(
                1,
                "Pizza Margarita",
                "Clásica con tomate y queso",
                18.0,
                20,
                "Mediana",
                "https://tuservidor.com/imagenes/margarita.jpg",
                1
        );

        Producto pizza2 = new Producto(
                2,
                "Pizza Pepperoni",
                "Con pepperoni extra",
                25.0,
                15,
                "Grande",
                "https://tuservidor.com/imagenes/pepperoni.jpg",
                1
        );

        listaCarrito.add(new ItemCarrito(pizza1, 1));
        listaCarrito.add(new ItemCarrito(pizza2, 2));
    }
}
