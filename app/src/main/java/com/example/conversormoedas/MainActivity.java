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
                this.mViewHolder.textDolar.setText("$ " + String.format("%.4f", converterDolar(reais)));
                this.mViewHolder.textEuro.setText("€ " + String.format("%.4f", converterEuro(reais)));
            }
        }
    }


    private Double converterDolar(Double reais) {
        // Criando o RestAdapter Trecho 01
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://economia.awesomeapi.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CotacaoService cotacaoService = retrofit.create(CotacaoService.class);

        Call<Dolar> cotacao = cotacaoService.getCotacaoDolar();
        cotacao.enqueue(new Callback<Dolar>(){
            @Override
            public void onResponse(Call<Dolar> call, Response<Dolar> response) {
                if (response.isSuccessful()) {
                    int statusCOde = response.code();
                    Dolar resposta = response.body();

                    Log.w("App", resposta.getUsd().getLow());
                } else {
                    Log.w("App",response.errorBody().toString());
                }// this will tell you why your api doesnt work most of time

            }

            @Override
            public void onFailure(Call<Dolar> call, Throwable t) {
                Log.w("App",t.toString());
            }
        });


        Double cotacaoDolar = 4.0;
        return reais / cotacaoDolar;
    }

    private Double converterEuro(Double reais) {
        Double cotacaoEuro = 5.0;
        return reais / cotacaoEuro;
    }

    private static class ViewHolder {
        EditText editValor;
        TextView textDolar;
        TextView textEuro;
        Button buttonConverter;
    }
}