package com.uny.crysdip.Pojo;

/**
 * Created by rahardyan on 24/04/16.
 */
public class IndustriDetail {
    private final String namaIndustri;
    private final String Deskripsi;
    private final String alamat;
    private final double lat;
    private final double lng;
    private final int jumlahKaryawan;
    private final String fotoUrl;

    public IndustriDetail(String namaIndustri, String deskripsi, String alamat, double lat, double lng, int jumlahKaryawan, String fotoUrl) {
        this.namaIndustri = namaIndustri;
        Deskripsi = deskripsi;
        this.alamat = alamat;
        this.lat = lat;
        this.lng = lng;
        this.jumlahKaryawan = jumlahKaryawan;
        this.fotoUrl = fotoUrl;
    }

    public String getNamaIndustri() {
        return namaIndustri;
    }

    public String getDeskripsi() {
        return Deskripsi;
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
}
