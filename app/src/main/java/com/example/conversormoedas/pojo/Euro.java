
package com.example.conversormoedas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Euro {

    @SerializedName("EUR")
    @Expose
    private Eur eur;

    public Eur getEur() {
        return eur;
    }

    public void setEur(Eur eur) {
        this.eur = eur;
    }

}
