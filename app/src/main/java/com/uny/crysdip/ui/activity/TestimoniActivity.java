package com.uny.crysdip.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.R;
import com.uny.crysdip.databinding.ActivityTestimoniBinding;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.pojo.Testimoni;
import com.uny.crysdip.viewmodel.TestimoniViewModel;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestimoniActivity extends AppCompatActivity {
    private static final String INDUSTRI_ID = "industri_id";
    private TestimoniLongViewModel testimoniLongViewModel = new TestimoniLongViewModel();
    private ActivityTestimoniBinding binding;

    @Inject
    CrysdipService crysdipService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_testimoni);
        CrysdipApplication.getComponent().inject(this);

        binding.setItemTestimoniLong(testimoniLongViewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        getTestimoniLong();
    }

    public static class TestimoniLongViewModel{
        public final ObservableList<TestimoniViewModel> items = new ObservableArrayList<>();
        public final ItemView itemView = ItemView.of(BR.itemTestimoniLong, R.layout.item_testimoni_short);
    }

    private void getTestimoniLong(){
        testimoniLongViewModel.items.clear();
        crysdipService.getTestimoniLong(getIntent().getIntExtra(INDUSTRI_ID, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Testimoni>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "error getTestimoniLong "+e.toString());
                    }

                    @Override
                    public void onNext(Testimoni testimoni) {
                        testimoniLongViewModel.items.add(new TestimoniViewModel(testimoni, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }));
                    }
                });
    }
}
