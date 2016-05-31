package com.uny.crysdip.db;

import com.uny.crysdip.CrysdipApplication;
import com.uny.crysdip.pojo.ListIndustri;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import io.realm.internal.Context;

/**
 * Created by rahardyan on 31/05/16.
 */
public class RealmDb {
    private Realm realmDb;

    public RealmDb() {
        this.realmDb = Realm.getInstance(new RealmConfiguration.Builder(CrysdipApplication.getInstance().getBaseContext()).name("crysdip.db").build());
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
           listIndustris = realmDb.where(ListIndustri.class).findAllSorted("count", false);
        } catch (RealmException e){
            e.printStackTrace();
        }

        return listIndustris;
    }
}
