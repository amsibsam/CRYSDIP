package com.uny.crysdip.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.R;
import com.uny.crysdip.cache.CacheAccountStore;
import com.uny.crysdip.ui.activity.HomeActivity;
import com.uny.crysdip.ui.activity.IntroActivity;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Intro1stFragment extends Fragment {
    @Inject
    CacheAccountStore cacheAccountStore;

    public Intro1stFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro1st, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (cacheAccountStore.hasAccount()) {
            Log.d("amsibsam", "login false");
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();
        }
    }
}
