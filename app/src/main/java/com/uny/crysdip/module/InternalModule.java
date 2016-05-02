package com.uny.crysdip.module;


import android.content.Context;

import com.uny.crysdip.cache.CacheAccountStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InternalModule {
    @Provides
    @Singleton
    CacheAccountStore provideCacheAccountStore(Context context){
        return new CacheAccountStore(context);
    }


}
