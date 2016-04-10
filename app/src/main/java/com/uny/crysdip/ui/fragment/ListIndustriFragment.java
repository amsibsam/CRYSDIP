package com.uny.crysdip.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uny.crysdip.R;
import com.uny.crysdip.databinding.FragmentListIndustriBinding;
import com.uny.crysdip.ui.activity.IndustryActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListIndustriFragment extends android.support.v4.app.Fragment {
    private FragmentListIndustriBinding binding;


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
        binding = FragmentListIndustriBinding.inflate(inflater, container, false);
        binding.tvIndustriName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), IndustryActivity.class));
            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
