package com.mundobinario.miaforobus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.mundobinario.miaforobus.modelo.Modelo;
import com.mundobinario.miaforobus.modelo.data.Contract;
import com.mundobinario.miaforobus.vista.Vista;

public class CalculoReduccionAforo extends AppCompatActivity {
    private TextView textViewAforoTeoricoSentados;
    private TextView textViewAforoTeoricoEnPie;
    private TextView textViewPorcentajeSentados;
    private TextView textViewPorcentajeEnPie;
    private TextView textViewTextoTotalAforo;
    private TextView textViewNumeroTotalAforoReducido;
    private TextView textViewCancelarReduccionAforo;
    private TextView textViewAceptarReduccionAforo;

    private SharedPreferences appSharedPref;
    private SharedPreferences.Editor appSharedPrefEditor;

    // es necesario guardar los valores actuales antes de cambiarlos por si no son correctos
    // los nuevos valores restaurarlos:
    private int aforoTeoricoSentados;
    private int aforoTeoricoEnPie;
    private int porcentajeSentados;
    private int porcentajeEnPie;
    private int totalAforoReducido;
    private boolean esDeCancelarOAceptarOBack;

    @Override
    protected void onPause() {
        super.onPause();
        // si el aforo es 0 o es menor a viajeros:
        // volver a cargar valores iniciales... y avisar mediante toast que no se guardaron los valores,
        //  en caso contrario:
        //  dejar los valores y
        //  avisar que se cambio el aforo con exito
        if (!esDeCancelarOAceptarOBack)
            finalizaSinAceptarNiCancelar();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // para que no ejecute nada en onPause() (no muestre "aforo guardado")
        esDeCancelarOAceptarOBack = true;
        // deja all como estaba y avisa de que no se cambio nada
        restauraValores();
        Toast.makeText(this, getResources().getString(R.string.no_hubo_cambios), Toast.LENGTH_SHORT).show();
    }

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
        // actualiza la orientación configurada en menu
        Modelo.setScreenOrientation(appSharedPref, CalculoReduccionAforo.this);
        // es necesario cargar las views actualizadas con sus nuevos valores, por si sale y son
        // modificadas las views, al volver ya no mostrará datos antiguos que no son los guardados
        Vista.actualizaViewsReduccionAforoConDatosSharedPref(textViewAforoTeoricoSentados,
                textViewAforoTeoricoEnPie, textViewPorcentajeSentados, textViewPorcentajeEnPie,
                textViewTextoTotalAforo, textViewNumeroTotalAforoReducido, appSharedPref, appSharedPrefEditor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_reduccion_aforo);

        // esta variable es para que no ejecute método finalizaSinAceptarNiCancelar() si viene de aceptar o cancelar
        esDeCancelarOAceptarOBack = false;
        // inicializo Views:
        textViewAforoTeoricoSentados = findViewById(R.id.text_view_aforo_teorico_sentados);
        textViewAforoTeoricoEnPie = findViewById(R.id.text_view_aforo_teorico_en_pie);
        textViewPorcentajeSentados = findViewById(R.id.text_view_porcentaje_sentados);
        textViewPorcentajeEnPie = findViewById(R.id.text_view_porcentaje_en_pie);
        textViewTextoTotalAforo = findViewById(R.id.tv_texto_total_del_aforo);
        textViewNumeroTotalAforoReducido = findViewById(R.id.text_view_numero_total_aforo_reducido);
        textViewCancelarReduccionAforo = findViewById(R.id.text_view_cancelar_reduccion_aforo);
        textViewAceptarReduccionAforo = findViewById(R.id.button_aceptar_reduccion_aforo);

        appSharedPref = getSharedPreferences(Contract.STRING_SHARED_PREF, MODE_PRIVATE);
        appSharedPrefEditor = appSharedPref.edit();

        // guardamos los valores actuales:
        aforoTeoricoSentados = appSharedPref.getInt(Contract.STRING_AFORO_SENTADO_SHARED_PREF, 29);
        aforoTeoricoEnPie = appSharedPref.getInt(Contract.STRING_AFORO_EN_PIE_SHARED_PREF, 64);
        porcentajeSentados = appSharedPref.getInt(Contract.STRING_PORCENTAJE_SENTADO_SHARED_PREF, 100);
        porcentajeEnPie = appSharedPref.getInt(Contract.STRING_PORCENTAJE_EN_PIE_SHARED_PREF, 100);
        totalAforoReducido = appSharedPref.getInt(Contract.AFORO_SHARED_PREF, 93);

