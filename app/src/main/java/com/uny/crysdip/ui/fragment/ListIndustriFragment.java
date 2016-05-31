package com.uny.crysdip.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.uny.crysdip.BR;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.db.RealmDb;
import com.uny.crysdip.pojo.ListIndustri;
import com.uny.crysdip.R;
import com.uny.crysdip.databinding.FragmentListIndustriBinding;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.ui.activity.IndustryActivity;
import com.uny.crysdip.ui.util.DrawableManager;
import com.uny.crysdip.viewmodel.IndustriViewModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmResults;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListIndustriFragment extends android.support.v4.app.Fragment {
    private FragmentListIndustriBinding binding;
    private IndustriListViewModel industriListViewModel = new IndustriListViewModel();
    private static final String INDUSTRI_ID = "industri_id";

    @Inject
    CrysdipService crysdipService;

    @Inject
    RealmDb realmDb;

    public static ListIndustriFragment newInstance(int page, String title) {
        ListIndustriFragment listIndustriFragment = new ListIndustriFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        listIndustriFragment.setArguments(args);
        return listIndustriFragment;
    }

    //////////////////////LIFE CYCLE SECTION////////////////////////////
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListIndustriBinding.inflate(inflater, container, false);
        setUpRecyclerView();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.setIndustriListViewModel(industriListViewModel);
        getIndustriList();
    }

    ////////////////INNER CLASS SECTION////////////////////
    public static class IndustriListViewModel {
        public final ObservableList<IndustriViewModel> items = new ObservableArrayList<>();
        public final ItemView itemView = ItemView.of(BR.itemViewModel, R.layout.item_industri);
    }

    //////////////////METHOD SECTINO///////////////////////
    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new SlideInLeftAnimator());
    }

    private void getIndustriList() {
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
        industriListViewModel.items.clear();
        if (isNetworkConnected()){
            Log.d("amsibsam", "online");
            crysdipService.getListIndustri()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .toList()
                    .subscribe(new Subscriber<List<ListIndustri>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("amsibsam", "error get industri " + e.toString());
                            binding.pbLoading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(List<ListIndustri> listIndustris) {
                            binding.pbLoading.setVisibility(View.GONE);
                            binding.recyclerView.setVisibility(View.VISIBLE);
                            for (final ListIndustri listIndustri : listIndustris){
                                realmDb.add(listIndustri);
                                industriListViewModel.items.add(new IndustriViewModel(listIndustri,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(getActivity(), IndustryActivity.class)
                                                        .putExtra(INDUSTRI_ID, listIndustri.getId()));
                                            }
                                        }));
                            }
                        }
                    });
        } else {
            binding.pbLoading.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            List<ListIndustri> listIndustris = realmDb.getListIndustriFromDb();
            for (ListIndustri listIndustri : listIndustris){
                Log.d("amsibsam", "industri offline "+listIndustri.getNamaIndustri());
                industriListViewModel.items.add(new IndustriViewModel(listIndustri, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Anda Offline", Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
