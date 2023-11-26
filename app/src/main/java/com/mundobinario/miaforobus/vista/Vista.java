package com.mundobinario.miaforobus.vista;

import android.content.SharedPreferences;
import android.widget.TextView;

import com.mundobinario.miaforobus.R;
import com.mundobinario.miaforobus.modelo.Modelo;
import com.mundobinario.miaforobus.modelo.data.Contract;

public class Vista {
    public static void actualizaViewsReduccionAforoConDatosSharedPref(TextView textViewAforoTeoricoSentados,
                                                                      TextView textViewAforoTeoricoEnPie,
                                                                      TextView textViewPorcentajeSentados,
                                                                      TextView textViewPorcentajeEnPie,
                                                                      TextView textViewTextoTotalAforo,
                                                                      TextView textViewNumeroTotalAforoReducido,
                                                                      SharedPreferences sharedPreferences,
                                                                      SharedPreferences.Editor sharedPrefEditor) {
        textViewAforoTeoricoSentados.setText("" + sharedPreferences.getInt(Contract.STRING_AFORO_SENTADO_SHARED_PREF, 29));
        textViewAforoTeoricoEnPie.setText("" + sharedPreferences.getInt(Contract.STRING_AFORO_EN_PIE_SHARED_PREF, 64));
        textViewPorcentajeSentados.setText("" + sharedPreferences.getInt(Contract.STRING_PORCENTAJE_SENTADO_SHARED_PREF, 100) + " %");
        textViewPorcentajeEnPie.setText(sharedPreferences.getInt(Contract.STRING_PORCENTAJE_EN_PIE_SHARED_PREF, 100) + " %");
        String textoTotalAforoReducido = "" + sharedPreferences.getInt(Contract.AFORO_SHARED_PREF, 93);
        textViewNumeroTotalAforoReducido.setText(Modelo.agregaEspacioPreUno(textoTotalAforoReducido));
        // determina si el aforo no está reducido:
        if (
                (sharedPreferences.getInt(Contract.STRING_PORCENTAJE_SENTADO_SHARED_PREF, 100) == 100)
                        &&
                        (sharedPreferences.getInt(Contract.STRING_PORCENTAJE_EN_PIE_SHARED_PREF, 100) == 100)
        ) {
            textViewTextoTotalAforo.setText(R.string.total_de_aforo);
        } else { // el aforo está reducido:
            textViewTextoTotalAforo.setText(R.string.total_de_aforo_reducido);
        }
    }
}
