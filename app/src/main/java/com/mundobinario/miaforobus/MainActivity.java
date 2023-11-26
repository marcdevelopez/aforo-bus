// PRIMERO CARGA ONCREATE Y LUEGO ONRESUME AL VOLVER DE OTRA ACTIVIDAD...

package com.mundobinario.miaforobus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mundobinario.miaforobus.entidades.Viaje;
import com.mundobinario.miaforobus.modelo.Modelo;
import com.mundobinario.miaforobus.modelo.data.Contract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TecladoDialogFragment.TecladoDialogoListener,
        MenuDialogFragment.MenuDialogoListener {

    // DECLARACIONES de VIEWS:
    private ImageView mIconoDeshacer;
    private FrameLayout frameBotonDeshacer;
    private ImageView mIconoManejoMaquina;
    private FrameLayout frameBotonManejoMaquina;
    private ImageView mIconoManejoManual;
    private FrameLayout frameBotonManejoManual;
    private FrameLayout frameBotonScreenOrientation;
    private FrameLayout frameBotonMenu;

    private LinearLayoutCompat mLayoutNuevoViaje;

    private TextView mTextViewViajerosActuales;

    private LinearLayoutCompat mLayoutTramo;
    private ImageView ivIcInicioTramo;
    private Button mBotonAforo;

    private ImageView ivIcSuben;
    private TextView textViewUltimoSuben;
    private ImageView ivIcBajan;
    private TextView textViewUltimoBajan;

    private FrameLayout mFrameBotonSuben;
    private ImageView mImageSubenMaquina;
    private ImageView mImageSubenManual;
    private LinearLayoutCompat mLinearIconoYNumBilletesManual;
    private TextView mTextViewNumBilletesManual;
    private LinearLayoutCompat linearHayMasBilletes;
    private ImageView ivHayMasBilletes;
    private TextView tvHayMasBilletes;
    private TextView mTextViewTotalSubenMaquina;
    private ImageView mImageViewBotonBajan;
    private TextView mTextViewNumTotalBajan;
    private TextView mTextViewNumTotalSuben;

    private TextView mTextViewPuedenSubir;
    private TextView mTextViewAvisosViaje;
    private CardView cardViewFondoAvisos;

    private ProgressBar progressActualOcupacion;
    private ProgressBar progressMaximaOcupacion;
    private TextView tvOcupacionActualPorcentaje;
    private TextView tvOcupacionMaximaPorcentaje;
    private TextView tvOcupacionMaximaViajeros;
    private TextView tvOcupacionMaximaHora;

    // objetos y variables que manejan los datos:
    private Viaje mViaje;
    private Viaje mRespaldoViaje;
    private List<ItemListaCambios> mListaCambiosActual;
    private List<ItemListaCambios> mListaCambiosAnterior;

    private boolean mUltimoCambioFueDeshacerOInicio;
    private boolean mRespaldoViajeIniciado;
    private boolean esPeticionTramoParaAvisoViajerosSinBillete;
    private boolean esUltimoCambioSuben;

    // cambia texto de textview mTextViewNumTotalBajan:
    private int totalBajanAntViaje;
    private int totalBajanActViaje;
    private int numTotalBajanParaTV; // este textview es el que se utiliza para el texto del textview

    private int numViajerosSinBillete;

    private String mCualFueUltimoCambio;

    // SharedPreferences:
    private SharedPreferences mAppSharedPref;
    private SharedPreferences.Editor mAppSharedPrefEditor;

    // constantes para agregar nuevo item a lista cambios:
    private final int LISTA_ANTERIOR_CAMBIOS = 1;
    private final int LISTA_ACTUAL_CAMBIOS = 2;

    private int movimientoListaCambios; // esta variable es necesaria para crear campo movimientos
    // en listacambios en resetviajeros, y para el textview de totalbajan

    private long backPressedTiempo = 0;
    private Toast backToast;

    // VIAJE ACTUAL:
    private SharedPreferences mHoraListaActualSharedPref;
    private SharedPreferences.Editor mHoraListaActualSharedPrefEditor;
    private SharedPreferences mIconoSubeBajaListaActualSharedPref;
    private SharedPreferences.Editor mIconoSubeBajaListaActualSharedPrefEditor;
    private SharedPreferences mMovimientoListaActualSharedPref;
    private SharedPreferences.Editor mMovimientoListaActualSharedPrefEditor;
    private SharedPreferences mViajerosListaActualSharedPref;
    private SharedPreferences.Editor mViajerosListaActualSharedPrefEditor;
    private SharedPreferences mOcupacionListaActualSharedPref;
    private SharedPreferences.Editor mOcupacionListaActualSharedPrefEditor;
    private SharedPreferences mIconoBilletesListaActualSharedPref;
    private SharedPreferences.Editor mIconoBilletesListaActualSharedPrefEditor;
    // VIAJE ANTERIOR:
    private SharedPreferences mHoraListaAnteriorSharedPref;
    private SharedPreferences.Editor mHoraListaAnteriorSharedPrefEditor;
    private SharedPreferences mIconoSubeBajaListaAnteriorSharedPref;
    private SharedPreferences.Editor mIconoSubeBajaListaAnteriorSharedPrefEditor;
    private SharedPreferences mMovimientoListaAnteriorSharedPref;
    private SharedPreferences.Editor mMovimientoListaAnteriorSharedPrefEditor;
    private SharedPreferences mViajerosListaAnteriorSharedPref;
    private SharedPreferences.Editor mViajerosListaAnteriorSharedPrefEditor;
    private SharedPreferences mOcupacionListaAnteriorSharedPref;
    private SharedPreferences.Editor mOcupacionListaAnteriorSharedPrefEditor;
    private SharedPreferences mIconoBilletesListaAnteriorSharedPref;
    private SharedPreferences.Editor mIconoBilletesListaAnteriorSharedPrefEditor;

    @Override
    public void onBackPressed() {
        backToast = Toast.makeText(
                getBaseContext(), getResources().getString(R.string.presiona_atras_para_salir),
                Toast.LENGTH_SHORT);
        // gracias codinginflow
        if (backPressedTiempo + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finishAffinity();
            return;
        } else {
            backToast.show();
        }
        backPressedTiempo = System.currentTimeMillis();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) { // para ocultar navegación:
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

        // establece la orientacion de la pantalla:
        Modelo.setScreenOrientation(mAppSharedPref, MainActivity.this);

        // oculta barra navegación, está en todas las activities
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // INICIO DE OBJETO SHAREDPREF:
        // ESTE OBJETO SHAREDPREF CREA ARCHIVO Y ES VISIBLE EN TODA LA APP
        mAppSharedPref = getSharedPreferences(Contract.STRING_SHARED_PREF, MODE_PRIVATE);
        mAppSharedPrefEditor = mAppSharedPref.edit();

        if (!mAppSharedPref.getBoolean(Contract.ONCREATE_EJECUTADO_SHARED_PREF, false)) {
            initSharedPrefs();
            construyeDatosYViewsActivity();
            /* se volvera a ejecutar este bloque de codigo si no ha ejecutado antes el onCreate()
            donde este boolean pasa a true y no ejecuta este bloque de nuevo
             */
            mAppSharedPrefEditor.putBoolean(Contract.ONCREATE_EJECUTADO_SHARED_PREF, false);
            mAppSharedPrefEditor.apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* // manejo de fecha:
        Calendar fechaActual = new GregorianCalendar();
        int anio = fechaActual.get(Calendar.YEAR);
        int mes = fechaActual.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DAY_OF_MONTH);
        int diaSemana = fechaActual.get(Calendar.DAY_OF_WEEK);
        int hora = fechaActual.get(Calendar.HOUR);
        int minuto = fechaActual.get(Calendar.MINUTE);
        int segundo = fechaActual.get(Calendar.SECOND);
         */

        setTVMaqDispLowHeigth();

        initSharedPrefs();

        iniciaViewsConFindViewById();

        construyeDatosYViewsActivity();

        // si es true ya no sera necesario actualizar datos y views en onPause:
        mAppSharedPrefEditor.putBoolean(Contract.ONCREATE_EJECUTADO_SHARED_PREF, true);
        mAppSharedPrefEditor.apply();

        // DESHACER:
        frameBotonDeshacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // solo se ejecuta si el ultimo cambio no fue deshacer o inicio, ya que entonces
                // será que ha habido algun cambio y se podrá deshacer:
                if (!mAppSharedPref.getBoolean(Contract.ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF, true)) {
                    mCualFueUltimoCambio = mAppSharedPref.getString(Contract.CUAL_FUE_ULTIMO_CAMBIO_SHARED_PREF, getString(R.string.no_hubo_cambios));
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(getString(R.string.mensaje_alertdialog_deshacer));
                    builder.setMessage(mCualFueUltimoCambio);
                    builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mUltimoCambioFueDeshacerOInicio = mAppSharedPref.getBoolean
                                    (Contract.ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF, true);
                            // si la anterior accion no fue pulsar iconoDeshacer:
                            if (!mUltimoCambioFueDeshacerOInicio && mRespaldoViajeIniciado) {
                                // llenar viaje con valores de respaldoViaje, siempre que respaldoViaje HAYA SIDO YA INICIADO UNA VEZ (respaldoViajeIniciado==true)
                                mViaje = (Viaje) mRespaldoViaje.clone();
                                // guardar en sharedPref viaje modificado
                                guardarViajeEnSharedPref();
                                // iconoDeshacer cambia a inactivo...dar true a ultimoCambioFueDeshacer y guardar en sharedPref
                                mIconoDeshacer.setImageResource(R.drawable.ic_deshacer_inactivo);
                                mUltimoCambioFueDeshacerOInicio = true;
                                mAppSharedPrefEditor.putBoolean(Contract.ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF, true);
                                mAppSharedPrefEditor.apply();
                                // se deshace las listasdecambios, pero depende de cual fue la
                                // ultima listacambio cambiada
                                if (mAppSharedPref.getInt(
                                        Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                                        Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL)
                                        == Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL) {
                                    deshaceListaCambiosActual();
                                }
                                if (mAppSharedPref.getInt(
                                        Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                                        Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL)
                                        == Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ANTERIOR) {
                                    deshaceListaCambiosAnterior();
                                }
                                if (mAppSharedPref.getInt(
                                        Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                                        Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL)
                                        == Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL_Y_ANTERIOR) {
                                    deshaceListaCambiosActual();
                                    deshaceListaCambiosAnterior();
                                }

                                // aqui vamos a cargar los datos del respaldo para textview bajan
                                // desde sharedpref a las variables de clase
                                // se hace respaldo cada vez que haya un cambio, asi se podrá
                                // restablecer en cualquier momento ya que siempre fue respaldado
                                // en el anterior cambio...
                                totalBajanActViaje = mAppSharedPref.getInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_RESPALDO_SHARED_PREF, 0);
                                totalBajanAntViaje = mAppSharedPref.getInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_RESPALDO_SHARED_PREF, 0);
                                numTotalBajanParaTV = mAppSharedPref.getInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_RESPALDO_SHARED_PREF, 0);
                                // y guardamos por si las moscas en sharedPref:
                                mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, totalBajanActViaje);
                                mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF, totalBajanAntViaje);
                                mAppSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, numTotalBajanParaTV);
                                mAppSharedPrefEditor.apply();
                                actualizaViewsDatosViaje(mTextViewViajerosActuales, mBotonAforo,
                                        mTextViewTotalSubenMaquina, mTextViewPuedenSubir);
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }

            }
        });

        // Modo Máquina:
        frameBotonManejoMaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mViaje.isManejoMaquina()) {
                    mIconoManejoMaquina.setImageResource(R.drawable.ic_maquina_seleccionado);
                    mIconoManejoManual.setImageResource(R.drawable.ic_sin_maquina_deseleccionado);
                    mImageSubenMaquina.setAlpha(1f);
                    mLinearIconoYNumBilletesManual.setAlpha(0f);
                    mImageSubenManual.setAlpha(0f);
                    mTextViewTotalSubenMaquina.setAlpha(1f);
                    mViaje.setManejoMaquina(true);
                    mAppSharedPrefEditor.putBoolean(Contract.MANEJO_MAQUINA_SHARED_PREF, true);
                    mAppSharedPrefEditor.apply();
                }
                actualizaTvHayMasBilletes();
            }
        });

        // Modo Manual:
        frameBotonManejoManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // aunque es redundante, este trozo de codigo evita el bug de que no aparezca el
                // totalsuben manual en textview cuando se inicia la app desde ondestroy
                // isManejoManual:
                mIconoManejoManual.setImageResource(R.drawable.ic_sin_maquina_seleccionado);
                mIconoManejoMaquina.setImageResource(R.drawable.ic_maquina_deseleccionado);
                mImageSubenMaquina.setAlpha(0f);
                if (getBilletesSinMaquinaViajeActual() > 0) {
                    mLinearIconoYNumBilletesManual.setAlpha(1f);
                    mTextViewNumBilletesManual.setText("" + getBilletesSinMaquinaViajeActual());

                } else {
                    mLinearIconoYNumBilletesManual.setAlpha(0f);
                    mTextViewNumBilletesManual.setText("");
                }
                mImageSubenManual.setAlpha(1f);
                mTextViewTotalSubenMaquina.setAlpha(0f);
                if (mViaje.isManejoMaquina()) {
                    mIconoManejoManual.setImageResource(R.drawable.ic_sin_maquina_seleccionado);
                    mIconoManejoMaquina.setImageResource(R.drawable.ic_maquina_deseleccionado);
                    mImageSubenMaquina.setAlpha(0f);
                    if (getBilletesSinMaquinaViajeActual() > 0) {
                        mLinearIconoYNumBilletesManual.setAlpha(1f);
                    } else {
                        mLinearIconoYNumBilletesManual.setAlpha(0f);
                    }

                    mImageSubenManual.setAlpha(1f);
                    mTextViewTotalSubenMaquina.setAlpha(0f);
                    mViaje.setManejoMaquina(false);
                    mAppSharedPrefEditor.putBoolean(Contract.MANEJO_MAQUINA_SHARED_PREF, false);
                    mAppSharedPrefEditor.apply();
                }
                actualizaTvHayMasBilletes();
            }
        });

        frameBotonScreenOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // guardamos en variable el estado actual de la orientación de pantalla, para saber cual es:
                boolean esOrientacionLandscape = mAppSharedPref.getBoolean(
                        Contract.ES_ORIENTACION_LANDSCAPE_SHARED_PREF, false);
                // todo:si es true cambiará a false en shared y llamará al método de Modelo
                //  .setScreenOrientation que cambia la orientación pasándole el shared y la activity,
                //  la orientación ya está guardada en shared y se usa en dicho método...
                if (esOrientacionLandscape) {
                    mAppSharedPrefEditor.putBoolean(Contract.ES_ORIENTACION_LANDSCAPE_SHARED_PREF, false);
                    mAppSharedPrefEditor.apply();
                } else {
                    mAppSharedPrefEditor.putBoolean(Contract.ES_ORIENTACION_LANDSCAPE_SHARED_PREF, true);
                    mAppSharedPrefEditor.apply();
                }
                // se llama al método que establece la orientación en la actual activity main:
                Modelo.setScreenOrientation(mAppSharedPref, MainActivity.this);
                // ocultamos barra estado y navegación:
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        });

        frameBotonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuDialogFragment menuDialogFragment = new MenuDialogFragment();
                menuDialogFragment.show(getSupportFragmentManager(), "MENU_DIALOG_FRAGMENT");
            }
        });

        // RESET:
        mLayoutNuevoViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // si viajeros != 0 boton aceptar sera "si" si no sera aceptar:
                String textoBotonAceptar;
                if (mViaje.getViajerosActuales() != 0)
                    textoBotonAceptar = getString(R.string.si);
                else textoBotonAceptar = getString(R.string.aceptar);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater imagenWarning = LayoutInflater.from(MainActivity.this);
                final View vistaWarning = imagenWarning.inflate(R.layout.icono_warning, null);
                builder.setView(vistaWarning);
                // carga el titulo directamente siempre
                builder.setTitle(R.string.titulo_alertdialog_nuevo_viaje_billetesmaquina_a_cero);
                // si viajeros != 0 establece mensaje, si no no lo establece, con 1 if suficiente
                if (mViaje.getViajerosActuales() != 0) builder.setMessage("   " +
                        getString(R.string.mensaje_dialog_continuaran_viajeros,
                                mViaje.getViajerosActuales()));
                builder.setPositiveButton(textoBotonAceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // máquina a 0, sin cambios en suben bajan
                        mViaje.setMaquina(0);
                        mAppSharedPrefEditor.putInt(Contract.MAQUINA_SHARED_PREF, 0);
                        mAppSharedPrefEditor.apply();
                        // viajeros los mismo,
                        // obtenemos numero de viajeros, establecemos suben en numero de viajeros
                        // y bajan se establece en 0. (tambien como siempre en sharedPref)
                        int viajerosActuales = mViaje.getViajerosActuales();
                        mViaje.setTotalSuben(viajerosActuales);
                        mViaje.setTotalBajan(0);
                        mAppSharedPrefEditor.putInt(Contract.TOTAL_SUBEN_SHARED_PREF, viajerosActuales);
                        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
                        mAppSharedPrefEditor.apply();
                        // AQUI ES DONDE SE COPIA LA LISTA A LA DEL ANTERIOR VIAJE
                        // copiamos el actual al anterior:
                        mListaCambiosAnterior = new ArrayList<>(mListaCambiosActual);
                        guardaListaCambiosYSizeAnteriorEnSharedPref();
                        //  nuevo viaje: por tanto reseteoListaCambios,
                        reseteoListaCambiosActual();

                        // Aqui ya estan cambiadas las listas, cambiar las fechas de listas:
                        Modelo.setFechaHoraListasViajes(mAppSharedPref, mAppSharedPrefEditor, getResources());

                        /* es necesario cambiar el boolean de deshacer a true para que esté
                        deshabilitado ya que se producian errores al iniciar viaje y continuar viajeros
                        y luego deshacer el ultimo cambio de sube o bajan ya que la listacambios se
                        resetea a 0 da como resultado un indice negativo al crear el respaldo...
                         */
                        mUltimoCambioFueDeshacerOInicio = true;
                        mAppSharedPrefEditor.putBoolean(Contract.ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF, true);
                        mAppSharedPrefEditor.apply();
                        actualizaIconoDeshacer();
                        // como es nuevo viaje es necesario cambia las variables de totalbajan para
                        // el textview de totalbajan
                        // Si hay viajeros: no tienen billete xq es nuevo viaje, de modo que el
                        // textview de totalbajan debe de seguir mostrando los viajeros anteriores
                        // hasta que no haya viajeros sin billete:
                        if (viajerosActuales > 0) { // viajeros sin billete
                            cambVarBajanNewViajeTramoVSinBillete();
                        } else { // viajeros actuales = 0 de modo que no hay viajeros sin billete
                            cambVarBajanNewViajeTramoVConBillete();
                        }
                        // por defecto en un nuevo viaje no hay tramos:
                        esPeticionTramoParaAvisoViajerosSinBillete = false; // Por defecto en inicio de viaje
                        // boolean esPeticionTramoParaAvisoViajerosSinBillete es false, de modo que se deja en
                        // false hasta proxima peticion
                        mAppSharedPrefEditor.putBoolean(
                                Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false);
                        mAppSharedPrefEditor.apply();
                        // actualiza views
                        actualizaViewsDatosViaje(
                                mTextViewViajerosActuales,
                                mBotonAforo,
                                mTextViewTotalSubenMaquina,
                                mTextViewPuedenSubir);
                        //  Toast de Nuevo Viaje
                        Toast.makeText(MainActivity.this, R.string.comienza_nuevo_viaje,
                                Toast.LENGTH_LONG).show();

                    }
                });
                // si viajeros !=0 establece el boton negativo si no no lo establece
                if (mViaje.getViajerosActuales() != 0) {
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // como es nuevo viaje y ha elegido iniciar viaje sin viajeros anteriores
                            // es necesario cambiar variables de totalbajan para textview de bajan:
                            // viajeros actuales = 0 de modo que no hay viajeros sin billete
                            cambVarBajanNewViajeTramoVConBillete();
                            // AQUI ES DONDE SE COPIA LA LISTA A LA DEL ANTERIOR VIAJE
                            // copiamos el actual al anterior:
                            mListaCambiosAnterior = new ArrayList<>(mListaCambiosActual);
                            guardaListaCambiosYSizeAnteriorEnSharedPref();
                            reset();
                            reseteoListaCambiosActual();

                            // Aqui ya estan cambiadas las listas, cambiar las fechas de listas:
                            Modelo.setFechaHoraListasViajes(mAppSharedPref, mAppSharedPrefEditor, getResources());

                            // Como al comienzo de un viaje por defecto NO ES TRAMO se cambia a false el boolean:
                            esPeticionTramoParaAvisoViajerosSinBillete = false; // que se deja en
                            // false hasta proxima peticion
                            mAppSharedPrefEditor.putBoolean(
                                    Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false);
                            mAppSharedPrefEditor.apply();
                            // actualiza views
                            actualizaViewsDatosViaje(
                                    mTextViewViajerosActuales,
                                    mBotonAforo,
                                    mTextViewTotalSubenMaquina,
                                    mTextViewPuedenSubir);
                            Toast.makeText(MainActivity.this, R.string.comienza_nuevo_viaje,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

                builder.setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, R.string.toast_abort_reset, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        // abrira la listacambios en click simple sobre viajeros actuales:
        mTextViewViajerosActuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaCambiosDialogFragment listaCambiosDialogFragment = new ListaCambiosDialogFragment(
                        mListaCambiosActual, mListaCambiosAnterior, mAppSharedPref, mAppSharedPrefEditor);
                listaCambiosDialogFragment.show(getSupportFragmentManager(), "lista cambios");
            }
        });

        mTextViewViajerosActuales.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // abre el fragment que maneja el onlongclick de vajeros:
                // debe pasarle parametrosint billetesmaquina e int billetessinmaquina
                MenuContextualViajerosActualesDialogFragment menuContextualViajeros =
                        new MenuContextualViajerosActualesDialogFragment(
                                mAppSharedPref,
                                getSupportFragmentManager(),
                                getBilletesMaquinaViajeActual(),
                                getBilletesSinMaquinaViajeActual(),
                                mViaje.getViajerosActuales());
                menuContextualViajeros.show(getSupportFragmentManager(), "menu contextual viajeros");
                return true;
            }
        });

        mLayoutTramo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater imagenInfo = LayoutInflater.from(MainActivity.this);
                final View vistaInfo = imagenInfo.inflate(R.layout.icono_info, null);
                builder.setView(vistaInfo);
                builder.setTitle(R.string.tituo_cambio_tramo);
                builder.setMessage(R.string.mensaje_alertdialog_tramo);
                builder.setPositiveButton(R.string.continuar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // abre teclado
                        openDialogTeclado(Contract.PETICION_TECLADO_TRAMO);
                    }
                });
                builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // AQUI VA EL CODIGO QUE SE EJECUTARÁ AL PULSAR CANCELAR
                    }
                });
                builder.show();
            }
        });

        mFrameBotonSuben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Si está seleccionado modo MÁQUINA:
                if (mViaje.isManejoMaquina()) {
                    // comoprueba si esta completo, el alert es el mismo que para subenmanual
                    if (mViaje.getAforo() == mViaje.getViajerosActuales())
                        alertDialogEstaCompleto();
                    else
                        openDialogTeclado(Contract.PETICION_TECLADO_SUBEN_MAQUINA);
                } else {
                    // Está seleccionado modo MANUAL:
                    // comprueba si está completo para no enviar al teclado:
                    if ((mViaje.getAforo() - mViaje.getViajerosActuales()) == 0) { // está completo
                        // alertdialog esta completo:
                        alertDialogEstaCompleto();
                    } else {
                        // para poder actualizar correctamente la maxima ocupacion en viaje actual,
                        // es necesario enviar a SubenManualActivity el total suben del actual viaje
                        int subenMaquinaPreIncremento = Modelo.getBilletesMaquinaViajeActual(mListaCambiosActual);
                        int subenSinMaquina = Modelo.getBilletesSinMaquinaViajeActual(mListaCambiosActual);
                        int totalSubenViajeActual = subenMaquinaPreIncremento + subenSinMaquina;
                        Intent intent = new Intent(MainActivity.this, SubenManualActivity.class);
                        intent.putExtra("totalSubenViajeActual", totalSubenViajeActual);
                        openActivitySubenManual(intent);
                    }

                }
            }
        });

        mFrameBotonSuben.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mViaje.isManejoMaquina()) { // SI ES MANEJOMAQUINA ABRE FRAGMENT DE RESETMAQUINA
                    // TODO: ABRE FRAGMENTDIALOG PERSONALIZADO CON OPCION RESET MAQUINA, LA ANIMACION QUE SEA
                    MenuContextualMaquinaDialogFragment menuContextualMaquinaDialogFragment = new MenuContextualMaquinaDialogFragment(
                            mAppSharedPref,
                            getSupportFragmentManager());
                    menuContextualMaquinaDialogFragment.show(getSupportFragmentManager(), "menu_contextual_maquina");
                }
                return true;
            }
        });

        mImageViewBotonBajan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // evita abrir teclado si no hay viajeros y avisa con dialog
                if (mViaje.getViajerosActuales() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater imagenWarning = LayoutInflater.from(MainActivity.this);
                    final View vistaWarning = imagenWarning.inflate(R.layout.icono_warning, null);
                    builder.setView(vistaWarning);
                    builder.setMessage(R.string.no_hay_viajeros_comprueba_la_maquina);
                    builder.setPositiveButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // todo: deberia de hacer una animacion del imageview de la maquina y del numero...
                        }
                    });
                    builder.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, BajanActivity.class);
                    openActivityBajan(intent);
                }

            }
        });

        mBotonAforo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalculoReduccionAforo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.entrapor_arriba, R.anim.sevapor_abajo);
            }
        });

    }

    private void agregaNuevoItemSubenOBajanListaCambiosDesdeResetViajeros(
            String resultadoTeclado, int antiguoViajeros, int cualListaCambios,
            boolean esBajanActualYAnteriorListaViaje, int bajanDelActualViaje, int bajanDelAnteriorViaje) { // en este metodo
        // si es bajan y hay bajan en actual y anterior viaje, se pondrá parametros bajanAnteriorViaje
        // y bajanActualViaje, ademas del boolean es bajanAntyAct. Si es solo en actual viaje estos
        // 2 utlimos parametros no se utilizan
        // todo: el siguiente codigo se puede hacer metodo en Modelo.java, con switch: agregaNuevoItemListaCambios
        // hora actual, viajeros y ocupacion son datos comunes, de modo que fuera de switch:
        String horaActual = Modelo.getHoraActualHHmmssString();
        int suben = mAppSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0);
        int bajan = mAppSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
        int viajeros = suben - bajan;
        int ocupacion = (viajeros * 100) / mAppSharedPref.getInt(Contract.AFORO_SHARED_PREF,
                93); // por defecto es 80 porque es el dato inicail de la app...
        int anteriorSizeListaCambios;
        ItemListaCambios[] listaCambios;
        switch (cualListaCambios) {
            // AQUI CREA LA LISTA PARA ACTUAL VIAJE
            case LISTA_ACTUAL_CAMBIOS:
                // AQUI IRA LA ADICIÓN DEL NUEVO ITEM SI ES PARA LA LISTA ACTUAL DE CAMBIOS
                // modificamos el tamaño del array guardado en SharedPref sumandole 1:
                anteriorSizeListaCambios = mAppSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, 0);
                mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF,
                        anteriorSizeListaCambios + 1);
                mAppSharedPrefEditor.apply();
                // se mueven todos los items de listaCambio en 1, para dejar libre el primero y poder
                // agregar el nuevo item ahi:
                // creamos copia de actual listaCambio guardado en sharedPref:
                listaCambios = new ItemListaCambios[anteriorSizeListaCambios];
                for (int i = 0; i < anteriorSizeListaCambios; i++) {
                    listaCambios[i] = new ItemListaCambios(
                            mHoraListaActualSharedPref.getString(String.valueOf(i), ""),
                            mIconoSubeBajaListaActualSharedPref.getInt(String.valueOf(i), R.drawable.transparencia),
                            mMovimientoListaActualSharedPref.getString(String.valueOf(i), "0"),
                            mViajerosListaActualSharedPref.getString(String.valueOf(i), "0"),
                            mOcupacionListaActualSharedPref.getString(String.valueOf(i), "0"),
                            mIconoBilletesListaActualSharedPref.getInt(String.valueOf(i), R.drawable.transparencia)
                    );
                }
                // Reordena datos en sharedpref: se empieza en int=1 para saltar el 0, ya que sera el nuevo item
                for (int i = 1; i < anteriorSizeListaCambios + 1; i++) {
                    mHoraListaActualSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getHora()); // se empieza desde el indice 0 de la copia del array listaCambios
                    mHoraListaActualSharedPrefEditor.apply();
                    mIconoSubeBajaListaActualSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoSubeBaja());
                    mIconoSubeBajaListaActualSharedPrefEditor.apply();
                    mMovimientoListaActualSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getMovimiento());
                    mMovimientoListaActualSharedPrefEditor.apply();
                    mViajerosListaActualSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getViajeros());
                    mViajerosListaActualSharedPrefEditor.apply();
                    mOcupacionListaActualSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getOcupacion());
                    mOcupacionListaActualSharedPrefEditor.apply();
                    mIconoBilletesListaActualSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoBillete());
                    mIconoBilletesListaActualSharedPrefEditor.apply();
                }
                // AQUI ES DONDE SE CREA EL ITEM DE BAJAN:
                mHoraListaActualSharedPrefEditor.putString(String.valueOf(0), horaActual);
                mHoraListaActualSharedPrefEditor.apply();
                //todo: si aumento los viajeros icono mas...
                if (antiguoViajeros > Integer.parseInt(resultadoTeclado)) {
                    mIconoSubeBajaListaActualSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_menos_24);
                    mIconoSubeBajaListaActualSharedPrefEditor.apply();
                } else {
                    mIconoSubeBajaListaActualSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_mas_rojo_24);
                    mIconoSubeBajaListaActualSharedPrefEditor.apply();
                }
                if (esBajanActualYAnteriorListaViaje && bajanDelActualViaje != 0) { // el cambio tiene viajeros que bajan del actual viaje
                    movimientoListaCambios = bajanDelActualViaje;
                } else {
                    movimientoListaCambios = antiguoViajeros - Integer.parseInt(resultadoTeclado);
                }
                mMovimientoListaActualSharedPrefEditor.putString(String.valueOf(0), String.valueOf(Math.abs(movimientoListaCambios)));
                mMovimientoListaActualSharedPrefEditor.apply();
                mViajerosListaActualSharedPrefEditor.putString(String.valueOf(0), String.valueOf(viajeros));
                mViajerosListaActualSharedPrefEditor.apply();
                mOcupacionListaActualSharedPrefEditor.putString(String.valueOf(0), String.valueOf(ocupacion) + "%");
                mOcupacionListaActualSharedPrefEditor.apply();
                mIconoBilletesListaActualSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_llave_inglesa_24);
                mIconoBilletesListaActualSharedPrefEditor.apply();
                cargaListasCambios(mAppSharedPref);
                break;

            // AQUI CREA LA LISTA PARA ANTERIOR VIAJE
            case LISTA_ANTERIOR_CAMBIOS:
                // AQUI IRA LA ADICIÓN DEL NUEVO ITEM SI ES PARA LA LISTA ANTERIOR DE CAMBIOS
                // modificamos el tamaño del array guardado en SharedPref sumandole 1:
                anteriorSizeListaCambios = mAppSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, 0);
                mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF,
                        anteriorSizeListaCambios + 1);
                mAppSharedPrefEditor.apply();
                // se mueven todos los items de listaCambio en 1, para dejar libre el primero y poder
                // agregar el nuevo item ahi:
                // creamos copia de actual listaCambio guardado en sharedPref:
                listaCambios = new ItemListaCambios[anteriorSizeListaCambios];
                for (int i = 0; i < anteriorSizeListaCambios; i++) {
                    listaCambios[i] = new ItemListaCambios(
                            mHoraListaAnteriorSharedPref.getString(String.valueOf(i), ""),
                            mIconoSubeBajaListaAnteriorSharedPref.getInt(String.valueOf(i), R.drawable.transparencia),
                            mMovimientoListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                            mViajerosListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                            mOcupacionListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                            mIconoBilletesListaAnteriorSharedPref.getInt(String.valueOf(i), R.drawable.transparencia)
                    );
                }
                // Reordena datos en sharedpref: se empieza en int=1 para saltar el 0, ya que sera el nuevo item
                for (int i = 1; i < anteriorSizeListaCambios + 1; i++) {
                    mHoraListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getHora()); // se empieza desde el indice 0 de la copia del array listaCambios
                    mHoraListaAnteriorSharedPrefEditor.apply();
                    mIconoSubeBajaListaAnteriorSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoSubeBaja());
                    mIconoSubeBajaListaAnteriorSharedPrefEditor.apply();
                    mMovimientoListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getMovimiento());
                    mMovimientoListaAnteriorSharedPrefEditor.apply();
                    mViajerosListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getViajeros());
                    mViajerosListaAnteriorSharedPrefEditor.apply();
                    mOcupacionListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                            listaCambios[i - 1].getOcupacion());
                    mOcupacionListaAnteriorSharedPrefEditor.apply();
                    mIconoBilletesListaAnteriorSharedPrefEditor.putInt(String.valueOf(i),
                            listaCambios[i - 1].getIconoBillete());
                    mIconoBilletesListaAnteriorSharedPrefEditor.apply();
                }
                // AQUI ES DONDE SE CREA EL ITEM DE BAJAN:
                mHoraListaAnteriorSharedPrefEditor.putString(String.valueOf(0), horaActual);
                mHoraListaAnteriorSharedPrefEditor.apply();
                // guardamos valor actual de movimientoListaCambios para luego sumarlo, ya que se
                // utilizara para totalbajantextview
                int bajanActualListaViaje = movimientoListaCambios;
                // si aumenta los viajeros: icono mas...
                if (antiguoViajeros > Integer.parseInt(resultadoTeclado)) {
                    mIconoSubeBajaListaAnteriorSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_menos_24);
                    mIconoSubeBajaListaAnteriorSharedPrefEditor.apply();
                } else {
                    mIconoSubeBajaListaAnteriorSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_mas_rojo_24);
                    mIconoSubeBajaListaAnteriorSharedPrefEditor.apply();
                }
                if (esBajanActualYAnteriorListaViaje && bajanDelAnteriorViaje != 0) { // el cambio tiene viajeros que bajan del anterior viaje
                    movimientoListaCambios = bajanDelAnteriorViaje;
                } else {
                    movimientoListaCambios = antiguoViajeros - Integer.parseInt(resultadoTeclado);
                }
                mMovimientoListaAnteriorSharedPrefEditor.putString(String.valueOf(0), String.valueOf(Math.abs(movimientoListaCambios)));
                mMovimientoListaAnteriorSharedPrefEditor.apply();
                // ahora, como el dato movimientoListaCambios se utiliza para total bajan textview,
                // y el dato de bajan se ha dividido en actual y anterior listacambios, lo sumamos
                // depues de que ya se haya guardado en sharedpref:
                movimientoListaCambios += bajanActualListaViaje;
                mViajerosListaAnteriorSharedPrefEditor.putString(String.valueOf(0), String.valueOf(viajeros));
                mViajerosListaAnteriorSharedPrefEditor.apply();
                mOcupacionListaAnteriorSharedPrefEditor.putString(String.valueOf(0), String.valueOf(ocupacion) + "%");
                mOcupacionListaAnteriorSharedPrefEditor.apply();
                // el icono es llave inglesa, se podria cambiar a sin billete...
                mIconoBilletesListaAnteriorSharedPrefEditor.putInt(String.valueOf(0), R.drawable.ic_llave_inglesa_24);
                mIconoBilletesListaAnteriorSharedPrefEditor.apply();
                cargaListasCambios(mAppSharedPref);
                break;
        }

    }

    private void deshaceListaCambiosActual() {
        // eliminaUltimoCambioDeListaCambios:
        // creamos copia de listaCambiosSharedPref pero sin el primer indice,
        // de modo que tiene la copia un indice menos:
        // para ello guardamos el tamaño actual del arraysharedlistaCambios,
        // para poder recorrer el original y quitar el indice 0 y luego pegarlo
        // de nuevo ya modificado en el arraysharedlistaCambios:
        // importante: da error     java.lang.NegativeArraySizeException: -1.
        // si la lista tiene 1 indice entonces tendrá 0 despues de deshacer,
        // de modo que se limpia la lista
        // y si tiene 0 querrá decir que se comenzó nuevo viaje y aun no se
        // ha subido nadie, de modo que se dejará igual.
        int anteriorSizeListaCambios = mAppSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, 0);
        // lo guardamos ya modificado el tamaño del arraySharedListaCambios:
        mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF,
                anteriorSizeListaCambios - 1);
        mAppSharedPrefEditor.apply();
        // creamos ahora el nuevo arrayListaCambios con un indice menos,
        // para luego pasarlo a sharedPref:
        ItemListaCambios[] nuevaListaCambios = new ItemListaCambios[anteriorSizeListaCambios - 1];
        // comenzamos a llenar el arrayListaCambios nuevo, pero desde el indice 1
        // del antiguo arraySharedListaCambios, para no copiar el que se elimina:
        for (int j = 1; j < anteriorSizeListaCambios; j++) {
            nuevaListaCambios[j - 1] = new ItemListaCambios(
                    mHoraListaActualSharedPref.getString(String.valueOf(j), ""),
                    mIconoSubeBajaListaActualSharedPref.getInt(String.valueOf(j), 0),
                    mMovimientoListaActualSharedPref.getString(String.valueOf(j), ""),
                    mViajerosListaActualSharedPref.getString(String.valueOf(j), ""),
                    mOcupacionListaActualSharedPref.getString(String.valueOf(j), ""),
                    mIconoBilletesListaActualSharedPref.getInt(String.valueOf(j), 0)
            );
        }
        // cambiamos los indices del arraySharedListaCambio por los del nuevo:
        // no olvidamos que el nuevo arraySharedListaCambios debe de tener 1 indice menos:
        for (int j = 0; j < anteriorSizeListaCambios - 1; j++) {
            mHoraListaActualSharedPrefEditor.putString(String.valueOf(j), nuevaListaCambios[j].getHora());
            mHoraListaActualSharedPrefEditor.apply();
            mIconoSubeBajaListaActualSharedPrefEditor.putInt(String.valueOf(j), nuevaListaCambios[j].getIconoSubeBaja());
            mIconoSubeBajaListaActualSharedPrefEditor.apply();
            mMovimientoListaActualSharedPrefEditor.putString(String.valueOf(j), nuevaListaCambios[j].getMovimiento());
            mMovimientoListaActualSharedPrefEditor.apply();
            mViajerosListaActualSharedPrefEditor.putString(String.valueOf(j), nuevaListaCambios[j].getViajeros());
            mViajerosListaActualSharedPrefEditor.apply();
            mOcupacionListaActualSharedPrefEditor.putString(String.valueOf(j), nuevaListaCambios[j].getOcupacion());
            mOcupacionListaActualSharedPrefEditor.apply();
            mIconoBilletesListaActualSharedPrefEditor.putInt(String.valueOf(j), nuevaListaCambios[j].getIconoBillete());
            mIconoBilletesListaActualSharedPrefEditor.apply();
        }
        // y solo queda actualizar el array listaCambios para cuando se pase al adapter:
        cargaListasCambios(mAppSharedPref);
    }

    private void deshaceListaCambiosAnterior() {
        // eliminaUltimoCambioDeListaCambios:
        // creamos copia de listaCambiosSharedPref pero sin el primer indice,
        // de modo que tiene la copia un indice menos:
        // para ello guardamos el tamaño actual del arraysharedlistaCambios,
        // para poder recorrer el original y quitar el indice 0 y luego pegarlo
        // de nuevo ya modificado en el arraysharedlistaCambios:
        // importante: da error     java.lang.NegativeArraySizeException: -1.
        // si la lista tiene 1 indice entonces tendrá 0 despues de deshacer,
        // de modo que se limpia la lista
        // y si tiene 0 querrá decir que se comenzó nuevo viaje y aun no se
        // ha subido nadie, de modo que se dejará igual.
        int anteriorSizeListaCambios = mAppSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, 0);
        // lo guardamos ya modificado el tamaño del arraySharedListaCambios:
        mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF,
                anteriorSizeListaCambios - 1);
        mAppSharedPrefEditor.apply();
        // creamos ahora el nuevo arrayListaCambios con un indice menos,
        // para luego pasarlo a sharedPref:
        ItemListaCambios[] nuevaListaCambios = new ItemListaCambios[anteriorSizeListaCambios - 1];
        // comenzamos a llenar el arrayListaCambios nuevo, pero desde el indice 1
        // del antiguo arraySharedListaCambios, para no copiar el que se elimina:
        for (int j = 1; j < anteriorSizeListaCambios; j++) {
            nuevaListaCambios[j - 1] = new ItemListaCambios(
                    mHoraListaAnteriorSharedPref.getString(String.valueOf(j), ""),
                    mIconoSubeBajaListaAnteriorSharedPref.getInt(String.valueOf(j), 0),
                    mMovimientoListaAnteriorSharedPref.getString(String.valueOf(j), ""),
                    mViajerosListaAnteriorSharedPref.getString(String.valueOf(j), ""),
                    mOcupacionListaAnteriorSharedPref.getString(String.valueOf(j), ""),
                    mIconoBilletesListaAnteriorSharedPref.getInt(String.valueOf(j), 0)
            );
        }
        // cambiamos los indices del arraySharedListaCambio por los del nuevo:
        // no olvidamos que el nuevo arraySharedListaCambios debe de tener 1 indice menos:
        for (int j = 0; j < anteriorSizeListaCambios - 1; j++) {
            mHoraListaAnteriorSharedPrefEditor.putString(String.valueOf(j), nuevaListaCambios[j].getHora());
            mHoraListaAnteriorSharedPrefEditor.apply();
            mIconoSubeBajaListaAnteriorSharedPrefEditor.putInt(String.valueOf(j), nuevaListaCambios[j].getIconoSubeBaja());
            mIconoSubeBajaListaAnteriorSharedPrefEditor.apply();
            mMovimientoListaAnteriorSharedPrefEditor.putString(String.valueOf(j), nuevaListaCambios[j].getMovimiento());
            mMovimientoListaAnteriorSharedPrefEditor.apply();
            mViajerosListaAnteriorSharedPrefEditor.putString(String.valueOf(j), nuevaListaCambios[j].getViajeros());
            mViajerosListaAnteriorSharedPrefEditor.apply();
            mOcupacionListaAnteriorSharedPrefEditor.putString(String.valueOf(j), nuevaListaCambios[j].getOcupacion());
            mOcupacionListaAnteriorSharedPrefEditor.apply();
            mIconoBilletesListaAnteriorSharedPrefEditor.putInt(String.valueOf(j), nuevaListaCambios[j].getIconoBillete());
            mIconoBilletesListaAnteriorSharedPrefEditor.apply();
        }
        // y solo queda actualizar el array listaCambios para cuando se pase al adapter:
        cargaListasCambios(mAppSharedPref);
    }

    private void iniciaViewsConFindViewById() {
        // prepara vistas:
        mIconoDeshacer = findViewById(R.id.image_view_deshacer);
        frameBotonDeshacer = findViewById(R.id.frame_boton_deshacer);
        mIconoManejoMaquina = findViewById(R.id.image_view_manejo_maquina);
        frameBotonManejoMaquina = findViewById(R.id.frame_boton_manejo_maquina);
        mIconoManejoManual = findViewById(R.id.image_view_manejo_manual);
        frameBotonManejoManual = findViewById(R.id.frame_boton_manejo_manual);
        frameBotonScreenOrientation = findViewById(R.id.frame_boton_screen_orientation);
        frameBotonMenu = findViewById(R.id.frame_menu);

        mLayoutNuevoViaje = findViewById(R.id.layout_nuevo_viaje);

        mTextViewViajerosActuales = findViewById(R.id.numero_viajeros_actuales);

        mLayoutTramo = findViewById(R.id.layout_tramo);
        ivIcInicioTramo = findViewById(R.id.iv_ic_inicio_tramo);
        mBotonAforo = findViewById(R.id.button_aforo);

        ivIcSuben = findViewById(R.id.iv_ic_suben);
        textViewUltimoSuben = findViewById(R.id.textview_ultimo_cambio_suben);
        ivIcBajan = findViewById(R.id.iv_ic_bajan);
        textViewUltimoBajan = findViewById(R.id.textview_ultimo_cambio_bajan);

        mFrameBotonSuben = findViewById(R.id.frame_boton_suben);
        mImageSubenMaquina = findViewById(R.id.image_boton_suben_maquina);
        mLinearIconoYNumBilletesManual = findViewById(R.id.linear_numero_billetes_manual);
        mTextViewNumBilletesManual = findViewById(R.id.tv_num_billetes_manual);
        linearHayMasBilletes = findViewById(R.id.layout_hay_mas_billetes);
        ivHayMasBilletes = findViewById(R.id.iv_ic_hay_mas_billetes);
        tvHayMasBilletes = findViewById(R.id.tv_hay_mas_billetes);
        mImageSubenManual = findViewById(R.id.image_boton_suben_manual);
        mTextViewTotalSubenMaquina = findViewById(R.id.numero_total_suben_maquina);
        mImageViewBotonBajan = findViewById(R.id.boton_bajan_fragmcontviaje_imageview);
        mTextViewNumTotalBajan = findViewById(R.id.tv_numero_total_bajan);
        mTextViewNumTotalSuben = findViewById(R.id.tv_total_suben);

        mTextViewPuedenSubir = findViewById(R.id.numero_pueden_subir);
        mTextViewAvisosViaje = findViewById(R.id.text_view_avisos_viaje);
        cardViewFondoAvisos = findViewById(R.id.cardview_fondo_avisos);

        progressActualOcupacion = findViewById(R.id.pb_ocupacion_ahora);
        progressMaximaOcupacion = findViewById(R.id.pb_ocupacion_maxima);
        tvOcupacionActualPorcentaje = findViewById(R.id.tv_ocupacion_actual_porcentaje);
        tvOcupacionMaximaPorcentaje = findViewById(R.id.tv_ocupacion_maxima_porcentaje);
        tvOcupacionMaximaViajeros = findViewById(R.id.tv_viajeros_maxima_ocupacion);
        tvOcupacionMaximaHora = findViewById(R.id.tv_hora_maxima_ocupacion);
    }

    private void initSharedPrefs() {
        // inicialización de objetos SahredPreferences:
        mAppSharedPref = getSharedPreferences(Contract.STRING_SHARED_PREF, MODE_PRIVATE);
        mAppSharedPrefEditor = mAppSharedPref.edit();
        // actual
        mHoraListaActualSharedPref = getSharedPreferences(Contract.STRING_HORA_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        mHoraListaActualSharedPrefEditor = mHoraListaActualSharedPref.edit();
        mIconoSubeBajaListaActualSharedPref = getSharedPreferences(Contract.STRING_ICONO_SUBE_BAJA_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        mIconoSubeBajaListaActualSharedPrefEditor = mIconoSubeBajaListaActualSharedPref.edit();
        mMovimientoListaActualSharedPref = getSharedPreferences(Contract.STRING_MOVIMIENTO_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        mMovimientoListaActualSharedPrefEditor = mMovimientoListaActualSharedPref.edit();
        mViajerosListaActualSharedPref = getSharedPreferences(Contract.STRING_VIAJEROS_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        mViajerosListaActualSharedPrefEditor = mViajerosListaActualSharedPref.edit();
        mOcupacionListaActualSharedPref = getSharedPreferences(Contract.STRING_OCUPACION_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        mOcupacionListaActualSharedPrefEditor = mOcupacionListaActualSharedPref.edit();
        mIconoBilletesListaActualSharedPref = getSharedPreferences(Contract.STRING_ICONO_BILLETE_ACTUAL_SHARED_PREF, MODE_PRIVATE);
        mIconoBilletesListaActualSharedPrefEditor = mIconoBilletesListaActualSharedPref.edit();
        // anterior
        mHoraListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_HORA_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        mHoraListaAnteriorSharedPrefEditor = mHoraListaAnteriorSharedPref.edit();
        mIconoSubeBajaListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_ICONO_SUBE_BAJA_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        mIconoSubeBajaListaAnteriorSharedPrefEditor = mIconoSubeBajaListaAnteriorSharedPref.edit();
        mMovimientoListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_MOVIMIENTO_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        mMovimientoListaAnteriorSharedPrefEditor = mMovimientoListaAnteriorSharedPref.edit();
        mViajerosListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_VIAJEROS_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        mViajerosListaAnteriorSharedPrefEditor = mViajerosListaAnteriorSharedPref.edit();
        mOcupacionListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_OCUPACION_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        mOcupacionListaAnteriorSharedPrefEditor = mOcupacionListaAnteriorSharedPref.edit();
        mIconoBilletesListaAnteriorSharedPref = getSharedPreferences(Contract.STRING_ICONO_BILLETE_ANTERIOR_SHARED_PREF, MODE_PRIVATE);
        mIconoBilletesListaAnteriorSharedPrefEditor = mIconoBilletesListaAnteriorSharedPref.edit();

    }

    private void setTVMaqDispLowHeigth() {
        /* Este codigo es para que en dispositivos con poca altura en relacion con su anchura
        debe de ser que alto/ancho sea menor o igual a 1.60, el dpi no influye... ya que al
        ser baja la pantalla encoge la imagen de la maquina y el textview se sale
                 */
        int screenHeight;
        int screenWidth;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = MainActivity.this.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            screenHeight = windowMetrics.getBounds().height() - insets.left - insets.right;
            screenWidth = windowMetrics.getBounds().width() - insets.top - insets.bottom;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenHeight = displayMetrics.heightPixels;
            screenWidth = displayMetrics.widthPixels;
        }
        int proporcionAltoAncho = screenHeight / screenWidth;
        if (proporcionAltoAncho <= 1.60) {
            LinearLayoutCompat.LayoutParams paramsDerecho = new LinearLayoutCompat.LayoutParams(
                    0, FrameLayout.LayoutParams.MATCH_PARENT, 27.0f);
            LinearLayoutCompat.LayoutParams paramsIzquierdo = new LinearLayoutCompat.LayoutParams(
                    0, FrameLayout.LayoutParams.MATCH_PARENT, 36.0f);
            LinearLayoutCompat linearDerecho = findViewById(R.id.linear_weight_derecha_maquina);
            LinearLayoutCompat linearIzquierdo = findViewById(R.id.linear_weight_izquierda_maquina);

            linearDerecho.setLayoutParams(paramsDerecho);
            linearIzquierdo.setLayoutParams(paramsIzquierdo);
        }
    }

    private void construyeDatosYViewsActivity() {
        mViaje = new Viaje();
        mRespaldoViaje = new Viaje();
        mListaCambiosActual = new ArrayList<>();
        // al crear nuevo ArrayList no se suma a lo anterior, y no hay duplicados,
        // en cargalistacambios debe ser igual para mlistacambiosanterior!
        mListaCambiosAnterior = new ArrayList<>();
        // INICIO DE OBJETOS SHAREDPREFERENCES:
        //actualiza datos y views desde sharedPref:
        cargaViajeYDatosDesdeSharedPref(mAppSharedPref);
        // establece el aforo para el progress, tambien en onresume:
        progressActualOcupacion.setMax(100);
        progressMaximaOcupacion.setMax(100);
        cargaListasCambios(mAppSharedPref);
        if (mAppSharedPref.getBoolean(Contract.RESPALDO_VIAJE_INICIADO_SHARED_PREF, false)) {
            // de donde no hay no se puede sacar:
            cargaRespaldoViajeSharedPref(mAppSharedPref);
            /* dejamos guardado el estado del respaldo para futuros usos en el ciclo de vida de
                 la actividad:
                                  */
            mRespaldoViajeIniciado = mAppSharedPref.getBoolean(
                    Contract.RESPALDO_VIAJE_INICIADO_SHARED_PREF, false);
        }
        actualizaIconoDeshacer();
        actualizaViewsDatosViaje(mTextViewViajerosActuales, mBotonAforo, mTextViewTotalSubenMaquina,
                mTextViewPuedenSubir);
    }

    private void alertDialogEstaCompleto() {
        AlertDialog.Builder builderCompleto = new AlertDialog.Builder(this);
        builderCompleto.setMessage(getResources().getString(
                R.string.dialog_esta_completo_no_pueden_subir_mas));
        LayoutInflater imagenWarning = LayoutInflater.from(MainActivity.this);
        final View vistaWarning = imagenWarning.inflate(R.layout.icono_warning, null);
        builderCompleto.setView(vistaWarning);
        builderCompleto.setPositiveButton(getResources().getString(
                R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // no hace nada
            }
        });
        builderCompleto.show();
    }

    private void actualizaUltimoSubeBaja() {
        // HALLAMOS DATOS PARA DECIDIR EN LOS IF

        // hallamos suben y bajan del actual viaje si no está vacía la lista
        boolean subenViajeActual = false;
        boolean bajanViajeActual = false;
        boolean bajanViajeAnterior = false;
        boolean subenViajeAnterior = false;
        // cuando encuentra al ultimo cambio es esUltimoEnCambiar true,
        // luego pasa ha ser false para saber que no es el último cambio
        boolean esUltimoEnCambiar = true;
        if (mListaCambiosActual.size() != 0) {
            for (int i = 0; i < mListaCambiosActual.size(); i++) {
                if (mListaCambiosActual.get(i).getIconoSubeBaja() == R.drawable.ic_mas_azul_24)
                    subenViajeActual = true;
                if (mListaCambiosActual.get(i).getIconoSubeBaja() == R.drawable.ic_menos_24)
                    bajanViajeActual = true;
                if (subenViajeActual && bajanViajeActual)
                    return;
            }
        }

        // buscamos la condición para actualizar ultimo sube o baja:
        if (mListaCambiosActual.size() == 0 && numViajerosSinBillete == 0) {
            textViewUltimoSuben.setText(R.string.actualiza_suben);
            textViewUltimoBajan.setText(R.string.actualiza_bajan);
            textViewUltimoSuben.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            textViewUltimoBajan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ivIcSuben.setImageDrawable(VectorDrawableCompat.create(getResources(),
                    R.drawable.ic_group_add_blanco, null));
            ivIcBajan.setImageDrawable(VectorDrawableCompat.create(getResources(),
                    R.drawable.ic_group_remove_blanco, null));
        } else if (mListaCambiosActual.size() == 0 && numViajerosSinBillete > 0) {
            // hay viaje anterior, hay subidas, puede o no puede haber bajadas...
            for (int i = 0; i < mListaCambiosAnterior.size(); i++) {
                // buscamos si hay bajadas en viaje anterior y cambiamos textviews sobre la marcha::
                if (!bajanViajeAnterior) {
                    if (mListaCambiosAnterior.get(i).getIconoSubeBaja() == R.drawable.ic_menos_24) {
                        int bajan = Integer.parseInt(mListaCambiosAnterior.get(i).getMovimiento());
                        String bajanX;
                        if (bajan == 1) bajanX = getString(R.string.baja, bajan);
                        else bajanX = getString(R.string.bajan, bajan);
                        String horaBajan = formateaHHmm(mListaCambiosAnterior.get(i).getHora());
                        textViewUltimoBajan.setText(horaBajan + " " + bajanX);
                        // además bajanViajeAnterior pasa a ser true ya que se encontró bajan...
                        bajanViajeAnterior = true;
                        // ahora damos color a icono y texto y resaltado, dependiendo si es último cambio...
                        if (esUltimoEnCambiar) {
                            // este debe de ir resaltado con color, es el último cambio:
                            textViewUltimoBajan.setTypeface(Typeface.DEFAULT_BOLD);
                            ivIcBajan.setImageDrawable(VectorDrawableCompat.create(getResources(),
                                    R.drawable.ic_group_remove_rojo, null));
                            // el boolean esUltimoEnCambiar debe pasar a false, ya que se encontró el último cambio
                            esUltimoEnCambiar = false;
                        } else {
                            // sin resaltar y color blanco, no es el último:
                            textViewUltimoBajan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            ivIcBajan.setImageDrawable(VectorDrawableCompat.create(getResources(),
                                    R.drawable.ic_group_remove_blanco, null));
                        }
                    }
                }
                // buscamos el suben y cambiamos textviews:
                // si aun no se encontró el suben:
                if (!subenViajeAnterior) {
                    if (mListaCambiosAnterior.get(i).getIconoSubeBaja() == R.drawable.ic_mas_azul_24) {
                        int suben = Integer.parseInt(mListaCambiosAnterior.get(i).getMovimiento());
                        String subenX;
                        if (suben == 1) subenX = getString(R.string.sube, suben);
                        else subenX = getString(R.string.suben, suben);
                        String horaSuben = formateaHHmm(mListaCambiosAnterior.get(i).getHora());
                        textViewUltimoSuben.setText(horaSuben + " " + subenX);
                        subenViajeAnterior = true;
                        // ahora damos color a icono y texto y resaltado, dependiendo si es último cambio...
                        if (esUltimoEnCambiar) {
                            // este debe de ir resaltado con color, es el último cambio:
                            textViewUltimoSuben.setTypeface(Typeface.DEFAULT_BOLD);
                            ivIcSuben.setImageDrawable(VectorDrawableCompat.create(getResources(),
                                    R.drawable.ic_group_add_azul, null));
                            // el boolean esUltimoEnCambiar debe pasar a false, ya que se encontró el último cambio
                            esUltimoEnCambiar = false;
                        } else {
                            // sin resaltar y color blanco, no es el último:
                            textViewUltimoSuben.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            ivIcSuben.setImageDrawable(VectorDrawableCompat.create(getResources(),
                                    R.drawable.ic_group_remove_blanco, null));
                        }

                    }
                }
                if (bajanViajeAnterior && subenViajeAnterior)
                    return;
            }
            // si no hubo bajadas en viaje anterior, nos aseguramos de que esté en blanco y sin hora:
            if (!bajanViajeAnterior) {
                textViewUltimoBajan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                ivIcBajan.setImageDrawable(VectorDrawableCompat.create(getResources(),
                        R.drawable.ic_group_remove_blanco, null));
            }
            // TODO: seguimos por aquí:
        } else if (subenViajeActual && bajanViajeActual) {
            // sube y baja está en viaje actual
        } else if (subenViajeActual && !bajanViajeActual && numViajerosSinBillete == 0) {
            // sube en viaje actual y baja "actualiza..."
        } else if (subenViajeActual && !bajanViajeActual
                && numViajerosSinBillete > 0
                && !esPeticionTramoParaAvisoViajerosSinBillete) {
            // sube en actual viaje, baja en anterior viaje
        } else if (subenViajeActual && !bajanViajeActual
                && numViajerosSinBillete > 0
                && esPeticionTramoParaAvisoViajerosSinBillete) {
            // puede haber VSB de tramo y de viaje anterior:
            if (numViajerosSinBillete > (getBilletesMaquinaViajeActual() + getBilletesSinMaquinaViajeActual())) {
                // hay del viaje anterior
            } else {
                // no hay del viaje anterior, de modo que deben de ser de tramo, de modo que se bajan = "actualiza" ya que aún no ha bajado nadie
            }
        }
    }

    // este método se cambia por actualizaUltimoSubeBaja()
    private void buscaYActualizaTextViewsUltimoSubeYBaja() {
        // todo solo me queda poner en bold ultimos bajan cuando hay VSB
        // todo la solución es guardar la hora del item cambiolista en año-mes-dia-hora-seg y asi
        //  poder comparar las horas de cada movimiento sumando cada numero de año,mes,dia etc... y
        //  el que tenga mayor numero es el mas reciente.. tiene trabajo de busqueda en todas las
        //  clases donde se utilice la hora del item listacambios...
        boolean guardadoSuben = false;
        boolean guardadoBajan = false;
        String suben = "";
        String bajan = "";
        String horaSuben = "";
        String horaBajan = "";
        int indiceSuben = 0;
        int indiceBajan = 0;

        if (mListaCambiosActual.size() == 0 && numViajerosSinBillete == 0) { // realiza un reseteo:
            textViewUltimoSuben.setText(R.string.actualiza_suben);
            textViewUltimoBajan.setText(R.string.actualiza_bajan);
            textViewUltimoSuben.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            textViewUltimoBajan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ivIcSuben.setImageDrawable(VectorDrawableCompat.create(getResources(),
                    R.drawable.ic_group_add_blanco, null));
            ivIcBajan.setImageDrawable(VectorDrawableCompat.create(getResources(),
                    R.drawable.ic_group_remove_blanco, null));

        } else {
            // la lista actual esta vacia pero hay VSB:
            // guarda
            if (mListaCambiosActual.size() == 0 && numViajerosSinBillete > 0) {
                // se actualiza ultimobajan:
                int i = 0;
                while (i < mListaCambiosAnterior.size() && !guardadoBajan) {
                    // obtenemos datos de ultimo bajan:
                    if (mListaCambiosAnterior.get(i).getIconoSubeBaja() == R.drawable.ic_menos_24) {
                        bajan = mListaCambiosAnterior.get(i).getMovimiento();
                        horaBajan = mListaCambiosAnterior.get(i).getHora();
                        guardadoBajan = true;
                    }
                    i++;
                }
                // se actualiza ultimosuben:
                int j = 0;
                while (j < mListaCambiosAnterior.size() && !guardadoSuben) {
                    // obtenemos datos de ultimo suben:
                    if (mListaCambiosAnterior.get(j).getIconoSubeBaja() == R.drawable.ic_mas_azul_24) {
                        suben = mListaCambiosAnterior.get(j).getMovimiento();
                        horaSuben = mListaCambiosAnterior.get(j).getHora();
                        guardadoSuben = true;
                    }
                    j++;
                }
            } else if (mListaCambiosActual.size() > 0) {
                int j = 0;
                int k = 0;
                for (int i = 0; i < mListaCambiosActual.size(); i++) {
                    if (mListaCambiosActual.get(i).getIconoSubeBaja() == R.drawable.ic_mas_azul_24 &&
                            !guardadoSuben) {
                        suben = mListaCambiosActual.get(i).getMovimiento();
                        horaSuben = mListaCambiosActual.get(i).getHora();
                        indiceSuben = i + 1; // con esto se sabe en que posicion esta el cambio para
                        // resaltar el ultimo cambio. se le suma 1 porque si es 0 no se habrá guardado
                        guardadoSuben = true;
                    }
                    // en este if comprobar que no hay viajeros sin billete, si los hay extraer dato de lista anterior
                    List<ItemListaCambios> miListaCambios;
                    // si hay VSB entonces la lista sera la anterior viaje, si no, la actual:

                    // importante: si es la actual puede usar la variable del for i, si es la
                    // anterior usar la variable j del exterior que ira aumentando en 1 cada vez que
                    // se use hasta antes del size de la lista anteior para no IndexOutOfBoundsException
                    // la variable k tendrá el valor de i o de j depende de la lista actual o anterior:
                    if (numViajerosSinBillete > 0
                            &&
                            mListaCambiosAnterior.size() > 0
                            &&
                            j < mListaCambiosAnterior.size()) {
                        miListaCambios = mListaCambiosAnterior;
                        k = j;
                        j++; // ahora aumentamos j para recorrer la listacambiosanterior...
                    } else {
                        miListaCambios = mListaCambiosActual;
                        k = i;
                    }

                    if (miListaCambios.get(k).getIconoSubeBaja() == R.drawable.ic_menos_24 &&
                            !guardadoBajan) {
                        bajan = miListaCambios.get(k).getMovimiento();
                        horaBajan = miListaCambios.get(k).getHora();
                        indiceBajan = k + 1; // con esto se sabe en que posicion esta el cambio para
                        // resaltar el ultimo cambio. se le suma 1 porque si es 0 no se habrá guardado
                        guardadoBajan = true;
                    }
                }
            }

            // para saber que movimiento fue el ultimo: si indice no es 0 se habrá guardado, y el
            // que tenga el indice menor será el ultimo cambio ya que estará más cerca del indice 0
            // que es el ultimo
            if (guardadoSuben) {
                horaSuben = formateaHHmm(horaSuben);
                String subenX = getString(R.string.suben, Integer.parseInt(suben));
                if (Integer.parseInt(suben) <= 1)
                    subenX = getString(R.string.sube, Integer.parseInt(suben)); // quitamos la n a suben, ya que es 1.
                textViewUltimoSuben.setText(horaSuben + " " + subenX);
                // arreglo torpe de ultimo cambio en bold: consultar hora
                if (indiceBajan != 0 && indiceSuben < indiceBajan) {
                    textViewUltimoSuben.setTypeface(Typeface.DEFAULT_BOLD);
                    ivIcSuben.setImageDrawable(VectorDrawableCompat.create(getResources(),
                            R.drawable.ic_group_add_azul, null));
                    textViewUltimoBajan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    ivIcBajan.setImageDrawable(VectorDrawableCompat.create(getResources(),
                            R.drawable.ic_group_remove_blanco, null));
                }
            } else {
                if (!guardadoSuben && mListaCambiosActual.size() == 0) {
                    textViewUltimoSuben.setText(R.string.actualiza_suben);
                    textViewUltimoSuben.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    ivIcSuben.setImageDrawable(VectorDrawableCompat.create(getResources(),
                            R.drawable.ic_group_add_blanco, null));
                }
            }
            if (guardadoBajan) {
                horaBajan = formateaHHmm(horaBajan);
                String bajanX = getString(R.string.bajan, Integer.parseInt(bajan));
                if (Integer.parseInt(bajan) <= 1)
                    bajanX = getString(R.string.baja, Integer.parseInt(bajan)); // quitamos la n a suben, ya que es 1.
                textViewUltimoBajan.setText(horaBajan + " " + bajanX);
                // arreglo torpe de ultimo cambio en bold: consultar hora
                if (indiceSuben != 0 && indiceBajan < indiceSuben) {
                    textViewUltimoBajan.setTypeface(Typeface.DEFAULT_BOLD);
                    textViewUltimoSuben.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    ivIcBajan.setImageDrawable(VectorDrawableCompat.create(getResources(),
                            R.drawable.ic_group_remove_rojo, null));
                    ivIcSuben.setImageDrawable(VectorDrawableCompat.create(getResources(),
                            R.drawable.ic_group_add_blanco, null));
                }
            } else {
                textViewUltimoBajan.setText(R.string.actualiza_bajan);
                textViewUltimoBajan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                ivIcBajan.setImageDrawable(VectorDrawableCompat.create(getResources(),
                        R.drawable.ic_group_remove_blanco, null));
            }
        }


    }

    private String formateaHHmm(String hora) {
        String[] horaMinutoSegundo = hora.split(":");
        return horaMinutoSegundo[0] + ":" + horaMinutoSegundo[1];
    }

    private void reseteoListaCambiosActual() {
        // reseteamos listaCambios:
        // obtenemos tamaño actual del array:
        int sizeListaCambios = mAppSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, 0);
        // recorremos el arraySharedPref y eliminamos con .remove:
        for (int j = 0; j < sizeListaCambios; j++) {
            mHoraListaActualSharedPrefEditor.remove(String.valueOf(j));
            mHoraListaActualSharedPrefEditor.apply();
            mIconoSubeBajaListaActualSharedPrefEditor.remove(String.valueOf(j));
            mIconoSubeBajaListaActualSharedPrefEditor.apply();
            mMovimientoListaActualSharedPrefEditor.remove(String.valueOf(j));
            mMovimientoListaActualSharedPrefEditor.apply();
            mViajerosListaActualSharedPrefEditor.remove(String.valueOf(j));
            mViajerosListaActualSharedPrefEditor.apply();
            mOcupacionListaActualSharedPrefEditor.remove(String.valueOf(j));
            mOcupacionListaActualSharedPrefEditor.apply();
            mIconoBilletesListaActualSharedPrefEditor.remove(String.valueOf(j));
            mIconoBilletesListaActualSharedPrefEditor.apply();
        }
        // cambiamos tamañoShared:
        mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, 0);
        mAppSharedPrefEditor.apply();
        // limpiamos el array listaCambios:
        mListaCambiosActual.clear();
    }

    private void reseteoListaCambiosAnterior() {
        // reseteamos listaCambios:
        // obtenemos tamaño actual del array:
        int sizeListaCambios = mAppSharedPref.getInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, 0);
        // recorremos el arraySharedPref y eliminamos con .remove:
        for (int j = 0; j < sizeListaCambios; j++) {
            mHoraListaAnteriorSharedPrefEditor.remove(String.valueOf(j));
            mHoraListaAnteriorSharedPrefEditor.apply();
            mIconoSubeBajaListaAnteriorSharedPrefEditor.remove(String.valueOf(j));
            mIconoSubeBajaListaAnteriorSharedPrefEditor.apply();
            mMovimientoListaAnteriorSharedPrefEditor.remove(String.valueOf(j));
            mMovimientoListaAnteriorSharedPrefEditor.apply();
            mViajerosListaAnteriorSharedPrefEditor.remove(String.valueOf(j));
            mViajerosListaAnteriorSharedPrefEditor.apply();
            mOcupacionListaAnteriorSharedPrefEditor.remove(String.valueOf(j));
            mOcupacionListaAnteriorSharedPrefEditor.apply();
            mIconoBilletesListaAnteriorSharedPrefEditor.remove(String.valueOf(j));
            mIconoBilletesListaAnteriorSharedPrefEditor.apply();
        }
        // cambiamos tamañoShared:
        mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, 0);
        mAppSharedPrefEditor.apply();
        // limpiamos el array listaCambios:
        mListaCambiosAnterior.clear();
    }


    private void reset() {
        mViaje.setMaquina(0);
        mViaje.setTotalSuben(0);
        mViaje.setTotalBajan(0);
        guardarViajeEnSharedPref();
        // no deshacer (icono y dato)
        desactivaDeshacer();
        // actualizar views
        actualizaViewsDatosViaje(mTextViewViajerosActuales, mBotonAforo, mTextViewTotalSubenMaquina,
                mTextViewPuedenSubir);
    }

    // Este metodo esta tambien en clase Modelo, aun lo utiliza subenMaquina...

    private void respaldaViajePreCambiosYActualizaNuevoDatoEnViaje(int tipoDeCambio, int valorDelCambio) {
        // en resto de acciones, (suben, bajan, maquina) llamar a un metodo que haga esto:
        // guarda copia de viaje en respaldoViaje (hacer método) y en sharedRespaldoViaje antes de que realice cambio en viaje.
        mRespaldoViaje = (Viaje) mViaje.clone();
        guardarRespaldoViajeEnSharedPref();
        //cambia valor de respaldoViajeIniciado a true y en su sharedPref
        mRespaldoViajeIniciado = true;
        mAppSharedPrefEditor.putBoolean(Contract.RESPALDO_VIAJE_INICIADO_SHARED_PREF, true);
        mAppSharedPrefEditor.apply();
        switch (tipoDeCambio) {
            case Contract.CAMBIO_SUBEN_MAQUINA: // Realiza el cambio en el viaje ya que se acaba de guardar el respaldoViaje con copia justo antes de la modificacion
                mViaje.setMaquina(valorDelCambio);
                break;
            case Contract.CAMBIO_SUBEN_MANUAL:
                mViaje.setTotalSuben(valorDelCambio);
                break;
            case Contract.CAMBIO_BAJAN:
                mViaje.setTotalBajan(valorDelCambio);
                break;
        }

        // da false a ultimoCambioFueDeshacer y guardar ultimoCambioFueDeshacer sharedPred.
        mUltimoCambioFueDeshacerOInicio = false;
        mAppSharedPrefEditor.putBoolean(Contract.ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF, false);
        mAppSharedPrefEditor.apply();
        actualizaIconoDeshacer();
    }

    private void actualizaIconoDeshacer() {
        if (mAppSharedPref.getBoolean(Contract.ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF, true)) {
            mIconoDeshacer.setImageResource(R.drawable.ic_deshacer_inactivo);
        } else {
            mIconoDeshacer.setImageResource(R.drawable.ic_deshacer_activo);
        }
    }

    private void actualizaViewsParaManejoMaquinaOManual() {
        if (mViaje.isManejoMaquina()) {
            mIconoManejoMaquina.setImageResource(R.drawable.ic_maquina_seleccionado);
            mIconoManejoManual.setImageResource(R.drawable.ic_sin_maquina_deseleccionado);
            mImageSubenMaquina.setAlpha(1f);
            mImageSubenManual.setAlpha(0f);
            mTextViewTotalSubenMaquina.setAlpha(1f);
        } else { // isManejoManual:
            mIconoManejoManual.setImageResource(R.drawable.ic_sin_maquina_seleccionado);
            mIconoManejoMaquina.setImageResource(R.drawable.ic_maquina_deseleccionado);
            mImageSubenMaquina.setAlpha(0f);
            if (getBilletesSinMaquinaViajeActual() > 0) {
                mLinearIconoYNumBilletesManual.setAlpha(1f);
                mTextViewNumBilletesManual.setText("" + getBilletesSinMaquinaViajeActual());

            } else {
                mLinearIconoYNumBilletesManual.setAlpha(0f);
                mTextViewNumBilletesManual.setText("");
            }
            mImageSubenManual.setAlpha(1f);
            mTextViewTotalSubenMaquina.setAlpha(0f);
        }
    }

    private void guardarViajeEnSharedPref() {
        mAppSharedPrefEditor.putInt(Contract.TOTAL_SUBEN_SHARED_PREF, mViaje.getTotalSuben());
        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_SHARED_PREF, mViaje.getTotalBajan());
        mAppSharedPrefEditor.putInt(Contract.AFORO_SHARED_PREF, mViaje.getAforo());
        mAppSharedPrefEditor.putInt(Contract.MAQUINA_SHARED_PREF, mViaje.getMaquina());
        mAppSharedPrefEditor.putBoolean(Contract.MANEJO_MAQUINA_SHARED_PREF, mViaje.isManejoMaquina());
        mAppSharedPrefEditor.apply();
    }

    private void guardarRespaldoViajeEnSharedPref() {
        mAppSharedPrefEditor.putInt(Contract.TOTAL_SUBEN_RESPALDO_SHARED_PREF, mRespaldoViaje.getTotalSuben());
        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_RESPALDO_SHARED_PREF, mRespaldoViaje.getTotalBajan());
        mAppSharedPrefEditor.putInt(Contract.AFORO_RESPALDO_SHARED_PREF, mRespaldoViaje.getAforo());
        mAppSharedPrefEditor.putInt(Contract.MAQUINA_RESPALDO_SHARED_PREF, mRespaldoViaje.getMaquina());
        mAppSharedPrefEditor.putBoolean(Contract.MANEJO_MAQUINA_RESPALDO_SHARED_PREF, mRespaldoViaje.isManejoMaquina());
        // vamos a guardar los 3 datos de bajan PARA EL TEXTVIEW BAJAN antes de hacer nada para
        // poder guardar RESPALDO
        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_RESPALDO_SHARED_PREF,
                totalBajanActViaje);
        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_RESPALDO_SHARED_PREF,
                totalBajanAntViaje);
        mAppSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_RESPALDO_SHARED_PREF,
                numTotalBajanParaTV);
        mAppSharedPrefEditor.apply();
        mAppSharedPrefEditor.apply();
    }

    private void cargaViajeYDatosDesdeSharedPref(SharedPreferences appSharedPref) {
        mViaje.setTotalSuben(appSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0));
        mViaje.setTotalBajan(appSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0));
        mViaje.setMaquina(appSharedPref.getInt(Contract.MAQUINA_SHARED_PREF, 0));
        mViaje.setAforo(appSharedPref.getInt(Contract.AFORO_SHARED_PREF, 93));
        mViaje.setManejoMaquina(appSharedPref.getBoolean(Contract.MANEJO_MAQUINA_SHARED_PREF, true));
        // carga y actualiza boolean para tramo y avisos de viajeros sin billete. si es la
        // primera vez sera false porque no se habrá pulsADO TRAMO POR 1A VEZ AUN:
        esPeticionTramoParaAvisoViajerosSinBillete = mAppSharedPref.getBoolean(
                Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false);
        esUltimoCambioSuben = appSharedPref.getBoolean(Contract.ES_ULTIMO_CAMBIO_SUBEN_SHARED_PREF, false);
        // necesario para el mensaje del alertDialog en deshacer:
        mCualFueUltimoCambio = mAppSharedPref.getString(Contract.CUAL_FUE_ULTIMO_CAMBIO_SHARED_PREF,
                getString(R.string.no_hubo_cambios));
        // variables para totalesbajan de textview bajan:
        totalBajanActViaje = appSharedPref.getInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, 0);
        totalBajanAntViaje = appSharedPref.getInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF, 0);
        numTotalBajanParaTV = appSharedPref.getInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, 0);
    }

    private void cargaRespaldoViajeSharedPref(SharedPreferences appSharedPref) {
        mRespaldoViaje.setTotalSuben(appSharedPref.getInt(Contract.TOTAL_SUBEN_RESPALDO_SHARED_PREF, 0));
        mRespaldoViaje.setTotalBajan(appSharedPref.getInt(Contract.TOTAL_BAJAN_RESPALDO_SHARED_PREF, 0));
        mRespaldoViaje.setMaquina(appSharedPref.getInt(Contract.MAQUINA_RESPALDO_SHARED_PREF, 0));
        mRespaldoViaje.setAforo(appSharedPref.getInt(Contract.AFORO_RESPALDO_SHARED_PREF, 93));
        mRespaldoViaje.setManejoMaquina(appSharedPref.getBoolean(Contract.MANEJO_MAQUINA_RESPALDO_SHARED_PREF, true));
    }

    private void cargaListasCambios(SharedPreferences appSharedPref) {
        // ACTUAL:
        int sizeListaActual = appSharedPref.getInt(
                Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, 0);
        // se debe crear nueva lista para que no se add a la anterior
        // si tiene tamaño el array es decir si ya se agrego algun elemento al array:
        mListaCambiosActual = new ArrayList<>();
        if (sizeListaActual > 0) {
            for (int i = 0; i < sizeListaActual; i++) {
                ItemListaCambios item = new ItemListaCambios(
                        mHoraListaActualSharedPref.getString(String.valueOf(i), "0"),
                        mIconoSubeBajaListaActualSharedPref.getInt(String.valueOf(i), 0),
                        mMovimientoListaActualSharedPref.getString(String.valueOf(i), "0"),
                        mViajerosListaActualSharedPref.getString(String.valueOf(i), "0"),
                        mOcupacionListaActualSharedPref.getString(String.valueOf(i), "0%"),
                        mIconoBilletesListaActualSharedPref.getInt(String.valueOf(i), 0)
                );
                mListaCambiosActual.add(item);
            }
        }
        // ANTERIOR:
        int sizeListaAnterior = appSharedPref.getInt(
                Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, 0);
        // se debe crear nueva lista para que no se add a la anterior
        // si tiene tamaño el array es decir si ya se agrego algun elemento al array:
        mListaCambiosAnterior = new ArrayList<>();
        if (sizeListaAnterior > 0) {
            for (int i = 0; i < sizeListaAnterior; i++) {
                ItemListaCambios item = new ItemListaCambios(
                        mHoraListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                        mIconoSubeBajaListaAnteriorSharedPref.getInt(String.valueOf(i), 0),
                        mMovimientoListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                        mViajerosListaAnteriorSharedPref.getString(String.valueOf(i), "0"),
                        mOcupacionListaAnteriorSharedPref.getString(String.valueOf(i), "0%"),
                        mIconoBilletesListaAnteriorSharedPref.getInt(String.valueOf(i), 0)
                );
                mListaCambiosAnterior.add(item);
            }
        }
    }

    private void actualizaViewsDatosViaje(TextView textViewViajerosActuales,
                                          Button buttonAforo,
                                          TextView textViewMaquina,
                                          TextView textViewpuedenSubir) {
        textViewViajerosActuales.setText(Modelo.agregaEspacioPreUno(String.valueOf(mViaje.getViajerosActuales())));
        // el aforo, si no es el 100%, pondrá aforo reducido a: en vez de aforo de:
        int porcentajeEnPie = mAppSharedPref.getInt(Contract.STRING_PORCENTAJE_EN_PIE_SHARED_PREF, 100);
        int porcentajeSentado = mAppSharedPref.getInt(Contract.STRING_PORCENTAJE_SENTADO_SHARED_PREF, 100);
        int totalAforoReducido = mAppSharedPref.getInt(Contract.AFORO_SHARED_PREF, 93);
        if (porcentajeEnPie == 100 && porcentajeSentado == 100) {
            buttonAforo.setText("Aforo de " + totalAforoReducido);
        } else {
            buttonAforo.setText("Aforo Reducido a: " + totalAforoReducido);
        }
        textViewMaquina.setText("" + mViaje.getMaquina());
        textViewpuedenSubir.setText(Modelo.agregaEspacioPreUno(String.valueOf(mViaje.getPuedenSubir())));

        // cambia el color segun porcentaje:
        int aforo = mViaje.getAforo();
        int viajerosActuales = mViaje.getViajerosActuales();
        int porcentaje;
        if (viajerosActuales == 0) {
            porcentaje = 0;
        } else {
            porcentaje = (viajerosActuales * 100) / aforo;
        }

        if (porcentaje < 33) {
            textViewViajerosActuales.setTextColor(Color.parseColor("#8BC34A"));
        } else if (porcentaje < 66) {
            textViewViajerosActuales.setTextColor(Color.parseColor("#FFD403"));
        } else {
            textViewViajerosActuales.setTextColor(Color.parseColor("#F44336"));
        }
        // actualiza ocupación
        // ACTUAL:
        // IMPORTANTE: ES UN BUG DE PROGRESSBAR: SI SE REPITE EL VALOR CON OTRO ANTERIOR NO MUESTRA
        // EL CORRECTO, ES NECESARIO SETEAR ANTES A OTRO DISTINTO
        // como da errores cuando se utiliza el metodo setprogress vamos a cambiar siempre el int
        // antes de setear el definitivo en el setprogress:
        if (porcentaje == 0) {
            progressActualOcupacion.setProgress(porcentaje + 1);
            progressActualOcupacion.setProgress(0);
        } else {
            progressActualOcupacion.setProgress(porcentaje - 1);
            progressActualOcupacion.setProgress(porcentaje);
        }
        if (porcentaje == 0) {
            tvOcupacionActualPorcentaje.setText("0 %");
        } else {
            tvOcupacionActualPorcentaje.setText(getResources().getString(
                    R.string.tv_porcenaje_ocu_actual, porcentaje));
        }
        // MÁXIMA:
        // hallar el porcentaje maximo y la hora
        // todo: este código se debe modificar para encontrar en el bloque for el indice de la lista
        //  donde viajeros tenga signo " [" para modificar el máximo aforo
        int maximaOcupacion = 0;
        int indiceHoraPunta = 0;
        int viajerosAhora = 0;
        String horaPunta = "";
        int porcentajeMaxOcupacion = 0;
        String viajerosHoraPunta = "";
        if (mListaCambiosActual.size() != 0) { // necesario el if ya que si no se puede producir un
            // java.lang.IndexOutOfBoundsException
            for (int i = 0; i < mListaCambiosActual.size(); i++) {
                // debemos saber si contiene .getViajeros() el string " ["; en ese caso el dato incluye
                // viajeros del viaje anterior con el formato <viajerosActuales> [+ <viajerosSinBillete>*]
                if (mListaCambiosActual.get(i).getViajeros().indexOf(" +") != -1) { // contiene " ["
                    // modificamos variable viajerosAhora para la comparación del siguiente if...
                    int indice = mListaCambiosActual.get(i).getViajeros().indexOf(" +");
                    String stringViajerosViajeActual = mListaCambiosActual.get(i).getViajeros().substring(0, indice);
                    viajerosAhora = Integer.parseInt(stringViajerosViajeActual);
                } else {
                    // no se altera, ya que el string no tiene formato <viajerosActuales> [+ <viajerosSinBillete>*]
                    viajerosAhora = Integer.parseInt(mListaCambiosActual.get(i).getViajeros());
                }
                if (viajerosAhora > maximaOcupacion) {
                    maximaOcupacion = viajerosAhora;
                    indiceHoraPunta = i;
                }
            }
            porcentajeMaxOcupacion = (maximaOcupacion * 100) / aforo;
            horaPunta = formateaHHmm(mListaCambiosActual.get(indiceHoraPunta).getHora());
            viajerosHoraPunta = String.valueOf(maximaOcupacion);
        }
        // mismo metodo que con progressActualOcupacion:
        if (porcentajeMaxOcupacion == 0) {
            progressMaximaOcupacion.setProgress(porcentajeMaxOcupacion + 1);
            progressMaximaOcupacion.setProgress(0);
        } else {
            progressMaximaOcupacion.setProgress(porcentajeMaxOcupacion - 1);
            progressMaximaOcupacion.setProgress(porcentajeMaxOcupacion);
        }
        if (porcentajeMaxOcupacion == 0) {
            tvOcupacionMaximaPorcentaje.setText("0 %");
            tvOcupacionMaximaViajeros.setText("0");
            tvOcupacionMaximaHora.setText(getResources().getString(R.string.titulo_tv_ocupacion_maxima));
        } else {
            tvOcupacionMaximaPorcentaje.setText(getResources().getString(
                    R.string.tv_porcenaje_ocu_max, porcentajeMaxOcupacion));
            tvOcupacionMaximaViajeros.setText(Modelo.agregaEspacioPreUno(String.valueOf(viajerosHoraPunta)));
            // depende de si coincide con la actual o no, mostrará una hora o "ahora"
            String textoOcupacionMaxima;
            if (maximaOcupacion == mViaje.getViajerosActuales())
                textoOcupacionMaxima = getResources().getString(R.string.ahora);
            else textoOcupacionMaxima = horaPunta;
            tvOcupacionMaximaHora.setText(String.valueOf(textoOcupacionMaxima));
        }
        // en este metodo se actualiza la variable numViajerosSinBillete
        actualizaAvisosViajerosSinBilleteYCompleto();
        // actualiza manejo maquina y manual y además añade num de billetes manuales en frame suben
        actualizaViewsParaManejoMaquinaOManual();
        actualizaTvHayMasBilletes();
        actualizaTvTotalBajan();
        buscaYActualizaTextViewsUltimoSubeYBaja();
        actualizaColorIcTramo();
        actualizaTvTotalSuben();
    }

    private void actualizaTvTotalSuben() {
        // total billetes maquina + billetes sin maquina:
        // se elige lo que marca la máquina porque si es tramo ayuda más,  y así se ve claro el
        // total en máquina y los sin máquina...
        int billetesMaquina = mViaje.getMaquina();
        int billetesSinMaquina = getBilletesSinMaquinaViajeActual();
        int totalSubenViaje = billetesMaquina + billetesSinMaquina;
        if (totalSubenViaje > 0) {
            mTextViewNumTotalSuben.setAlpha(1);
            mTextViewNumTotalSuben.setText(getResources().getString(
                    R.string.total_suben_viaje, totalSubenViaje));
        } else {
            mTextViewNumTotalSuben.setAlpha(0);
            mTextViewNumTotalSuben.setText("");
        }

    }


    private void actualizaColorIcTramo() {
        if (esPeticionTramoParaAvisoViajerosSinBillete) {
            // cambia los imageview al drawable seleccionado
            ivIcInicioTramo.setImageResource(R.drawable.ic_tramo_seleccionado);
        } else {
            // cambia los imageview al drawable deseleccionado
            ivIcInicioTramo.setImageResource(R.drawable.ic_tramo_deseleccionado);
        }
    }

    private void actualizaTvHayMasBilletes() {
        if (mViaje.isManejoMaquina()) {
            if (getBilletesSinMaquinaViajeActual() > 0) {
                // hay que avisar que hay mas billetes y que son manuales:
                linearHayMasBilletes.setAlpha(1.0f);
                ivHayMasBilletes
                        .setImageDrawable(VectorDrawableCompat
                                .create(
                                        getResources(),
                                        R.drawable.ic_sin_maquina_hay_mas_billetes,
                                        null));
                tvHayMasBilletes.setText(String.valueOf(getBilletesSinMaquinaViajeActual()));
            } else {
                // pasa a alpha 0 el linear
                linearHayMasBilletes.setAlpha(0.0f);
            }
        }
        if (!mViaje.isManejoMaquina()) {
            if (mViaje.getMaquina() > 0) {
                // Hay que avisar que hay mas billetes y que son de máquina:
                linearHayMasBilletes.setAlpha(1.0f);
                ivHayMasBilletes
                        .setImageDrawable(VectorDrawableCompat
                                .create(
                                        getResources(),
                                        R.drawable.ic_maquina_hay_mas_billetes,
                                        null));
                // ahora se pone getMaquina por si es tramo o se resetea, ya que se pretende avisar
                // del número total en máquina y no crear confusion
                tvHayMasBilletes.setText(String.valueOf(mViaje.getMaquina()));
            } else {
                // pasa a alpha 0 el linear
                linearHayMasBilletes.setAlpha(0.0f);
            }
        }
    }

    private void actualizaTvTotalBajan() {
        // si numTotalBajanParaTV > 0 -> tv alpha -> 1 , y cambia el texto
        if (numTotalBajanParaTV > 0) {
            mTextViewNumTotalBajan.setAlpha(1);
            mTextViewNumTotalBajan.setText(getResources().getString(
                    R.string.bajan_en_total, numTotalBajanParaTV));
        } else {
            // si no: alpha -> 0 y texto ""
            mTextViewNumTotalBajan.setAlpha(0);
            mTextViewNumTotalBajan.setText("");
        }
    }

    // todo Los 3 siguientes metodos estan en Modelo clase, utilizar esos metodos y borrar este...
    private int getBilletesMaquinaViajeActual() {
        int subenConBilleteMaquina = 0;
        // recoge datos para subenmaquina:
        for (int i = 0; i < mListaCambiosActual.size(); i++) {
            // si este item de la lista es de billete maquina
            if (mListaCambiosActual.get(i).getIconoBillete() == R.drawable.ic_billete_maquina_lista_cambios_24)
                // suma a subenmaquina la cantidad de movimiento:
                subenConBilleteMaquina += Integer.parseInt(mListaCambiosActual.get(i).getMovimiento());
        }
        return subenConBilleteMaquina;
    }

    private int getBilletesSinMaquinaViajeActual() {
        int subenSinBilleteMaquina = 0;
        // recoge datos para subenmanual:
        for (int i = 0; i < mListaCambiosActual.size(); i++) {
            // si este item de la lista es de billete sin maquina
            if (mListaCambiosActual.get(i).getIconoBillete() == R.drawable.ic_billete_sin_maquina_lista_cambios_24)
                // suma a subenmanual la cantidad de movimiento:
                subenSinBilleteMaquina += Integer.parseInt(mListaCambiosActual.get(i).getMovimiento());
        }
        return subenSinBilleteMaquina;
    }

    private int getTotalBajan() {
        int totalBajan = 0;
        // recoge datos para bajan:
        for (int i = 0; i < mListaCambiosActual.size(); i++) {
            // si este item de la lista es de icono menos:
            if (mListaCambiosActual.get(i).getIconoSubeBaja() == R.drawable.ic_menos_24)
                // suma a totalBajan la cantidad de movimiento:
                totalBajan += Integer.parseInt(mListaCambiosActual.get(i).getMovimiento());
        }
        return totalBajan;
    }

    private void actualizaAvisosViajerosSinBilleteYCompleto() {
        // actualiza si hay viajeos de anterior viaje:
        int subenConBilleteMaquina = getBilletesMaquinaViajeActual();
        int subenSinBilleteMaquina = getBilletesSinMaquinaViajeActual();
        numViajerosSinBillete = 0;
        int totalSubenViaje = subenConBilleteMaquina + subenSinBilleteMaquina;
        // si hay mas viajeros que los que han subiedo en este viaje
        // todo: lo suyo seria que se obtuviera del array de viaje directamente en vez de sharedpref,
        //  se deberia de cargar desde sharedpref al realizar cambios en datos...
        int totalSuben = mAppSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0);
        int totalBajan = mAppSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
        int viajerosActuales = totalSuben - totalBajan;
        // aqui hay que diferenciar si la actualizacion viene de tramo o no:
        // si es de tramo hay un booblean espeticionTramoParaAvisoViajerosSinBillete que estará en true
        // y en ese caso viajerosDelViajeAnterior será= viajerosActuales - maquina
        // de tal manera que si hay mas viajerosActuales que maquina saltará el aviso y si no, no.
        // al salir de este método se vuelve a poner el boolean en false...
        // este boolean debe de estar guardado en sharedPref por lo que pueda pasar
        if (esPeticionTramoParaAvisoViajerosSinBillete) { // con sustituir al valor de totalSubenViaje
            // por el de maquina es suficiente para saber si hay mas viajeros que maquina:
            totalSubenViaje = mViaje.getMaquina() + subenSinBilleteMaquina; // necesario sumar
            // subenSinBilleteMaquina para que los tenga en cuenta en el aviso y no los cuente como
            // viajeros sin billete en el aviso, ya que no se sabe donde se les acaba el billete,
            // porque no aparecen en la máquina...
            // Si es tramo no se pondrá el boolean esPeticionTramoParaAvisoViajerosSinBillete en
            // false hasta que inicie nuevo viaje, si no dejaria de avisar como tramo el aviso...
        }
        numViajerosSinBillete = viajerosActuales - totalSubenViaje;
        if (numViajerosSinBillete < 0) numViajerosSinBillete = 0; // con esto no dejamos que haya
        // resultados negativos, solo necesitamos saber si hay mas viajeros que billetes...
        // GUARDAMOS LA VARIABLE numViajerosSinBillete EN SHARED YA QUE ES NECESARIA EN ACTIVITYBAJAN
        mAppSharedPrefEditor.putInt(Contract.NUM_VIAJEROS_SIN_BILLETE_DESDE_AVISOS_SHARED_PREF,
                numViajerosSinBillete);
        mAppSharedPrefEditor.apply();
        if ((numViajerosSinBillete) > 0) { // AVISO DE QUEDAN VIAJEROS SIN BILLETES:
            String textoAviso = getString(R.string.hay_viajeros_del_viaje_anterior, numViajerosSinBillete);
            mTextViewAvisosViaje.setText(textoAviso);
            // la fuentetipo es normal para avisos:
            Typeface fuentePorDefecto = Typeface.DEFAULT;
            mTextViewAvisosViaje.setTypeface(fuentePorDefecto);
            // cambia el color del fondo a rojo para avisos:
            cardViewFondoAvisos.setCardBackgroundColor(getResources().getColor(R.color.rojo_apagado));
            mTextViewAvisosViaje.setBackgroundColor(getResources().getColor(R.color.rojo_apagado));
        } else { // CAMBIA EL FONDO COLOR A NEGRO
            cardViewFondoAvisos.setCardBackgroundColor(getResources().getColor(R.color.black));
            mTextViewAvisosViaje.setBackgroundColor(getResources().getColor(R.color.black));
            // SI ESTÁ COMPLETO CAMBIA FUENTE Y TEXTO DE OCUPACION A: COMPLETO CON LUMINOSO
            if (mViaje.getViajerosActuales() == mViaje.getAforo()) {
                Typeface fuenteLuminosoLetreo = Typeface.createFromAsset(getAssets(), "font_assets/display_luminoso_letrero.ttf");
                mTextViewAvisosViaje.setTypeface(fuenteLuminosoLetreo);
                mTextViewAvisosViaje.setText(R.string.completo);
            } else {
                // SI NO ESTÁ COMPLETO cambia la fuentetipo a OTRA LETRA:
                Typeface fuentePorDefecto = Typeface.DEFAULT;
                mTextViewAvisosViaje.setTypeface(fuentePorDefecto);
                // y elimina el mensaje de viajeros sin billete:
                mTextViewAvisosViaje.setText("");
            }


        }
    }

    private void openActivitySubenManual(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.entraporla_izq, R.anim.sevaporla_der);
    }

    private void openActivityBajan(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.entraporla_der, R.anim.sevaporla_izq);
    }

    private void openDialogTeclado(int peticionTeclado) {

        TecladoDialogFragment tecladoDialogFragment = new TecladoDialogFragment(peticionTeclado);
        tecladoDialogFragment.show(getSupportFragmentManager(), "TECLADO_DIALOG_FRAGMENT");
    }

    @Override // MANEJA EL CLICK DE ACEPTAR DEL TECLADO
    public void aplicaResultadoTeclado(String resultadoTeclado, int peticionTeclado) {

        switch (peticionTeclado) {
            case Contract.PETICION_TECLADO_SUBEN_MAQUINA:
                aplicaResultadoSubenMaquina(resultadoTeclado);
                break;

            case Contract.PETICION_TECLADO_TRAMO: // NUEVO TRAMO
                if (resultadoTeclado.equals("")) { // evita excepcion de tipo de dato incorrecto
                    Toast.makeText(this, R.string.no_introdujiste_nada, Toast.LENGTH_LONG).show();
                } else {
                    // nuevoViajeMaquina(resultadoTeclado); // No llamará a este método, este método queda
                    // en exclusiva para cuando se inicia nuevo viaje desde actualiza suben maquina...
                    // lo unico que debe de hacer es: cambiar el dato en máquina, lo demás sigue igual.
                    if (Integer.parseInt(resultadoTeclado) < mViaje.getMaquina()) {
                        // es correcto el dato de teclado y cambia maquina a dato teclado
                        mViaje.setMaquina(Integer.parseInt(resultadoTeclado));
                        mAppSharedPrefEditor.putInt(Contract.MAQUINA_SHARED_PREF, Integer.parseInt(resultadoTeclado));
                        mAppSharedPrefEditor.apply();
                        esPeticionTramoParaAvisoViajerosSinBillete = true; // es para que reste a viajeros
                        // los billetes de maquina y asi salte aviso de viajeros sin billetes o no, ya que se
                        // obtienen datos distintos dependiendo de si es nuevo viaje o nuevo tramo...
                        mAppSharedPrefEditor.putBoolean(
                                Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, true);
                        mAppSharedPrefEditor.apply();
                        // El cambio de tramo se acaba de realizar, hay que comprobar si hay
                        //  viajeros sin billete para cambiar las variables de totalbajan para el
                        //  textview de bajan:
                        if (mViaje.getViajerosActuales() > mViaje.getMaquina()) { // hay viajeros
                            // sin billete
                            cambVarBajanNewViajeTramoVSinBillete();
                        } else { // no hay viajeros sin billete
                            cambVarBajanNewViajeTramoVConBillete();
                        }
                        // el cambio de color del icono tramo se realiza en metodo actualizaViewsDatosViaje:
                        actualizaViewsDatosViaje(mTextViewViajerosActuales, mBotonAforo, mTextViewTotalSubenMaquina,
                                mTextViewPuedenSubir);
                        Toast.makeText(this, R.string.cambio_de_tramo_con_exito, Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(resultadoTeclado) == mViaje.getMaquina()) { // es igual no hay cambios
                        Toast.makeText(this, R.string.cantidad_la_misma_no_hubo_cambios,
                                Toast.LENGTH_LONG).show();
                    } else if (Integer.parseInt(resultadoTeclado) > mViaje.getMaquina()) {
                        // es incorrecto el dato de teclado, al cambiar de tramo debe de haber menos billetes en maquina
                        // AlertDialog explicando el tema "Al cambiar de tramo hay menos
                        // billetes en máquina y has introducido más billetes en máquina de los que había. Vuelve a introducir el dato o pulsa el icono  de 'actualiza suben máquina' si lo que deseas es añadir mas billetes en máquina"
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.alert_dialog_dato_teclado_incorrecto_tramo);
                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                        View view = layoutInflater.inflate(R.layout.icono_warning, null);
                        builder.setView(view);
                        builder.setPositiveButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // no hace nada, cierra y avisa de que no se hizo ningun cambio con un toast
                                Toast.makeText(MainActivity.this, R.string.no_hubo_cambios, Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                    }
                    /*  Es necesario comprobar que la cantidad es inferior, ya que si es un tramo se le
                      quita una porción del viaje y es imposible que sea superior. De modo que si es
                      superior plantear la posibilidad de actualizar la máquina en el botón de actualiza
                      suben maquina...
                                         */
                }
                break;

            case Contract.PETICION_TECLADO_RESET_VIAJEROS:
                if (resultadoTeclado.equals("")) { // evita excepcion de tipo de dato incorrecto
                    Toast.makeText(this, R.string.no_introdujiste_nada, Toast.LENGTH_LONG).show();
                } else {
                    // es necesario comprobar que el dato es correcto, es decir, que viajeros no
                    // supere el aforo...
                    if (Integer.parseInt(resultadoTeclado) > mViaje.getAforo()) {
                        // solo muestra en Toast que no es posible, y salta al break sin hacer nada
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.viajeros_es_superior_al_aforo_alertdialog_warning);
                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                        View view = layoutInflater.inflate(R.layout.icono_warning, null);
                        builder.setView(view);
                        builder.setPositiveButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // no hace nada, cierra y avisa de que no se hizo ningun cambio con un toast
                                Toast.makeText(MainActivity.this, R.string.no_hubo_cambios, Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.show();
                    } else if (Integer.parseInt(resultadoTeclado) == mViaje.getViajerosActuales()) {
                        // necesario ya que si no saldrá en listacambios como movimiento 0...
                        Toast.makeText(this, R.string.cantidad_la_misma_no_hubo_cambios, Toast.LENGTH_LONG).show();
                    } else if ((getBilletesSinMaquinaViajeActual() + getBilletesMaquinaViajeActual())
                            <
                            Integer.parseInt(resultadoTeclado)) {
                        // evita con esto que se resetee los viajeros  una cantidad mayor a la del
                        // total de billetes vendidos, problemas como el que salte aviso de viajeros
                        // sin billete que solo está pensado para viajes nuevos o tramo nuevo, y
                        // cuando se bajaban todos los VSB , despeués de haber hecho reset a más
                        // viajeros no contaba los que bajantotal hasta no haber bajado la misma
                        // cantida que la diferencia del reset...  esto es lo que evita este if
                        int totalBilletes = getBilletesSinMaquinaViajeActual() + getBilletesMaquinaViajeActual();
                        int resetViajeros = Integer.parseInt(resultadoTeclado);
                        int posiblesViajerosSinBilletes = resetViajeros - totalBilletes;
                        // muestro en alertdialog que revise datos de billetes en maquina y manual
                        // ya que desea reset a mas viajeros que los billetes que ha vendido
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(getResources().getString(
                                R.string.viajeros_sin_billete_revisa_billetes_y_vuelve_aqui,
                                totalBilletes, resetViajeros, posiblesViajerosSinBilletes));
                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                        View view = layoutInflater.inflate(R.layout.icono_warning, null);
                        builder.setView(view);
                        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // no hace nada, cierra y avisa de que no se hizo ningun cambio con un toast
                                Toast.makeText(MainActivity.this, R.string.no_hubo_cambios, Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.show();
                    } else {
                        // obtenemos viajerossinbillete ahora:
                        int viajerosSinBilleteRealParaTotalBajan = numViajerosSinBillete; // gracias
                        // a esto consigo utilizar la variable viajerossinbillete para totalbajan
                        // para textview de bajan
                        int antiguoViajeros = mViaje.getViajerosActuales(); // usado para saber si
                        // aumento o disminuyo los viajeros y para saber en cuantos...
                        // establece totalSuben con el dato del teclado
                        mViaje.setTotalSuben(Integer.parseInt(resultadoTeclado));
                        // establece totalBajan en 0
                        mViaje.setTotalBajan(0);
                        // guarda datos en sharedPref
                        mAppSharedPrefEditor.putInt(Contract.TOTAL_SUBEN_SHARED_PREF, mViaje.getTotalSuben());
                        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
                        // importante para que no se pueda deshacer despues de resetviajeros:
                        desactivaDeshacer();
                        // actualizar views
                        actualizaViewsDatosViaje(mTextViewViajerosActuales, mBotonAforo,
                                mTextViewTotalSubenMaquina, mTextViewPuedenSubir);
                        // Tengo que saber si bajan o suben despues del reset
                        // Si suben se modifica solo la lista actual
                        if (antiguoViajeros < Integer.parseInt(resultadoTeclado)) { // suben, solo cambia la actual listacambios
                            agregaNuevoItemSubenOBajanListaCambiosDesdeResetViajeros(
                                    resultadoTeclado, antiguoViajeros, LISTA_ACTUAL_CAMBIOS,
                                    false, 0, 0);

                        } else { // bajan
                            int cantidadBajan = antiguoViajeros - Integer.parseInt(resultadoTeclado);
                            if ((viajerosSinBilleteRealParaTotalBajan - cantidadBajan) > 0) { // quedan viajeros sin billete
                                // si es tramo se guarda en actual lista viaje. Como es reset no tiene deshacer:
                                if (mAppSharedPref.getBoolean( // es tramo:
                                        Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false)) {
                                    agregaNuevoItemSubenOBajanListaCambiosDesdeResetViajeros(
                                            resultadoTeclado, antiguoViajeros, LISTA_ACTUAL_CAMBIOS,
                                            false, 0, 0);
                                    // En Reset viajeros:
                                    // no es necesario guardar ultimalistacambiada porque cuando se
                                    // hace reset se inhabilita deshacer...
                                } else { // no es tramo:
                                    agregaNuevoItemSubenOBajanListaCambiosDesdeResetViajeros(
                                            resultadoTeclado, antiguoViajeros, LISTA_ANTERIOR_CAMBIOS,
                                            false, 0, 0);
                                }
                            } else { // es igual o menor que 0, es decir ya se bajaron todos los viajeros sin billete
                                // si viajeros sin billete menos cantidad bajan no es 0 es que es negativo de modo que han bajado del actual viaje
                                int diferenciaEntreNumVSByCantidadBajan =
                                        (viajerosSinBilleteRealParaTotalBajan - cantidadBajan);
                                if (diferenciaEntreNumVSByCantidadBajan != 0) { // esta cantidad es los que bajan de actual viaje
                                    diferenciaEntreNumVSByCantidadBajan = Math.abs(diferenciaEntreNumVSByCantidadBajan);
                                } // evita cambiar signo a 0
                                // aqui se obtiene la cantidad que bajan sin billete:
                                int totalbajanViajeAnteriorSinBillete = cantidadBajan - diferenciaEntreNumVSByCantidadBajan;

                                // SI HAY VIAJEROS QUE BAJAN DE ESTE VIAJE: MODIFICA LA LISTA ACTUAL DE VIAJE:
                                // excepcion: si es tramo, que bajaran todos en 1 sola cantidad en actual lista viaje:
                                if (mAppSharedPref.getBoolean(
                                        Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false)) {
                                    // aqui es tramo:
                                    agregaNuevoItemSubenOBajanListaCambiosDesdeResetViajeros(
                                            resultadoTeclado, antiguoViajeros, LISTA_ACTUAL_CAMBIOS,
                                            false, 0, 0);
                                } else {
                                    // aqui no es tramo:
                                    if (diferenciaEntreNumVSByCantidadBajan != 0) {
                                        agregaNuevoItemSubenOBajanListaCambiosDesdeResetViajeros(
                                                resultadoTeclado,
                                                antiguoViajeros,
                                                LISTA_ACTUAL_CAMBIOS,
                                                true,
                                                diferenciaEntreNumVSByCantidadBajan,
                                                0);
                                    }
                                    // SI HAY VIAJEROS QUE BAJAN SIN BILLETE MODIFICA LA LISTA ANTERIOR DE VIAJE:
                                    if (totalbajanViajeAnteriorSinBillete != 0) {
                                        agregaNuevoItemSubenOBajanListaCambiosDesdeResetViajeros(
                                                resultadoTeclado, antiguoViajeros,
                                                LISTA_ANTERIOR_CAMBIOS,
                                                true,
                                                0,
                                                totalbajanViajeAnteriorSinBillete);
                                    }
                                }

                            }
                        }

                        // actualiza los textviews de ultimoscambios:
                        buscaYActualizaTextViewsUltimoSubeYBaja();

                        // cambiamos variables totalbajan para textview bajan:

                        // aqui es donde hay que restar a numtotalbajantv el
                        //  resultadoteclado-antiguoviajeros para que resulte como si se anulara
                        //  un bajan anterior al sumarlo ahora...
                        // comprobar que el resultado no sea negativo, y en tal caso aplicar la
                        // formula: totalbajan=totalsuben(billetesmaquina + billetessinmaquina)-viajerosactuales(resultadoteclado)
                        if (antiguoViajeros < Integer.parseInt(resultadoTeclado)) {
                            // es suben, mejor dicho rectifica un bajan... es necesario tambien
                            // cambiar el totalbajantextview
                            int incrementoReset = Integer.parseInt(resultadoTeclado) - antiguoViajeros;
                            // cambiamos el valor de variables total bajan:
                            // el if es para que el resultado de totalbajan no sea negativo, por errores del usuario...
                            if ((numTotalBajanParaTV - incrementoReset) < 0) {
                                // formula:  totalbajan = totalsuben - viajerosactuales
                                totalBajanActViaje =
                                        (getBilletesMaquinaViajeActual() +
                                                getBilletesSinMaquinaViajeActual()) -
                                                Integer.parseInt(resultadoTeclado);
                                numTotalBajanParaTV = (getBilletesMaquinaViajeActual() +
                                        getBilletesSinMaquinaViajeActual()) -
                                        Integer.parseInt(resultadoTeclado);
                            } else {
                                totalBajanActViaje = totalBajanActViaje - incrementoReset;
                                numTotalBajanParaTV = numTotalBajanParaTV - incrementoReset;
                            }
                            // guardamos en sharedpref los cambios:
                            mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, totalBajanActViaje);
                            mAppSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, numTotalBajanParaTV);
                            mAppSharedPrefEditor.apply();
                            actualizaTvTotalBajan();
                        }

                        // Aqui esta el problema de totalbajan cuando resetviajeros y bajan del actual viaje...
                        // El problema está en la variable movimientoListaCambios que obtiene el valor solo del ultimo cambio en el metodo agregaritem...
                        if (antiguoViajeros > Integer.parseInt(resultadoTeclado)) { // es bajan y vamos a cambiar las variables de bajan para textview:
                            int cantidadBajan = Math.abs(movimientoListaCambios);
                            // actualizamos las variables para total bajan del textview de bajan:
                            if ((viajerosSinBilleteRealParaTotalBajan - cantidadBajan) > 0) {
                                // aun quedan viajeros sin billete despues de la bajada
                                int totalBajanViajeAnterior = mAppSharedPref.getInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF, 0);
                                totalBajanViajeAnterior += cantidadBajan; // se suma cantidadbajan al anterior valor
                                mAppSharedPrefEditor.putInt(
                                        Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF, totalBajanViajeAnterior);
                                numTotalBajanParaTV = totalBajanViajeAnterior;
                                mAppSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, totalBajanViajeAnterior);
                                mAppSharedPrefEditor.apply();
                            } else { // es igual o menos que 0, es decir ya se bajaron todos los viajeros sin billete
                                // El problema es que en vez de darnos los viajeros sin billete nos da 0 numViajerosSinBillete
                                int diferenciaEntreNumVSByCantidadBajan = (viajerosSinBilleteRealParaTotalBajan - cantidadBajan);
                                if (diferenciaEntreNumVSByCantidadBajan != 0)
                                    diferenciaEntreNumVSByCantidadBajan =
                                            Math.abs(diferenciaEntreNumVSByCantidadBajan); // evita cambiar signo a 0
                                int agregarATotalBajanViajeActual = diferenciaEntreNumVSByCantidadBajan;
                                // se obtiene el totalbajan acumulado de actualviaje ya que no quedan del anterior sin billete
                                int totalBajanViajeActual = mAppSharedPref.getInt(
                                        Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, 0);
                                totalBajanViajeActual += agregarATotalBajanViajeActual; // se suma los del viaje actual
                                numTotalBajanParaTV = totalBajanViajeActual;
                                mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, totalBajanViajeActual);
                                mAppSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, totalBajanViajeActual);
                                mAppSharedPrefEditor.apply();
                            }
                            actualizaTvTotalBajan();
                        }
                    }
                }
                break;
            case Contract.PETICION_TECLADO_RESET_MAQUINA:
                if (resultadoTeclado.equals("")) { // evita excepcion de tipo de dato incorrecto
                    Toast.makeText(this, R.string.no_introdujiste_nada, Toast.LENGTH_LONG).show();
                } else {
                    // aqui reseteamos la máquina:
                    // el valor de maquina será resultadoteclado:
                    mViaje.setMaquina(Integer.parseInt(resultadoTeclado));
                    // se guardara en sharedPref
                    mAppSharedPrefEditor.putInt(Contract.MAQUINA_SHARED_PREF, Integer.valueOf(resultadoTeclado));
                    mAppSharedPrefEditor.apply();
                    // importante: desactiva deshacer para que no haya datos erroneos...
                    desactivaDeshacer();
                    /* como el cambio solo afecta al texview de maquina no llamamos a actualizaviews
                     y solo cambiamos el textview modificado
                                          */
                    mTextViewTotalSubenMaquina.setText("" + resultadoTeclado);
                }
                break;
        }
    }

    private void aplicaResultadoSubenMaquina(String resultadoTecladoParam) {
        if (resultadoTecladoParam.equals("")) {
            // todo aqui manejar boton desde fuera (coindg in flow video sobre personalizar alertdialog)
            //  para que al pulsar aceptar no salga del teclado hasta que no introduzca un dato... con dimish cierra dialogo
            Toast.makeText(this, R.string.no_introdujiste_nada, Toast.LENGTH_LONG).show();
        } else {
            if (mViaje.getMaquina() > Integer.parseInt(resultadoTecladoParam)) { /* el resultado del
                 teclado es inferior al anterior, de modo que pregunta que hacer en AlertDialog:
                 deseas nuevo viaje?
                */
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage(R.string.alert_dialog_introducida_cantidad_inferior_nuevo_viaje);
                LayoutInflater imagenWarning = LayoutInflater.from(MainActivity.this);
                final View vistaWarning = imagenWarning.inflate(R.layout.icono_warning, null);
                alertDialog.setView(vistaWarning);
                alertDialog.setPositiveButton(R.string.boton_alert_dialog_nuevo_viaje, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nuevoViajeMaquina(resultadoTecladoParam);
                    }
                });
                alertDialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, R.string.toast_post_alert_dialog_no_actualizo_cantidad, Toast.LENGTH_SHORT).show();
                        // no hace nada y sale sin cambios ya que cancelo el el alertdialog y salta al break del switch sin hacer nada
                    }
                });
                alertDialog.show();
            } else {
                actualizaMaquinaConIncremento(resultadoTecladoParam);
            }
        }

    }

    private void actualizaMaquinaConIncremento(String stringActualizacionMaquina) {

        // en este caso el resultado de teclado es superior al anterior y sigue con normalidad:
        // actualiza totalSuben y viajerosActuales + guarda datos en sharedPref:
        // necesario int para luego hayar el incremento en datos de la maquina:
        int anteriorDatoMaquina = mViaje.getMaquina();
        int incrementoMaquina = hayarIncrementoMaquina(anteriorDatoMaquina,
                Integer.parseInt(stringActualizacionMaquina));

        // aqui va la comprobacion de tope de aforo:
        // comprueba que el dato obtenido en resultadoTeclado (stringActualizacionMaquina) es correcto
        int posibleTotalViajeros = mViaje.getViajerosActuales() + incrementoMaquina;
        if (posibleTotalViajeros > mViaje.getAforo()) {
            String tituloDialog = "";
            int puedenSubir = mViaje.getAforo() - mViaje.getViajerosActuales();
            int excedesElAforoEn = posibleTotalViajeros - mViaje.getAforo();
            tituloDialog = getString(
                    R.string.aforo_excedido_dialogo_teclado_suben_maquina, puedenSubir, excedesElAforoEn);
            // no es posible, asi que lanza un alertDialog avisando...
            AlertDialog.Builder builderAforoExcedido = new AlertDialog.Builder(this);
            builderAforoExcedido.setTitle(tituloDialog);
            LayoutInflater imagenWarning = LayoutInflater.from(MainActivity.this);
            final View vistaWarning = imagenWarning.inflate(R.layout.icono_warning, null);
            builderAforoExcedido.setView(vistaWarning);
            builderAforoExcedido.setPositiveButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // no hace nada solo vuelve al mainactivity...
                    Toast.makeText(MainActivity.this, R.string.no_hubo_cambios, Toast.LENGTH_LONG).show();
                }
            });
            builderAforoExcedido.show();
            // hasta aqui es la comprobacion
        } else {
            // se comprueba que hay incremento, si no no se hace ningun cambio:
            if (incrementoMaquina != 0) {
                respaldaViajePreCambiosYActualizaNuevoDatoEnViaje(Contract.CAMBIO_SUBEN_MAQUINA,
                        Integer.parseInt(stringActualizacionMaquina));
                // actualiza datosMaquina en sharedPref, el cambio se realizo en metodo respaldaViajePreCambios
                mAppSharedPrefEditor.putInt(Contract.MAQUINA_SHARED_PREF, mViaje.getMaquina());
                // actualiza totalSuben:
                mViaje.setTotalSuben(mViaje.getTotalSuben() + incrementoMaquina);
                mAppSharedPrefEditor.putInt(Contract.TOTAL_SUBEN_SHARED_PREF, mViaje.getTotalSuben());

                // guarda datos en String cualFueUltimoCambio para incluirlo en el alertDialog de deshacer
                mCualFueUltimoCambio =
                        "\n  "
                                + getString(R.string.actualizaste_la_maquina_de) + " "
                                + anteriorDatoMaquina
                                + " " + getString(R.string.a) + " "
                                + mViaje.getMaquina()
                                + getString(R.string.lo_que_aumento_el_numero_de_viajeros_en) + " "
                                + incrementoMaquina
                                + getString(R.string.por_eso_ahora_hay) + " "
                                + mViaje.getViajerosActuales()
                                + " " + getString(R.string.viajeros_con_barra_s) + "\n";
                mAppSharedPrefEditor.putString(Contract.CUAL_FUE_ULTIMO_CAMBIO_SHARED_PREF, mCualFueUltimoCambio);
                mAppSharedPrefEditor.apply();
                // metodo: agregaItemListaCambiosSubenMaquina
                // agregamos itemcambio a listaDeCambios
                // metodo agregaItemListaCambios:
                addItemListCambiosSubenMaquina(incrementoMaquina);
                // guardamos arraylist listaCambios: metodo guardaListaCambiosEnSharedPref
                guardaListaCambiosYSizeActualEnSharedPref();

                // Si es primer movimiento de app se guarda fecha de actual viaje, las próximas fechas se cambian al iniciar viaje...
                Modelo.setFechaPrimerMovimientoListaViaje(mAppSharedPref, mAppSharedPrefEditor);

                // actualiza View puedenSubir:
                cargaViajeYDatosDesdeSharedPref(mAppSharedPref);
                actualizaViewsDatosViaje(mTextViewViajerosActuales, mBotonAforo,
                        mTextViewTotalSubenMaquina, mTextViewPuedenSubir);
            } else {
                Toast.makeText(this, R.string.no_hubo_cambios, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void nuevoViajeMaquina(String resultadoTecladoParam) {
        if (Integer.parseInt(resultadoTecladoParam) > 0) {
            // aqui va la comprobacion de tope de aforo:
            // comprueba que el dato obtenido en resultadoTeclado (stringActualizacionMaquina) es correcto
            int posibleTotalViajeros = mViaje.getViajerosActuales() + Integer.parseInt(resultadoTecladoParam);
            if (posibleTotalViajeros > mViaje.getAforo()) {
                int puedenSubir = mViaje.getAforo() - mViaje.getViajerosActuales();
                int excedesElAforoEn = posibleTotalViajeros - mViaje.getAforo();
                // No es posible, asi que lanza un alertDialog avisando...
                AlertDialog.Builder builderAforoExcedido = new AlertDialog.Builder(this);
                builderAforoExcedido.setMessage(getString(
                        R.string.aforo_excedido_dialogo_teclado_suben_maquina, puedenSubir, excedesElAforoEn));
                LayoutInflater imagenWarning = LayoutInflater.from(MainActivity.this);
                final View vistaWarning = imagenWarning.inflate(R.layout.icono_warning, null);
                builderAforoExcedido.setView(vistaWarning);
                builderAforoExcedido.setPositiveButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // no hace nada solo vuelve al mainactivity...
                        Toast.makeText(MainActivity.this, R.string.no_hubo_cambios, Toast.LENGTH_LONG).show();
                    }
                });
                builderAforoExcedido.show();
                // hasta aqui es la comprobacion
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage(
                        getString(R.string.alert_dialog1_deseas_sumar_viajeros)
                                + "\n"
                                + getString(R.string.alert_dialog2_los_viajeros_actuales_son)
                                + " " + mViaje.getViajerosActuales() + ","
                                + "\n"
                                + getString(R.string.alert_dialog3_y_si_suma_seran)
                                + " " + (Integer.parseInt(resultadoTecladoParam) + mViaje.getViajerosActuales()) + "."
                );
                alertDialog.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // sumamos a los actuales totalsuben el resultado del teclado, ya que luego pondrá a 0 el total bajan...despues de restarlos
                        int viajerosSuben = mViaje.getTotalSuben();
                        mViaje.setTotalSuben(viajerosSuben + Integer.parseInt(resultadoTecladoParam));
                        ejecutaCambioNuevoViajeMaquina(resultadoTecladoParam);
                        Toast.makeText(MainActivity.this, R.string.se_han_sumado_los_viajeros_de_la_maquina, Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // no suma el resultado teclado a antiguos viajeros, lo ejecuta normalmente:
                        ejecutaCambioNuevoViajeMaquina(resultadoTecladoParam);
                    }
                });
                alertDialog.show();
            }
        } else {
            ejecutaCambioNuevoViajeMaquina(resultadoTecladoParam);
        }
    }

    private void ejecutaCambioNuevoViajeMaquina(String resultadoTecladoParam) {
        mViaje.setMaquina(Integer.parseInt(resultadoTecladoParam));
        mAppSharedPrefEditor.putInt(Contract.MAQUINA_SHARED_PREF, Integer.parseInt(resultadoTecladoParam));
        // totalSuben sera igual al viajerosActuales, y el totalBajan será 0.
        int numViajeros = mViaje.getViajerosActuales();
        mViaje.setTotalSuben(numViajeros);
        mViaje.setTotalBajan(0);
        // guardamos cambios en sharedPref:
        mAppSharedPrefEditor.putInt(Contract.TOTAL_SUBEN_SHARED_PREF, mViaje.getTotalSuben());
        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
        mAppSharedPrefEditor.apply();
        desactivaDeshacer();
        // AQUI ES DONDE SE DEBE COPIAR LA LISTA EN LA DE ANTERIOR VIAJE
        mListaCambiosAnterior = new ArrayList<>(mListaCambiosActual);
        guardaListaCambiosYSizeAnteriorEnSharedPref();
        // actualizar views
        reseteoListaCambiosActual();

        // Aqui ya estan cambiadas las listas, cambiar las fechas de listas:
        Modelo.setFechaHoraListasViajes(mAppSharedPref, mAppSharedPrefEditor, getResources());

        cambiaFechasListasCambiosViaje();

        // ya limpia la listacambios se empieza con resultadoTeclado si es mayor a 0:
        if (!resultadoTecladoParam.equals("0")) {
            addItemListCambiosSubenMaquina(Integer.parseInt(resultadoTecladoParam));
            guardaListaCambiosYSizeActualEnSharedPref();
        }
        // aqui se actualiza variables de totalbajan para el textview de bajan:
        // si aun hay viajeros es que van sin billete de modo que continua contando el totalbajan
        // con los del anterior viaje hasta que no queden viajeros sin billete:
        if (mViaje.getViajerosActuales() > 0) {
            cambVarBajanNewViajeTramoVSinBillete();
        } else {
            cambVarBajanNewViajeTramoVConBillete();
        }
        // Como al comienzo de un viaje por defecto NO ES TRAMO se cambia a false el boolean:
        esPeticionTramoParaAvisoViajerosSinBillete = false; // que se deja en
        // false hasta proxima peticion
        mAppSharedPrefEditor.putBoolean(
                Contract.ES_PETICION_TRAMO_PARA_AVISO_VIAJEROS_SIN_BILLETE_SHARED_PREF, false);
        mAppSharedPrefEditor.apply();
        actualizaViewsDatosViaje(mTextViewViajerosActuales, mBotonAforo, mTextViewTotalSubenMaquina,
                mTextViewPuedenSubir);
        Toast.makeText(this, R.string.comienza_nuevo_viaje, Toast.LENGTH_SHORT).show();

    }

    private void cambiaFechasListasCambiosViaje() {

        // todo necesito: si el viaje

    }

    private void cambVarBajanNewViajeTramoVSinBillete() {
        totalBajanAntViaje = totalBajanActViaje; // se conserva el total viajeros
        // del anterior viaje
        totalBajanActViaje = 0; // aun no se han bajado del actual viaje nadie
        // ya que hay viajeros sin billete todavia
        numTotalBajanParaTV = totalBajanAntViaje; // se establece valor del
        // totalbajan para que cuando se actualize el textview de total bajan en
        // metodo actualizaViewsDatosViaje sea correcto
        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ANTERIOR_VIAJE_SHARED_PREF, totalBajanAntViaje); // ojo, debe de ser de la variable totalBajanAntViaje, ya que es la que acumulará las bajadas...
        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, 0);
        mAppSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, totalBajanAntViaje);
        mAppSharedPrefEditor.apply();
    }

    private void cambVarBajanNewViajeTramoVConBillete() {
        // no hay viajeros sin billete:
        totalBajanActViaje = 0; // no se han bajado del actual viaje nadie ni hay sin billete
        numTotalBajanParaTV = totalBajanActViaje; // siempre se cambia el total

        mAppSharedPrefEditor.putInt(Contract.TOTAL_BAJAN_ACTUAL_VIAJE_SHARED_PREF, 0);
        mAppSharedPrefEditor.putInt(Contract.NUM_TOTAL_BAJAN_PARA_TV_SHARED_PREF, totalBajanActViaje);
        mAppSharedPrefEditor.apply();
    }

    private void addItemListCambiosSubenMaquina(int incrementoMaquina) {
        // metodo con retorno private String getHoraActualString
        String horaActual = Modelo.getHoraActualHHmmssString();
        // hayamos viajeros despues del incremento:
        int suben = mAppSharedPref.getInt(Contract.TOTAL_SUBEN_SHARED_PREF, 0);
        int bajan = mAppSharedPref.getInt(Contract.TOTAL_BAJAN_SHARED_PREF, 0);
        int viajeros = suben - bajan;
        int ocupacion = (viajeros * 100) / mViaje.getAforo();
        // datos para campo viajeros, en caso de viajeros sin billete:
        int subenMaquinaPreIncremento = Modelo.getBilletesMaquinaViajeActual(mListaCambiosActual);
        int subenSinMaquina = Modelo.getBilletesSinMaquinaViajeActual(mListaCambiosActual);
        // aqui tengo el total suben en actual viaje incluyendo el incrementomaquina que aun no se añadio a la lista (.add)
        int totalSubenActualViajePostIncremento =
                subenMaquinaPreIncremento +
                        subenSinMaquina +
                        incrementoMaquina;
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
            ocupacion = (totalSubenActualViajePostIncremento * 100) / mViaje.getAforo();
        } else {
            argViajeros = String.valueOf(viajeros);
        }
        // agregamos el item a la listaCambios en caso de no haber VSB:
        mListaCambiosActual.add(0, new ItemListaCambios(
                        horaActual,
                        R.drawable.ic_mas_azul_24,
                        "" + incrementoMaquina,
                        argViajeros,
                        "" + ocupacion + "%",
                        R.drawable.ic_billete_maquina_lista_cambios_24
                )
        );
        // para guardar en sharedPref es necesario guardar el tamaño y cada uno de los item...
        // guardamos el tamaño del array en sharedpref:
        mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, mListaCambiosActual.size());
        // para deshacer, las listacambios, es necesario saber qué lista/s tuvo/ieron el último cambio:
        // este dato de sharedpref tambien se guarda cuando suben manual y bajan. En Reset viajeros
        // no es necesario porque cuando se hace reset se inhabilita deshacer...
        mAppSharedPrefEditor.putInt(Contract.STRING_KEY_ULTIMA_LISTACAMBIOS_CAMBIADA_SHARED_PREF,
                Contract.ULTIMA_LISTACAMBIOS_CAMBIADA_ES_ACTUAL);
        // es la actual porque la lista anterior solo se cambia en caso de bajar sin billete... y aqui se sube
        mAppSharedPrefEditor.apply();
    }

    private void guardaListaCambiosYSizeActualEnSharedPref() {
        for (int i = 0; i < mListaCambiosActual.size(); i++) {
            mHoraListaActualSharedPrefEditor.putString(String.valueOf(i),
                    mListaCambiosActual.get(i).getHora());
            mHoraListaActualSharedPrefEditor.apply();
            mIconoSubeBajaListaActualSharedPrefEditor.putInt(String.valueOf(i),
                    mListaCambiosActual.get(i).getIconoSubeBaja());
            mIconoSubeBajaListaActualSharedPrefEditor.apply();
            mMovimientoListaActualSharedPrefEditor.putString(String.valueOf(i),
                    mListaCambiosActual.get(i).getMovimiento());
            mMovimientoListaActualSharedPrefEditor.apply();
            mViajerosListaActualSharedPrefEditor.putString(String.valueOf(i),
                    mListaCambiosActual.get(i).getViajeros());
            mViajerosListaActualSharedPrefEditor.apply();
            mOcupacionListaActualSharedPrefEditor.putString(String.valueOf(i),
                    mListaCambiosActual.get(i).getOcupacion());
            mOcupacionListaActualSharedPrefEditor.apply();
            mIconoBilletesListaActualSharedPrefEditor.putInt(String.valueOf(i),
                    mListaCambiosActual.get(i).getIconoBillete());
            mIconoBilletesListaActualSharedPrefEditor.apply();
            mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ACTUAL_SHARED_PREF, mListaCambiosActual.size());
            mAppSharedPrefEditor.apply();
        }
    }

    private void guardaListaCambiosYSizeAnteriorEnSharedPref() {
        if (mListaCambiosAnterior.size() == 0) {
            // se clear el mlist... y se remove todos los shared, para que no quede la anterior
            // lista guardada en caso de array con tamaño 0 cuando no tiene mivimientos:
            reseteoListaCambiosAnterior();
        } else {
            for (int i = 0; i < mListaCambiosAnterior.size(); i++) {
                mHoraListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                        mListaCambiosAnterior.get(i).getHora());
                mHoraListaAnteriorSharedPrefEditor.apply();
                mIconoSubeBajaListaAnteriorSharedPrefEditor.putInt(String.valueOf(i),
                        mListaCambiosAnterior.get(i).getIconoSubeBaja());
                mIconoSubeBajaListaAnteriorSharedPrefEditor.apply();
                mMovimientoListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                        mListaCambiosAnterior.get(i).getMovimiento());
                mMovimientoListaAnteriorSharedPrefEditor.apply();
                mViajerosListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                        mListaCambiosAnterior.get(i).getViajeros());
                mViajerosListaAnteriorSharedPrefEditor.apply();
                mOcupacionListaAnteriorSharedPrefEditor.putString(String.valueOf(i),
                        mListaCambiosAnterior.get(i).getOcupacion());
                mOcupacionListaAnteriorSharedPrefEditor.apply();
                mIconoBilletesListaAnteriorSharedPrefEditor.putInt(String.valueOf(i),
                        mListaCambiosAnterior.get(i).getIconoBillete());
                mIconoBilletesListaAnteriorSharedPrefEditor.apply();
                mAppSharedPrefEditor.putInt(Contract.SIZE_LISTA_CAMBIOS_ANTERIOR_SHARED_PREF, mListaCambiosAnterior.size());
                mAppSharedPrefEditor.apply();
            }
        }
    }

    private void desactivaDeshacer() {
        // iconoDeshacer cambia a inactivo... ultimoCambioFueDeshacer -> true, guarda en sharedPref
        mIconoDeshacer.setImageResource(R.drawable.ic_deshacer_inactivo);
        mUltimoCambioFueDeshacerOInicio = true;
        mAppSharedPrefEditor.putBoolean(Contract.ULTIMO_CAMBIO_FUE_DESHACER_O_INICIO_SHARED_PREF, true);
        mAppSharedPrefEditor.apply();
    }

    private int hayarIncrementoMaquina(int anteriorDatoMaquina, int nuevoDatoMaquina) {
        int incremento = nuevoDatoMaquina - anteriorDatoMaquina;
        return incremento;
    }

    // LOS SIGUIENTES METODOS SON DE LA INTERFAZ DEL MENUFRAGMENT, de momento solo 1 ... sobrecargar
    // en interface de fragmentMenuDialog si hay alguno con switch u otra variable a guardar..
    @Override
    public void manejoItemsMenuDialog(int itemSeleccionado) {

    }
}