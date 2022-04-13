package com.example.conversormoedas;

import com.example.conversormoedas.pojo.Dolar;
import com.example.conversormoedas.pojo.Euro;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CotacaoService {
    @GET("json/last/USD-BRL")
    Call<Dolar> getCotacaoDolar();

    @GET("json/last/EUR-BRL")
    Call<Euro> getCotacaoEuro();


}
