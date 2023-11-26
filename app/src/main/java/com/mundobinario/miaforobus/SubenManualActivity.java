package com.mundobinario.miaforobus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.mundobinario.miaforobus.modelo.Modelo;
import com.mundobinario.miaforobus.modelo.data.Contract;

import java.util.ArrayList;

public class SubenManualActivity extends AppCompatActivity {
    public static final String DATOS_TECLADO_SUBEN_MANUAL = "com.mundobinario.miaforobus.DATOS_SUBEN_MANUAL";

    private TextView subtotalSubenTextView;
    private TextView totalSubenTextView;
    private TextView mas1, mas2, mas3, mas4, mas5, mas6, mas7, mas8, mas9, mas10, mas11, mas12, mas13,
            mas14, mas15, mas16, mas17, mas18, mas19, mas20, mas21, mas22;
    private LinearLayoutCompat layoutBotonRectificar;
    private LinearLayoutCompat layoutBotonEnviarSuben;
    private int resultadoSuben;
    private boolean introducidaCantidadEnTeclado;
    private boolean esPrimeraVezPulsaTecla;
    private ArrayList<Integer> cantidadesSubtotal;
    // SharedPreferences:
    private SharedPreferences appSharedPref;
    private SharedPreferences.Editor appSharedPrefEditor;
    private SharedPreferences horaListaSharedPref;
    private SharedPreferences.Editor horaListaSharedPrefEditor;
    private SharedPreferences iconoSubeBajaListaSharedPref;
    private SharedPreferences.Editor iconoSubeBajaListaSharedPrefEditor;
    private SharedPreferences movimientoListaSharedPref;
    private SharedPreferences.Editor movimientoListaSharedPrefEditor;
    private SharedPreferences viajerosListaSharedPref;
    private SharedPreferences.Editor viajerosListaSharedPrefEditor;
    private SharedPreferences ocupacionListaSharedPref;
    private SharedPreferences.Editor ocupacionListaSharedPrefEditor;
    private SharedPreferences iconoBilletesListaSharedPref;
    private SharedPreferences.Editor iconoBilletesListaSharedPrefEditor;

    private int totalSuben;
    private int totalSubenViajeActual;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // oculta barra navegación, está en todas las activities
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        appSharedPref = getSharedPreferences(Contract.STRING_SHARED_PREF, Context.MODE_PRIVATE);
        // establece orientación elegida en menu:
        Modelo.setScreenOrientation(appSharedPref, SubenManualActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suben_manual);

        // recibimos el intExtra de MainActivity para totalSubenViajeActual, necesario para hayar
        // maxima ocupacion si hay viajeros sin billete (anterior viaje)
        totalSubenViajeActual = getIntent().getIntExtra("totalSubenViajeActual", 0);
        introducidaCantidadEnTeclado = false;
        esPrimeraVezPulsaTecla = true;
        resultadoSuben = 0;
        cantidadesSubtotal = new ArrayList();


        // inicialización de objetos SahredPreferences:
        appSharedPref = getSharedPreferences(Contract.STRING_SHARED_PREF, Context.MODE_PRIVATE);
        appSharedPrefEditor = appSharedPref.edit();
        // establece orientación elegida en menú:
        Modelo.setScreenOrientation(appSharedPref, SubenManualActivity.this);


