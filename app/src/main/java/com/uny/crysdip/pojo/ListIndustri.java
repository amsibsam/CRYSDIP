package com.uny.crysdip.pojo;

import android.net.Uri;

/**
 * Created by rahardyan on 03/04/16.
 */
public class ListIndustri implements Comparable{
    private final int id;
    private final String namaIndustri;
    private final String alamat;
    private final Uri fotoUrl;
    private final int count;

    public ListIndustri(int id, String nama, String alamat, Uri foto_url, int count) {
        this.id = id;
        this.namaIndustri = nama;
        this.alamat = alamat;
        this.fotoUrl = foto_url;
        this.count = count;
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

    public int getCount() {
        return count;
    }


    @Override
    public int compareTo(Object another) {
        if(((ListIndustri)another).getCount() > count){
            return 1;
        }if(((ListIndustri)another).getCount() == count){
            return 0;
        }else{
            return -1;
        }
    }
}
