package com.uny.crysdip.application;

import android.app.Application;

import com.uny.crysdip.application.module.CrysdipComponent;

/**
 * Created by root on 29/03/16.
 */
public class CrysdipApplication extends Application {
    private static CrysdipApplication instance;
    private CrysdipComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        component = CrysdipComponent.Initializer.init(this);
    }

    public static CrysdipApplication getInstance(){
        return instance;
    }

    public static CrysdipComponent getComponent(){
        return instance.component;
    }
}
