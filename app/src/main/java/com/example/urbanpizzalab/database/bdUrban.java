package com.example.urbanpizzalab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class bdUrban  extends SQLiteOpenHelper {
    private static final String DB_NAME = "Urban.db";
    public static final int DB_VERSION = 1;
    public bdUrban(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla Categoria
        db.execSQL("CREATE TABLE Categoria (" +
                "ID_Categoria INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre VARCHAR(50))");

        // Tabla Producto
        db.execSQL("CREATE TABLE Producto (" +
                "ID_Producto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre VARCHAR(50), " +
                "Descripcion VARCHAR(100), " +
                "Precio DOUBLE, " +
                "Stock INTEGER, " +
                "Tamaño VARCHAR(100), " +
                "ImagenURL VARCHAR(100), " +
                "ID_Categoria INTEGER, " +
                "FOREIGN KEY(ID_Categoria) REFERENCES Categoria(ID_Categoria))");

        // Tabla Distrito
        db.execSQL("CREATE TABLE Distrito (" +
                "ID_Distrito INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre VARCHAR(50))");

        // Tabla Usuario
        db.execSQL("CREATE TABLE Usuario (" +
                "ID_Usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre VARCHAR(50), " +
                "Apellido VARCHAR(50), " +
                "Email VARCHAR(50), " +
                "Contrasenia VARCHAR(50), " +
                "DNI INTEGER)");

        // Tabla Metodo_Pago
        db.execSQL("CREATE TABLE Metodo_Pago (" +
                "ID_MetodoPago INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre VARCHAR(50))");

        // Tabla Pedido
        db.execSQL("CREATE TABLE Pedido (" +
                "ID_Pedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_Usuario INTEGER, " +
                "Fecha DATE, " +
                "Estado VARCHAR(100), " +
                "ID_MetodoPago INTEGER, " +
                "FOREIGN KEY(ID_Usuario) REFERENCES Usuario(ID_Usuario), " +
                "FOREIGN KEY(ID_MetodoPago) REFERENCES Metodo_Pago(ID_MetodoPago))");

        // Tabla Detalle_Pedido
        db.execSQL("CREATE TABLE Detalle_Pedido (" +
                "ID_DetallePedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_Pedido INTEGER, " +
                "ID_Producto INTEGER, " +
                "Cantidad INTEGER, " +
                "Total DOUBLE, " +
                "ImagenURL VARCHAR(100), " +
                "ID_Categoria INTEGER, " +
                "FOREIGN KEY(ID_Pedido) REFERENCES Pedido(ID_Pedido), " +
                "FOREIGN KEY(ID_Producto) REFERENCES Producto(ID_Producto), " +
                "FOREIGN KEY(ID_Categoria) REFERENCES Categoria(ID_Categoria))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes actualizar tu estructura si cambias de versión
        db.execSQL("DROP TABLE IF EXISTS Detalle_Pedido");
        db.execSQL("DROP TABLE IF EXISTS Pedido");
        db.execSQL("DROP TABLE IF EXISTS Metodo_Pago");
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        db.execSQL("DROP TABLE IF EXISTS Distrito");
        db.execSQL("DROP TABLE IF EXISTS Producto");
        db.execSQL("DROP TABLE IF EXISTS Categoria");
        onCreate(db);
    }
}