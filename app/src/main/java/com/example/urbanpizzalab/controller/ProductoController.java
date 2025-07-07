package com.example.urbanpizzalab.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.urbanpizzalab.database.bdUrban;
import com.example.urbanpizzalab.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoController extends bdUrban {
    private final Context context;

    public ProductoController(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public List<Producto> MostrarProductos(int id_categoria){
        bdUrban x = new ProductoController(context);
        SQLiteDatabase database = x.getReadableDatabase();

        List<Producto> datos = new ArrayList<>();
        Cursor act = null;

        act = database.rawQuery("SELECT * FROM Producto WHERE ID_Categoria = " + id_categoria + " GROUP BY Nombre", null);

        if (act.moveToFirst()){
            do{
                datos.add(new Producto (Integer.parseInt(act.getString(0)),
                        act.getString(1),
                        act.getString(2),
                        Double.parseDouble(act.getString(3)),
                        Integer.parseInt(act.getString(4)),
                        act.getString(5),
                        act.getString(6),
                        Integer.parseInt(act.getString(7))
                ));
            }while(act.moveToNext());
        }
        act.close();
        return datos;
    }

    public Producto MostrarProductosID(int id_producto) {
        bdUrban x = new ProductoController(context);
        SQLiteDatabase database = x.getReadableDatabase();

        Producto datos = null;
        Cursor act = null;

        // Elimina GROUP BY, ya que solo buscas un producto específico por ID
        act = database.rawQuery(
                "SELECT * FROM Producto WHERE ID_Producto = ?",
                new String[] { String.valueOf(id_producto) }
        );

        if (act.moveToFirst()) {
            datos = new Producto(
                    act.getInt(0),
                    act.getString(1),
                    act.getString(2),
                    act.getDouble(3),
                    act.getInt(4),
                    act.getString(5),
                    act.getString(6),
                    act.getInt(7)
            );
        }

        act.close();
        return datos;
    }

    public List<Producto> ListarProductos(){
        bdUrban x = new ProductoController(context);
        SQLiteDatabase database = x.getReadableDatabase();

        List<Producto> datos = new ArrayList<>();
        Cursor act = null;

        act = database.rawQuery("SELECT * FROM Producto GROUP BY Nombre", null);

        if (act.moveToFirst()){
            do{
                datos.add(new Producto (Integer.parseInt(act.getString(0)),
                        act.getString(1),
                        act.getString(2),
                        Double.parseDouble(act.getString(3)),
                        Integer.parseInt(act.getString(4)),
                        act.getString(5),
                        act.getString(6),
                        Integer.parseInt(act.getString(7))
                ));
            }while(act.moveToNext());
        }
        act.close();
        return datos;
    }

    public List<String> MostrarTamano(int id_producto) {
        bdUrban x = new ProductoController(context);
        SQLiteDatabase database = x.getReadableDatabase();

        List<String> listaTamanos = new ArrayList<>();
        Cursor cursorCategoria = null;
        Cursor cursorTamanos = null;

        try {
            // 1. Obtener el ID_Categoria a partir del ID_Producto
            cursorCategoria = database.rawQuery(
                    "SELECT ID_Categoria FROM Producto WHERE ID_Producto = ?",
                    new String[] { String.valueOf(id_producto) }
            );

            if (cursorCategoria.moveToFirst()) {
                int id_categoria = cursorCategoria.getInt(0); // Extraemos el ID_Categoria

                // 2. Obtener los tamaños agrupados por ID_Categoria
                cursorTamanos = database.rawQuery(
                        "SELECT Tamanio FROM Producto WHERE ID_Categoria = ? GROUP BY Tamanio",
                        new String[] { String.valueOf(id_categoria) }
                );

                if (cursorTamanos.moveToFirst()) {
                    do {
                        listaTamanos.add(cursorTamanos.getString(0));
                    } while (cursorTamanos.moveToNext());
                }
            }
        } finally {
            if (cursorCategoria != null) cursorCategoria.close();
            if (cursorTamanos != null) cursorTamanos.close();
        }

        return listaTamanos;
    }



}
