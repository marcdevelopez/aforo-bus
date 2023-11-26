package com.mundobinario.miaforobus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mundobinario.miaforobus.modelo.Modelo;
import com.mundobinario.miaforobus.modelo.data.Contract;

public class BajanActivity extends AppCompatActivity {
    public static final String DATOS_TECLADO_BAJAN = "com.mundobinario.miaforobus.DATOS_BAJAN";
    private final int LISTA_ANTERIOR_CAMBIOS = 1;
    private final int LISTA_ACTUAL_CAMBIOS = 2;

    private TextView mas1, mas2, mas3, mas4, mas5, mas6, mas7, mas8, mas9, mas10, mas11, mas12, mas13,
            mas14, mas15, mas16, mas17, mas18, mas19, mas20, mas21, mas22, mas23, mas24, mas25, mas26,
            mas27, mas28;
    // SharedPreferences:
    private SharedPreferences appSharedPref;
    private SharedPreferences.Editor appSharedPrefEditor;
    // SharedPrefs listaActual:
    private SharedPreferences horaListaActualSharedPref;
    private SharedPreferences.Editor horaListaActualSharedPrefEditor;
    private SharedPreferences iconoSubeBajaListaActualSharedPref;
    private SharedPreferences.Editor iconoSubeBajaListaActualSharedPrefEditor;
    private SharedPreferences movimientoListaActualSharedPref;
    private SharedPreferences.Editor movimientoListaActualSharedPrefEditor;
    private SharedPreferences viajerosListaActualSharedPref;
    private SharedPreferences.Editor viajerosListaActualSharedPrefEditor;
    private SharedPreferences ocupacionListaActualSharedPref;
    private SharedPreferences.Editor ocupacionListaActualSharedPrefEditor;
    private SharedPreferences iconoBilletesListaActualSharedPref;
    private SharedPreferences.Editor iconoBilletesListaActualSharedPrefEditor;
    // SharedPrefs listaAnterior:
    private SharedPreferences horaListaAnteriorSharedPref;
    private SharedPreferences.Editor horaListaAnteriorSharedPrefEditor;
    private SharedPreferences iconoSubeBajaListaAnteriorSharedPref;
    private SharedPreferences.Editor iconoSubeBajaListaAnteriorSharedPrefEditor;
    private SharedPreferences movimientoListaAnteriorSharedPref;
    private SharedPreferences.Editor movimientoListaAnteriorSharedPrefEditor;
    private SharedPreferences viajerosListaAnteriorSharedPref;
    private SharedPreferences.Editor viajerosListaAnteriorSharedPrefEditor;
    private SharedPreferences ocupacionListaAnteriorSharedPref;
    private SharedPreferences.Editor ocupacionListaAnteriorSharedPrefEditor;
    private SharedPreferences iconoBilletesListaAnteriorSharedPref;
    private SharedPreferences.Editor iconoBilletesListaAnteriorSharedPrefEditor;

    private int totalBajan;
    private int numViajerosSinBillete;

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
        Modelo.setScreenOrientation(appSharedPref, BajanActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajan);


        appSharedPref = getSharedPreferences(Contract.STRING_SHARED_PREF, Context.MODE_PRIVATE);
        appSharedPrefEditor = appSharedPref.edit();

        Modelo.setScreenOrientation(appSharedPref, BajanActivity.this);

