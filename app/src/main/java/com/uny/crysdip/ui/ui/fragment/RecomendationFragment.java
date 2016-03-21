package com.uny.crysdip.ui.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uny.crysdip.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecomendationFragment extends android.support.v4.app.Fragment {


    public RecomendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recomendation, container, false);
    }

}
