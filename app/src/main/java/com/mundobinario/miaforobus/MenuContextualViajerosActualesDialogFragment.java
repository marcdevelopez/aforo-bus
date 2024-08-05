package com.mundobinario.miaforobus;

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.mundobinario.miaforobus.modelo.data.Contract;

import java.util.ArrayList;

public class MenuContextualViajerosActualesDialogFragment extends AppCompatDialogFragment {
    private TextView tvResetViajerosActuales;
    private SharedPreferences mSharedPref;
    private FragmentManager mSuportFragmentManager;
    private int mBilletesMaquinaViajeActual;
    private int mBilletesSinMaquinaViajeActual;
    private int mViajerosActuales;

    public MenuContextualViajerosActualesDialogFragment(SharedPreferences sharedPref,
                                                        FragmentManager suportFragmentManager,
                                                        int billetesMaquinaViajeActual,
                                                        int billetesSinMaquinaViajeActual,
                                                        int viajerosActuales) {
        mSharedPref = sharedPref;
        mSuportFragmentManager = suportFragmentManager;
        mBilletesMaquinaViajeActual = billetesMaquinaViajeActual;
        mBilletesSinMaquinaViajeActual = billetesSinMaquinaViajeActual;
        mViajerosActuales = viajerosActuales;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SharedPreferences.Editor editorPref = mSharedPref.edit();
        AlertDialog alertDialog; // necesario para poder hacer dimish en alguna view...
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_menu_contextual_viajeros_actuales, null);
        builder.setView(view);
        alertDialog = builder.create();
        // para hacer transparente el fondo del dialogo, comprobar que es androidx.appcompat.app.AlertDialog:
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvResetViajerosActuales = view.findViewById(R.id.tv_reset_viajeros_actuales);

        tvResetViajerosActuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* abrira dialogo avisando del cambio a realizar, pero si se selecciono que no se
                muestre mas el aviso, pasará directamente al teclado
                                 */
                alertDialog.dismiss(); // cierra el actual dialogo

                // comprueba que se pueda hacer el reset:
                // comprobamos si aun no se han vendido billetes, para evitar añadir viajeros
                // reseteando viajeros, ya que asi aparecerian todos como sin billete:
                int totalBilletesDelViaje = mBilletesMaquinaViajeActual
                        + mBilletesSinMaquinaViajeActual;
                if (totalBilletesDelViaje == 0 && mViajerosActuales == 0) { /* es necesario que
                tampoco haya viajeros actuales para el caso de que aun queden viajeros del viaje
                anterior y deseemos resetear viajeros
                   */
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    final View vistaWarning = inflater.inflate(R.layout.icono_warning, null);
                    builder.setView(vistaWarning);
                    builder.setMessage(getResources().getString(
                            R.string.aun_no_hay_billetes_vendidos_agrega_billetes));
                    builder.setPositiveButton(getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // no hace nada, solo "dimish" del método para cerrar, es solo un aviso
                        }
                    });
                    builder.show();
                } else {
                    // maneja el reset viajeros:
                    // muestra aviso de lo que hará:
                    if (!mSharedPref.getBoolean(
                            "NO_SE_MUESTRA_AVISO_ALERT_RESET_VIAJEROS_ACTUALES",
                            false)) { // hay aviso sobre la accion que realizará
                        CharSequence[] check = new CharSequence[1];
                        ArrayList selected = new ArrayList();
                        check[0] = getResources().getString(R.string.no_volver_a_mostrar);
                        AlertDialog.Builder avisoBuiderReset = new AlertDialog.Builder(getActivity());
                        avisoBuiderReset.setTitle(getResources().getString(
                                R.string.titulo_alert_dialog_reset_viajeros_explicacion));
                        avisoBuiderReset.setMultiChoiceItems(check, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selected.add(which);
                                    // guardamos la seleccion
                                    editorPref.putBoolean("NO_SE_MUESTRA_AVISO_ALERT_RESET_VIAJEROS_ACTUALES", true);
                                    editorPref.apply();
                                } else if (selected.contains(which)) {
                                    // cambiamos la seleccion
                                    selected.remove(Integer.valueOf(which)); // al deseleccionar lo quita
                                    editorPref.putBoolean("NO_SE_MUESTRA_AVISO_ALERT_RESET_VIAJEROS_ACTUALES", false);
                                    editorPref.apply();
                                }
                            }
                        });
                        avisoBuiderReset.setPositiveButton(getResources().getString(R.string.aceptar),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        abreDialogoTeclado();
                                    }
                                });
                        avisoBuiderReset.setNegativeButton(getResources().getString(R.string.cancelar),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // solo cierra el dialogo con dimish() implicito
                                    }
                                });
                        avisoBuiderReset.show();
                    } else {
                        // muestra el teclado directamente
                        abreDialogoTeclado();
                    }
                }


            }
        });


        return alertDialog;
    }

    private void abreDialogoTeclado() {
        TecladoDialogFragment tecladoDialogFragment = new TecladoDialogFragment(
                Contract.PETICION_TECLADO_RESET_VIAJEROS);
        tecladoDialogFragment.show(mSuportFragmentManager,"teclado dialogo");
    }
}
