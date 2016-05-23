package com.uny.crysdip.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uny.crysdip.BR;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.cache.CacheAccountStore;
import com.uny.crysdip.pojo.IndustriDetail;
import com.uny.crysdip.R;
import com.uny.crysdip.databinding.ActivityIndustryBinding;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.pojo.Testimoni;
import com.uny.crysdip.viewmodel.TestimoniViewModel;

import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IndustryActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String INDUSTRI_ID = "industri_id";
    private TestimoniListViewModel testimoniListViewModel = new TestimoniListViewModel();
    ActivityIndustryBinding binding;
    private String markerTitle;
    private int industriId;
    private int mahasiswaId;
    private AlertDialog alert;

    @Inject
    CrysdipService crysdipService;

    @Inject
    CacheAccountStore cacheAccountStore;

    //////////////////////LIFE CYCLE SECTION///////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_industry);
        industriId = getIntent().getIntExtra(INDUSTRI_ID, 1);
        mahasiswaId = cacheAccountStore.getCachedAccount().getId();

        //cek isGoogleMap installed or not
        //if not installed go to playstore to download google map.
        isGoogleMapInstalled("com.google.android.apps.maps", this);

        //setup google map if installed.
        setUpMap();

        //change title color when toolbar collapsing.
        changeTitleColorOnToolbarCollapsed();

        binding.btnSendTestimoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send testimoni to server
                sendTestimoni(getIntent().getIntExtra(INDUSTRI_ID, 1), cacheAccountStore.getCachedAccount().getId(), binding.etTestimoni.getText().toString());
            }
        });

        binding.checkboxFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if isChecked = true, then favorite this industri, if isChecked false then unfavorite this industri
                setFavoriteOrUnFavorite(isChecked);
            }
        });

        binding.btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndustryActivity.this, TestimoniActivity.class).putExtra(INDUSTRI_ID, getIntent().getIntExtra(INDUSTRI_ID, 1)));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.setTestimoniListViewModel(testimoniListViewModel);
        getTestimoni();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getIndustriDetailAndSetToView(googleMap);
    }

    ///////////////INNER CLASS SECTION//////////////////
    public static class TestimoniListViewModel{
        public final ObservableList<TestimoniViewModel> items = new ObservableArrayList<>();
        public final ItemView itemView = ItemView.of(BR.itemListViewModel, R.layout.item_testimoni_short);
    }

    /////////////////METHOD SECTION//////////////////////
    private void getTestimoni(){
        crysdipService.getTestimoni(getIntent().getIntExtra(INDUSTRI_ID, -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(new Subscriber<List<Testimoni>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "error get testimoni "+e.toString());

                    }

                    @Override
                    public void onNext(List<Testimoni> testimonis) {
                        binding.pbLoading.setVisibility(View.GONE);
                        binding.listView.setVisibility(View.VISIBLE);
                        Log.d("amsibsam", "testimonis size "+testimonis.size());

                        if (testimonis.size() == 0){
                            binding.listView.setVisibility(View.GONE);
                            binding.tvEmptyTestimoni.setVisibility(View.VISIBLE);
                            binding.btnSeeMore.setVisibility(View.GONE);
                        } else {
                            binding.listView.setVisibility(View.VISIBLE);
                            binding.tvEmptyTestimoni.setVisibility(View.GONE);
                            binding.btnSeeMore.setVisibility(View.VISIBLE);
                        }

                        testimoniListViewModel.items.clear();
                        for (final Testimoni testimoni : testimonis){
                            Log.d("amsibsam", "nama testimoni "+testimoni.getName());
                            testimoniListViewModel.items.add(new TestimoniViewModel(testimoni, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }));
                        }
                    }
                });
    }

    private void sendTestimoni(int industriId, int mahasiswaId, String testimoni){
        crysdipService.postTestimoni(testimoni, mahasiswaId, industriId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(IndustryActivity.this, "gagal mengirim", Toast.LENGTH_SHORT).show();
                        Log.e("amsibsam", "error send testimoni "+e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        getTestimoni();
                        binding.etTestimoni.setText("");
                    }
                });
    }

    private void setUpMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    private void changeTitleColorOnToolbarCollapsed() {
        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("amsibsam", "offset "+verticalOffset);
                if (verticalOffset > -200 ){
                    binding.tvIndustriName.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    binding.tvIndustriName.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
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

    private void getIndustriDetailAndSetToView(final GoogleMap map){
        map.getUiSettings().setMapToolbarEnabled(true);
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

                        binding.tvAlamatIndustri.setText(industriDetail.getAlamat().toString());
                        binding.tvIndustriName.setText(industriDetail.getNamaIndustri().toString());

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
    }

    private void setFavoriteOrUnFavorite(boolean isChecked){
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

}
