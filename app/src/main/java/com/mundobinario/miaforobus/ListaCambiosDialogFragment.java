package com.mundobinario.miaforobus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mundobinario.miaforobus.modelo.Modelo;
import com.mundobinario.miaforobus.modelo.data.Contract;

import java.util.ArrayList;
import java.util.List;

public class ListaCambiosDialogFragment extends AppCompatDialogFragment {
    private LinearLayoutCompat
            linearContainerRecyclerActual,
            linearContainerViajeAnterior,
            linearTVTotalViajeActual,
            linearTVViajeActual,
            linearTVViajeAnterior,
            linearTVTotalViajeAnterior;
    public RecyclerView recyclerViewActualViaje, recyclerViewAnteriorViaje;
    private TextView tvNumTotalViajerosActual, tvNumTotalViajerosAnterior, tvViajeActual, tvViajeAnterior;
    private Switch aSwitch;
    List<ItemListaCambios> miListaActualCambios = new ArrayList<>();
    List<ItemListaCambios> miListaAnteriorCambios = new ArrayList<>();
    private int altoTotalPantalla;
    // fraccionAlto es que fraccion del alto de pantalla ocupara cada recyclerview del actual fragment de listacambios
    private float dpAlto;
    private float scale;
    private float altoViajeActualRecycler = 0.5f;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharerPrefEditor;

    private int maximaOcupacion;
    private int indiceHoraPunta;

    private boolean esLandscape;

    // ArrayList<ItemListaCambios> miListaCambios;

    // constructor vacio requerido
    public ListaCambiosDialogFragment(
            List listaActualCambios, List listaAnteriorCambios,
            SharedPreferences sharedPref, SharedPreferences.Editor sharedEditor) {
        miListaActualCambios = listaActualCambios;
        miListaAnteriorCambios = listaAnteriorCambios;
        sharedPreferences = sharedPref;
        sharerPrefEditor = sharedEditor;
    }

    // CON EL SIGUIENTE METODO OVERRIDE SE IMPLEMENTA LA ANIMACIÓN EN FRAGMENT
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // esta es la llamada a la animacion:
        // depende de la orientación la animación saldrá de un lugar o de otro
        esLandscape = false;
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            esLandscape = true;
        if (esLandscape) {
            getDialog().getWindow()
                    .getAttributes().windowAnimations = R.style.LandAnimacionDialogoTecladoViajeros;
        } else {
            getDialog().getWindow()
                    .getAttributes().windowAnimations = R.style.AnimacionDialogoTecladoViajeros;
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_lista_cambios, null);

        esLandscape = false;
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            esLandscape = true;

        linearContainerRecyclerActual = view.findViewById(R.id.linear_contenedor_recycler_actual);
        linearContainerViajeAnterior = view.findViewById(R.id.linear_contenedor_viaje_anterior);
        linearTVTotalViajeActual = view.findViewById(R.id.linear_tv_total_viaje_actual);
        linearTVViajeAnterior = view.findViewById(R.id.linear_tv_viaje_anterior);
        linearTVViajeActual = view.findViewById(R.id.linear_tv_viaje_actual);
        linearTVTotalViajeAnterior = view.findViewById(R.id.linear_tv_total_viaje_anterior);
        recyclerViewActualViaje = view.findViewById(R.id.lista_cambios_recyclerview_viaje_actual);
        recyclerViewAnteriorViaje = view.findViewById(R.id.lista_cambios_recyclerview_viaje_anterior);
        tvViajeActual = view.findViewById(R.id.tv_viaje_actual);
        tvViajeAnterior = view.findViewById(R.id.tv_viaje_anterior);
        tvNumTotalViajerosActual = view.findViewById(R.id.tv_total_viajeros_actual_viaje);
        tvNumTotalViajerosAnterior = view.findViewById(R.id.tv_total_viajeros_anterior_viaje);
        aSwitch = view.findViewById(R.id.switch_mostrar_anterior_viaje);

        // ponemos fecha a los viajes, si las tienen:
        String stringTvViajeActual = getResources().getString(R.string.tv_viaje_actual);
        String stringTvViajeAnterior = getResources().getString(R.string.tv_viaje_anterior);

        // CREAMOS STRING PARA FECHA HORA DE LISTA ACTUAL, SI TIENE FECHA:
        String fechaActualViaje = sharedPreferences.getString(
                Contract.STRING_KEY_FECHA_ACTUAL_VIAJE_SHARED_PREF,
                getResources().getString(R.string.no_existe));
        // podría ser fechaActualViaje == "no existe", en ese caso no ejecuta los bloques...
        if (fechaActualViaje != getResources().getString(R.string.no_existe)) {
            // si es hoy, pone hoy:
            String fechaActualViajeSinHora = fechaActualViaje.substring(0, 8);
            String horaActualViaje = fechaActualViaje.substring(8);
            if (fechaActualViajeSinHora.equals(Modelo.getFechaActualddMMyy()))
                fechaActualViaje = getResources().getString(R.string.hoy) + horaActualViaje;
            // si es ayer, pone ayer:
            if (fechaActualViajeSinHora.equals(Modelo.getFechaAyerddMMyy()))
                fechaActualViaje = getResources().getString(R.string.ayer) + horaActualViaje;
        }


