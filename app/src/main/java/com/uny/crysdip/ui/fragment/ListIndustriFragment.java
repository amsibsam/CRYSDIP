package com.uny.crysdip.ui.fragment;


import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uny.crysdip.BR;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.Pojo.ListIndustri;
import com.uny.crysdip.R;
import com.uny.crysdip.databinding.FragmentListIndustriBinding;
import com.uny.crysdip.network.CrysdipService;
import com.uny.crysdip.ui.activity.IndustryActivity;
import com.uny.crysdip.viewmodel.IndustriViewModel;

import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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

    public static ListIndustriFragment newInstance(int page, String title) {
        ListIndustriFragment listIndustriFragment = new ListIndustriFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        listIndustriFragment.setArguments(args);
        return listIndustriFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrysdipApplication.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListIndustriBinding.inflate(inflater, container, false);


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.setIndustriListViewModel(industriListViewModel);
        getIndustriList();

    }

    private void getIndustriList(){
        industriListViewModel.items.clear();
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

                    }

                    @Override
                    public void onNext(List<ListIndustri> industris) {
                        binding.pbLoading.setVisibility(View.GONE);
                        binding.listView.setVisibility(View.VISIBLE);
                        Log.d("amsibsam", "Jumlah "+industris.size());
                        for (final ListIndustri listindustri : industris){
                            industriListViewModel.items.add(new IndustriViewModel(listindustri,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(getActivity(), IndustryActivity.class)
                                            .putExtra(INDUSTRI_ID, listindustri.getId()));
                                        }
                                    }));
                        }
                    }
                });
    }

    public static class IndustriListViewModel{
        public final ObservableList<IndustriViewModel> items = new ObservableArrayList<>();
        public final ItemView itemView = ItemView.of(BR.itemViewModel, R.layout.item_industri);
    }

}
