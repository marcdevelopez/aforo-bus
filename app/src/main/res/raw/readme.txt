Modificación class TecladoDialogo:

En el constructor se recibe la peticionTeclado, y dependiendo de dónde provenga la petición
se cambiará el texto del título del teclado, que será un texto fijo para más simplicidad.
El texto del título irá dentro del mismo CardView para poder aprovechar mejor el espacio,
pero tendrá otro color (blanco o azul claro), a lo ancho en 1 sola línea, sobre el resultado del
teclado.
Los int final está de momento en MainActivity y son:
    PETICION_TECLADO_SUBEN_MAQUINA = 1;
    PETICION_TECLADO_TRAMO = 2;
    PETICION_TECLADO_RESET_VIAJEROS = 3;

    ************************************************************************************************
    COMPROBAR QUE LA CANTIDAD DE NUEVOS VIAJEROS NO SUPERA EL AFORO:

    Lugares donde hacer la comprobación:
    ->suben máquina
    Es mejor comprobarlo en el mismo DialogFragment TecladoDialogo para poder escribir de nuevo la
    cantidad sin salir del teclado. Para ello en el AlertDialog se recordará el número máximo de
    viajeros que podrán subir.
        |->nuevo viaje con viajeros iniciales
    ->suben sin máquina
    Método:
    A viajeros actuales se le suma los nuevos viajeros -> posibleTotalViajeros
    Si posibleTotalViajeros > aforo entonces avisa y no se efectúa la actualización de viajeros
    Si no: sigue con normalidad...

    ************************************************************************************************
    EN TODOS LOS ALERTDIALOGS:
    todos con titulo con lo basico del mensaje hace mas facil entender dialogo
    parrafo empieza con 3 espacios de tabulacion
    separacion entre parrafos de 1 linea vacia

    ************************************************************************************************
    MENUS CONTEXTUALES CON DIALOGFRAGMENTS:
    la transparencia se consigue:
    despues de: alertDialog = builder.create(); agregar:
    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))
    ojo: es necesario utilizar androidx.appcompat.app.AlertDialog, ya que con el app.AlertDialog no
    funciona...

    ************************************************************************************************

    EN ONLONGCLICK PARA DISTINTAS VIEWS SIMULA EL MENU CONTEXTVIEW:
    en todos la animacion será del alpha para que se note que es una accion distinta sobre la view
    el menú contextual creado aqui será de fonfo claro ya que no se ejecutará sino en casos excep-
    cionales

    ************************************************************************************************

    RESPALDOS:
    Se realizan en método guardarRespaldoViajeEnSharedPref() de MainActivity o Modelo.
    Ahi se guardan los respaldo que se utilicen de una vez todos, sin importar que sean el último
    cambio (viaje, totalbajan...) al guardarlos siempre, será más facil al restaurar ya que se respalda
    siempre en cada cambio ya que guardarRespaldoViajeEnSharedPref se llama cuando hay subida o bajada.

    ************************************************************************************************

    SVG: vectorDrawables:
    para que se vean bien los xml de SVG graficos vectoriales hasta api 21 lollipop:
    en el gradle:
    // Gradle Plugin 2.0+
     android {
       defaultConfig {
         vectorDrawables.useSupportLibrary = true
        }
     }
     y en los ImageViews:
     app:srcCompat="@drawable/ic_add"
     Con esto se consigue que se vea bien antes de lollipop los vectores...
     ojo! si no se usa app:srcCompat en el xml da un error
     para cambiar dinamicamente en java el vector, usar:
     .imageView.setImageDrawable(VectorDrawableCompat.create(getResources(), drawableRes, null));
     fuente: https://stackoverflow.com/questions/37615470/support-library-vectordrawable-resourcesnotfoundexception

     ************************************************************************************************

     FRAGMENTS:
     importante: para que no se pueda hacer click cuando esté abierto el fragment: en xml del fragment:
        android:clickable="true" en el layout principal.

     ************************************************************************************************
     DETECCION ORIENTACIÓN DE PANTALLA:
     if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    esLandscape = true;

     ************************************************************************************************
     BARRA DE ESTADO Y DE NAVEGACIÓN:
     se oculta la de estado en el theme xml de aplicacion.
     se oculta la navegación programaticamente en cada Activity en:
     onResume y onWindowFocusChanged
     con el código:
      View decorView = getWindow().getDecorView();
             decorView.setSystemUiVisibility(
                     View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                             | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                             | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                             | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                             | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                             | View.SYSTEM_UI_FLAG_FULLSCREEN);

