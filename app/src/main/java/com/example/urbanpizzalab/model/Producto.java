package com.example.urbanpizzalab.model;

public class Producto {
    private int ID_Producto;
    private String Nombre;
    private String Descripcion;
    private double Precio;
    private int Stock;
    private String Tamaño;
    private String ImagenURL;
    private int ID_Categoria;

    public Producto(int ID_Producto, String Nombre, String Descripcion, double Precio,
                    int Stock, String Tamaño, String ImagenURL, int ID_Categoria) {
        this.ID_Producto = ID_Producto;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
        this.Stock = Stock;
        this.Tamaño = Tamaño;
        this.ImagenURL = ImagenURL;
        this.ID_Categoria = ID_Categoria;
    }

    public int getID_Producto() {
        return ID_Producto;
    }

    public void setID_Producto(int ID_Producto) {
        this.ID_Producto = ID_Producto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getTamaño() {
        return Tamaño;
    }

    public void setTamaño(String tamaño) {
        Tamaño = tamaño;
    }

    public String getImagenURL() {
        return ImagenURL;
    }

    public void setImagenURL(String imagenURL) {
        ImagenURL = imagenURL;
    }

    public int getID_Categoria() {
        return ID_Categoria;
    }

    public void setID_Categoria(int ID_Categoria) {
        this.ID_Categoria = ID_Categoria;
    }
}
