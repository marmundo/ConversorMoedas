package com.example.conversormoedas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conversormoedas.pojo.Dolar;
import com.example.conversormoedas.pojo.Euro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private final ViewHolder mViewHolder = new ViewHolder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.editValor = findViewById(R.id.editReais);
        this.mViewHolder.textDolar = findViewById(R.id.textDolar);
        this.mViewHolder.textEuro = findViewById(R.id.textEuro);

        this.mViewHolder.buttonConverter = findViewById(R.id.buttonConverter);
        this.mViewHolder.buttonConverter.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonConverter) {
            String mValor = this.mViewHolder.editValor.getText().toString();
            if (mValor.equals("")) {
                Toast.makeText(this, this.getString(R.string.informe_valor), Toast.LENGTH_LONG).show();
            } else {
                Double reais = Double.parseDouble(mValor);
                converterDolar(reais);
                converterEuro(reais);
            }
        }
    }


    private void converterDolar(Double reais) {

        final double[] cotacaoDolar = {1.0};
        CotacaoService cotacaoService = getCotacaoService();

        Call<Dolar> cotacao = cotacaoService.getCotacaoDolar();
        cotacao.enqueue(new Callback<Dolar>() {
            @Override
            public void onResponse(Call<Dolar> call, Response<Dolar> response) {
                if (response.isSuccessful()) {
                    int statusCOde = response.code();
                    Dolar resposta = response.body();


                    mViewHolder.textDolar.setText("$ " + resposta.getUsd().getLow());
                } else {
                    Log.w("App", response.errorBody().toString());
                }// this will tell you why your api doesnt work most of time

            }

            @Override
            public void onFailure(Call<Dolar> call, Throwable t) {
                Log.w("App", t.toString());
            }
        });


    }

    private CotacaoService getCotacaoService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://economia.awesomeapi.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CotacaoService cotacaoService = retrofit.create(CotacaoService.class);
        return cotacaoService;
    }

    private void converterEuro(Double reais) {


        final double[] cotacaoEuro = {1.0};
        CotacaoService cotacaoService = getCotacaoService();

        Call<Euro> cotacao = cotacaoService.getCotacaoEuro();
        cotacao.enqueue(new Callback<Euro>() {
            @Override
            public void onResponse(Call<Euro> call, Response<Euro> response) {
                Log.w("App", "Teste");
                if (response.isSuccessful()) {
                    Log.w("App", "Teste");
                    int statusCOde = response.code();
                    Euro resposta = response.body();

                    mViewHolder.textEuro.setText("€ " + resposta.getEur().getLow());
                } else {
                    Log.w("App", response.errorBody().toString());
                }// this will tell you why your api doesnt work most of time

            }

            @Override
            public void onFailure(Call<Euro> call, Throwable t) {
                Log.w("App", t.toString());
            }
        });

    }

    private static class ViewHolder {
        EditText editValor;
        TextView textDolar;
        TextView textEuro;
        Button buttonConverter;
    }
}