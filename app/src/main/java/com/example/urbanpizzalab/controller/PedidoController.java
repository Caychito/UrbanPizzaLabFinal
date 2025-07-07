package com.example.urbanpizzalab.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.urbanpizzalab.database.bdUrban;
import com.example.urbanpizzalab.model.DetallePedido;
import com.example.urbanpizzalab.model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoController extends bdUrban {

    private final Context context;

    public PedidoController(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    // Insertar un pedido (cabecera)
    public long insertarPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        long idPedido = -1;
        try {
            db.execSQL("INSERT INTO Pedido (ID_Usuario, Fecha, Estado, ID_MetodoPago) VALUES (" +
                    pedido.getID_Usuario() + ", " +
                    "'" + pedido.getFecha() + "', " +
                    "'" + pedido.getEstado() + "', " +
                    pedido.getID_MetodoPago() + ")");

            Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
            if (cursor.moveToFirst()) {
                idPedido = cursor.getLong(0);
            }
            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return idPedido;
    }

    // Insertar detalle de pedido
    public boolean insertarDetallePedido(DetallePedido detalle) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("INSERT INTO Detalle_Pedido (ID_Pedido, ID_Producto, Cantidad, Total, ImagenURL, ID_Categoria) VALUES (" +
                    detalle.getID_Pedido() + ", " +
                    detalle.getID_Producto() + ", " +
                    detalle.getCantidad() + ", " +
                    detalle.getTotal() + ", " +
                    "'" + detalle.getImagenURL() + "', " +
                    detalle.getID_Categoria() + ")");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // Insertar nuevo método de pago (opcional para poblar)
    public boolean insertarMetodoPago(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("INSERT INTO Metodo_Pago (Nombre) VALUES ('" + nombre + "')");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // Obtener nombres de los métodos de pago (para mostrar en Spinner)
    public List<String> obtenerMetodosPago() {
        List<String> metodos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Nombre FROM Metodo_Pago", null);
        if (cursor.moveToFirst()) {
            do {
                metodos.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return metodos;
    }
}
