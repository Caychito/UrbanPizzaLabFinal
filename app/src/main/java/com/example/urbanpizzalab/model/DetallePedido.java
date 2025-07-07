package com.example.urbanpizzalab.model;

public class DetallePedido {
    private int ID_DetallePedido;
    private int ID_Pedido;
    private int ID_Producto;
    private int Cantidad;
    private double Total;
    private String ImagenURL;
    private int ID_Categoria;
    public DetallePedido(int ID_DetallePedido, int ID_Pedido, int ID_Producto,
                         int Cantidad, double Total, String ImagenURL, int ID_Categoria) {
        this.ID_DetallePedido = ID_DetallePedido;
        this.ID_Pedido = ID_Pedido;
        this.ID_Producto = ID_Producto;
        this.Cantidad = Cantidad;
        this.Total = Total;
        this.ImagenURL = ImagenURL;
        this.ID_Categoria = ID_Categoria;
    }

    public DetallePedido(int ID_Producto, int Cantidad, double Total, String ImagenURL, int ID_Categoria) {
        this.ID_Producto = ID_Producto;
        this.Cantidad = Cantidad;
        this.Total = Total;
        this.ImagenURL = ImagenURL;
        this.ID_Categoria = ID_Categoria;
    }

    // Getters y setters
    public int getID_DetallePedido() { return ID_DetallePedido; }
    public void setID_DetallePedido(int ID_DetallePedido) { this.ID_DetallePedido = ID_DetallePedido; }

    public int getID_Pedido() { return ID_Pedido; }
    public void setID_Pedido(int ID_Pedido) { this.ID_Pedido = ID_Pedido; }

    public int getID_Producto() { return ID_Producto; }
    public void setID_Producto(int ID_Producto) { this.ID_Producto = ID_Producto; }

    public int getCantidad() { return Cantidad; }
    public void setCantidad(int cantidad) { Cantidad = cantidad; }

    public double getTotal() { return Total; }
    public void setTotal(double total) { Total = total; }

    public String getImagenURL() { return ImagenURL; }
    public void setImagenURL(String imagenURL) { ImagenURL = imagenURL; }

    public int getID_Categoria() { return ID_Categoria; }
    public void setID_Categoria(int ID_Categoria) { this.ID_Categoria = ID_Categoria; }

}
