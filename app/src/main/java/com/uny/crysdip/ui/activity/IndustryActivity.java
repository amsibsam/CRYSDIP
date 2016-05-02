package com.uny.crysdip.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.cache.CacheAccountStore;
import com.uny.crysdip.pojo.IndustriDetail;
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
    private int industriId;
    private int mahasiswaId;
    private AlertDialog alert;

    @Inject
    CrysdipService crysdipService;

    @Inject
    CacheAccountStore cacheAccountStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_industry);

        isGoogleMapInstalled("com.google.android.apps.maps", this);

        industriId = getIntent().getIntExtra(INDUSTRI_ID, 1);
        mahasiswaId = cacheAccountStore.getCachedAccount().getId();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        binding.checkboxFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    crysdipService.setFavorite(industriId, mahasiswaId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<String>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(String s) {

                                }
                            });
                } else {
                    crysdipService.setUnfavorite(industriId, mahasiswaId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<String>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(String s) {

                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(true);
        final boolean[] toolbarButton = {false};

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Harap tunggu..");
        progressDialog.show();
        crysdipService.getIndustriDetail(industriId, mahasiswaId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<IndustriDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "error get detail "+e.toString());
                        Toast.makeText(IndustryActivity.this, "tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(IndustriDetail industriDetail) {
                        progressDialog.dismiss();
                        boolean isFavorite = industriDetail.isFavorite();
                        markerTitle = industriDetail.getNamaIndustri().toString();
                        binding.industriName.setText(industriDetail.getNamaIndustri().toString());
                        binding.tvAlamatIndustri.setText(industriDetail.getAlamat().toString());

                        if (isFavorite){
                            binding.checkboxFavorite.setChecked(true);
                        } else {
                            binding.checkboxFavorite.setChecked(false);
                        }
                        // Add a marker in Sydney and move the camera
                        final LatLng tempat = new LatLng(industriDetail.getLat(), industriDetail.getLng());
                        Log.d("amsibsam", "tempat "+industriDetail.getLat()+", "+industriDetail.getLng());
                        map.addMarker(new MarkerOptions().position(tempat).title(industriDetail.getNamaIndustri()));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(tempat, 12f));

                        Log.d("amsibsam", "onNext");

                    }
                });

//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Toast.makeText(IndustryActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
//                binding.checkboxFavorite.setTranslationY(-60);
//                marker.showInfoWindow();
//                toolbarButton[0] = true;
//                return true;
//            }
//        });




    }

    private void isGoogleMapInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            openGoogleMapOnMarket(this);
        }
    }

    private void openGoogleMapOnMarket(final Activity activity){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(
                "Google map tidak terinstall, silahkan unduh terlebih dahulu")
                .setTitle("Google Map tidak terinstall")
                .setCancelable(false)
                .setPositiveButton("Install",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String packageName = "com.google.android.apps.maps";

                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                                } catch (ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                                }
                                alert.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
        alert = builder.create();
        alert.show();
    }
}
