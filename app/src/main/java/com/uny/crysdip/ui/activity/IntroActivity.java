package com.uny.crysdip.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.cache.CacheAccountStore;
import com.uny.crysdip.ui.fragment.Intro1stFragment;
import com.uny.crysdip.ui.fragment.Intro2ndFragment;
import com.uny.crysdip.ui.fragment.Intro3rdFragment;

import javax.inject.Inject;

//use appintro paolorotolo library

public class IntroActivity extends AppIntro2 {

    @Override
    public void init(Bundle savedInstanceState) {
        Intro1stFragment intro1stFragment = new Intro1stFragment();
        Intro2ndFragment intro2ndFragment = new Intro2ndFragment();
        Intro3rdFragment intro3rdFragment = new Intro3rdFragment();

        addSlide(intro1stFragment);
        addSlide(intro2ndFragment);
        addSlide(intro3rdFragment);


        setProgressButtonEnabled(true);

//        setFadeAnimation();
//        setZoomAnimation();
//        setFlowAnimation();
//        setSlideOverAnimation();
        setDepthAnimation();
    }


    @Override
    public void onDonePressed() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }
}