        // CREAMOS STRING PARA FECHA HORA DE LISTA ANTERIOR, SI TIENE FECHA:
        String fechaAnteriorViaje = sharedPreferences.getString(
                Contract.STRING_KEY_FECHA_ANTERIOR_VIAJE_SHARED_PREF,
                getResources().getString(R.string.no_existe));
        // podría ser fechaAnteriorViaje == "no existe", en ese caso no ejecuta los bloques...
        if (fechaAnteriorViaje != getResources().getString(R.string.no_existe)) {

        }
        // si es hoy, pone hoy:
        String fechaAnteriorViajeSinHora = fechaAnteriorViaje.substring(0, 8);
        String horaAnteriorViaje = fechaAnteriorViaje.substring(8);
        if (fechaAnteriorViajeSinHora.equals(Modelo.getFechaActualddMMyy()))
            fechaAnteriorViaje = getResources().getString(R.string.hoy) + horaAnteriorViaje;
        // si es ayer, pone ayer:
        if (fechaAnteriorViajeSinHora.equals(Modelo.getFechaAyerddMMyy()))
            fechaAnteriorViaje = getResources().getString(R.string.ayer) + horaAnteriorViaje;

        // cambiamos el texto de las fechas:
        tvViajeActual.setText(stringTvViajeActual + " (" + fechaActualViaje + ")");
        tvViajeAnterior.setText(stringTvViajeAnterior + " (" + fechaAnteriorViaje + ")");

