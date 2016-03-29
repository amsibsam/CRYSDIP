package com.uny.crysdip.application.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by root on 29/03/16.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        ExternalModule.class,
})
public interface CrysdipComponent {

    final class Initializer {
        public static CrysdipComponent init(Application application) {
            return DaggerCrysdipComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}
