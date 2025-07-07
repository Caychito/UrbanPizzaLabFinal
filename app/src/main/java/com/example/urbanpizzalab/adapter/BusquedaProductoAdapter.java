package com.example.urbanpizzalab.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urbanpizzalab.R;
import com.example.urbanpizzalab.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class BusquedaProductoAdapter extends ArrayAdapter<Producto> {
    private Context context;
    private List<Producto> productosOriginal;
    private List<Producto> productosFiltrados;

    public BusquedaProductoAdapter(Context context, List<Producto> productos) {
        super(context, 0, productos);
        this.context = context;
        this.productosOriginal = new ArrayList<>(productos);
        this.productosFiltrados = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return productosFiltrados.size();
    }

    @Override
    public Producto getItem(int position) {
        return productosFiltrados.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Producto producto = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        }
        TextView nombre = convertView.findViewById(R.id.txtNombreProducto);
        TextView descripcion = convertView.findViewById(R.id.txtDescripcionProducto);
        ImageView imagen = convertView.findViewById(R.id.imgProducto);

        nombre.setText(producto.getNombre());
        descripcion.setText(producto.getDescripcion());

        if (producto.getImagenURL() != null && !producto.getImagenURL().isEmpty()) {
            String base64String = producto.getImagenURL();

            Bitmap bitmap = base64ToBitmap(base64String);
            if (bitmap != null) {
                imagen.setImageBitmap(bitmap);
            }
        }
        return convertView;
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
    public Filter getFilter() {
        return filtroProducto;
    }

    private Filter filtroProducto = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Producto> sugerencias = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                sugerencias.addAll(productosOriginal);
            } else {
                String filtro = constraint.toString().toLowerCase().trim();
                for (Producto producto : productosOriginal) {
                    if (producto.getNombre().toLowerCase().contains(filtro)) {
                        sugerencias.add(producto);
                    }
                }
            }

            FilterResults resultados = new FilterResults();
            resultados.values = sugerencias;
            resultados.count = sugerencias.size();
            return resultados;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productosFiltrados.clear();
            if (results != null && results.count > 0) {
                productosFiltrados.addAll((List<Producto>) results.values);
            }
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Producto) resultValue).getNombre();
        }
    };
}
