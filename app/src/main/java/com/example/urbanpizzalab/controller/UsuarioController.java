package com.example.urbanpizzalab.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.urbanpizzalab.database.bdUrban;
import com.example.urbanpizzalab.model.Usuario;

public class UsuarioController extends bdUrban {
    private final String tUsuario = "Usuario";
    private final Context context;

    public UsuarioController(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    // REGISTER
    public boolean insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("INSERT INTO " + tUsuario + " (Nombre, Apellido, Email, Contrasenia, DNI) VALUES (" +
                    "'" + usuario.getNombre() + "', " +
                    "'" + usuario.getApellido() + "', " +
                    "'" + usuario.getEmail() + "', " +
                    "'" + usuario.getContrasenia() + "', " +
                    usuario.getDNI() + ")"
            );
            db.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Puedes mostrar un Toast si deseas
            return false;
        }
    }

    // LOGIN (Validar existencia)
    public boolean validarLogin(String email, String contrasenia) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tUsuario + " WHERE Email = ? AND Contrasenia = ?", new String[]{email, contrasenia});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    // UPDATE contraseña
    public void actualizarContrasenia(String email, String nuevaContrasenia) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            db.execSQL("UPDATE " + tUsuario + " SET Contrasenia = '" + nuevaContrasenia + "' WHERE Email = '" + email + "'");
            db.close();
        }
    }

    // RECUPERAR contraseña (traerla desde email si existe)
    public String obtenerContraseniaPorEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Contrasenia FROM " + tUsuario + " WHERE Email = ?", new String[]{email});

        String contrasenia = null;
        if (cursor.moveToFirst()) {
            contrasenia = cursor.getString(0);
        }

        cursor.close();
        return contrasenia;
    }
}
