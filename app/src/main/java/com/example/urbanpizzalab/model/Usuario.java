package com.example.urbanpizzalab.model;

public class Usuario {
    private int ID_Usuario;
    private String Nombre;
    private String Apellido;
    private String Email;
    private String Contraseña;
    private int DNI;

    public Usuario(int ID_Usuario, String Nombre, String Apellido, String Email,
                   String Contraseña, int DNI) {
        this.ID_Usuario = ID_Usuario;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Email = Email;
        this.Contraseña = Contraseña;
        this.DNI = DNI;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContrasenia() {
        return Contraseña;
    }

    public void setContrasenia(String contraseña) {
        Contraseña = contraseña;
    }


    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }
}
