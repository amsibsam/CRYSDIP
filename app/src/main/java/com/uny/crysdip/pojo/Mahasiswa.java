package com.uny.crysdip.pojo;

/**
 * Created by root on 03/04/16.
 */
public class Mahasiswa {
    private final int id;
    private final String namaMahasiswa;
    private final String alamat;
    private final String namaProdi;
    private final String nim;


    public Mahasiswa(int id, String namaMahasiswa, String alamat, String namaProdi, String nim) {
        this.id = id;
        this.namaMahasiswa = namaMahasiswa;
        this.alamat = alamat;
        this.namaProdi = namaProdi;
        this.nim = nim;
    }

    public int getId() {
        return id;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNim() {
        return nim;
    }

    public String getNamaProdi() {
        return namaProdi;
    }
}
