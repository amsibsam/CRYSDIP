package com.uny.crysdip.ui.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.uny.crysdip.ui.ui.fragment.FavoritFragment;
import com.uny.crysdip.ui.ui.fragment.ListIndustriFragment;
import com.uny.crysdip.ui.ui.fragment.RecomendationFragment;

/**
 * Created by root on 21/03/16.
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ListIndustriFragment tab1 = new ListIndustriFragment();
                return tab1;
            case 1:
                RecomendationFragment tab2 = new RecomendationFragment();
                return tab2;
            case 2:
                FavoritFragment tab3 = new FavoritFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
