package com.uny.crysdip.Pojo;

/**
 * Created by root on 03/04/16.
 */
public class ListIndustri {
    private final String namaIndustri;
    private final String alamat;

    public ListIndustri(String nama, String alamat) {
        this.namaIndustri = nama;
        this.alamat = alamat;

    }

    public String getNamaIndustri() {
        return namaIndustri;
    }


    public String getAlamat() {
        return alamat;
    }

}
