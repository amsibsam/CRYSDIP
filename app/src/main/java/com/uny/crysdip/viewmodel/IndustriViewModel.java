package com.uny.crysdip.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.uny.crysdip.Pojo.ListIndustri;

import butterknife.Bind;

/**
 * Created by rahardyan on 24/04/16.
 */
public class IndustriViewModel extends BaseObservable {
    private ListIndustri industri;
    private View.OnClickListener viewIndustriDetailClickListener;

    public IndustriViewModel(ListIndustri industri, View.OnClickListener viewIndustriDetailClickListener){
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
    public View.OnClickListener getViewIndustriDetailClickListener(){
        return viewIndustriDetailClickListener;
    }
}
