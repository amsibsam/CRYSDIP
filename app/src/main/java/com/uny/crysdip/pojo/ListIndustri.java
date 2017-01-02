package com.uny.crysdip.pojo;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rahardyan on 03/04/16.
 */
public class ListIndustri extends RealmObject{
    @PrimaryKey
    private  int id;
    private String namaIndustri;
    private String alamat;
    private String fotoUrl;
    private int count;
    private String tag;

    public ListIndustri() {
    }

    public ListIndustri(int id, String nama, String alamat, String foto_url, int count, String spesifikasi) {
        this.id = id;
        this.namaIndustri = nama;
        this.alamat = alamat;
        this.fotoUrl = foto_url;
        this.count = count;
        this.tag = spesifikasi;
    }

    public String getNamaIndustri() {
        return namaIndustri;
    }

    public String getFotoUrl() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setNamaIndustri(String namaIndustri) {
        this.namaIndustri = namaIndustri;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
