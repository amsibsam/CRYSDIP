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

import com.google.android.gms.location.places.GeoDataApi;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.R;
import com.uny.crysdip.cache.CacheAccountStore;
import com.uny.crysdip.databinding.ActivityHomeBinding;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.ui.adapter.TabAdapter;

import java.util.Locale;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Inject
    CrysdipService crysdipService;

    @Inject
    CacheAccountStore cacheAccountStore;

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
                switch (tab.getPosition()){
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


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
