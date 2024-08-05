package com.mundobinario.miaforobus.modelo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mundobinario.miaforobus.ItemListaCambios;
import com.mundobinario.miaforobus.R;
import com.mundobinario.miaforobus.modelo.data.Contract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Modelo {

    // variables necesarias para metodos:
    static int seleccionPicker;

    // se consigue que los cambios se realicen cuando el usuario los haya realizado y no antes
    public static void respaldaViajePreCambiosYActualizaNuevoDatoEnViaje(
            int tipoDeCambio,
            int valorDelCambio,
            SharedPreferences appSharedPref,
            SharedPreferences.Editor appSharedPrefEditor
    ) {
        // AQUI GUARDA EL RESPALDO:
        guardaRespaldoViajeEnSharedPef(appSharedPref, appSharedPrefEditor);
        appSharedPrefEditor.putBoolean(Contract.RESPALDO_VIAJE_INICIADO_SHARED_PREF, true);
        appSharedPrefEditor.apply();
        // AQUI ACTUALIZA CON LOS DATOS NUEVOS
        switch (tipoDeCambio) {
            case Contract.CAMBIO_SUBEN_MAQUINA:
                // viaje.setMaquina(valorDelCambio);
                // se guarda en sharedPref para que no se puerda al volver a la actividad, en vez de usar bundle...
                appSharedPrefEditor.putInt(Contract.MAQUINA_SHARED_PREF, valorDelCambio);
                appSharedPrefEditor.apply();
                break;
            case Contract.CAMBIO_SUBEN_MANUAL:
                // viaje.setTotalSuben(valorDelCambio);
                appSharedPrefEditor.putInt(Contract.TOTAL_SUBEN_SHARED_PREF, valorDelCambio);
                appSharedPrefEditor.apply();
                break;
            case Contract.CAMBIO_BAJAN:
                // viaje.setTotalBajan(valorDelCambio);
                appSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_SHARED_PREF, valorDelCambio);
                appSharedPrefEditor.apply();
                break;
        }
        actualizaCambioDeshacerEnFalse(appSharedPrefEditor);
    }

    public static void guardaRespaldoViajeEnSharedPef(
            SharedPreferences appSharedPref,
            SharedPreferences.Editor appSharedPrefEditor
    ) {
        // en vez de utilizar objeto viaje se utiliza el valor que tenga el sharedpref
        // que guarda los datos del objeto Viaje...
        appSharedPrefEditor.putInt(Contract.TOTAL_SUBEN_RESPALDO_SHARED_PREF,
                appSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0));
        appSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_RESPALDO_SHARED_PREF,
                appSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0));
        appSharedPrefEditor.putInt(Contract.AFORO_RESPALDO_SHARED_PREF,
                appSharedPref.getInt(Contract.AFORO_SHARED_PREF, 93));
        appSharedPrefEditor.putInt(Contract.MAQUINA_RESPALDO_SHARED_PREF,
                appSharedPref.getInt(Contract.MAQUINA_SHARED_PREF, 0));
        appSharedPrefEditor.putBoolean(Contract.MANEJO_MAQUINA_RESPALDO_SHARED_PREF,
                appSharedPref.getBoolean(Contract.MANEJO_MAQUINA_SHARED_PREF, true));
        // vamos a guardar los 3 datos de bajan PARA EL TEXTVIEW BAJAN antes de hacer nada para
        // poder guardar RESPALDO
        appSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_RESPALDO_SHARED_PREF,
                appSharedPref.getInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, 0));
        appSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_RESPALDO_SHARED_PREF,
                appSharedPref.getInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF, 0));
        appSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_RESPALDO_SHARED_PREF,
                appSharedPref.getInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, 0));
        appSharedPrefEditor.apply();
    }

    public static void actualizaCambioDeshacerEnFalse(SharedPreferences.Editor appSharedPrefEditor) {
        // guardar ultimoCambioFueDeshacer sharedPref como false
        appSharedPrefEditor.putBoolean(Contract.ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF, false);
        appSharedPrefEditor.apply();
        // actualizaIconoDeshacer(appSharedPref, iconoDeshacer);
        // el icono se actualiza en onResume...
    }


    public static void modificaAforoTeoricoYPorcentaje(
            String stringSharedPref,
            int aforoPorDefecto,
            int maxValuePicker,
            int minValuePicker,
            String titleDialog,
            TextView textView,
            TextView textViewTextoAforo,
            TextView textViewtotalReducido,
            NumberPicker numberPicker,
            AlertDialog.Builder builder,
            SharedPreferences sharedPreferences,
            SharedPreferences.Editor sharedPrefEditor
    ) {
        int aforoSentadoAntiguo = sharedPreferences.getInt(stringSharedPref, aforoPorDefecto);

        numberPicker.setMaxValue(maxValuePicker);
        numberPicker.setMinValue(minValuePicker);
        numberPicker.setValue(aforoSentadoAntiguo);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                seleccionPicker = i1;
            }
        });
        builder.setTitle(titleDialog);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                numberPicker.clearFocus();  /* GRACIAS A PONER ESTE METODO numberPicker.clearFocus()
                 AQUI SI ABRE TECLADO SE
                 PUEDE OBTENER EL VALOR DESDE EL ACEPTAR DEL DIALOG, SIN EL SOLO SE PUEDE OBTENER
                 EL VALOR ELEGIDO PULSANDO EL TECLADO DEL MOVIL
                */
                sharedPrefEditor.putInt(stringSharedPref, seleccionPicker);
                sharedPrefEditor.apply();
                // necesario poner % despues del seleccionPicker en caso de porcentajes:
                if (stringSharedPref.equals(Contract.STRING_PORCENTAJE_SENTADO_SHARED_PREF)
                        || stringSharedPref.equals(Contract.STRING_PORCENTAJE_EN_PIE_SHARED_PREF)) {
                    textView.setText("" + seleccionPicker + " %");
                } else {
                    textView.setText("" + seleccionPicker);
                }
                modificaTotalAforoReducido(
                        sharedPreferences.getInt(Contract.STRING_AFORO_SENTADO_SHARED_PREF, 29),
                        sharedPreferences.getInt(Contract.STRING_AFORO_EN_PIE_SHARED_PREF, 64),
                        sharedPreferences.getInt(Contract.STRING_PORCENTAJE_SENTADO_SHARED_PREF, 100),
                        sharedPreferences.getInt(Contract.STRING_PORCENTAJE_EN_PIE_SHARED_PREF, 100),
                        textViewTextoAforo, textViewtotalReducido,
                        sharedPrefEditor);
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    public static void modificaTotalAforoReducido(
            int viajerosSentados,
            int viajerosEnPie,
            int porcentajeSentados,
            int porcentajeEnPie,
            TextView totalTextoAforo,
            TextView totalNumeroReducido,
            SharedPreferences.Editor sharedPrefEditor
    ) {
        int sentadosReducido = (viajerosSentados * porcentajeSentados) / 100;
        int enPieReducido = (viajerosEnPie * porcentajeEnPie) / 100;
        String resultadoReducido = String.valueOf(sentadosReducido + enPieReducido);
        resultadoReducido = agregaEspacioPreUno(resultadoReducido);
        // aqui cambio el texto si porcentaje es 100 o no. necesito textviewtextototalaforo
        // si es 100 el porcentaje es que no esta reducido:
        if ((porcentajeEnPie == 100) && (porcentajeSentados == 100)) {
            totalTextoAforo.setText(R.string.total_de_aforo);
        } else {
            totalTextoAforo.setText(R.string.total_de_aforo_reducido);
        }
        // ahora cambiamos el numero total de aforo:
        totalNumeroReducido.setText(resultadoReducido);
        sharedPrefEditor.putInt(Contract.AFORO_SHARED_PREF,
                sentadosReducido + enPieReducido);
        sharedPrefEditor.apply();
    }

    // metodos comunes a varias activities, prepara aspecto de la vista:
    // este metodo devuelve un String con un espacio de mas delante de los 1....
    public static String agregaEspacioPreUno(String numeroSinEspacioPreUno) {
        String numeroConEspacioPreUno = "";
        // leo en bucle for el string, y cuando encuantra un 1 añade " 1", si no añade nmero tal cual
        for (int i = 0; i < numeroSinEspacioPreUno.length(); i++) {
            if (numeroSinEspacioPreUno.substring(i, i + 1).equals("1")) {
                numeroConEspacioPreUno += " 1";
            } else {
                numeroConEspacioPreUno += numeroSinEspacioPreUno.substring(i, i + 1);
            }
        }
        return numeroConEspacioPreUno;
    }

    public static int getBilletesMaquinaViajeActual(List<ItemListaCambios> listaCambios) {
        int subenConBilleteMaquina = 0;
        // recoge datos para subenmaquina:
        for (int i = 0; i < listaCambios.size(); i++) {
            // si este item de la lista es de billete maquina
            if (listaCambios.get(i).getIconoBillete() == R.drawable.ic_billete_maquina_lista_cambios_24)
                // suma a subenmaquina la cantidad de movimiento:
                subenConBilleteMaquina += Integer.parseInt(listaCambios.get(i).getMovimiento());
        }
        return subenConBilleteMaquina;
    }

    public static int getBilletesSinMaquinaViajeActual(List<ItemListaCambios> listaCambios) {
        int subenSinBilleteMaquina = 0;
        // recoge datos para subenmanual:
        for (int i = 0; i < listaCambios.size(); i++) {
            // si este item de la lista es de billete sin maquina
            if (listaCambios.get(i).getIconoBillete() == R.drawable.ic_billete_sin_maquina_lista_cambios_24)
                // suma a subenmanual la cantidad de movimiento:
                subenSinBilleteMaquina += Integer.parseInt(listaCambios.get(i).getMovimiento());
        }
        return subenSinBilleteMaquina;
    }

    public static int getTotalBajan(List<ItemListaCambios> listaCambios) {
        int totalBajan = 0;
        // recoge datos para bajan:
        for (int i = 0; i < listaCambios.size(); i++) {
            // si este item de la lista es de icono menos:
            if (listaCambios.get(i).getIconoSubeBaja() == R.drawable.ic_menos_24)
                // suma a totalBajan la cantidad de movimiento:
                totalBajan += Integer.parseInt(listaCambios.get(i).getMovimiento());
        }
        return totalBajan;
    }

    public static void setScreenOrientation(SharedPreferences sharedPreferences, AppCompatActivity activity) {
        boolean esOrientacionLandscape;
        esOrientacionLandscape = sharedPreferences.getBoolean(Contract.ES_ORIENTACION_LANDSCAPE_SHARED_PREF, false);
        if (esOrientacionLandscape)
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static String getFechaActualddMMyy() {
        Calendar fechaActual = new GregorianCalendar();
        String anio = String.valueOf(fechaActual.get(Calendar.YEAR));
        int beginIndex = anio.length() - 2;
        int endIndex = anio.length();
        anio = anio.substring(beginIndex, endIndex);
        int mes = fechaActual.get(Calendar.MONTH) + 1; // 0 = enero
        String stringMes = String.valueOf(mes);
        if (mes < 10) stringMes = "0" + mes;
        int dia = fechaActual.get(Calendar.DAY_OF_MONTH);
        String stringDia = String.valueOf(dia);
        if (dia < 10) stringDia = "0" + dia;
        if (Integer.parseInt(anio) < 10) anio = "0" + anio;
        int diaSemana = fechaActual.get(Calendar.DAY_OF_WEEK); // 1 = domingo, 7 = sabado
        int hora = fechaActual.get(Calendar.HOUR);
        int minuto = fechaActual.get(Calendar.MINUTE);
        int segundo = fechaActual.get(Calendar.SECOND);
        String fecha;
        fecha = stringDia + "/" + stringMes + "/" + anio;

        return fecha;
    }

    public static String getFechaAyerddMMyy() {
        Calendar fechaActual = new GregorianCalendar();
        // modificamos para hallar fecha de ayer:
        String ayer;
        fechaActual.add(Calendar.DATE, -1);
        Date date = fechaActual.getTime();
        String fecha;
        ayer = (new SimpleDateFormat("dd/MM/yy").format(date));
        return ayer;
    }

    public static String getHoraActualHHmmssString() {
        String horaActual;
        Calendar fechaActual = new GregorianCalendar();
        int hora = fechaActual.get(Calendar.HOUR_OF_DAY);
        int minuto = fechaActual.get(Calendar.MINUTE);
        String minuto0;
        String segundo0;
        int segundo = fechaActual.get(Calendar.SECOND);
        // añadimos 0 si el minuto o segundo es inferior a 10:
        if (minuto < 10) {
            minuto0 = "0" + minuto;
        } else minuto0 = "" + minuto;
        if (segundo < 10) {
            segundo0 = "0" + segundo;
        } else segundo0 = "" + segundo;
        horaActual = hora + ":" + minuto0 + ":" + segundo0;
        return horaActual;
    }

    public static String getHoraActualHHmmString() {
        Calendar fechaActual = new GregorianCalendar();
        // modificamos para hallar fecha de ayer:
        String horaMinuto;
        Date date = fechaActual.getTime();
        String fecha;
        horaMinuto = (new SimpleDateFormat("HH:mm").format(date));
        return horaMinuto;
    }

    // este método se usa cuando aun no hay primera fecha de viaje y se detecta cuando se introduce primera subida de viajeros
    public static void setFechaPrimerMovimientoListaViaje(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        if (sharedPreferences.getBoolean(
                Contract.STRING_KEY_ES_PRIMER_MOVIMIENTO_APP_SHARED_PREF, true)) {
            String fechaYHoraActual = getFechaActualddMMyy() + ", " + getHoraActualHHmmString();
            editor.putString(
                    Contract.STRING_KEY_FECHA_ACTUAL_VIAJE_SHARED_PREF, fechaYHoraActual);
            // se pasa a false ya que las proximas veces no serán 1er movimiento, ya que fue esta
            editor.putBoolean(
                    Contract.STRING_KEY_ES_PRIMER_MOVIMIENTO_APP_SHARED_PREF, false);
            editor.apply();
        }
    }

    public static void setFechaHoraListasViajes(
            SharedPreferences sharedPreferences, SharedPreferences.Editor editor, Resources resources) {
        // esta fecha se pasa al anterior viaje
        String fechaViajeActual = sharedPreferences.getString(
                Contract.STRING_KEY_FECHA_ACTUAL_VIAJE_SHARED_PREF,
                resources.getString(R.string.no_existe));
        editor.putString(
                Contract.STRING_KEY_FECHA_ANTERIOR_VIAJE_SHARED_PREF, fechaViajeActual);
        // ponemos fecha y hora actual a actualViaje:
        String fechaYHoraActual = getFechaActualddMMyy() + ", " + getHoraActualHHmmString();
        editor.putString(
                Contract.STRING_KEY_FECHA_ACTUAL_VIAJE_SHARED_PREF, fechaYHoraActual);
        editor.apply();
    }


}
