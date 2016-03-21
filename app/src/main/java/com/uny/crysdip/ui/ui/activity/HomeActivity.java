package com.uny.crysdip.ui.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewParent;

import com.uny.crysdip.R;
import com.uny.crysdip.databinding.ActivityHomeBinding;
import com.uny.crysdip.ui.ui.adapter.TabAdapter;
import com.uny.crysdip.ui.ui.fragment.ListIndustriFragment;

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
        //TODO: use proper image icon
        tabLayout.addTab(tabLayout.newTab().setText("List PI"));
        tabLayout.addTab(tabLayout.newTab().setText("Recomendation"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite"));

        final PagerAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
