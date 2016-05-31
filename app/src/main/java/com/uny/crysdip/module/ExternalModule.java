package com.uny.crysdip.module;

import android.content.Context;

import com.uny.crysdip.db.RealmDb;
import com.uny.crysdip.network.CrysdipService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 30/01/16.
 */
@Module
public final class ExternalModule {
    @Provides
    @Singleton
    CrysdipService provideCrysdipService(Context context) {
        return new CrysdipService(context);
    }

    @Provides
    @Singleton
    RealmDb provideRealmDb(){
        return new RealmDb();
    }
}

