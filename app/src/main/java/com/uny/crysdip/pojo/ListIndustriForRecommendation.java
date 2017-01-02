package com.uny.crysdip.pojo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rahardyan on 03/04/16.
 */
public class ListIndustriForRecommendation extends RealmObject{
    @PrimaryKey
    private int id;
    private String namaIndustri;
    private String alamat;
    private String fotoUrl;
    private int count;
    private RealmList<Spesifikasi> spesifikasis;
    private int value;


    public ListIndustriForRecommendation() {
    }

    public ListIndustriForRecommendation(int id, String nama, String alamat, String foto_url,
                                         int count, RealmList<Spesifikasi> spesifikasis) {
        this.id = id;
        this.namaIndustri = nama;
        this.alamat = alamat;
        this.fotoUrl = foto_url;
        this.count = count;
        this.spesifikasis = spesifikasis;
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

    public RealmList<Spesifikasi> getSpesifikasis() {
        return spesifikasis;
    }

    public void setSpesifikasis(RealmList<Spesifikasi> spesifikasis) {
        this.spesifikasis = spesifikasis;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
