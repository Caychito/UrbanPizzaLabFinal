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



}
