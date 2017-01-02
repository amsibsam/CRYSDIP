package com.uny.crysdip.db;

import com.google.android.gms.drive.query.SortOrder;
import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.pojo.ListIndustri;
import com.uny.crysdip.pojo.ListIndustriForRecommendation;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmException;
import io.realm.internal.Context;

/**
 * Created by rahardyan on 31/05/16.
 */
public class RealmDb {
    private Realm realmDb;

    public RealmDb() {
        this.realmDb = Realm.getInstance(new RealmConfiguration
                .Builder()
                .name("crysdip.db")
                .deleteRealmIfMigrationNeeded()
                .build());
    }

    public Realm getRealmDb() {
        return realmDb;
    }

    public void add(RealmObject realmObject){
        realmDb.beginTransaction();
        realmDb.copyToRealmOrUpdate(realmObject);
        realmDb.commitTransaction();
    }

    public List<ListIndustri> getListIndustriFromDb(){
        RealmResults<ListIndustri> listIndustris = null;
        try{
           listIndustris = realmDb.where(ListIndustri.class).findAllSorted("count", Sort.DESCENDING);
        } catch (RealmException e){
            e.printStackTrace();
        }

        return listIndustris;
    }

    public List<ListIndustriForRecommendation> getListForRecommendation() {
        RealmResults<ListIndustriForRecommendation> listIndustriForRecommendations = null;
        try {
            listIndustriForRecommendations = realmDb.where(ListIndustriForRecommendation.class).findAll();
        } catch (RealmException e){
            e.printStackTrace();
        }

        return listIndustriForRecommendations;
    }
}
