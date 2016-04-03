package com.uny.crysdip.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uny.crysdip.R;
import com.uny.crysdip.databinding.ActivityHomeBinding;
import com.uny.crysdip.ui.adapter.TabAdapter;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        tabLayout = binding.tabLayout;
        viewPager = binding.pager;

        setSupportActionBar(binding.toolbar);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_home), 0);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_recomendation), 1);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_favorite), 2);

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
}
