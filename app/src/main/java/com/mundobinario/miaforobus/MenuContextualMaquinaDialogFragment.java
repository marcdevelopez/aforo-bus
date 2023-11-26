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

public class MenuContextualMaquinaDialogFragment extends AppCompatDialogFragment {

    private TextView tvResetMaquina;
    private SharedPreferences mSharedPref;
    private FragmentManager mSuportFragmentManager;

    // crear constructor con parametro para saber si viene de maquina o de manual...
    public MenuContextualMaquinaDialogFragment(SharedPreferences sharedPref,
                                               FragmentManager suportFragmentManager) {
        mSharedPref = sharedPref;
        mSuportFragmentManager = suportFragmentManager;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SharedPreferences.Editor editorPref = mSharedPref.edit();
        AlertDialog alertDialog; // necesario para poder hacer dimish en alguna view...
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_menu_contextual_maquina, null);
        builder.setView(view);
        alertDialog = builder.create();
        // para hacer transparente el fondo del dialogo, comprobar que es androidx.appcompat.app.AlertDialog:
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvResetMaquina = view.findViewById(R.id.tv_reset_maquina);

        tvResetMaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* abrira dialogo avisando del cambio a realizar, pero si se selecciono que no se
                muestre mas el aviso, pasará directamente al teclado
                                 */
                alertDialog.dismiss(); // no se nos olvide cerrar el actual dialogo...
                if (!mSharedPref.getBoolean(
                        "NO_SE_MUESTRA_AVISO_ALERT_RESET_MAQUINA",
                        false)) {
                    CharSequence[] check = new CharSequence[1];
                    ArrayList selected = new ArrayList();
                    check[0] = getResources().getString(R.string.no_volver_a_mostrar);
                    AlertDialog.Builder avisoBuiderReset = new AlertDialog.Builder(getActivity());
                    avisoBuiderReset.setTitle(getResources().getString(
                            R.string.titulo_alert_dialog_reset_maquina_explicacion));
                    avisoBuiderReset.setMultiChoiceItems(check, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                selected.add(which);
                                // guardamos la seleccion
                                editorPref.putBoolean("NO_SE_MUESTRA_AVISO_ALERT_RESET_MAQUINA", true);
                                editorPref.apply();
                            } else if (selected.contains(which)) {
                                // cambiamos la seleccion
                                selected.remove(Integer.valueOf(which)); // si es deseleccionado tambien lo quita
                                editorPref.putBoolean("NO_SE_MUESTRA_AVISO_ALERT_RESET_MAQUINA", false);
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
        });

        return alertDialog;
    }

    private void abreDialogoTeclado() {
        TecladoDialogFragment tecladoDialogFragment = new TecladoDialogFragment(
                Contract.PETICION_TECLADO_RESET_MAQUINA);
        tecladoDialogFragment.show(mSuportFragmentManager, "teclado dialogo");
    }
}
