package com.uny.crysdip.ui.activity;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.R;
import com.uny.crysdip.cache.CacheAccountStore;

import javax.inject.Inject;

public class SplashScreenActivity extends AppCompatActivity {
    @Inject
    CacheAccountStore cacheAccountStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        CrysdipApplication.getComponent().inject(this);

        Intent intent;

        if (cacheAccountStore.hasAccount()){
            intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, IntroActivity.class);
        }


        final Intent finalIntent = intent;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(finalIntent);
                finish();
            }
        }, 1000);
    }
}
