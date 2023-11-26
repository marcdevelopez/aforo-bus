package com.mundobinario.miaforobus;

public class ItemListaCambios {
    private String miHora;
    private int miIconoSubeBaja;
    private String miMovimiento; // numero de viajeros que suben o bajan
    private String miViajeros; // total de viajeros despues del movimiento
    private String miOcupacion;
    private int miIconoBillete;

    public ItemListaCambios(String hora, int iconoSubeBaja, String movimiento, String viajeros,
                            String ocupacion, int iconoBillete) {
        miHora = hora;
        miIconoSubeBaja = iconoSubeBaja;
        miMovimiento = movimiento;
        miViajeros = viajeros;
        miOcupacion = ocupacion;
        miIconoBillete = iconoBillete;
    }

    // getters:
    public String getHora() {
        return miHora;
    }

    public int getIconoSubeBaja() {
        return miIconoSubeBaja;
    }

    public String getMovimiento() {
        return miMovimiento;
    }

    public String getViajeros() {
        return miViajeros;
    }

    public String getOcupacion() {
        return miOcupacion;
    }

    public int getIconoBillete() {
        return miIconoBillete;
    }

    // setters:
    public void setHora(String hora) {
        miHora = hora;
    }

    public void setIconoSubeBaja(int iconoSubeBaja) {
        miIconoSubeBaja = iconoSubeBaja;
    }

    public void setMovimiento(String movimiento) {
        miMovimiento = movimiento;
    }

    public void setViajeros(String viajeros) {
        miViajeros = viajeros;
    }

    public void setOcupacion(String ocupacion) {
        miOcupacion = ocupacion;
    }

    public void setIconoBillete(int iconoBillete) {
        miIconoBillete = iconoBillete;
    }

}