        // hayamos el alto de pantalla para establecer al alto del recycler:
        scale = getResources().getDisplayMetrics().density;
        int anchoTotalPantalla;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getActivity().getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            altoTotalPantalla = windowMetrics.getBounds().height() - insets.top - insets.bottom;
            anchoTotalPantalla = windowMetrics.getBounds().width();
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            altoTotalPantalla = displayMetrics.heightPixels;
            anchoTotalPantalla = displayMetrics.widthPixels;
        }
        dpAlto = (altoTotalPantalla) / scale;

        // damos altura a viajeactual:
        linearTVViajeActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        ));
        linearContainerRecyclerActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, (int) (altoTotalPantalla * altoViajeActualRecycler)));
        linearTVTotalViajeActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        // mostramos u ocultamos el viaje anterior dependiendo del shared guardado:
        if (!sharedPreferences.getBoolean( // si no hay que mostrar el viaje anterior
                Contract.SWITCH_MOSTRAR_VIAJE_ANTERIOR_SHARED_PREF, false)) {
            cierraViajeAnterior();
            aSwitch.setChecked(false);
        } else {
            abreViajeAnterior();
            aSwitch.setChecked(true);
        }

        builder.setView(view);
        builder.setPositiveButton(R.string.cerrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        // textview total viajeros en el viaje actual:
        int totalBilletesManuales = Modelo.getBilletesSinMaquinaViajeActual(miListaActualCambios);
        int totalBilletesMaquina = Modelo.getBilletesMaquinaViajeActual(miListaActualCambios);
        int totalViajeros = totalBilletesManuales + totalBilletesMaquina;
        tvNumTotalViajerosActual.setText(getResources().getString(R.string.total_viajeros_del_viaje_actual,
                totalViajeros));
        // textview total viajeros en el viaje anterior:
        totalBilletesManuales = Modelo.getBilletesSinMaquinaViajeActual(miListaAnteriorCambios);
        totalBilletesMaquina = Modelo.getBilletesMaquinaViajeActual(miListaAnteriorCambios);
        totalViajeros = totalBilletesManuales + totalBilletesMaquina;
        tvNumTotalViajerosAnterior.setText(getResources().getString(R.string.total_viajeros_del_viaje_anterior,
                totalViajeros));

        // cambiamos la altura del linearcontenedor del anterior viaje si se cambia el switch:
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    abreViajeAnterior();
                    sharerPrefEditor.putBoolean(Contract.SWITCH_MOSTRAR_VIAJE_ANTERIOR_SHARED_PREF, true);
                    sharerPrefEditor.apply();
                } else {
                    cierraViajeAnterior();
                    sharerPrefEditor.putBoolean(Contract.SWITCH_MOSTRAR_VIAJE_ANTERIOR_SHARED_PREF, false);
                    sharerPrefEditor.apply();
                }
            }
        });

        // ACTUAL viaje recycler:
        recyclerViewActualViaje.setLayoutManager(new LinearLayoutManager(getContext()));
        // encuentra hora punta:
        // busca el indice de la List que tiene la hora punta:
        encuentraIndiceHoraPunta(miListaActualCambios);
        // llenarLista();
        ListaCambiosAdapter adapterActual = new ListaCambiosAdapter(indiceHoraPunta, miListaActualCambios, view.getContext(), getResources());
        recyclerViewActualViaje.setAdapter(adapterActual);

        // ANTERIOR viaje recycler:
        recyclerViewAnteriorViaje.setLayoutManager(new LinearLayoutManager(getContext()));
        // encuentra hora punta:
        // busca el indice de la List que tiene la hora punta:
        encuentraIndiceHoraPunta(miListaAnteriorCambios);
        // llenarLista();
        ListaCambiosAdapter adapterAnterior = new ListaCambiosAdapter(indiceHoraPunta, miListaAnteriorCambios, view.getContext(), getResources());
        recyclerViewAnteriorViaje.setAdapter(adapterAnterior);

        AlertDialog alertDialog = builder.create(); // creo el AlertDialog para poder modificar el boton de cerrar color

        return alertDialog;
    }

    private void encuentraIndiceHoraPunta(List<ItemListaCambios> listaCambios) {
        maximaOcupacion = 0;
        indiceHoraPunta = 0;
        int viajerosAhora = 0;
        for (int i = 0; i < listaCambios.size(); i++) {
            // debemos saber si contiene .getViajeros() el string " ["; en ese caso el dato incluye
            // viajeros del viaje anterior con el formato <viajerosActuales> [+ <viajerosSinBillete>*]
            if (listaCambios.get(i).getViajeros().indexOf(" +") != -1) { // contiene " ["
                // modificamos variable viajerosAhora para la comparación del siguiente if...
                int indice = listaCambios.get(i).getViajeros().indexOf(" +");
                String stringViajerosViajeActual = listaCambios.get(i).getViajeros().substring(0, indice);
                viajerosAhora = Integer.parseInt(stringViajerosViajeActual);
            } else {
                // no se altera, ya que el string no tiene formato <viajerosActuales> [+ <viajerosSinBillete>*]
                viajerosAhora = Integer.parseInt(listaCambios.get(i).getViajeros());
            }
            if (viajerosAhora > maximaOcupacion) {
                maximaOcupacion = viajerosAhora;
                indiceHoraPunta = i;
            }
        }
    }

    private void abreViajeAnterior() {
        // todo: poner altura de textviews de v anterior a 20dp
        // restauramos alturas de viaje anterior
        // abre tvViajeAnterior
        linearTVViajeAnterior.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        ));
        // abre total viajeAnterior
        linearTVTotalViajeAnterior.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        ));
        if (esLandscape) { // es landscape: solo muestra viaje anterior por falta de espacio y
            // cambiar texto de swith a "muestra solo viaje anterior"
            // hacemos invisible viaje actual poniendo a 0 sus alturas:
            linearTVViajeActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT, 0));
            linearContainerRecyclerActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT, 0));
            linearTVTotalViajeActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT, 0));

            // tener en cuenta que  debe mostrar ademas un textview del titulo viajeanterior... de
            // modo que debe ser algo menor la altura que si es viaje actual (de 0.4 a 3.5...)
            linearContainerViajeAnterior.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT, (int) (altoTotalPantalla * 0.335f)
            )); // se cambia el alto aqui porque si no no hay espacio entre textviews que rodean al recycler...

        } else { // es portrait: cada viaje altura a 0.2
            // abre los 2 viajes
            float reduccionPantalla = 0.214f;
            linearContainerViajeAnterior.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT, (int) (altoTotalPantalla * reduccionPantalla)
            )); // se cambia el alto aqui porque si no no hay espacio entre textviews que rodean al recycler...
            linearTVViajeActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ));
            linearContainerRecyclerActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT, (int) (altoTotalPantalla * reduccionPantalla)
            ));
            linearTVTotalViajeActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        }

    }

    private void cierraViajeAnterior() {
        // Poner altura de linear textviews de v anterior a 0
        // el alto del linear sera 0 para que no se vea
        linearTVViajeAnterior.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, 0));
        linearContainerViajeAnterior.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, 0));
        linearTVTotalViajeAnterior.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, 0));
        // hacemos visible viaje actual con sus parametros de dimensiones:
        linearTVViajeActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        ));
        if (esLandscape) altoViajeActualRecycler = 0.335f;
        else altoViajeActualRecycler = 0.5f;
        linearContainerRecyclerActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, (int) (altoTotalPantalla * altoViajeActualRecycler)));
        linearTVTotalViajeActual.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
    }
}
