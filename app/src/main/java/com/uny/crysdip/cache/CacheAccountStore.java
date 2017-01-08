package com.uny.crysdip.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.uny.crysdip.pojo.Mahasiswa;

/**
 * Created by rahardyan on 26/04/16.
 */
public class CacheAccountStore {
    private static final String TAG = CacheAccountStore.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    public CacheAccountStore(Context context) {
        this.sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public boolean hasAccount(){
        return sharedPreferences.contains("id");
    }

    public void cacheAccount(Mahasiswa mahasiswa){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", mahasiswa.getId());
        editor.putString("nama", mahasiswa.getNamaMahasiswa());
        editor.putString("prodi", mahasiswa.getNamaProdi());
        editor.putString("nim", mahasiswa.getNim());
        editor.putString("alamat", mahasiswa.getAlamat());
        editor.commit();
    }

    public Mahasiswa getCachedAccount(){
        int id = sharedPreferences.getInt("id", 1);
        String nama = sharedPreferences.getString("nama", "");
        String prodi = sharedPreferences.getString("prodi", "");
        String nim = sharedPreferences.getString("nim", "");
        String alamat = sharedPreferences.getString("alamat", "");

        return new Mahasiswa(id, nama, alamat, prodi, nim);
    }

    public void firstLogin(boolean firstLogin) {
        sharedPreferences.edit().putBoolean("first_login", firstLogin).commit();
    }

    public boolean isFirstLogin() {
        return sharedPreferences.getBoolean("first_login", true);
    }
}
