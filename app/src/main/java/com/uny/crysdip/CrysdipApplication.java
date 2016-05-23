package com.uny.crysdip;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by rahardyan on 09/04/16.
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static CrysdipApplication getInstance() {
        return instance;
    }

    public static CrysdipComponent getComponent() {
        return instance.component;
    }
}
