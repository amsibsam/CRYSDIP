package com.uny.crysdip.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.databinding.FragmentRecomendationBinding;
import com.uny.crysdip.db.RealmDb;
import com.uny.crysdip.pojo.ListIndustriForRecommendation;
import com.uny.crysdip.ui.activity.RecomendationListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecomendationFragment extends android.support.v4.app.Fragment {
    private FragmentRecomendationBinding binding;
    private List<String> spesifikasi = new ArrayList<>();

    @Inject
    RealmDb realmDb;

    public RecomendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CrysdipApplication.getComponent().inject(this);
        binding = FragmentRecomendationBinding.inflate(inflater, container, false);


        binding.rgProdiCotainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) getActivity().findViewById(checkedId);
                if (radioButton.getText().equals("Informatika")) {
                    binding.tvMinatBakat.setVisibility(View.VISIBLE);
                    binding.informatikaContainer.setVisibility(View.VISIBLE);
                    binding.cbElektronikaContainer.setVisibility(View.GONE);
                } else {
                    binding.tvMinatBakat.setVisibility(View.VISIBLE);
                    binding.informatikaContainer.setVisibility(View.GONE);
                    binding.cbElektronikaContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        informatikaSetButtonClick();
        elektronikaSetButtonClick();

        binding.btnRekomendasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RecomendationListActivity.generateIntent(getActivity(),
                        spesifikasi.toArray(new String[spesifikasi.size()])));
            }
        });


        return binding.getRoot();
    }

    private void informatikaSetButtonClick() {
        binding.cbInstalasiJaringan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("instalasi jaringan");
                } else {
                    spesifikasi.remove("instalasi jaringan");
                }
            }
        });

        binding.cbAdministrasiJaringan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("administrasi jaringan");
                } else {
                    spesifikasi.remove("administrasi jaringan");
                }
            }
        });

        binding.cbAnimasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("animasi");
                } else {
                    spesifikasi.remove("animasi");
                }
            }
        });

        binding.cbDesainGrafis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("desain grafis");
                } else {
                    spesifikasi.remove("desain grafis");
                }
            }
        });

        binding.cbFilm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("film");
                } else {
                    spesifikasi.remove("film");
                }
            }
        });

        binding.cbMobileProgramming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("mobile programming");
                } else {
                    spesifikasi.remove("mobile programming");
                }
            }
        });

        binding.cbWebProgramming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("web programming");
                } else {
                    spesifikasi.remove("web programming");
                }
            }
        });

        binding.cbSistemInformasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("sistem informasi");
                } else {
                    spesifikasi.remove("sistem informasi");
                }
            }
        });
    }

    private void elektronikaSetButtonClick() {
        binding.cbInstalasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("instalasi");
                } else {
                    spesifikasi.remove("instalasi");
                }
            }
        });

        binding.cbTelekomunikasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("telekomunikasi");
                } else {
                    spesifikasi.remove("telekomunikasi");
                }
            }
        });

        binding.cbAudioVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("audio video");
                } else {
                    spesifikasi.remove("audio video");
                }
            }
        });

        binding.cbAnalog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("analog");
                } else {
                    spesifikasi.remove("analog");
                }
            }
        });

        binding.cbTechnicalSupport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("technical support");
                } else {
                    spesifikasi.remove("technical support");
                }
            }
        });

        binding.cbElektronikaIndustri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spesifikasi.add("elektronika industri");
                } else {
                    spesifikasi.remove("elektronika industri");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<ListIndustriForRecommendation> listIndustriForRecommendations = realmDb.getListForRecommendation();

        for (final ListIndustriForRecommendation singleItem : listIndustriForRecommendations) {
            Log.d("amsibsam", "hapus value "+singleItem.getValue());
            realmDb.getRealmDb().beginTransaction();
            singleItem.setValue(0);
            realmDb.getRealmDb().copyToRealmOrUpdate(singleItem);
            realmDb.getRealmDb().commitTransaction();
        }
    }
}