        // actualiza orientacion elegida desde el menu de la app
        Modelo.setScreenOrientation(appSharedPref, CalculoReduccionAforo.this);

        Vista.actualizaViewsReduccionAforoConDatosSharedPref(
                textViewAforoTeoricoSentados,
                textViewAforoTeoricoEnPie,
                textViewPorcentajeSentados,
                textViewPorcentajeEnPie,
                textViewTextoTotalAforo,
                textViewNumeroTotalAforoReducido,
                appSharedPref,
                appSharedPrefEditor
        );

        textViewAforoTeoricoSentados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberPicker mnumberPicker = new NumberPicker(CalculoReduccionAforo.this);
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(CalculoReduccionAforo.this)
                        .setView(mnumberPicker);
                Modelo.modificaAforoTeoricoYPorcentaje(
                        Contract.STRING_AFORO_SENTADO_SHARED_PREF,
                        29,
                        160,
                        0,
                        getString(R.string.establece_aforo_teorico_sentado),
                        textViewAforoTeoricoSentados,
                        textViewTextoTotalAforo,
                        textViewNumeroTotalAforoReducido,
                        mnumberPicker,
                        mbuilder,
                        appSharedPref,
                        appSharedPrefEditor);

            }
        });

        textViewAforoTeoricoEnPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberPicker mnumberPicker = new NumberPicker(CalculoReduccionAforo.this);
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(CalculoReduccionAforo.this)
                        .setView(mnumberPicker);
                Modelo.modificaAforoTeoricoYPorcentaje(
                        Contract.STRING_AFORO_EN_PIE_SHARED_PREF,
                        64,
                        160,
                        0,
                        getString(R.string.establece_aforo_teorico_en_pie),
                        textViewAforoTeoricoEnPie,
                        textViewTextoTotalAforo,
                        textViewNumeroTotalAforoReducido,
                        mnumberPicker,
                        mbuilder,
                        appSharedPref,
                        appSharedPrefEditor);
            }
        });

        textViewPorcentajeSentados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberPicker mnumberPicker = new NumberPicker(CalculoReduccionAforo.this);
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(CalculoReduccionAforo.this)
                        .setView(mnumberPicker);
                Modelo.modificaAforoTeoricoYPorcentaje(
                        Contract.STRING_PORCENTAJE_SENTADO_SHARED_PREF,
                        100,
                        100,
                        0,
                        getString(R.string.establece_porcentaje_sentado),
                        textViewPorcentajeSentados,
                        textViewTextoTotalAforo,
                        textViewNumeroTotalAforoReducido,
                        mnumberPicker,
                        mbuilder,
                        appSharedPref,
                        appSharedPrefEditor);
                Vista.actualizaViewsReduccionAforoConDatosSharedPref(
                        textViewAforoTeoricoSentados,
                        textViewAforoTeoricoEnPie,
                        textViewPorcentajeSentados,
                        textViewPorcentajeEnPie,
                        textViewTextoTotalAforo,
                        textViewNumeroTotalAforoReducido,
                        appSharedPref,
                        appSharedPrefEditor
                );
            }
        });

        textViewPorcentajeEnPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberPicker mnumberPicker = new NumberPicker(CalculoReduccionAforo.this);
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(CalculoReduccionAforo.this)
                        .setView(mnumberPicker);
                Modelo.modificaAforoTeoricoYPorcentaje(
                        Contract.STRING_PORCENTAJE_EN_PIE_SHARED_PREF,
                        100,
                        100,
                        0,
                        getString(R.string.establece_porcentaje_en_pie),
                        textViewPorcentajeEnPie,
                        textViewTextoTotalAforo,
                        textViewNumeroTotalAforoReducido,
                        mnumberPicker,
                        mbuilder,
                        appSharedPref,
                        appSharedPrefEditor);
                Vista.actualizaViewsReduccionAforoConDatosSharedPref(
                        textViewAforoTeoricoSentados,
                        textViewAforoTeoricoEnPie,
                        textViewPorcentajeSentados,
                        textViewPorcentajeEnPie,
                        textViewTextoTotalAforo,
                        textViewNumeroTotalAforoReducido,
                        appSharedPref,
                        appSharedPrefEditor
                );
            }
        });
        textViewCancelarReduccionAforo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // volver  a poner valores que habia al abrir la activity ya que no se confirmo los cambios
                restauraValores();
                Toast.makeText(CalculoReduccionAforo.this,
                        getResources().getString(R.string.no_hubo_cambios), Toast.LENGTH_SHORT).show();
                esDeCancelarOAceptarOBack = true;
                meVoyAMainActivity();
            }
        });

        textViewAceptarReduccionAforo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esDeCancelarOAceptarOBack = true;
                // necesario para evitar error de excepcion que imposibilita la app al mantener el error guardado en sharedpref del aforo...
                if (appSharedPref.getInt(Contract.AFORO_SHARED_PREF, 93) == 0) {
                    // poner los valores que habian al abrir la activity
                    Toast.makeText(CalculoReduccionAforo.this, R.string.aforo_no_puede_ser_0, Toast.LENGTH_LONG).show();
                    restauraValores();
                    meVoyAMainActivity();
                } else {
                    // comprobamos que hay menos viajeros que el aforo seleccionado para que al volver al main no de resultados raros...:
                    int viajerosActuales =
                            appSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0) -
                                    appSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
                    if (appSharedPref.getInt(Contract.AFORO_SHARED_PREF, 0) < viajerosActuales) {
                        AlertDialog.Builder builderAforoIncorrecto = new AlertDialog.Builder(CalculoReduccionAforo.this);
                        LayoutInflater imagenWarning = LayoutInflater.from(CalculoReduccionAforo.this);
                        final View vistaWarning = imagenWarning.inflate(R.layout.icono_warning, null);
                        builderAforoIncorrecto.setView(vistaWarning);
                        builderAforoIncorrecto.setMessage(R.string.aforo_menor_a_viajeros_alertdialog_warning);
                        builderAforoIncorrecto.setPositiveButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // poner los valores que habian al abrir la activity
                                restauraValores();
                                // solo muestra el Toast de "no hizo nada..."
                                Toast.makeText(CalculoReduccionAforo.this, R.string.no_hubo_cambios,
                                        Toast.LENGTH_LONG).show();
                                meVoyAMainActivity();
                            }
                        });
                        builderAforoIncorrecto.show();
                    } else {
                        // como ya está all guardado y no hay errorres solo se avisa que se guardó el aforo
                        Toast.makeText(CalculoReduccionAforo.this,
                                getResources().getString(R.string.aforo_guardado),
                                Toast.LENGTH_SHORT).show();
                        meVoyAMainActivity();
                    }
                }
            }
        });
    }

    private void meVoyAMainActivity() {
        Intent intent = new Intent(CalculoReduccionAforo.this, MainActivity.class);
        startActivity(intent);
        finish(); // ojo este método tiene: overridePendingTransition(0, R.anim.sevaporla_izq); para hacer animacion
    }

    private void restauraValores() {
        // para evitar datos erroneos cuando los datos introducidos no han sido correctos
        // ponemos en sharedpreferences los antiguos valores, ya que se cambiaron:
        appSharedPrefEditor.putInt(Contract.STRING_AFORO_SENTADO_SHARED_PREF, aforoTeoricoSentados);
        appSharedPrefEditor.putInt(Contract.STRING_AFORO_EN_PIE_SHARED_PREF, aforoTeoricoEnPie);
        appSharedPrefEditor.putInt(Contract.STRING_PORCENTAJE_SENTADO_SHARED_PREF, porcentajeSentados);
        appSharedPrefEditor.putInt(Contract.STRING_PORCENTAJE_EN_PIE_SHARED_PREF, porcentajeEnPie);
        appSharedPrefEditor.putInt(Contract.AFORO_SHARED_PREF, totalAforoReducido);
        appSharedPrefEditor.apply();
    }

    private void finalizaSinAceptarNiCancelar() {
        int aforo = appSharedPref.getInt(Contract.AFORO_SHARED_PREF, 93);
        int viajerosActuales =
                appSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0) -
                        appSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
        if (aforo == 0 || aforo < viajerosActuales) { // 2 unicas posibilidades de aforo erróneo
            restauraValores();
            Toast.makeText(this, getResources().getString(R.string.no_hubo_cambios), Toast.LENGTH_SHORT).show();
        } else { // se dejan los cambios realizados
            Toast.makeText(this, getResources().getString(R.string.aforo_guardado), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    // Muy importante sobrescribir este método ya que ahorro código en onBackPressed, y así sólo tengo que poner la animación aquí...
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.entrapor_abajo, R.anim.sevapor_arriba);
    }


}