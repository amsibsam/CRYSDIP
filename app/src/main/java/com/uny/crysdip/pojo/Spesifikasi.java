package com.uny.crysdip.pojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rahardyan on 02/01/17.
 */

public class Spesifikasi extends RealmObject {
    @PrimaryKey
    private String spec;
    private double df;
    private double idf;
    private double value;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setDf(double df) {
        this.df = df;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getDf() {
        return df;
    }

    public double getIdf() {
        return idf;
    }

    public double getValue() {
        return value;
    }
}
