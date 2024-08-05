package com.mundobinario.miaforobus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;

import com.mundobinario.miaforobus.modelo.Modelo;
import com.mundobinario.miaforobus.modelo.data.Contract;

public class TecladoDialogFragment extends AppCompatDialogFragment {

    private TextView tvResultadoTeclado, tvTituloTeclado;
    private String resultadoTeclado;
    private TextView tv_tecla1, tv_tecla2, tv_tecla3, tv_tecla4, tv_tecla5, tv_tecla6, tv_tecla7, tv_tecla8, tv_tecla9, tv_tecla0;
    private TextView tv_teclaCorregir;
    private FrameLayout frame_tecla_corregir_retroceso;
    private int mpeticionTeclado;
    // mpeticionTeclado se utiliza para cambiar el textview que pedirá qué datos introducir con el teclado...

    private TecladoDialogoListener listener;

    public TecladoDialogFragment(int peticionTeclado) {
        mpeticionTeclado = peticionTeclado;
    }

    // CON EL SIGUIENTE METODO OVERRIDE SE IMPLEMENTA LA ANIMACIÓN EN FRAGMENT
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // depende de la orientación la animación saldrá de un lugar o de otro
        boolean esLandscape = false;
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            esLandscape = true;

        // identifica que peticionteclado es y cambiar la animacion ... con switch o if...
        switch (mpeticionTeclado) {
            case Contract.PETICION_TECLADO_RESET_VIAJEROS:
                if (esLandscape) {
                    getDialog().getWindow()
                            .getAttributes().windowAnimations = R.style.LandAnimacionDialogoTecladoViajeros;
                } else {
                    getDialog().getWindow()
                            .getAttributes().windowAnimations = R.style.AnimacionDialogoTecladoViajeros;
                }
                break;
            case Contract.PETICION_TECLADO_SUBEN_MAQUINA: // al ponerlos seguidos sin break se ahorra poner 2 veces la misma animacion
            case Contract.PETICION_TECLADO_RESET_MAQUINA:
                if (esLandscape) {
                    getDialog().getWindow()
                            .getAttributes().windowAnimations = R.style.LandAnimacionDialogoTecladoMaquina;
                } else {
                    getDialog().getWindow()
                            .getAttributes().windowAnimations = R.style.AnimacionDialogoTecladoMaquina;
                }
                break;
            case Contract.PETICION_TECLADO_TRAMO:
                if (esLandscape) {
                    getDialog().getWindow()
                            .getAttributes().windowAnimations = R.style.LandAnimacionDialogoTecladoTramo;
                } else {
                    getDialog().getWindow()
                            .getAttributes().windowAnimations = R.style.AnimacionDialogoTecladoTramo;
                }
                break;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // ESTO POR LO QUE MAS QUIERAS NO LO CAMBIES NI HARTO VINO, GUARDALO COMO ORO EN PAÑO:
        AlertDialog alertDialog; // NECESARIO PARA LUEGO PODER HACER DIMISH() CON MI PROPIO BOTON ...
        // ... MAS ABAJO
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Si es orientacion landscape y es version mayor a 21
        // Se infla el layout dialog_fragment_teclado_v21.xml
        View view;
        int rotacion = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).
                getDefaultDisplay().getRotation();
        int horizontal = Surface.ROTATION_90;
        int horizontalInversa = Surface.ROTATION_270;
        if (
                (rotacion == horizontal || (rotacion == horizontalInversa))
                        &&
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        ) {
            // para landscape y version 21 o mayor
            view = inflater.inflate(R.layout.dialog_fragment_teclado_v21, null);
        } else {
            view = inflater.inflate(R.layout.dialog_fragment_teclado, null);
        }

