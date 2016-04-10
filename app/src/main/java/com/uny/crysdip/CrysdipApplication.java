package com.uny.crysdip;

import android.app.Application;

/**
 * Created by root on 09/04/16.
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

    public static CrysdipApplication getInstance() {
        return instance;
    }

    public static CrysdipComponent getComponent() {
        return instance.component;
    }
}
