package com.uny.crysdip.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.uny.crysdip.R;
import com.uny.crysdip.databinding.FragmentRecomendationBinding;
import com.uny.crysdip.ui.activity.RecomendationListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecomendationFragment extends android.support.v4.app.Fragment {
    private FragmentRecomendationBinding binding;
    private String namaKategori1 = "-";
    private String namaKategori2 = "-";
    private String namaKategori3 = "-";
    private String namaKategori4 = "-";
    private String namaKategori5 = "-";

    private String spesifikasi1 = "-";
    private String spesifikasi2 = "-";
    private String spesifikasi3 = "-";
    private String spesifikasi4 = "-";
    private String spesifikasi5 = "-";
    private String spesifikasi6 = "-";
    private String spesifikasi7 = "-";
    private String spesifikasi8 = "-";
    private String spesifikasi9 = "-";
    private String spesifikasi10 = "-";
    private String spesifikasi11 = "-";
    private String spesifikasi12 = "-";

    public RecomendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecomendationBinding.inflate(inflater, container, false);

        binding.cbNetworking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    namaKategori1 = "Networking";
                } else {
                    namaKategori1 = "-";
                }
            }
        });


        binding.cbMobileProgramming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    namaKategori2 = "Mobile Programming";
                    Log.d("amsibsam", "kategori2 klik "+namaKategori2);
                } else {
                    namaKategori2 = "-";
                }
            }
        });

        binding.cbDesktopProgramming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    namaKategori3 = "Desktop Programming";
                } else {
                    namaKategori3 = "-";
                }
            }
        });

        binding.cbWebProgramming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    namaKategori4 = "Web Programming";
                } else {
                    namaKategori4 = "-";
                }
            }
        });

        binding.cbMultimedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    namaKategori5 = "Multimedia";
                } else {
                    namaKategori5 = "-";
                }
            }
        });

        binding.cbInstalasiJaringan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi1 = "Instalasi Jaringan";
                } else {
                    spesifikasi1 = "-";
                }
            }
        });

        binding.cbAdministrasiJaringan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi2 = "administrasi jaringan";
                } else {
                    spesifikasi2 = "-";
                }
            }
        });

        binding.cbAndroid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi3 = "android";
                } else {
                    spesifikasi3 = "-";
                }
            }
        });

        binding.cbWindowsPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi4 = "windows phone";
                } else {
                    spesifikasi4 = "-";
                }
            }
        });

        binding.cbBackend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi5 = "backend";
                } else {
                    spesifikasi5 = "-";
                }
            }
        });

        binding.cbAdministrasiServer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi6 = "administrasi server";
                } else {
                    spesifikasi6 = "-";
                }
            }
        });

        binding.cbAdministrasiJaringan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi7 = "administrasi jaringan";
                } else {
                    spesifikasi7 = "-";
                }
            }
        });

        binding.cbDesainer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi8 = "desainer";
                } else {
                    spesifikasi8 = "-";
                }
            }
        });

        binding.cbFrontend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    spesifikasi9 = "frontend";
                } else {
                    spesifikasi9 = "-";
                }
            }
        });


        binding.btnRekomendasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RecomendationListActivity.class)
                        .putExtra("kategori1", namaKategori1)
                        .putExtra("kategori2", namaKategori2)
                        .putExtra("kategori3", namaKategori3)
                        .putExtra("kategori4", namaKategori4)
                        .putExtra("kategori5", namaKategori5)
                        .putExtra("spesifikasi1", spesifikasi1)
                        .putExtra("spesifikasi2", spesifikasi2)
                        .putExtra("spesifikasi3", spesifikasi3)
                        .putExtra("spesifikasi4", spesifikasi4)
                        .putExtra("spesifikasi5", spesifikasi5)
                        .putExtra("spesifikasi6", spesifikasi6)
                        .putExtra("spesifikasi7", spesifikasi7)
                        .putExtra("spesifikasi8", spesifikasi8)
                        .putExtra("spesifikasi9", spesifikasi9));
            }
        });


        return binding.getRoot();
    }


}