        // inicializamos objetos sharedpref para actualLista:
        horaListaActualSharedPref = getSharedPreferences(Contract.STRING_HORA_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        horaListaActualSharedPrefEditor = horaListaActualSharedPref.edit();
        iconoSubeBajaListaActualSharedPref = getSharedPreferences(Contract.STRING_ICONO_SUBE_BAJA_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        iconoSubeBajaListaActualSharedPrefEditor = iconoSubeBajaListaActualSharedPref.edit();
        movimientoListaActualSharedPref = getSharedPreferences(Contract.STRING_MOVIMIENTO_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        movimientoListaActualSharedPrefEditor = movimientoListaActualSharedPref.edit();
        viajerosListaActualSharedPref = getSharedPreferences(Contract.STRING_VIAJEROS_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        viajerosListaActualSharedPrefEditor = viajerosListaActualSharedPref.edit();
        ocupacionListaActualSharedPref = getSharedPreferences(Contract.STRING_OCUPACION_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        ocupacionListaActualSharedPrefEditor = ocupacionListaActualSharedPref.edit();
        iconoBilletesListaActualSharedPref = getSharedPreferences(Contract.STRING_ICONO_BILLETE_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        iconoBilletesListaActualSharedPrefEditor = iconoBilletesListaActualSharedPref.edit();

        // inicializamos objetos sharedpref para anteriorLista:
        horaListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_HORA_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        horaListaAnteriorSharedPrefEditor = horaListaAnteriorSharedPref.edit();
        iconoSubeBajaListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_ICONO_SUBE_BAJA_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        iconoSubeBajaListaAnteriorSharedPrefEditor = iconoSubeBajaListaAnteriorSharedPref.edit();
        movimientoListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_MOVIMIENTO_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        movimientoListaAnteriorSharedPrefEditor = movimientoListaAnteriorSharedPref.edit();
        viajerosListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_VIAJEROS_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        viajerosListaAnteriorSharedPrefEditor = viajerosListaAnteriorSharedPref.edit();
        ocupacionListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_OCUPACION_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        ocupacionListaAnteriorSharedPrefEditor = ocupacionListaAnteriorSharedPref.edit();
        iconoBilletesListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_ICONO_BILLETE_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        iconoBilletesListaAnteriorSharedPrefEditor = iconoBilletesListaAnteriorSharedPref.edit();

        totalBajan = appSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
        numViajerosSinBillete = appSharedPref.getInt(
                Contract.NUM_VIAJEROS_SIN_BILLETE_DESDE_AVISOS_SHARED_PREF, 0);

        mas1 = findViewById(R.id.tecla1_bajan);
        mas2 = findViewById(R.id.tecla2_bajan);
        mas3 = findViewById(R.id.tecla3_bajan);
        mas4 = findViewById(R.id.tecla4_bajan);
        mas5 = findViewById(R.id.tecla5_bajan);
        mas6 = findViewById(R.id.tecla6_bajan);
        mas7 = findViewById(R.id.tecla7_bajan);
        mas8 = findViewById(R.id.tecla8_bajan);
        mas9 = findViewById(R.id.tecla9_bajan);
        mas10 = findViewById(R.id.tecla10_bajan);
        mas11 = findViewById(R.id.tecla11_bajan);
        mas12 = findViewById(R.id.tecla12_bajan);
        mas13 = findViewById(R.id.tecla13_bajan);
        mas14 = findViewById(R.id.tecla14_bajan);
        mas15 = findViewById(R.id.tecla15_bajan);
        mas16 = findViewById(R.id.tecla16_bajan);
        mas17 = findViewById(R.id.tecla17_bajan);
        mas18 = findViewById(R.id.tecla18_bajan);
        mas19 = findViewById(R.id.tecla19_bajan);
        mas20 = findViewById(R.id.tecla20_bajan);
        mas21 = findViewById(R.id.tecla21_bajan);
        mas22 = findViewById(R.id.tecla22_bajan);
        mas23 = findViewById(R.id.tecla23_bajan);
        mas24 = findViewById(R.id.tecla24_bajan);
        mas25 = findViewById(R.id.tecla25_bajan);
        mas26 = findViewById(R.id.tecla26_bajan);
        mas27 = findViewById(R.id.tecla27_bajan);
        mas28 = findViewById(R.id.tecla28_bajan);

        // creamos objeto Animation para las teclas:
        Animation animacionTecla = AnimationUtils.loadAnimation(BajanActivity.this,
                R.anim.animacion_teclas_bajan_suben_manual);
        animacionTecla.setFillAfter(true); // mantiene la animacion despues de terminar,
        // en xml android:fillAfter="true" no trabaja, al menos en v19 kitkat...

        mas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas1.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 1);
            }
        });
        mas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas2.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 2);
            }
        });
        mas3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas3.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 3);
            }
        });
        mas4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas4.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 4);
            }
        });
        mas5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas5.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 5);
            }
        });
        mas6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas6.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 6);
            }
        });
        mas7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas7.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 7);
            }
        });
        mas8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas8.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 8);
            }
        });
        mas9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas9.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 9);
            }
        });
        mas10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas10.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 10);
            }
        });
        mas11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas11.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 11);
            }
        });
        mas12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas12.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 12);
            }
        });
        mas13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas13.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 13);
            }
        });
        mas14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas14.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 14);
            }
        });
        mas15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas15.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 15);
            }
        });
        mas16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas16.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 16);
            }
        });
        mas17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas17.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 17);
            }
        });
        mas18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas18.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 18);
            }
        });
        mas19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas19.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 19);
            }
        });
        mas20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas20.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 20);
            }
        });
        mas21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas21.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 21);
            }
        });
        mas22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas22.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 22);
            }
        });
        mas23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas23.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 23);
            }
        });
        mas24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas24.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 24);
            }
        });
        mas25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas25.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 25);
            }
        });
        mas26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas26.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 26);
            }
        });
        mas27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas27.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 27);
            }
        });
        mas28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mas28.startAnimation(animacionTecla);
                enviaDatos(appSharedPrefEditor, 28);
            }
        });
    }

    private void enviaDatos(SharedPreferences.Editor editor, int cantidadBajan) {
        // ACTUALIZAMOS Y GUARDAMOS DATO TOTAL BAJAN:
        // COMPROBAMOS QUE EL VIAJEROS ACTUALES NO SEA NEGATIVO:
        int totalSuben = appSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0);
        int viajerosActuales = totalSuben - totalBajan;
        if ((viajerosActuales - cantidadBajan) < 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            LayoutInflater imagenInfo = LayoutInflater.from(BajanActivity.this);
            final View vistaInfo = imagenInfo.inflate(R.layout.icono_warning, null);
            alertDialog.setView(vistaInfo);
            alertDialog.setMessage(getString(R.string.no_pueden_bajar_mas_de_los_viajeros_que_hay, viajerosActuales, cantidadBajan));
            alertDialog.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(BajanActivity.this, R.string.no_hubo_cambios, Toast.LENGTH_LONG).show();
                    meVoyAlMainActivity();
                }
            });
            alertDialog.show();
        } else { // RESPALDAR: realiza los cambios para la bajada de viajeros

            totalBajan = totalBajan + cantidadBajan;
            editor.putInt(Contract.TOTAL_BAJAN_SHARED_PREF, totalBajan);
            Modelo.respaldaViajePreCambiosYActualizaNuevoDatoEnViaje(Contract.CAMBIO_BAJAN, totalBajan,
                    appSharedPref, appSharedPrefEditor);
            // todo este mensaje habria que crearlo para respaldo para deshacer donde se crea el item de
            //  bajan ya que depende de que viajeros bajen si del actual o del anterior viaje, o de ambos
            String mensaje =
                    "\n\n   "
                            + getString(R.string.bajaron) + " "
                            + cantidadBajan + " "
                            + getString(R.string.viajeros_con_barra_s) + "\n\n";
            editor.putString(Contract.CUAL_FUE_ULTIMO_CAMBIO_SHARED_PREF, mensaje);
            // apply() es asincrono, asi no pausa la apilcacion en el hilo principal...
            editor.apply();

            if ((numViajerosSinBillete - cantidadBajan) > 0) { // quedan viajeros sin billete
                // actualizamos las variables para totalBajan del textview de bajan:
                int totalBajanViajeAnterior = appSharedPref.getInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF, 0);
                totalBajanViajeAnterior += cantidadBajan; // se suma cantidadbajan al anterior valor
                appSharedPrefEditor.putInt(
                        Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF, totalBajanViajeAnterior);
                appSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, totalBajanViajeAnterior);
                appSharedPrefEditor.apply();
                // esta cantidadBajan es la que se pone en la listaAnteriorCambios el primero
                // siguiendo la metodologia de esta clase para añadir un item lineas 345 a 403...
                // TODOS SON SIN BILLETE, DE MODO QUE TODOS A LA ANTERIOR LISTA:
                // EXISTE UNA EXCEPCION: SI ES TRAMO NO SE AÑADEN A LISTAANTERIOR, YA QUE EL AVISO
                // DE VIAJEROS SIN BILLETE ES DEL TRAMO ANTERIOR NO DEL VIAJE ANTERIOR, DE MODO QUE
                // SERIAN DEL ACTUAL VIAJE:
                // si es tramo va a guardar cambio en lista actual (ESTO SE HACE CON CADA CASO...):
                if (appSharedPref.getBoolean(
                        Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false)) {
                    agregaNuevoItemBajanListaCambios(cantidadBajan, LISTA_ACTUAL_CAMBIOS);
                    // para deshacer, las listacambios, es necesario saber qué lista/s tuvo/ieron el último cambio:
                    // este dato de sharedpref tambien se guarda cuando suben manual y bajan. En Reset viajeros
                    // no es necesario porque cuando se hace reset se inhabilita deshacer...
                    appSharedPrefEditor.putInt(Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                            Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL);
                    appSharedPrefEditor.apply();
                } else { // lo guarda en la anterior porque no es tramo sino viaje:
                    agregaNuevoItemBajanListaCambios(cantidadBajan, LISTA_ANTERIOR_CAMBIOS);
                    // para deshacer, las listacambios, es necesario saber qué lista/s tuvo/ieron el último cambio:
                    // este dato de sharedpref tambien se guarda cuando suben manual y bajan. En Reset viajeros
                    // no es necesario porque cuando se hace reset se inhabilita deshacer...
                    appSharedPrefEditor.putInt(Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                            Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ANTERIOR);
                    appSharedPrefEditor.apply();
                    // es la anterior porque la lista anterior solo se cambia en caso de bajar sin billete...
                }
            } else { // es igual o menos que 0, es decir ya se bajaron todos los viajeros sin billete
                // aqui se obtiene el numero que bajan del actual viaje:
                int diferenciaEntreNumVSByCantidadBajan = (numViajerosSinBillete - cantidadBajan);
                if (diferenciaEntreNumVSByCantidadBajan != 0) {
                    diferenciaEntreNumVSByCantidadBajan = Math.abs(diferenciaEntreNumVSByCantidadBajan);
                } // evita cambiar signo a 0
                // de modo que cantidad que bajan de anterior viaje debe ser
                // cantidadBajan - los que bajan del actual viaje
                // aqui se obtiene la cantidad que bajan sin billete:
                int totalbajanViajeAnteriorSinBillete = cantidadBajan - diferenciaEntreNumVSByCantidadBajan;
                int agregarATotalBajanViajeActual = diferenciaEntreNumVSByCantidadBajan;

                // se obtiene el totalbajan acumulado de actualviaje ya que no quedan del anterior sin billete
                // SI HAY VIAJEROS QUE BAJAN DE ESTE VIAJE: MODIFICA LA LISTA ACTUAL DE VIAJE:
                // excepcion: si es tramo, que bajaran todos en 1 sola cantidad en actual lista viaje:
                if (appSharedPref.getBoolean(
                        Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false)) {
                    // aqui es tramo:
                    agregaNuevoItemBajanListaCambios(cantidadBajan, LISTA_ACTUAL_CAMBIOS);
                    // el ultimo cambio es en la lista actual
                    appSharedPrefEditor.putInt(
                            Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                            Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL);
                    appSharedPrefEditor.apply();
                } else { // aqui no es tramo:
                    if (diferenciaEntreNumVSByCantidadBajan != 0) {
                        agregaNuevoItemBajanListaCambios(diferenciaEntreNumVSByCantidadBajan, LISTA_ACTUAL_CAMBIOS);
                    }
                    // SI HAY VIAJEROS QUE BAJAN SIN BILLETE MODIFICA LA LISTA ANTERIOR DE VIAJE:
                    // excepto si es tramo, que se guarda en la actual:
                    if (totalbajanViajeAnteriorSinBillete != 0) {
                        agregaNuevoItemBajanListaCambios(totalbajanViajeAnteriorSinBillete, LISTA_ANTERIOR_CAMBIOS);
                    }
                }

                // AQUI SE DETECTA CUAL FUE LA ULTIMA LISTADECAMBIOS CAMBIADA PARA LUEGO EN CASO DE
                // DESHACER PODER DESHACER LA ULTIMA LISTA...
                // SI NO ES TRAMO (YA SE GUARDO EN CASO DE TRAMO) SE HA DE GUARDAR  ULTIMO CAMBIO EN LA RESPECTIVA LISTA:
                if (!appSharedPref.getBoolean(
                        Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false)) {
                    if ((diferenciaEntreNumVSByCantidadBajan != 0) && (totalbajanViajeAnteriorSinBillete != 0)) {
                        // hubo cambio en ambas listas:
                        // de modo que gusrdamos en shared que el cambio fue en ambas listas para
                        // deshacer las 2 listas a la vez
                        appSharedPrefEditor.putInt(
                                Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                                Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL_Y_ANTERIOR);
                        appSharedPrefEditor.apply();
                    } else { // no hubo cambio en hambas listas:
                        // if para cada lista por separado
                        if (diferenciaEntreNumVSByCantidadBajan != 0) { // es la actual
                            appSharedPrefEditor.putInt(
                                    Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                                    Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL);
                            appSharedPrefEditor.apply();
                        }
                        if (totalbajanViajeAnteriorSinBillete != 0) { // es la anterior
                            appSharedPrefEditor.putInt(
                                    Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                                    Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ANTERIOR);
                            appSharedPrefEditor.apply();
                        }
                    }
                }

                // ESTE DATO ES PARA EL TEXTVIEW NUM TOTAL BAJAN:
                int totalBajanViajeActual = appSharedPref.getInt(
                        Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, 0);
                totalBajanViajeActual += agregarATotalBajanViajeActual; // se suma los del viaje actual
                appSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, totalBajanViajeActual);
                appSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, totalBajanViajeActual);
                appSharedPrefEditor.apply();
                // Como el método para agregar item a listacambios funciona igual para ambas
                //  listas, ya que lo que lo diferencia es solo la cantidad que bajan y la
                //  listacambios a modificar, se creará un método único con los 3 param
                //  llamado agregaNuevoItemBajanListaCambios
            }

            meVoyAlMainActivity();
        }
    }

    private void agregaNuevoItemBajanListaCambios(int cantidadBajanEnEstaLista, int cualListaCambios) {
        // todo: el siguiente codigo se puede hacer metodo en Modelo.java, con switch: agregaNuevoItemListaCambios
        // hora actual, viajeros y ocupacion son datos comunes, de modo que fuera de switch:
        String horaActual = Modelo.getHoraActualHHmmssString();
        int suben = appSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0);
        int bajan = appSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
        int viajeros = suben - bajan;
        int ocupacion = (viajeros * 100) / appSharedPref.getInt(Contract.AFORO_SHARED_PREF, 93); // por defecto es 80 porque es el dato inicail de la app...
        int anteriorSizeListaCambios;
        ItemListaCambios[] listaCambios;
        switch (cualListaCambios) {
            // AQUI CREA LA LISTA PARA ACTUAL VIAJE
            case LISTA_ACTUAL_CAMBIOS:
                // AQUI IRA LA ADICIÓN DEL NUEVO ITEM SI ES PARA LA LISTA ACTUAL DE CAMBIOS
                // modificamos el tamaño del array guardado en SharedPref sumandole 1:
                anteriorSizeListaCambios = appSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, 0);
                appSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF,
                        anteriorSizeListaCambios + 1);
                appSharedPrefEditor.apply();
                // se mueven todos los items de listaCambio en 1, para dejar libre el primero y poder
                // agregar el nuevo item ahi:
                // creamos copia de actual listaCambio guardado en sharedPref:
                listaCambios = new ItemListaCambios[anteriorSizeListaCambios];
                for (int i = 0; i < anteriorSizeListaCambios; i++) {
                    listaCambios[i] = new ItemListaCambios(
                            horaListaActualSharedPref.getString(String.valueOf(i), ""),
                            iconoSubeBajaListaActualSharedPref.getInt(String.valueOf(i), R.drawable.transparencia),
                            movimientoListaActualSharedPref.getString(String.valueOf(i), "0"),
                            viajerosListaActualSharedPref.getString(String.valueOf(i), "0"),
                            ocupacionListaActualSharedPref.getString(String.valueOf(i), "0"),
                            iconoBilletesListaActualSharedPref.getInt(String.valueOf(i), R.drawable.transparencia)
                    );
                }
                // Reordena datos en sharedpref: se empieza en int=1 para saltar el 0, ya que sera el nuevo item
                for (int i = 1; i < anteriorSizeListaCambios + 1; i++) {
                    horaListaActualSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getHora()); // se empieza desde el indice 0 de la copia del array listaCambios
                    horaListaActualSharedPrefEditor.apply();
                    iconoSubeBajaListaActualSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoSubeBaja());
                    iconoSubeBajaListaActualSharedPrefEditor.apply();
                    movimientoListaActualSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getMovimiento());
                    movimientoListaActualSharedPrefEditor.apply();
                    viajerosListaActualSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getViajeros());
                    viajerosListaActualSharedPrefEditor.apply();
                    ocupacionListaActualSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getOcupacion());
                    ocupacionListaActualSharedPrefEditor.apply();
                    iconoBilletesListaActualSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoBillete());
                    iconoBilletesListaActualSharedPrefEditor.apply();
                }
                // AQUI ES DONDE SE CREA EL ITEM DE BAJAN:
                horaListaActualSharedPrefEditor.putString(String.valueOf(0), horaActual);
                horaListaActualSharedPrefEditor.apply();
                iconoSubeBajaListaActualSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_menos_24);
                iconoSubeBajaListaActualSharedPrefEditor.apply();
                movimientoListaActualSharedPrefEditor.putString(String.valueOf(0), String.valueOf(cantidadBajanEnEstaLista));
                movimientoListaActualSharedPrefEditor.apply();
                viajerosListaActualSharedPrefEditor.putString(String.valueOf(0), String.valueOf(viajeros));
                viajerosListaActualSharedPrefEditor.apply();
                ocupacionListaActualSharedPrefEditor.putString(String.valueOf(0), String.valueOf(ocupacion) + "%");
                ocupacionListaActualSharedPrefEditor.apply();
                iconoBilletesListaActualSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_con_billete_listacambios);
                iconoBilletesListaActualSharedPrefEditor.apply();
                break;

            // AQUI CREA LA LISTA PARA ANTERIOR VIAJE
            case LISTA_ANTERIOR_CAMBIOS:
                // AQUI IRA LA ADICIÓN DEL NUEVO ITEM SI ES PARA LA LISTA ANTERIOR DE CAMBIOS
                // modificamos el tamaño del array guardado en SharedPref sumandole 1:
                anteriorSizeListaCambios = appSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, 0);
                appSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF,
                        anteriorSizeListaCambios + 1);
                appSharedPrefEditor.apply();
                // se mueven todos los items de listaCambio en 1, para dejar libre el primero y poder
                // agregar el nuevo item ahi:
                // creamos copia de actual listaCambio guardado en sharedPref:
                listaCambios = new ItemListaCambios[anteriorSizeListaCambios];
                for (int i = 0; i < anteriorSizeListaCambios; i++) {
                    listaCambios[i] = new ItemListaCambios(
                            horaListaAnteriorSharedPref.getString(String.valueOf(i), ""),
                            iconoSubeBajaListaAnteriorSharedPref.getInt(String.valueOf(i), R.drawable.transparencia),
                            movimientoListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                            viajerosListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                            ocupacionListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                            iconoBilletesListaAnteriorSharedPref.getInt(String.valueOf(i), R.drawable.transparencia)
                    );
                }
                // Reordena datos en sharedpref: se empieza en int=1 para saltar el 0, ya que sera el nuevo item
                for (int i = 1; i < anteriorSizeListaCambios + 1; i++) {
                    horaListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getHora()); // se empieza desde el indice 0 de la copia del array listaCambios
                    horaListaAnteriorSharedPrefEditor.apply();
                    iconoSubeBajaListaAnteriorSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoSubeBaja());
                    iconoSubeBajaListaAnteriorSharedPrefEditor.apply();
                    movimientoListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getMovimiento());
                    movimientoListaAnteriorSharedPrefEditor.apply();
                    viajerosListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getViajeros());
                    viajerosListaAnteriorSharedPrefEditor.apply();
                    ocupacionListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getOcupacion());
                    ocupacionListaAnteriorSharedPrefEditor.apply();
                    iconoBilletesListaAnteriorSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoBillete());
                    iconoBilletesListaAnteriorSharedPrefEditor.apply();
                }
                // AQUI ES DONDE SE CREA EL ITEM DE BAJAN:
                horaListaAnteriorSharedPrefEditor.putString(String.valueOf(0), horaActual);
                horaListaAnteriorSharedPrefEditor.apply();
                iconoSubeBajaListaAnteriorSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_menos_24);
                iconoSubeBajaListaAnteriorSharedPrefEditor.apply();
                movimientoListaAnteriorSharedPrefEditor.putString(String.valueOf(0), String.valueOf(cantidadBajanEnEstaLista));
                movimientoListaAnteriorSharedPrefEditor.apply();
                viajerosListaAnteriorSharedPrefEditor.putString(String.valueOf(0), String.valueOf(viajeros));
                viajerosListaAnteriorSharedPrefEditor.apply();
                ocupacionListaAnteriorSharedPrefEditor.putString(String.valueOf(0), String.valueOf(ocupacion) + "%");
                ocupacionListaAnteriorSharedPrefEditor.apply();
                iconoBilletesListaAnteriorSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_sin_billete_listacambios);
                iconoBilletesListaAnteriorSharedPrefEditor.apply();
                break;
        }

    }


    private void meVoyAlMainActivity() {
        // creamos el intent:
        Intent intent = new Intent(BajanActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // ojo este método tiene: overridePendingTransition(0, R.anim.sevaporla_izq); para hacer animacion
    }

    @Override
    // Muy importante sobrescribir este método ya que ahorro código en onBackPressed, y así sólo tengo que poner la animación aquí...
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.entraporla_izq, R.anim.sevaporla_der);
    }
}