        horaListaSharedPref = getSharedPreferences(Contract.STRING_HORA_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        horaListaSharedPrefEditor = horaListaSharedPref.edit();
        iconoSubeBajaListaSharedPref = getSharedPreferences(Contract.STRING_ICONO_SUBE_BAJA_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        iconoSubeBajaListaSharedPrefEditor = iconoSubeBajaListaSharedPref.edit();
        movimientoListaSharedPref = getSharedPreferences(Contract.STRING_MOVIMIENTO_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        movimientoListaSharedPrefEditor = movimientoListaSharedPref.edit();
        viajerosListaSharedPref = getSharedPreferences(Contract.STRING_VIAJEROS_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        viajerosListaSharedPrefEditor = viajerosListaSharedPref.edit();
        ocupacionListaSharedPref = getSharedPreferences(Contract.STRING_OCUPACION_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        ocupacionListaSharedPrefEditor = ocupacionListaSharedPref.edit();
        iconoBilletesListaSharedPref = getSharedPreferences(Contract.STRING_ICONO_BILLETE_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        iconoBilletesListaSharedPrefEditor = iconoBilletesListaSharedPref.edit();

        totalSuben = appSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0);

        subtotalSubenTextView = findViewById(R.id.text_view_subtotal_suben_manual_teclado);
        totalSubenTextView = findViewById(R.id.text_view_total_suben_manual_teclado);
        mas1 = findViewById(R.id.tecla1_suben_manual);
        mas2 = findViewById(R.id.tecla2_suben_manual);
        mas3 = findViewById(R.id.tecla3_suben_manual);
        mas4 = findViewById(R.id.tecla4_suben_manual);
        mas5 = findViewById(R.id.tecla5_suben_manual);
        mas6 = findViewById(R.id.tecla6_suben_manual);
        mas7 = findViewById(R.id.tecla7_suben_manual);
        mas8 = findViewById(R.id.tecla8_suben_manual);
        mas9 = findViewById(R.id.tecla9_suben_manual);
        mas10 = findViewById(R.id.tecla10_suben_manual);
        mas11 = findViewById(R.id.tecla11_suben_manual);
        mas12 = findViewById(R.id.tecla12_suben_manual);
        mas13 = findViewById(R.id.tecla13_suben_manual);
        mas14 = findViewById(R.id.tecla14_suben_manual);
        mas15 = findViewById(R.id.tecla15_suben_manual);
        mas16 = findViewById(R.id.tecla16_suben_manual);
        mas17 = findViewById(R.id.tecla17_suben_manual);
        mas18 = findViewById(R.id.tecla18_suben_manual);
        mas19 = findViewById(R.id.tecla19_suben_manual);
        mas20 = findViewById(R.id.tecla20_suben_manual);
        mas21 = findViewById(R.id.tecla21_suben_manual);
        mas22 = findViewById(R.id.tecla22_suben_manual);
        layoutBotonEnviarSuben = findViewById(R.id.linearlayout_boton_enviar_suben_manual);
        layoutBotonRectificar = findViewById(R.id.linearlayout_boton_rectificar_suben_manual);

        // creamos objeto Animation para las teclas:
        Animation animacionTecla = AnimationUtils.loadAnimation(SubenManualActivity.this,
                R.anim.animacion_teclas_bajan_suben_manual);
        Animation animacionEnviarRectificar = AnimationUtils.loadAnimation(SubenManualActivity.this,
                R.anim.decrece_y_crece_tecla_teclado);

        // SOLO CUANDO ESTÉ VISIBLE EL TITULO, AL PULSAR SOBRE EL, ABRIRA DIALOG EXPLICANDO FUNCION
        subtotalSubenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (esPrimeraVezPulsaTecla) { // aun no ha introducido cantidades
                    AlertDialog.Builder dialogoExplicativo =
                            new AlertDialog.Builder(SubenManualActivity.this);
                    dialogoExplicativo.setTitle(R.string.titulo_dialog_cada_tecla_es_una_cantidad);
                    dialogoExplicativo.setMessage(R.string.mensaje_dialog_se_iran_sumando_cantidades);
                    dialogoExplicativo.setPositiveButton(getString(R.string.cerrar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // corre el dimish(), sin mas cierra el dialogo...
                        }
                    });
                    dialogoExplicativo.show();
                }
            }
        });

        mas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas1.startAnimation(animacionTecla);
                actualizaResultadoTeclado(1);
                cantidadesSubtotal.add(1);
            }
        });
        mas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas2.startAnimation(animacionTecla);
                actualizaResultadoTeclado(2);
                cantidadesSubtotal.add(2);
            }
        });
        mas3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas3.startAnimation(animacionTecla);
                actualizaResultadoTeclado(3);
                cantidadesSubtotal.add(3);
            }
        });
        mas4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas4.startAnimation(animacionTecla);
                actualizaResultadoTeclado(4);
                cantidadesSubtotal.add(4);
            }
        });
        mas5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas5.startAnimation(animacionTecla);
                actualizaResultadoTeclado(5);
                cantidadesSubtotal.add(5);
            }
        });
        mas6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas6.startAnimation(animacionTecla);
                actualizaResultadoTeclado(6);
                cantidadesSubtotal.add(6);
            }
        });
        mas7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas7.startAnimation(animacionTecla);
                actualizaResultadoTeclado(7);
                cantidadesSubtotal.add(7);
            }
        });
        mas8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas8.startAnimation(animacionTecla);
                actualizaResultadoTeclado(8);
                cantidadesSubtotal.add(8);
            }
        });
        mas9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas9.startAnimation(animacionTecla);
                actualizaResultadoTeclado(9);
                cantidadesSubtotal.add(9);
            }
        });
        mas10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas10.startAnimation(animacionTecla);
                actualizaResultadoTeclado(10);
                cantidadesSubtotal.add(10);
            }
        });
        mas11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas11.startAnimation(animacionTecla);
                actualizaResultadoTeclado(11);
                cantidadesSubtotal.add(11);
            }
        });
        mas12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas12.startAnimation(animacionTecla);
                actualizaResultadoTeclado(12);
                cantidadesSubtotal.add(12);
            }
        });
        mas13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas13.startAnimation(animacionTecla);
                actualizaResultadoTeclado(13);
                cantidadesSubtotal.add(13);
            }
        });
        mas14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas14.startAnimation(animacionTecla);
                actualizaResultadoTeclado(14);
                cantidadesSubtotal.add(14);
            }
        });
        mas15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas15.startAnimation(animacionTecla);
                actualizaResultadoTeclado(15);
                cantidadesSubtotal.add(15);
            }
        });
        mas16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas16.startAnimation(animacionTecla);
                actualizaResultadoTeclado(16);
                cantidadesSubtotal.add(16);
            }
        });
        mas17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas17.startAnimation(animacionTecla);
                actualizaResultadoTeclado(17);
                cantidadesSubtotal.add(17);
            }
        });
        mas18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas18.startAnimation(animacionTecla);
                actualizaResultadoTeclado(18);
                cantidadesSubtotal.add(18);
            }
        });
        mas19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas19.startAnimation(animacionTecla);
                actualizaResultadoTeclado(19);
                cantidadesSubtotal.add(19);
            }
        });
        mas20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas20.startAnimation(animacionTecla);
                actualizaResultadoTeclado(20);
                cantidadesSubtotal.add(20);
            }
        });
        mas21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas21.startAnimation(animacionTecla);
                actualizaResultadoTeclado(21);
                cantidadesSubtotal.add(21);
            }
        });
        mas22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas22.startAnimation(animacionTecla);
                actualizaResultadoTeclado(22);
                cantidadesSubtotal.add(22);
            }
        });

        layoutBotonRectificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidadesSubtotal.size() != 0) {
                    layoutBotonRectificar.startAnimation(animacionEnviarRectificar);
                    String totalSubenConEspacioPreUnos;
                    if (cantidadesSubtotal.size() == 1) {
                        /* si tiene solo 1 indice elimina el indice y actualiza los textviews a ""
                        cambia color gravedad y texto a subtotal para volver al inicio...
                        ademas cambia los booleanos de esprimeravez a true y el introducidacantida a false
                        ademas de poner a 0 el resultado
                                                */
                        cantidadesSubtotal.clear();
                        resultadoSuben = 0;
                        esPrimeraVezPulsaTecla = true;
                        introducidaCantidadEnTeclado = false;
                        subtotalSubenTextView.setText(getString(R.string.selecciona_cuantos_suben));
                        subtotalSubenTextView.setTextColor(getResources().getColor(R.color.white));
                        subtotalSubenTextView.setGravity(Gravity.CENTER);
                        totalSubenTextView.setText("");
                    } else {
                        /*si tiene más de 1 indice:
                        eliminará el último y sumará todos para actualizar el resultado
                        además cambiara los textview:
                        el subtotal: el primer numero sin +, el resto con + a su izquierda
                                                 */
                        cantidadesSubtotal.remove(cantidadesSubtotal.size() - 1); // eliminamos el ultimo indice
                        resultadoSuben = 0; // esnecesario resetear ya que lo sumamos ahora en el for:
                        for (int i = 0; i < cantidadesSubtotal.size(); i++) {
                            if (i == 0)
                                // AÑADE ESPACIO ANTES DEL 1 PARA MEJOR ESPACIADO:
                                subtotalSubenTextView.setText(
                                        Modelo.agregaEspacioPreUno(String.valueOf(cantidadesSubtotal.get(i)))); // añade la primera cantidad, sin signo +
                            else { // tiene mas de 1 indice el subtotal:
                                subtotalSubenTextView.setText(subtotalSubenTextView.getText().toString() +
                                        " + " + Modelo.agregaEspacioPreUno(String.valueOf(cantidadesSubtotal.get(i)))); // rellenamos el textview con todas las cantidades, incluido el signo +...
                            }
                            resultadoSuben += cantidadesSubtotal.get(i); // obtiene el total

                        }
                        totalSubenConEspacioPreUnos = Modelo.agregaEspacioPreUno("" + resultadoSuben);
                        totalSubenTextView.setText(totalSubenConEspacioPreUnos);
                    }


                } else {
                    Toast.makeText(SubenManualActivity.this,
                            getResources().getString(R.string.presiona_rectificar_aun_no_seleccionaste_nada),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        layoutBotonEnviarSuben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutBotonEnviarSuben.startAnimation(animacionEnviarRectificar);
                envioDatos(appSharedPrefEditor);
            }
        });


    }

    private void envioDatos(SharedPreferences.Editor editor) {
        if (resultadoSuben == 0) {
            Toast.makeText(this, R.string.no_hubo_cambios, Toast.LENGTH_SHORT).show();
            meVoyAlMainActivity();
        } else {
            // aqui va la comprobacion del tope aforo
            // comprueba que el dato obtenido en resultadoSuben es posible...
            int totalBajan = appSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
            int viajerosActuales = totalSuben - totalBajan;
            int posibleTotalViajeros = viajerosActuales + resultadoSuben;
            int aforo = appSharedPref.getInt(Contract.AFORO_SHARED_PREF, 93);
            if (posibleTotalViajeros > aforo) {
                int puedenSubir = aforo - viajerosActuales;
                int excedesElAforoEn = posibleTotalViajeros - aforo;
                // no es posible, asi que lanza un alertDialog avisando...
                AlertDialog.Builder builderAforoExcedido = new AlertDialog.Builder(this);
                builderAforoExcedido.setMessage(getString(
                        R.string.aforo_excedido_dialogo_teclado_suben_maquina, puedenSubir,
                        excedesElAforoEn));
                LayoutInflater imagenWarning = LayoutInflater.from(SubenManualActivity.this);
                final View vistaWarning = imagenWarning.inflate(R.layout.icono_warning, null);
                builderAforoExcedido.setView(vistaWarning);
                builderAforoExcedido.setPositiveButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // no hace nada solo vuelve al mainactivity...
                        Toast.makeText(SubenManualActivity.this, R.string.no_hubo_cambios, Toast.LENGTH_LONG).show();
                        // creamos el intent:
                        meVoyAlMainActivity();
                    }
                });
                builderAforoExcedido.show();
                // hasta aqui es la comprobacion
            } else {
                totalSuben = totalSuben + resultadoSuben;
                editor.putInt(Contract.TOTAL_SUBEN_SHARED_PREF, totalSuben);
                // Aqui va metodo respaldaViajePreCambiosYActualizaNuevoDatoEnViaje
                // y se modifica para que en vez de utilizar el objeto Viaje use directamente los datos de sharedPref
                // para que se actualicen sin necesidad de pasar objetos viaje entre actividades...
                Modelo.respaldaViajePreCambiosYActualizaNuevoDatoEnViaje(Contract.CAMBIO_SUBEN_MANUAL,
                        totalSuben, appSharedPref, appSharedPrefEditor);
                // crea y guarda cual fue el ultimo cambio en sharedPref:
                String mensaje =
                        "\n\n   "
                                + getString(R.string.aniadiste) + " "
                                + resultadoSuben
                                + " " + getString(R.string.viajeros_no_incluidos_en_maquina) + "\n\n";
                editor.putString(Contract.CUAL_FUE_ULTIMO_CAMBIO_SHARED_PREF, mensaje);
                // apply() es asincrono, asi no pausa la apilcacion en el hilo principal...
                editor.apply();
                // modificamos el tamaño del array guardado en sharedPref sumandole 1:
                int anteriorSizeListaCambios = appSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, 0);
                appSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF,
                        anteriorSizeListaCambios + 1);
                appSharedPrefEditor.apply();
                // se mueven todos los items de listaCambio en 1, para dejar libre el primero y poder agregar el nuevo item ahi:
                // creamos copia de actual listaCambio guardado en sharedPref:
                ItemListaCambios[] listaCambios = new ItemListaCambios[anteriorSizeListaCambios];
                for (int i = 0; i < anteriorSizeListaCambios; i++) {
                    listaCambios[i] = new ItemListaCambios(
                            horaListaSharedPref.getString(String.valueOf(i), ""),
                            iconoSubeBajaListaSharedPref.getInt(String.valueOf(i), 0),
                            movimientoListaSharedPref.getString(String.valueOf(i), "0"),
                            viajerosListaSharedPref.getString(String.valueOf(i), "0"),
                            ocupacionListaSharedPref.getString(String.valueOf(i), "0"),
                            iconoBilletesListaSharedPref.getInt(String.valueOf(i), 0)
                    );
                }
                // se empieza en int=1 para saltar el 0, ya que sera el nuevo item
                for (int i = 1; i < anteriorSizeListaCambios + 1; i++) {
                    horaListaSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getHora()); // se empieza desde el indice 0 de la copia del array listaCambios
                    horaListaSharedPrefEditor.apply();
                    iconoSubeBajaListaSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoSubeBaja());
                    iconoSubeBajaListaSharedPrefEditor.apply();
                    movimientoListaSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getMovimiento());
                    movimientoListaSharedPrefEditor.apply();
                    viajerosListaSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getViajeros());
                    viajerosListaSharedPrefEditor.apply();
                    ocupacionListaSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getOcupacion());
                    ocupacionListaSharedPrefEditor.apply();
                    iconoBilletesListaSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoBillete());
                    iconoBilletesListaSharedPrefEditor.apply();
                }
                addItemListCambiosSubenManual(aforo);
                // para deshacer, en las listacambios, es necesario saber qué lista/s tuvo/ieron el último cambio:
                // este dato de sharedpref tambien se guarda cuando suben manual y bajan. En Reset viajeros
                // no es necesario porque cuando se hace reset se inhabilita deshacer...
                appSharedPrefEditor.putInt(Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                        Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL);
                // es la actual porque la lista anterior solo se cambia en caso de bajar sin billete... y aqui se sube
                appSharedPrefEditor.apply();
                // creamos el intent:
                meVoyAlMainActivity();
            }
        }
    }

    private void addItemListCambiosSubenManual(int paramAforo) {
        // AQUI SE CREA EL ITEM NUEVO CON LOS NUEVOS DATOS DE SUBEN MANUAL:
        String horaActual = Modelo.getHoraActualHHmmssString();
        int suben = totalSuben;
        int bajan = appSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
        int viajeros = suben - bajan;
        int ocupacion = (viajeros * 100) / paramAforo;
        // datos para campo viajeros, en caso de viajeros sin billete:
        int totalSubenActualViajePostIncremento = totalSubenViajeActual + resultadoSuben;
        int viajerosSinBillete = viajeros - totalSubenActualViajePostIncremento;
        // VSB = viajeros sin billete
        // con esto se evita problemas de
        // resultado negativo en caso de errores al contar viajeros
        // ahora que tenemos los datos de VSB podemos podemos modificar viajeros
        // con la condicion de que haya o no VSB, ya que si hay viajeros del viaje anterior no se
        // tendrá en cuenta para la máxima ocupación
        if (viajerosSinBillete < 0) viajerosSinBillete = 0;
        String argViajeros;
        if (viajerosSinBillete > 0) {
            // modificamos el argumento viajeros para que muestre solo
            // viajeros del actual viaje para la máxima ocuoación
            argViajeros = totalSubenActualViajePostIncremento + " +" + viajerosSinBillete;
            // no olvidemos modificar dato ocupación para que refleje solo el de actual viaje:
            ocupacion = (totalSubenActualViajePostIncremento * 100) / paramAforo;
        } else {
            argViajeros = String.valueOf(viajeros);
        }
        horaListaSharedPrefEditor.putString(String.valueOf(0), horaActual);
        horaListaSharedPrefEditor.apply();
        iconoSubeBajaListaSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_mas_azul_24);
        iconoSubeBajaListaSharedPrefEditor.apply();
        movimientoListaSharedPrefEditor.putString(String.valueOf(0), String.valueOf(resultadoSuben));
        movimientoListaSharedPrefEditor.apply();
        viajerosListaSharedPrefEditor.putString(String.valueOf(0), argViajeros);
        viajerosListaSharedPrefEditor.apply();
        ocupacionListaSharedPrefEditor.putString(String.valueOf(0), ocupacion + "%");
        ocupacionListaSharedPrefEditor.apply();
        iconoBilletesListaSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_billete_sin_maquina_lista_cambios_24);
        iconoBilletesListaSharedPrefEditor.apply();

        // Aqui se comprueba que sea el primer suben de app para poner fecha al viaje, el resto de fechas desde inicio viaje...
        Modelo.setFechaPrimerMovimientoListaViaje(appSharedPref, appSharedPrefEditor);
    }

    private void meVoyAlMainActivity() {
        Intent intent = new Intent(SubenManualActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // ojo este método tiene: overridePendingTransition(0, R.anim.sevaporla_izq); para hacer animacion
    }

    private void actualizaResultadoTeclado(int numeroTecla) {

        if (esPrimeraVezPulsaTecla) {
            subtotalSubenTextView.setGravity(GravityCompat.END);
            subtotalSubenTextView.setTextColor(getResources().getColor(R.color.azul_pueden_subir));
            subtotalSubenTextView.setText("");
            esPrimeraVezPulsaTecla = false; /* necesario para que solo limpie subtotalSubenTextView
                                                                                   la primera vez */
        }
        // maneja EL SUBTOTAL:
        // AÑADE EL SIGNO + SI NO ES EL RPIMER VALOR AÑADIDO:
        if (introducidaCantidadEnTeclado) {
            subtotalSubenTextView.setText(subtotalSubenTextView.getText() + " + ");
        }
        subtotalSubenTextView.setText(subtotalSubenTextView.getText() +
                Modelo.agregaEspacioPreUno(String.valueOf(numeroTecla)));

        introducidaCantidadEnTeclado = true;

        // MANEJA EL RESULTADOSUBEN:
        resultadoSuben = resultadoSuben + numeroTecla;
        // CAMBIA EL TEXTO TOTALSUBEN Y AGREGA ESPACIO ANTES DEL 1:
        totalSubenTextView.setText(Modelo.agregaEspacioPreUno("" + resultadoSuben));

    }

    @Override
    // Muy importante sobrescribir este método ya que ahorro código en onBackPressed, y así sólo tengo que poner la animación aquí...
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.entraporla_der, R.anim.sevaporla_izq);
    }

/* Esta llamada es innecesaria porque ya lo realiza finish() en su método que he sobrescrito más arriba...
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.sevaporla_izq);
    }

 */

}