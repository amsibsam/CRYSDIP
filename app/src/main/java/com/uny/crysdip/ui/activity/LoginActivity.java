package com.uny.crysdip.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.R;
import com.uny.crysdip.cache.CacheAccountStore;
import com.uny.crysdip.databinding.ActivityLoginBinding;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.pojo.Mahasiswa;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Inject
    CrysdipService crysdipService;

    @Inject
    CacheAccountStore cacheAccountStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        CrysdipApplication.getComponent().inject(this);
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(100);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(500);
        set.addAnimation(animation);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = binding.etNim.getText().toString();
                String password = binding.etPassword.getText().toString();
                if (nim.equals("")||nim == null || password.equals("") || password == null){
                    Toast.makeText(LoginActivity.this, "isi nim dan password", Toast.LENGTH_SHORT).show();
                } else {
                    login(binding.etNim.getText().toString(), binding.etPassword.getText().toString());
                }

            }
        });
    }


    private void login(String nim, String password){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Harap tunggu..");
        progressDialog.show();
        crysdipService.login(nim, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Mahasiswa>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "error login "+e.toString());
                        if (e.getMessage().contains("401")) {
                            Toast.makeText(LoginActivity.this, "Password atau nim salah", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(Mahasiswa mahasiswa) {
                        progressDialog.dismiss();
                        Log.d("amsibsam", "mahasiswa id" + mahasiswa.getId());
                        cacheAccountStore.cacheAccount(mahasiswa);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                });
    }
}
