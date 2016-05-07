package com.uny.crysdip.ui.fragment;


import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.R;
import com.uny.crysdip.cache.CacheAccountStore;
import com.uny.crysdip.databinding.FragmentFavoritBinding;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.pojo.ListIndustri;
import com.uny.crysdip.ui.activity.IndustryActivity;
import com.uny.crysdip.viewmodel.IndustriViewModel;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends android.support.v4.app.Fragment {
    private FragmentFavoritBinding binding;
    private FavoriteIndustriListViewModel favoriteIndustriListViewModel = new FavoriteIndustriListViewModel();
    private static final String INDUSTRI_ID = "industri_id";

    @Inject
    CrysdipService crysdipService;

    @Inject
    CacheAccountStore cacheAccountStore;

    public FavoritFragment() {
        // Required empty public constructor
    }

    //////////////////////////LIFE CYCLE SECTION////////////////////////
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritBinding.inflate(inflater, container, false);
        binding.setItemFavoriteViewModel(favoriteIndustriListViewModel);

        setUpRecyclerView();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavoriteIndustriList();
    }

    //////////////INNER CLASS SECTION/////////////
    public static class FavoriteIndustriListViewModel{
        public final ObservableList<IndustriViewModel> items = new ObservableArrayList<>();
        public final ItemView itemView = ItemView.of(BR.itemFavoriteViewModel, R.layout.item_industri);
    }


    //////////////METHOD SECTION////////////////////
    private void getFavoriteIndustriList(){
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);
        crysdipService.getFavoritedIndustri(cacheAccountStore.getCachedAccount().getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ListIndustri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final ListIndustri listIndustri) {
                        binding.pbLoading.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        favoriteIndustriListViewModel.items.add(new IndustriViewModel(listIndustri,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(getActivity(), IndustryActivity.class)
                                                .putExtra(INDUSTRI_ID, listIndustri.getId()));
                                    }
                                }));
                    }
                });
    }

    private void setUpRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);

        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new SlideInLeftAnimator());
    }

}
