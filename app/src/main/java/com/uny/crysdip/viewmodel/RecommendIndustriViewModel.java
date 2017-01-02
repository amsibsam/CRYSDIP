package com.uny.crysdip.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.view.View;

import com.uny.crysdip.pojo.ListIndustri;
import com.uny.crysdip.pojo.ListIndustriForRecommendation;

/**
 * Created by rahardyan on 24/04/16.
 */
public class RecommendIndustriViewModel extends BaseObservable {
    private ListIndustriForRecommendation industri;
    private View.OnClickListener viewIndustriDetailClickListener;

    public RecommendIndustriViewModel(ListIndustriForRecommendation industri, View.OnClickListener viewIndustriDetailClickListener){
        this.industri = industri;
        this.viewIndustriDetailClickListener = viewIndustriDetailClickListener;
    }

    @Bindable
    public String getName() {
        return industri.getNamaIndustri();
    }

    @Bindable
    public String getAlamat(){
        return industri.getAlamat();
    }

    @Bindable
    public Uri getFoto(){
        return Uri.parse(industri.getFotoUrl());
    }

    @Bindable
    public String getFavorite() {
        return String.valueOf(industri.getCount());
    }

    @Bindable
    public View.OnClickListener getViewIndustriDetailClickListener(){
        return viewIndustriDetailClickListener;
    }
}