        builder.setView(view);
        alertDialog = builder.create(); // CON EL METODO CREATE SE AÑADE EL VALOR A ALERTDIALOG ...
        // ... PARA LUEGO EN DIMISH PODER CERRAR EL DIALOGO
        // ajustar alto de tecla depende de alto de pantalla:
        // obtiene el alto de pantalla:
        int altoTotalPantalla;
        int anchoTotalPantalla;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getActivity().getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            altoTotalPantalla = windowMetrics.getBounds().height() - insets.top - insets.bottom;
            anchoTotalPantalla = windowMetrics.getBounds().width() - insets.left - insets.right;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            altoTotalPantalla = displayMetrics.heightPixels;
            anchoTotalPantalla = displayMetrics.widthPixels;
        }
        final float escalaDensidad = getResources().getDisplayMetrics().density;
        // paso 120dp (display) + 45dp (aceptarcancelar) + 60dp (barraestado y resto) = 225dp a px
        int altoOcupadoEnDp = 300;
        int alturaOcupada = (int) ((altoOcupadoEnDp * escalaDensidad) + 0.5f);
        int pixelsARestarEnAncho = (int) ((50 * escalaDensidad) + 0.5f);
        // Al alto de pantalla se le resta el resultado de arriba y obtengo el alto de teclado en px
        int altoTeclado = altoTotalPantalla - alturaOcupada;
        // con setlayoutparam establezco el alto del teclado. Solo para portrait
        // ojo, el alto o ancho, si esta en landscape cambia a la posicion actual
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutCompat linearContenedorTeclado = view.findViewById(R.id.linear_contenedor_teclado);
            linearContenedorTeclado.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    anchoTotalPantalla - pixelsARestarEnAncho,
                    altoTeclado));
            linearContenedorTeclado.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        } else { // landscape:


            LinearLayoutCompat linearContenedorTeclado = view.findViewById(R.id.linear_contenedor_teclado);
            LinearLayoutCompat linearContenedorDisplay = view.findViewById(R.id.linear_contenedor_display_teclado);

            // el ancho como esta en landscape es el alto...
            if (anchoTotalPantalla / escalaDensidad < 600) { // se verá recortado el display, tiene pocos dp de alto la pantalla...
                linearContenedorTeclado.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        (int) ((anchoTotalPantalla) * 0.35), (int) ((altoTotalPantalla) * 0.7)));
                linearContenedorDisplay.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        (int) ((anchoTotalPantalla) * 0.25), (int) ((altoTotalPantalla) * 0.7)));
            } else {
                linearContenedorTeclado.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        (int) ((anchoTotalPantalla) * 0.4), (int) ((altoTotalPantalla) * 0.7)));
                linearContenedorDisplay.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        (int) ((anchoTotalPantalla) * 0.2), (int) ((altoTotalPantalla) * 0.7)));
            }


            /*
            if (altoTotalPantalla >= 600) {
                LinearLayoutCompat linearContenedorTeclado = view.findViewById(R.id.linear_contenedor_teclado);
                linearContenedorTeclado.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        (int) ((altoTotalPantalla) / 1.2), (int) ((anchoTotalPantalla) / 2.9)));
                linearContenedorTeclado.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                LinearLayoutCompat linearContenedorDisplay = view.findViewById(R.id.linear_contenedor_display_teclado);
                linearContenedorDisplay.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        (int) ((altoTotalPantalla) / 3.5), (int) ((anchoTotalPantalla) / 2.9)));
                linearContenedorDisplay.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            } else {
                LinearLayoutCompat linearContenedorTeclado = view.findViewById(R.id.linear_contenedor_teclado);
                linearContenedorTeclado.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        (int) ((altoTotalPantalla)/1.4), (int) ((anchoTotalPantalla)/2.9)));
                linearContenedorTeclado.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                LinearLayoutCompat linearContenedorDisplay = view.findViewById(R.id.linear_contenedor_display_teclado);
                linearContenedorDisplay.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        (int) ((altoTotalPantalla)/3.5), (int) ((anchoTotalPantalla)/2.9)));
                linearContenedorDisplay.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            */

        }


        // cargamos las views:
        tvResultadoTeclado = view.findViewById(R.id.text_view_resultado_teclado);
        tvTituloTeclado = view.findViewById(R.id.textview_titulo_dialogo_teclado);
        tv_tecla1 = view.findViewById(R.id.tecla1);
        tv_tecla2 = view.findViewById(R.id.tecla2);
        tv_tecla3 = view.findViewById(R.id.tecla3);
        tv_tecla4 = view.findViewById(R.id.tecla4);
        tv_tecla5 = view.findViewById(R.id.tecla5);
        tv_tecla6 = view.findViewById(R.id.tecla6);
        tv_tecla7 = view.findViewById(R.id.tecla7);
        tv_tecla8 = view.findViewById(R.id.tecla8);
        tv_tecla9 = view.findViewById(R.id.tecla9);
        tv_tecla0 = view.findViewById(R.id.tecla0);
        tv_teclaCorregir = view.findViewById(R.id.tecla_corregir_c);
        frame_tecla_corregir_retroceso = view.findViewById(R.id.frame_tecla_corregir_retroceso);

        cambiaTituloTeclado();

        resultadoTeclado = "";

        // creamos objeto Animation para las teclas:
        Animation animacionTecla = AnimationUtils.loadAnimation(getContext(),
                R.anim.decrece_y_crece_tecla_teclado);

        tv_tecla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla1.startAnimation(animacionTecla);
                actualizaResultadoTeclado("1");
            }
        });
        tv_tecla2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla2.startAnimation(animacionTecla);
                actualizaResultadoTeclado("2");

            }
        });
        tv_tecla3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla3.startAnimation(animacionTecla);
                actualizaResultadoTeclado("3");

            }
        });
        tv_tecla4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla4.startAnimation(animacionTecla);
                actualizaResultadoTeclado("4");

            }
        });
        tv_tecla5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla5.startAnimation(animacionTecla);
                actualizaResultadoTeclado("5");

            }
        });
        tv_tecla6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla6.startAnimation(animacionTecla);
                actualizaResultadoTeclado("6");

            }
        });
        tv_tecla7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla7.startAnimation(animacionTecla);
                actualizaResultadoTeclado("7");

            }
        });
        tv_tecla8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla8.startAnimation(animacionTecla);
                actualizaResultadoTeclado("8");

            }
        });
        tv_tecla9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla9.startAnimation(animacionTecla);
                actualizaResultadoTeclado("9");

            }
        });
        tv_tecla0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tecla0.startAnimation(animacionTecla);
                actualizaResultadoTeclado("0");

            }
        });
        tv_teclaCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_teclaCorregir.startAnimation(animacionTecla);
                tvResultadoTeclado.setText("");
                resultadoTeclado = "";
            }
        });
        frame_tecla_corregir_retroceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame_tecla_corregir_retroceso.startAnimation(animacionTecla);
                // si resultadoteclado="" toast avisando de que no hay texto
                if (resultadoTeclado.equals("")) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.presiona_rectificar_aun_no_seleccionaste_nada),
                            Toast.LENGTH_SHORT).show();
                } else if (resultadoTeclado.length() == 1) {
                    // si el tamaño del string es 1 se deja resultado en "" y se borra el textview
                    resultadoTeclado = "";
                    tvResultadoTeclado.setText("");
                } else {
                    // se le quita a resultadoteclado el ultimo caracter, se guarda como resultadoTeclado
                    resultadoTeclado = resultadoTeclado.substring(0, resultadoTeclado.length() - 1);
                    // y se cambia el textview utilizando metodo agregaespaciopreuno
                    tvResultadoTeclado.setText(Modelo.agregaEspacioPreUno(resultadoTeclado));
                }
            }
        });

        // ESTA ES LA MANERA DE USAR MIS PROPIOS BOTONES EN UN ALERTDIALOG:
        TextView botonPositivo = view.findViewById(R.id.textview_aceptar_dialog_teclado);
        TextView botonNegativo = view.findViewById(R.id.textview_cancelar_dialog_teclado);
        botonPositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.aplicaResultadoTeclado(resultadoTeclado, mpeticionTeclado);
                alertDialog.dismiss(); // cierra dialog!
            }
        });

        botonNegativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cancela no hace nada solo cierra dialog
                alertDialog.dismiss(); // CIERRA EL DIALOGO, IMPORTANTE!
            }
        });

        // OJO NO SE HACE .SHOW, AQUI ES .CREATE DE ALERTDIALOG.BUILDER, ESTE ALERT LO INCLUYE YA...
        return alertDialog;

    }

    private void cambiaTituloTeclado() {
        switch (mpeticionTeclado) {
            case Contract.PETICION_TECLADO_RESET_VIAJEROS:
                tvTituloTeclado.setText(R.string.titulo_dialogo_teclado_reset_viajeros);
                break;
            case Contract.PETICION_TECLADO_SUBEN_MAQUINA:
                tvTituloTeclado.setText(R.string.titulo_dialogo_teclado_actualiza_billetes_maquina);
                break;
            case Contract.PETICION_TECLADO_TRAMO:
                tvTituloTeclado.setText(R.string.titulo_dialogo_teclado_billetes_maquina_tramo);
                break;
            case Contract.PETICION_TECLADO_RESET_MAQUINA:
                tvTituloTeclado.setText(R.string.titulo_teclado_resetea_maquina);
                break;
        }
    }

    private void actualizaResultadoTeclado(String valorTecla) {
        // no acepta cantidad mayor de 999:
        // primero comprueba si con nueva tecla excede el numero de 160
        // si no lo excede hace el proceso normalmente
        // si lo excede lanza el toast y no hace nada
        if (valorTecla.equals("1")) {
            if (Integer.parseInt(resultadoTeclado + valorTecla) > 999) { // se pasa de 160
                Toast.makeText(getContext(), getString(R.string.excedido_limite_viajeros), Toast.LENGTH_SHORT).show();
            } else {
                tvResultadoTeclado.setText(tvResultadoTeclado.getText() + " 1");
                resultadoTeclado = resultadoTeclado + valorTecla;
            }
        } else {
            if (Integer.parseInt(resultadoTeclado + valorTecla) > 999) { // se pasa de 160
                Toast.makeText(getContext(), getString(R.string.excedido_limite_viajeros), Toast.LENGTH_SHORT).show();
            } else {
                tvResultadoTeclado.setText(tvResultadoTeclado.getText() + valorTecla);
                resultadoTeclado = resultadoTeclado + valorTecla;
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (TecladoDialogoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "debe implementar interface TecladoDialogoListener");
        }
    }

    public interface TecladoDialogoListener {
        void aplicaResultadoTeclado(String resultadoTeclado, int peticionTeclado);
    }

}