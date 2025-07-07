package com.example.urbanpizzalab.model;

import java.util.ArrayList;
import java.util.List;
public class CarritoGlobal {
    public static List<ItemCarrito> listaCarrito = new ArrayList<>();

    public static void agregarProducto(ItemCarrito item) {
        // Si el producto ya está, solo aumenta la cantidad
        for (ItemCarrito existente : listaCarrito) {
            if (existente.getProducto().getID_Producto() == item.getProducto().getID_Producto()) {
                existente.setCantidad(existente.getCantidad() + item.getCantidad());
                return;
            }
        }
        listaCarrito.add(item);
    }

    public static void vaciar() {
        listaCarrito.clear();
    }
}
