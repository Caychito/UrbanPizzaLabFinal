package com.example.urbanpizzalab.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.adapter.CarritoAdapter;
import com.example.urbanpizzalab.adapter.ProductoAdapter;
import com.example.urbanpizzalab.controller.ProductoController;
import com.example.urbanpizzalab.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class categoria extends AppCompatActivity {

    private RecyclerView recyclerProducto;
    private ProductoAdapter productoAdapter;
    private ProductoController productoController;
    private CardView CV_PRODUCTO;
    int idcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerProducto = findViewById(R.id.rcl_Productos);
        CV_PRODUCTO = findViewById(R.id.cv_producto);
        productoController = new ProductoController(this);

        idcat = getIntent().getIntExtra("idCategoria", 0);

        List<Producto> ListaProducto = productoController.MostrarProductos(idcat);
        productoAdapter = new ProductoAdapter(this,ListaProducto);
        recyclerProducto.setLayoutManager(new LinearLayoutManager(this));
        recyclerProducto.setAdapter(productoAdapter);


    }
}