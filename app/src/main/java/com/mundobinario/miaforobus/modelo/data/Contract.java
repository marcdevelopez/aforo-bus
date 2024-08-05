package com.mundobinario.miaforobus.modelo.data;

public class Contract {

    // CalculoReduccionAforo:
    public static final String STRING_AFORO_SENTADO_SHARED_PREF = "AFORO_SENTADO";
    public static final String STRING_AFORO_EN_PIE_SHARED_PREF = "AFORO_EN_PIE";
    public static final String STRING_PORCENTAJE_SENTADO_SHARED_PREF = "PORCENTAJE_SENTADO";
    public static final String STRING_PORCENTAJE_EN_PIE_SHARED_PREF = "PORCENTAJE_EN_PIE";
    // EL TOTAL DEL AFORO REDUCIDO ESTÁ EN EL KEY STRING AFORO_SHARED_PREF "AFORO"

    // CONSTANTES PARA SHAREDPREFERENCES:
    public static final String STRING_SHARED_PREF = "STRING_SHARED_PREF";
    // LISTACAMBIOS ACTUAL:
    public static final String STRING_HORA_LISTA_CAMBIOS_ACTUAL_SHARED_PREF =
            "HORA_LISTA_CAMBIOS_ACTUAL";
    public static final String STRING_ICONO_SUBE_BAJA_ACTUAL_SHARED_PREF = "ICONO_SUBE_BAJA_ACTUAL";
    public static final String STRING_MOVIMIENTO_ACTUAL_SHARED_PREF = "MOVIMIENTO_ACTUAL";
    public static final String STRING_VIAJEROS_ACTUAL_SHARED_PREF = "VIAJEROS_ACTUAL";
    public static final String STRING_OCUPACION_ACTUAL_SHARED_PREF = "OCUPACION_ACTUAL";
    public static final String STRING_ICONO_BILLETE_ACTUAL_SHARED_PREF = "ICONO_BILLETE_ACTUAL";
    // LISTACAMBIOS ANTERIOR:
    public static final String STRING_HORA_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF =
            "HORA_LISTA_CAMBIOS_ANTERIOR";
    public static final String STRING_ICONO_SUBE_BAJA_ANTERIOR_SHARED_PREF =
            "ICONO_SUBE_BAJA_ANTERIOR";
    public static final String STRING_MOVIMIENTO_ANTERIOR_SHARED_PREF = "MOVIMIENTO_ANTERIOR";
    public static final String STRING_VIAJEROS_ANTERIOR_SHARED_PREF = "VIAJEROS_ANTERIOR";
    public static final String STRING_OCUPACION_ANTERIOR_SHARED_PREF = "OCUPACION_ANTERIOR";
    public static final String STRING_ICONO_BILLETE_ANTERIOR_SHARED_PREF = "ICONO_BILLETE_ANTERIOR";

    // sharedPref de viaje:
    public static final String MANEJO_MAQUINA_SHARED_PREF = "MANEJO_MAQUINA";
    public static final String TOTAL_SUBEN_SHARED_PREF = "TOTAL_SUBEN";
    public static final String MAQUINA_SHARED_PREF = "MAQUINA";
    public static final String TOTAL_BAJAN_SHARED_PREF = "TOTAL_BAJAN";
    public static final String AFORO_SHARED_PREF = "AFORO";
    public static final String TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF = "TOTAL_BAJAN_ACTUAL_VIAJE";
    public static final String TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF = "TOTAL_BAJAN_ANTERIOR_VIAJE";
    public static final String NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF = "NUM_TOTAL_BAJAN_PARA_TV";
    public static final String NUM_VIAJEROS_SIN_BILLETE_DESDE_AVISOS_SHARED_PREF =
            "NUM_VIAJEROS_SIN_BILLETE_DESDE_AVISOS";

