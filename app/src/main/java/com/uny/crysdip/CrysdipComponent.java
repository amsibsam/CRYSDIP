package com.uny.crysdip;

import android.app.Application;

import com.uny.crysdip.module.ApplicationModule;
import com.uny.crysdip.module.ExternalModule;
import com.uny.crysdip.module.InternalModule;
import com.uny.crysdip.ui.activity.HomeActivity;
import com.uny.crysdip.ui.activity.IndustryActivity;
import com.uny.crysdip.ui.activity.IntroActivity;
import com.uny.crysdip.ui.activity.LoginActivity;
import com.uny.crysdip.ui.activity.RecomendationListActivity;
import com.uny.crysdip.ui.activity.SplashScreenActivity;
import com.uny.crysdip.ui.activity.TestimoniActivity;
import com.uny.crysdip.ui.fragment.FavoritFragment;
import com.uny.crysdip.ui.fragment.Intro1stFragment;
import com.uny.crysdip.ui.fragment.ListIndustriFragment;
import com.uny.crysdip.ui.fragment.RecomendationFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rahardyan on 29/03/16.
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
    void inject(IndustryActivity industryActivity);
    void inject(IntroActivity introActivity);
    void inject(Intro1stFragment intro1stFragment);
    void inject(FavoritFragment favoritFragment);
    void inject(SplashScreenActivity splashScreenActivity);
    void inject(RecomendationListActivity recomendationListActivity);
    void inject(TestimoniActivity testimoniActivity);
    void inject(RecomendationFragment recomendationFragment);


    final class Initializer {
        public static CrysdipComponent init(Application application) {
            return DaggerCrysdipComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}
