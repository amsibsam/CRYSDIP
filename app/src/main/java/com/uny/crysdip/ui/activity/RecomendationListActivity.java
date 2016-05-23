package com.uny.crysdip.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.R;
import com.uny.crysdip.databinding.ActivityRecomendationListBinding;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.pojo.ListIndustri;
import com.uny.crysdip.viewmodel.IndustriViewModel;

import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecomendationListActivity extends AppCompatActivity {
    private static final String INDUSTRI_ID = "industri_id";
    private ActivityRecomendationListBinding binding;
    ItemRecomendationViewModel itemRecomendationViewModel = new ItemRecomendationViewModel();

    @Inject
    CrysdipService crysdipService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recomendation_list);
        binding.setItemRecomendationList(itemRecomendationViewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        getRecomendation();
    }

    public static class ItemRecomendationViewModel{
        public final ObservableList<IndustriViewModel> items = new ObservableArrayList();
        public final ItemView itemView = ItemView.of(BR.itemViewModel, R.layout.item_industri);

    }

    private void getRecomendation(){
        String kategori1 = getIntent().getStringExtra("kategori1");
        String kategori2 = getIntent().getStringExtra("kategori2");
        String kategori3 = getIntent().getStringExtra("kategori3");
        String kategori4 = getIntent().getStringExtra("kategori4");
        String kategori5 = getIntent().getStringExtra("kategori5");

        String spesifikasi1 = getIntent().getStringExtra("spesifikasi1");
        String spesifikasi2 = getIntent().getStringExtra("spesifikasi2");
        String spesifikasi3 = getIntent().getStringExtra("spesifikasi3");
        String spesifikasi4 = getIntent().getStringExtra("spesifikasi4");
        String spesifikasi5 = getIntent().getStringExtra("spesifikasi5");
        String spesifikasi6 = getIntent().getStringExtra("spesifikasi6");
        String spesifikasi7 = getIntent().getStringExtra("spesifikasi7");
        String spesifikasi8 = getIntent().getStringExtra("spesifikasi8");
        String spesifikasi9 = getIntent().getStringExtra("spesifikasi9");

        crysdipService.getRecomendation(kategori1, kategori2, kategori3, kategori4, kategori5,
                spesifikasi1, spesifikasi2, spesifikasi3, spesifikasi4, spesifikasi5, spesifikasi6,
                spesifikasi7,spesifikasi8,spesifikasi9)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ListIndustri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "error getfavorit "+e.toString());
                    }

                    @Override
                    public void onNext(final ListIndustri listIndustri) {
                        itemRecomendationViewModel.items.add(new IndustriViewModel(listIndustri, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(RecomendationListActivity.this, IndustryActivity.class)
                                        .putExtra(INDUSTRI_ID, listIndustri.getId()));
                            }
                        }));
                    }
                });
    }
}
