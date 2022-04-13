
package com.example.conversormoedas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dolar {

    @SerializedName("USD")
    @Expose
    private Usd usd;

    public Usd getUsd() {
        return usd;
    }

    public void setUsd(Usd usd) {
        this.usd = usd;
    }

}
