package com.uny.crysdip.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Geocoder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.gms.location.places.GeoDataApi;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.R;
import com.uny.crysdip.cache.CacheAccountStore;
import com.uny.crysdip.databinding.ActivityHomeBinding;
import com.uny.crysdip.db.RealmDb;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.pojo.Spesifikasi;
import com.uny.crysdip.ui.adapter.TabAdapter;

import java.util.Locale;

import javax.inject.Inject;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Inject
    CrysdipService crysdipService;

    @Inject
    CacheAccountStore cacheAccountStore;

    @Inject
    RealmDb realmDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        tabLayout = binding.tabLayout;
        viewPager = binding.pager;

        setSupportActionBar(binding.toolbar);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_home), 0);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_recomendation), 1);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_favorite), 2);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));

        final PagerAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        binding.toolbarTitle.setText("Beranda");
                        break;
                    case 1:
                        binding.toolbarTitle.setText("Rekomendasi");
                        break;
                    case 2:
                        binding.toolbarTitle.setText("Favorit");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (cacheAccountStore.isFirstLogin()) {
            setShowCaseGuide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (final Spesifikasi spesifikasi : realmDb.getListSpesifikasi()) {
            realmDb.getRealmDb().beginTransaction();
            spesifikasi.setDf(0);
            spesifikasi.setIdf(0);
            realmDb.getRealmDb().copyToRealmOrUpdate(spesifikasi);
            realmDb.getRealmDb().commitTransaction();
        }
    }

    private void setShowCaseGuide() {
        final MaterialTapTargetPrompt favorit = new MaterialTapTargetPrompt.Builder(HomeActivity.this)
                .setTarget(binding.favoriteTarget)
                .setIcon(R.drawable.ic_tab_favorite)
                .setPrimaryText("Favorit")
                .setSecondaryText("Menu yang industri yang telah ditandai sebagai favorit")
                .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener() {
                    @Override
                    public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                        cacheAccountStore.firstLogin(false);
                    }

                    @Override
                    public void onHidePromptComplete() {

                    }
                }).create();

        final MaterialTapTargetPrompt recomendation = new MaterialTapTargetPrompt.Builder(HomeActivity.this)
                .setTarget(binding.recommendationTarget)
                .setIcon(R.drawable.ic_tab_recomendation)
                .setPrimaryText("Rekomendasi")
                .setSecondaryText("Menu yang berfungsi unuk memberikan rekomendasi berdasarkan minat")
                .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener() {
                    @Override
                    public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                        favorit.show();
                    }

                    @Override
                    public void onHidePromptComplete() {

                    }
                }).create();


        MaterialTapTargetPrompt home = new MaterialTapTargetPrompt.Builder(HomeActivity.this)
                .setTarget(binding.homeTarget)
                .setIcon(R.drawable.ic_tab_home)
                .setPrimaryText("Beranda")
                .setSecondaryText("Menu yang berisi semua daftar industri")
                .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener() {
                    @Override
                    public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                        recomendation.show();
                    }

                    @Override
                    public void onHidePromptComplete() {

                    }
                }).create();

        home.show();
    }

}
