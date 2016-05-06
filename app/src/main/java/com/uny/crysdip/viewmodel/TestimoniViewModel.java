package com.uny.crysdip.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.uny.crysdip.pojo.ListIndustri;
import com.uny.crysdip.pojo.Testimoni;

/**
 * Created by rahardyan on 24/04/16.
 */
public class TestimoniViewModel extends BaseObservable {
    private Testimoni testimoni;
    private View.OnClickListener viewIndustriDetailClickListener;

    public TestimoniViewModel(Testimoni testimoni, View.OnClickListener viewIndustriDetailClickListener){
        this.testimoni = testimoni;
        this.viewIndustriDetailClickListener = viewIndustriDetailClickListener;
    }

    @Bindable
    public String getName() {
        return testimoni.getName();
    }

    @Bindable
    public String getTestimoni(){
        return testimoni.getTestimoni();
    }

    @Bindable
    public String getDate(){
        return testimoni.getCreatedAt();
    }

    @Bindable
    public View.OnClickListener getViewIndustriDetailClickListener(){
        return viewIndustriDetailClickListener;
    }
}
