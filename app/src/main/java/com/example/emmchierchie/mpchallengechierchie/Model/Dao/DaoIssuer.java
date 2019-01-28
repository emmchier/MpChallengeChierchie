package com.example.emmchierchie.mpchallengechierchie.Model.Dao;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Issuer;
import com.example.emmchierchie.mpchallengechierchie.Utils.ResultListener;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaoIssuer {

    // lógica similar en DAOInstallments
    private Retrofit retrofit;
    private DaoService service;

    private String key = "444a9ef5-8a6b-429f-abdf-587639155d88";
    private String baseURL = "https://api.mercadopago.com/v1/";

    public DaoIssuer() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.client(httpClient.build()).build();
        service = retrofit.create(DaoService.class);
    }
    public void getAllIssuers(final ResultListener<List<Issuer>> listener, String methodId) {
        Call<List<Issuer>> retrofitListener = service.getIssuers(key, methodId);
        retrofitListener.enqueue( new Callback<List<Issuer>>() {
            @Override
            public void onResponse(Call<List<Issuer>> call, Response<List<Issuer>> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<List<Issuer>> call, Throwable t) {
                listener.finish(new ArrayList<Issuer>());
            }
        } );
    }
}
