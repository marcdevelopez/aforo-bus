package com.mundobinario.miaforobus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;

import com.mundobinario.miaforobus.modelo.data.Contract;

public class MenuDialogFragment extends AppCompatDialogFragment {

    private FrameLayout ivAtras;
    private Button btPolitica;
    private Button btAyuda;
    private FragmentManager fragmentManager;

    // el listener para ejecutar seleccion en activityMain:
    private MenuDialogoListener listener;

    public MenuDialogFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.AnimacionMenuAjustes;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_menu, null);
        builder.setView(view);
        alertDialog = builder.create();


        // cargamos las views:
        ivAtras = view.findViewById(R.id.iv_atras);
        btPolitica = view.findViewById(R.id.bt_politica_privacidad);
        btAyuda = view.findViewById(R.id.bt_ayuda);

        ivAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss(); // cierra el dialogo del menu
            }
        });

        btPolitica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo: abrir politica en alert dialog
                PoliticaPrivacidadDialogFragment politica = new PoliticaPrivacidadDialogFragment();
                politica.show(fragmentManager,"POLITICA_FRAGMENT");
            }
        });

        btAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo: abrir ayuda con correo electronico en alert dialog
            }
        });

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (MenuDialogoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "debe implementar interface MenuDialogFragment");
        }
    }

    public interface MenuDialogoListener {
        // sobrecarga de métodos para los que no sean de switch
        // ejemplo:
        // void manejoItemsMenuDialog(int itemSeleccionado, boolean orientacionLandscape);

        void manejoItemsMenuDialog(int itemSeleccionado);
    }

}
