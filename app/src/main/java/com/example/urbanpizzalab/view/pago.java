package com.example.urbanpizzalab.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.adapter.ResumenPedidoAdapter;
import com.example.urbanpizzalab.controller.PedidoController;
import com.example.urbanpizzalab.model.CarritoGlobal;
import com.example.urbanpizzalab.model.DetallePedido;
import com.example.urbanpizzalab.model.ItemCarrito;
import com.example.urbanpizzalab.model.Pedido;
import com.example.urbanpizzalab.model.Producto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class pago extends AppCompatActivity {

    private RecyclerView recyclerResumen;
    private Spinner spinnerMetodoPago;
    private TextView txtTotal;
    private Button btnConfirmarPago;

    private PedidoController pedidoController;
    private List<ItemCarrito> listaResumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pago);

        recyclerResumen = findViewById(R.id.recyclerResumen);
        spinnerMetodoPago = findViewById(R.id.spinnerMetodoPago);
        txtTotal = findViewById(R.id.txtTotal);
        btnConfirmarPago = findViewById(R.id.btnConfirmarPago);

        pedidoController = new PedidoController(this);
        listaResumen = CarritoGlobal.listaCarrito;

        // Configurar RecyclerView con el adaptador
        recyclerResumen.setLayoutManager(new LinearLayoutManager(this));
        recyclerResumen.setAdapter(new ResumenPedidoAdapter(this, listaResumen));

        cargarSpinnerMetodosPago();
        calcularTotal();

        btnConfirmarPago.setOnClickListener(v -> procesarPedido());
    }

    private void cargarSpinnerMetodosPago() {
        List<String> metodos = pedidoController.obtenerMetodosPago();

        if (metodos.isEmpty()) {
            // Insertamos por defecto algunos si no hay
            pedidoController.insertarMetodoPago("Efectivo");
            pedidoController.insertarMetodoPago("Yape");
            pedidoController.insertarMetodoPago("Plin");
            metodos = pedidoController.obtenerMetodosPago();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, metodos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMetodoPago.setAdapter(adapter);
    }

    private void calcularTotal() {
        double total = 0;
        for (ItemCarrito item : listaResumen) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        txtTotal.setText(String.format(Locale.getDefault(), "Total: S/ %.2f", total));
    }

    private void procesarPedido() {
        if (listaResumen.isEmpty()) {
            Toast.makeText(this, "Tu carrito está vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulamos ID de usuario fijo
        int idUsuario = 1;

        String metodoSeleccionado = spinnerMetodoPago.getSelectedItem().toString();
        int idMetodoPago = obtenerIDMetodoPago(metodoSeleccionado);

        String fechaActual = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Pedido nuevoPedido = new Pedido(idUsuario, fechaActual, "Pendiente", idMetodoPago);

        long idPedidoInsertado = pedidoController.insertarPedido(nuevoPedido);
        if (idPedidoInsertado == -1) {
            Toast.makeText(this, "Error al registrar pedido", Toast.LENGTH_SHORT).show();
            return;
        }

        for (ItemCarrito item : listaResumen) {
            Producto producto = item.getProducto();
            int cantidad = item.getCantidad();
            double total = cantidad * producto.getPrecio();

            DetallePedido detalle = new DetallePedido(
                    producto.getID_Producto(),
                    cantidad,
                    total,
                    producto.getImagenURL(),
                    producto.getID_Categoria()
            );
            detalle.setID_Pedido((int) idPedidoInsertado);
            pedidoController.insertarDetallePedido(detalle);
        }

        // Limpiamos carrito global
        CarritoGlobal.vaciar();
        Toast.makeText(this, "¡Pedido registrado con éxito!", Toast.LENGTH_LONG).show();


        Intent intent = new Intent(pago.this, MainActivity.class);
        startActivity(intent); // Volvemos o puedes redirigir a otra pantalla si prefieres
        finish();
    }

    private int obtenerIDMetodoPago(String nombreMetodo) {
        List<String> metodos = pedidoController.obtenerMetodosPago();
        for (int i = 0; i < metodos.size(); i++) {
            if (metodos.get(i).equalsIgnoreCase(nombreMetodo)) {
                return i + 1; // IDs son autoincrementales desde 1
            }
        }
        return 1; // Default fallback
    }
}
