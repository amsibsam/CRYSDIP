package com.uny.crysdip.ui.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.uny.crysdip.R;
import com.uny.crysdip.ui.ui.fragment.Intro1stFragment;
import com.uny.crysdip.ui.ui.fragment.Intro2ndFragment;
import com.uny.crysdip.ui.ui.fragment.Intro3rdFragment;

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
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }
}
