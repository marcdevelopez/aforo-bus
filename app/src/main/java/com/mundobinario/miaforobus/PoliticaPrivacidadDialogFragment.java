package com.mundobinario.miaforobus;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class PoliticaPrivacidadDialogFragment extends DialogFragment {

    // el listener para ejecutar seleccion en activityMain:
    private PoliticaPrivacidadDialogoListener listener;

    public PoliticaPrivacidadDialogFragment() {
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
        View view = inflater.inflate(R.layout.dialog_fragment_politicas, null);
        builder.setView(view);
        alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PoliticaPrivacidadDialogoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "debe implementar interface MenuDialogFragment");
        }
    }

    public interface PoliticaPrivacidadDialogoListener {
        // sobrecarga de métodos para los que no sean de switch
        // ejemplo:
        // void manejoItemsMenuDialog(int itemSeleccionado, boolean orientacionLandscape);

        void manejoItemsMenuDialog(int itemSeleccionado);
    }

}
