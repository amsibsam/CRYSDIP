package com.uny.crysdip.Pojo;

/**
 * Created by root on 03/04/16.
 */
public class ListIndustri {
    private final int id;
    private final String namaIndustri;
    private final String alamat;

    public ListIndustri(int id, String nama, String alamat) {
        this.id = id;
        this.namaIndustri = nama;
        this.alamat = alamat;

    }

    public String getNamaIndustri() {
        return namaIndustri;
    }


    public String getAlamat() {
        return alamat;
    }

    public int getId() {
        return id;
    }
}
