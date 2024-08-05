package com.mundobinario.miaforobus;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.List;

public class ListaCambiosAdapter extends RecyclerView.Adapter<ListaCambiosAdapter.MyViewHolder> {
    private List<ItemListaCambios> miListaCambios;
    private LayoutInflater miLayoutInflater;
    private Context mContext;
    private Resources mResources;
    private int indiceHoraPunta;
    Context context;

    public ListaCambiosAdapter(int nuevoIndiceHoraPunta, List<ItemListaCambios> listaCambios, Context context, Resources resources) {
        miLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        miListaCambios = listaCambios;
        indiceHoraPunta = nuevoIndiceHoraPunta;
        mResources = resources;
    }

    @NonNull
    @Override
    public ListaCambiosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        // crea una nueva vista
        boolean attachToParentRapido = false;
        View v = miLayoutInflater.inflate(R.layout.item_lista_cambio_recyclerview, parent, attachToParentRapido);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // reemplaza el contenido de una view
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewHora.setText(miListaCambios.get(position).getHora());
        // ojo, usar para vectores svg etc: imageView.setImageDrawable(VectorDrawableCompat.create(getResources(), drawableRes, null));
        holder.imageViewIconoSubeBaja.setImageDrawable(VectorDrawableCompat.create(
                mResources,miListaCambios.get(position).getIconoSubeBaja(),null));
        holder.textViewMovimiento.setText(miListaCambios.get(position).getMovimiento());
        if (miListaCambios.get(position).getIconoSubeBaja() == R.drawable.ic_menos_24 ||
                miListaCambios.get(position).getIconoBillete() == R.drawable.ic_llave_inglesa_24) {
            holder.textViewMovimiento.setTextColor(Color.parseColor("#D13A2F"));
        } else {
            holder.textViewMovimiento.setTextColor(Color.parseColor("#2196F3")); // azul_pueden_subir
        }
        holder.textViewViajeros.setText(miListaCambios.get(position).getViajeros());
        // el color de viajeros cambia depende del porcentaje de ocupacion:
        String colorViajeros;
        String porcentajeSinPorciento = miListaCambios.get(position).getOcupacion();
        int indicePorciento = porcentajeSinPorciento.indexOf("%");
        porcentajeSinPorciento = porcentajeSinPorciento.substring(0, indicePorciento);
        int porcentaje = Integer.parseInt(porcentajeSinPorciento);
        if (porcentaje < 33) {
            colorViajeros = "#8BC34A";
            holder.frameFondoViajeros.setBackgroundResource(R.drawable.shape_fondo_viajeros_listacambios_negro_verde);
        } else if (porcentaje < 66) {
            colorViajeros = "#FFD403";
            holder.frameFondoViajeros.setBackgroundResource(R.drawable.shape_fondo_viajeros_listacambios_negro_ambar);
        } else {
            colorViajeros = "#F44336";
            holder.frameFondoViajeros.setBackgroundResource(R.drawable.shape_fondo_viajeros_listacambios_negro_rojo);
        }
        holder.textViewViajeros.setTextColor(Color.parseColor(colorViajeros));
        holder.textViewOcupacion.setText(miListaCambios.get(position).getOcupacion());
        // PONEMOS EN NEGRITAS LOS VIAJEROS Y OCUPACION SI ES HORA PUNTA:
        if (position == indiceHoraPunta) {
            holder.textViewOcupacion.setTypeface(Typeface.DEFAULT_BOLD);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
                /* es necesario cambiar los 2 colores para que ocupe el total del fondo y no se vea
                cortado, además de que sea totalmente opaco el color ya que se solapan */
                holder.cardViewFondoItem.setCardBackgroundColor(Color.parseColor("#FFD0D0"));
                holder.linearFondoItem.setBackgroundColor(Color.parseColor("#FFD0D0"));
            } else {
                holder.linearFondoItem.setBackgroundColor(Color.parseColor("#FFD0D0"));
            }
        } else {
            // VIAJEROS VA EN AZUL, POR ESO LE CAMBIAMOS AQUI EL COLOR
            // el typeface es normal, IMPORTANTE si no saldran mas de 1 en negrita ...
            holder.textViewOcupacion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
                holder.cardViewFondoItem.setCardBackgroundColor(Color.parseColor("#DAEAF1"));
                holder.linearFondoItem.setBackgroundColor(Color.parseColor("#DAEAF1"));
            } else {
                holder.linearFondoItem.setBackgroundColor(Color.parseColor("#DAEAF1"));
            }

            // holder.linearFondoItem.setBackgroundColor(R.id.layout_historico_viaje); // desmarcar si da problemas y poner todos los colores personalizados para que no salte el dark de noche...
        }
        // ojo, usar para vectores svg etc: imageView.setImageDrawable(VectorDrawableCompat.create(getResources(), drawableRes, null));
        holder.imageViewIconoBillete.setImageDrawable(VectorDrawableCompat.create(
                mResources,miListaCambios.get(position).getIconoBillete(),null));
    }

    @Override
    public int getItemCount() {
        return miListaCambios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewFondoItem;
        LinearLayoutCompat linearFondoItem;
        TextView textViewHora, textViewMovimiento, textViewViajeros, textViewOcupacion;
        ImageView imageViewIconoSubeBaja, imageViewIconoBillete;
        FrameLayout frameFondoViajeros;

        MyViewHolder(View itemView) {
            super(itemView);
            cardViewFondoItem = itemView.findViewById(R.id.cardview_fondo_item_recycler_viaje);
            linearFondoItem = itemView.findViewById(R.id.linear_layout_fondo_item_recycler_viaje);
            frameFondoViajeros = itemView.findViewById(R.id.frame_viajeros_listacambios);
            textViewHora = itemView.findViewById(R.id.text_view_hora_lista_cambios);
            imageViewIconoSubeBaja = itemView.findViewById(R.id.image_view_sube_baja_lista_cambios);
            textViewMovimiento = itemView.findViewById(R.id.text_view_movimiento_viajeros_lista_cambios);
            textViewViajeros = itemView.findViewById(R.id.text_view_viajeros_lista_cambios);
            textViewOcupacion = itemView.findViewById(R.id.text_view_ocupacion_lista_cambios);
            imageViewIconoBillete = itemView.findViewById(R.id.image_view_billete_lista_cambios);
        }


    }
}
