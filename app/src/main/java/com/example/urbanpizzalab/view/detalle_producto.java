package com.example.urbanpizzalab.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.controller.ProductoController;
import com.example.urbanpizzalab.model.CarritoGlobal;
import com.example.urbanpizzalab.model.ItemCarrito;
import com.example.urbanpizzalab.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class detalle_producto extends AppCompatActivity {

    TextView Titulo, Precio, Descripcion, Cantidad;
    ImageView ImagenProd, ReducirProd, AumentarProd;
    Spinner Tamanio;

    Button AgregarCarrito;

    private ProductoController pc;
    private int baseIdProd; // ID base recibido por intent
    private List<String> listaTamanos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detalle_producto);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navbarContainer, new NavbarFragment())
                .commit();
        Tamanio = findViewById(R.id.spn_tamanio);
        Titulo = findViewById(R.id.titletxt);
        Precio = findViewById(R.id.textView12);
        Descripcion = findViewById(R.id.textdescripcion);
        ImagenProd = findViewById(R.id.imageView9);
        ReducirProd = findViewById(R.id.imageView10);
        AumentarProd = findViewById(R.id.imageView11);
        Cantidad = findViewById(R.id.textView13);
        AgregarCarrito = findViewById(R.id.btnAñadirCarrito);

        baseIdProd = getIntent().getIntExtra("idProducto", 0);
        pc = new ProductoController(this);

        cargarProducto(baseIdProd); // Llama al mtodo para mostrar datos

        Cantidad.setText("1");

        // Evento para aumentar
        AumentarProd.setOnClickListener(v -> {
            int cantidadActual = Integer.parseInt(Cantidad.getText().toString());
            cantidadActual++;
            Cantidad.setText(String.valueOf(cantidadActual));
        });

        // Evento para reducir
        ReducirProd.setOnClickListener(v -> {
            int cantidadActual = Integer.parseInt(Cantidad.getText().toString());
            if (cantidadActual > 1) {
                cantidadActual--;
                Cantidad.setText(String.valueOf(cantidadActual));
            }
        });

        AgregarCarrito.setOnClickListener(v -> {
            int cantidadSeleccionada = Integer.parseInt(Cantidad.getText().toString());
            Producto productoActual = pc.MostrarProductosID(baseIdProd);

            ItemCarrito nuevoItem = new ItemCarrito(productoActual, cantidadSeleccionada);
            CarritoGlobal.agregarProducto(nuevoItem);

            Intent intent = new Intent(detalle_producto.this, carrito.class);
            startActivity(intent);
        });
    }

    private void cargarProducto(int idProducto) {
        Producto producto = pc.MostrarProductosID(idProducto);

        if (producto == null) return;

        double hola = producto.getPrecio();
        // Mostrar info del producto
        Titulo.setText(producto.getNombre());
        Precio.setText("S/. " + producto.getPrecio());
        Descripcion.setText(producto.getDescripcion());

        // Convertir imagen desde base64
        Bitmap bitmap = base64ToBitmap(producto.getImagenURL());
        if (bitmap != null) {
            ImagenProd.setImageBitmap(bitmap);
        }

        // Obtener y configurar el spinner solo la primera vez
        listaTamanos = pc.MostrarTamano(idProducto);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaTamanos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Tamanio.setAdapter(adapter);

        // Seleccionar el tamaño actual del producto
        String tamActual = producto.getTamaño();
        int posicion = listaTamanos.indexOf(tamActual);
        if (posicion != -1) {
            Tamanio.setSelection(posicion);
        }

        // Listener para cambios en el tamaño
        Tamanio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstTime = true; // Para evitar que se dispare al iniciar

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstTime) {
                    firstTime = false;
                    return;
                }

                String tamañoSeleccionado = listaTamanos.get(position);
                int nuevoId = baseIdProd; // Default

                if (tamañoSeleccionado.equalsIgnoreCase("Mediana")) {
                    nuevoId = baseIdProd + 1;
                } else if (tamañoSeleccionado.equalsIgnoreCase("Grande")) {
                    nuevoId = baseIdProd + 2;
                } else if (tamañoSeleccionado.equalsIgnoreCase("Pequeña")) {
                    nuevoId = baseIdProd;
                }

                cargarProducto(nuevoId); // Recargar datos con el nuevo ID
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private Bitmap base64ToBitmap(String base64Str) {
        try {
            byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

