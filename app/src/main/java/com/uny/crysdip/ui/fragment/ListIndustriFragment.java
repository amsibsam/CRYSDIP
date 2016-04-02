package com.uny.crysdip.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uny.crysdip.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListIndustriFragment extends android.support.v4.app.Fragment {


    public static ListIndustriFragment newInstance(int page, String title) {
        ListIndustriFragment listIndustriFragment = new ListIndustriFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        listIndustriFragment.setArguments(args);
        return listIndustriFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_industri, container, false);
    }

}
