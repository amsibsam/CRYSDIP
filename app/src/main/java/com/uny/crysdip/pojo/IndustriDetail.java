package com.uny.crysdip.pojo;

/**
 * Created by rahardyan on 24/04/16.
 */
public class IndustriDetail {
    private final String namaIndustri;
    private final String deskripsi;
    private final String alamat;
    private final double lat;
    private final double lng;
    private final int jumlahKaryawan;
    private final String fotoUrl;
    private final boolean isFavorite;

    public IndustriDetail(String namaIndustri, String deskripsi, String alamat, double lat, double lng, int jumlahKaryawan, String fotoUrl, boolean isFavorite) {
        this.namaIndustri = namaIndustri;
        this.deskripsi = deskripsi;
        this.alamat = alamat;
        this.lat = lat;
        this.lng = lng;
        this.jumlahKaryawan = jumlahKaryawan;
        this.fotoUrl = fotoUrl;
        this.isFavorite = isFavorite;
    }

    public String getNamaIndustri() {
        return namaIndustri;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getAlamat() {
        return alamat;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getJumlahKaryawan() {
        return jumlahKaryawan;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
