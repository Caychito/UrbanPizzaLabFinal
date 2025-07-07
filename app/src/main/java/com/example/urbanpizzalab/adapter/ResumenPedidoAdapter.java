package com.example.urbanpizzalab.adapter;

import android.content.Context;
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

public class ResumenPedidoAdapter extends RecyclerView.Adapter<ResumenPedidoAdapter.ViewHolder> {

    private Context context;
    private List<ItemCarrito> listaResumen;

    public ResumenPedidoAdapter(Context context, List<ItemCarrito> listaResumen) {
        this.context = context;
        this.listaResumen = listaResumen;
    }

    @NonNull
    @Override
    public ResumenPedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_resumen_pedido, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumenPedidoAdapter.ViewHolder holder, int position) {
        ItemCarrito item = listaResumen.get(position);
        Producto producto = item.getProducto();

        holder.txtNombre.setText(producto.getNombre());
        holder.txtCantidad.setText("Cantidad: " + item.getCantidad());
        double subtotal = item.getCantidad() * producto.getPrecio();
        holder.txtSubtotal.setText(String.format("Subtotal: S/ %.2f", subtotal));

        String base64String = producto.getImagenURL();

        Bitmap bitmap = base64ToBitmap(base64String);
        if (bitmap != null) {
            holder.imgProducto.setImageBitmap(bitmap);
        }
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
        return listaResumen.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto;
        TextView txtNombre, txtCantidad, txtSubtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            txtNombre = itemView.findViewById(R.id.txtNombreProducto);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtSubtotal = itemView.findViewById(R.id.txtSubtotal);
        }
    }
}