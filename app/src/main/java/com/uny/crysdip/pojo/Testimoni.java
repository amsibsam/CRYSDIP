package com.uny.crysdip.pojo;

import java.util.Date;

/**
 * Created by rahardyan on 02/05/16.
 */
public class Testimoni {
    private final Date createdAt;
    private final String name;
    private final String nim;
    private final String testimoni;


    public Testimoni(Date createdAt, String name, String nim, String testimoni) {
        this.createdAt = createdAt;
        this.name = name;
        this.nim = nim;
        this.testimoni = testimoni;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public String getTestimoni() {
        return testimoni;
    }
}
