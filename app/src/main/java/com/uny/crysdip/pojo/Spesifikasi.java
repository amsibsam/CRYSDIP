package com.uny.crysdip.pojo;

import io.realm.RealmObject;

/**
 * Created by rahardyan on 02/01/17.
 */

public class Spesifikasi extends RealmObject {
    private String spec;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
