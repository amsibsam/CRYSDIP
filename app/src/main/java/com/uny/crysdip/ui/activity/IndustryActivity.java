package com.uny.crysdip.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.Pojo.IndustriDetail;
import com.uny.crysdip.R;
import com.uny.crysdip.databinding.ActivityIndustryBinding;
import com.uny.crysdip.network.CrysdipService;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IndustryActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String INDUSTRI_ID = "industri_id";
    ActivityIndustryBinding binding;
    private GoogleMap map;
    private double latitude;
    private double longitude;
    private String markerTitle;

    @Inject
    CrysdipService crysdipService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_industry);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        crysdipService.getIndustriDetail(getIntent().getIntExtra(INDUSTRI_ID, 1))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<IndustriDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IndustriDetail industriDetail) {

                        markerTitle = industriDetail.getNamaIndustri().toString();
                        binding.industriName.setText(industriDetail.getNamaIndustri().toString());
                        binding.tvAlamatIndustri.setText(industriDetail.getAlamat().toString());



                        // Add a marker in Sydney and move the camera
                        LatLng tempat = new LatLng(industriDetail.getLat(), industriDetail.getLng());
                        map.addMarker(new MarkerOptions().position(tempat).title(industriDetail.getNamaIndustri()));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(tempat, 12f));

                    }
                });


    }
}
