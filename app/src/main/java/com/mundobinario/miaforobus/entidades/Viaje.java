package com.mundobinario.miaforobus.entidades;

import android.util.Log;

public class Viaje implements Cloneable {

    // se redifine el metodo clone() de Object para poder clonar objetos:
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            Log.println(Log.ERROR, "Error en clone()", "" + ex);
        }
        return obj;
    }

    private int totalSuben = 0;
    private int totalBajan = 0;
    private int aforo = 93;
    private int Maquina = 0;
    private boolean manejoMaquina = true;
    private int viajerosActuales;
    private int puedenSubir;

    public Viaje() {
    }

    public int getTotalSuben() {
        return totalSuben;
    }

    public void setTotalSuben(int totalSuben) {
        this.totalSuben = totalSuben;
    }

    public int getMaquina() {
        return Maquina;
    }

    public void setMaquina(int maquina) {
        this.Maquina = maquina;
    }

    public int getTotalBajan() {
        return totalBajan;
    }

    public void setTotalBajan(int totalBajan) {
        this.totalBajan = totalBajan;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public int getViajerosActuales() {
        viajerosActuales = totalSuben - totalBajan;
        return viajerosActuales;
    }

    public int getPuedenSubir() {
        puedenSubir = aforo - viajerosActuales;
        return puedenSubir;
    }

    public boolean isManejoMaquina() {
        return manejoMaquina;
    }

    public void setManejoMaquina(boolean manejoMaquina) {
        this.manejoMaquina = manejoMaquina;
    }
}
