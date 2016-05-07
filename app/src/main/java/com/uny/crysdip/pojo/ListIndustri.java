package com.uny.crysdip.pojo;

import android.net.Uri;

/**
 * Created by rahardyan on 03/04/16.
 */
public class ListIndustri {
    private final int id;
    private final String namaIndustri;
    private final String alamat;
    private final Uri fotoUrl;

    public ListIndustri(int id, String nama, String alamat, Uri foto_url) {
        this.id = id;
        this.namaIndustri = nama;
        this.alamat = alamat;
        this.fotoUrl = foto_url;
    }

    public String getNamaIndustri() {
        return namaIndustri;
    }

    public Uri getFotoUrl() {
        return fotoUrl;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getId() {
        return id;
    }
}