    // sharedPref de respaldoViaje:
    public static final String MANEJO_MAQUINA_RESPALDO_SHARED_PREF = "RESPALDO_MANEJO_MAQUINA";
    public static final String TOTAL_SUBEN_RESPALDO_SHARED_PREF = "RESPALDO_TOTAL_SUBEN";
    public static final String MAQUINA_RESPALDO_SHARED_PREF = "RESPALDO_MAQUINA";
    public static final String TOTAL_BAJAN_RESPALDO_SHARED_PREF = "RESPALDO_TOTAL_BAJAN";
    public static final String AFORO_RESPALDO_SHARED_PREF = "RESPALDO_AFORO";

    // respaldo para totalesbajan
    public static final String TOTAL_BAJAN_ACTUAL_VIAJE_RESPALDO_SHARED_PREF =
            "TOTAL_BAJAN_ACTUAL_VIAJE_RESPALDO";
    public static final String TOTAL_BAJAN_ANTERIOR_VIAJE_RESPALDO_SHARED_PREF =
            "TOTAL_BAJAN_ANTERIOR_VIAJE_RESPALDO";
    public static final String NUM_TOTAL_BAJAN_PARA_TV_RESPALDO_SHARED_PREF =
            "NUM_TOTAL_BAJAN_PARA_TV_RESPALDO";

    // sharedPref de listaCambios:
    public static final String SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF = "SIZE_LISTA_CAMBIOS_ACTUAL";
    public static final String SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF = "SIZE_LISTA_CAMBIOS_ANTERIOR";
    public static final String SWITCH_MOSTRAR_VIAJE_ANTERIOR_SHARED_PREF =
            "SWITCH_MOSTRAR_VIAJE_ANTERIOR";
    public static final String STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF =
            "ULTIMA_LISTA_CAMBIOS_CAMBIADA";
    public static final String STRING_KEY_ES_PRIMER_MOVIMIENTO_APP_SHARED_PREF =
            "ES_PRIMER_MOVIMIENTO_APP";
    public static final String STRING_KEY_FECHA_ACTUAL_VIAJE_SHARED_PREF =
            "FECHA_ACTUAL_VIAJE";
    public static final String STRING_KEY_FECHA_ANTERIOR_VIAJE_SHARED_PREF =
            "FECHA_ANTERIOR_VIAJE";

    // otros sharedPref:
    public static final String ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF =
            "ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO";
    public static final String CUAL_FUE_ULTIMO_CAMBIO_SHARED_PREF = "CUAL_FUE_ULTIMO_CAMBIO";
    public static final String RESPALDO_VIAJE_INICIADO_SHARED_PREF = "RESPALDO_VIAJE_INICIADO";
    public static final String ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF =
            "ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE";
    public static final String ES_ULTIMO_CAMBIO_SUBEN_SHARED_PREF = "ES_ULTIMO_CAMBIO_SUBEN";
    public static final String ES_ORIENTACION_LANDSCAPE_SHARED_PREF = "ES_ORIENTACION_LANDSCAPE";

    // constantes para MANEJO RESPALDO Y DESHACER:
    public static final int CAMBIO_SUBEN_MAQUINA = 1;
    public static final int CAMBIO_SUBEN_MANUAL = 2;
    public static final int CAMBIO_BAJAN = 3;

    // constantes de String para manejar el DIALOGFRAMENT del TECLADO:
    public static final int PETICION_TECLADO_SUBEN_MAQUINA = 1;
    public static final int PETICION_TECLADO_TRAMO = 2;
    public static final int PETICION_TECLADO_RESET_VIAJEROS = 3;
    public static final int PETICION_TECLADO_RESET_MAQUINA = 4;

    // constantes de String para manejar el MenuDialogFragment Menu:
    public static final int ITEM_SELECCIONADO_SWITCH_LANDSCAPE = 1;

    // constantes int para saber que listacambios (actual o anterior o ambas) tuvo el último cambio:
    public static final int ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL = 1;
    public static final int ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ANTERIOR = 2;
    public static final int ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL_Y_ANTERIOR = 3;

    // ESTA CONSTANTE ES PARA EVITAR EJECUTAR ONPAUSE CON MISMAS INSTRUCCIONES QUE EN ONCREATE 2 VECES CONSECUTIVAS...
    public static final String ONCREATE_EJECUTADO_SHARED_PREF = "ONCREATE_EJECUTADO";

}
