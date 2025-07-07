package com.example.urbanpizzalab.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.model.ItemCarrito;
import com.example.urbanpizzalab.model.Producto;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private List<ItemCarrito> carritoItems;
    private OnCantidadChangeListener listener;

    public interface OnCantidadChangeListener {
        void onCantidadChanged();
    }

    public CarritoAdapter(List<ItemCarrito> carritoItems, OnCantidadChangeListener listener) {
        this.carritoItems = carritoItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCarrito item = carritoItems.get(position);
        Producto producto = item.getProducto();

        holder.tvNombre.setText(producto.getNombre());
        holder.tvTamaño.setText("Tamaño: " + producto.getTamaño());
        holder.tvPrecio.setText("Precio: S/." + producto.getPrecio());
        holder.tvCantidad.setText(String.valueOf(item.getCantidad()));
        String base64String = producto.getImagenURL();
        Bitmap bitmap = base64ToBitmap(base64String);
        if (bitmap != null) {
            holder.ivImagen.setImageBitmap(bitmap);
        }
        // Aquí carga tu imagen si tienes URL o recurso local
        // holder.ivImagen.setImageResource(...) o usa Glide/Picasso

        // ➕
        holder.tvSumar.setOnClickListener(v -> {
            item.setCantidad(item.getCantidad() + 1);
            notifyItemChanged(position);
            listener.onCantidadChanged();
        });

        // ➖
        holder.tvRestar.setOnClickListener(v -> {
            if (item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
                notifyItemChanged(position);
                listener.onCantidadChanged();
            } else {
                carritoItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, carritoItems.size());
                listener.onCantidadChanged();
            }
        });

        // 🗑️
        holder.tvEliminar.setOnClickListener(v -> {
            carritoItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, carritoItems.size());
            listener.onCantidadChanged();
        });
    }
    private Bitmap base64ToBitmap(String base64Str) {
        try {
            byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return carritoItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagen;
        TextView tvNombre, tvTamaño, tvPrecio, tvCantidad;
        TextView tvSumar, tvRestar, tvEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.ivImagenProducto);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvTamaño = itemView.findViewById(R.id.tvTamañoProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvSumar = itemView.findViewById(R.id.tvSumar);
            tvRestar = itemView.findViewById(R.id.tvRestar);
            tvEliminar = itemView.findViewById(R.id.tvEliminar);
        }
    }

    public List<ItemCarrito> getCarritoItems() {
        return carritoItems;
    }
}

