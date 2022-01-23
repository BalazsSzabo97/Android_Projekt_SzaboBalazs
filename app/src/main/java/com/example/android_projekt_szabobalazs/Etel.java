package com.example.android_projekt_szabobalazs;

import java.util.Random;

public class Etel {
    private int id;
    private String nev;
    private String kep;
    private String ar;
    private String suly;

    //Nem találtam olyan Online API-t ami egy étlapot töltene ki, ezért egy fotógallériából API-ból készítettem egyett.
    //Az id-t, nevet és a képet átveszi, viszont az ár és a súly az random generálódik.
    public Etel (int id, String nev, String kep)
    {
        Random r = new Random();
        this.id = id;
        this.nev = nev;
        this.kep = kep;
        this.ar = String.valueOf((r.nextInt((40 - 6) + 1) + 6)) + ".99 Ron";
        this.suly = String.valueOf((r.nextInt((30 - 4) + 1)*50 + 200)) + " g";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getKep() {
        return kep;
    }

    public void setKep(String kep) {
        this.kep = kep;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getSuly() {
        return suly;
    }

    public void setSuly(String suly) {
        this.suly = suly;
    }

    @Override
    public String toString() {
        return "Etel{" +
                "id=" + id +
                ", nev='" + nev + '\'' +
                ", kep='" + kep + '\'' +
                ", ar=" + ar +
                ", suly=" + suly +
                '}';
    }

}


