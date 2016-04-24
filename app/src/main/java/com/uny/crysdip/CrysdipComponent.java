package com.uny.crysdip;

import android.app.Application;

import com.uny.crysdip.module.ApplicationModule;
import com.uny.crysdip.module.ExternalModule;
import com.uny.crysdip.module.InternalModule;
import com.uny.crysdip.ui.activity.HomeActivity;
import com.uny.crysdip.ui.activity.LoginActivity;
import com.uny.crysdip.ui.fragment.ListIndustriFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by root on 29/03/16.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        ExternalModule.class,
        InternalModule.class
})
public interface CrysdipComponent {

    void inject(LoginActivity loginActivity);
    void inject(HomeActivity homeActivity);
    void inject(ListIndustriFragment listIndustriFragment);

    final class Initializer {
        public static CrysdipComponent init(Application application) {
            return DaggerCrysdipComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}
