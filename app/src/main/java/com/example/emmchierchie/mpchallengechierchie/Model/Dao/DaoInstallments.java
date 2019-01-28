package com.example.emmchierchie.mpchallengechierchie.Model.Dao;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Installment;
import com.example.emmchierchie.mpchallengechierchie.Utils.ResultListener;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaoInstallments {

    // mediante Retrofit obtengo una manera de descargar objetos JSON con información de APIs REST
    private Retrofit retrofit;

    // hago los calls a través de la interfaz del Service
    private DaoService service;

    // necesito construir la URL con la Base + la key de permito par bajar los datos
    private String key = "444a9ef5-8a6b-429f-abdf-587639155d88";
    private String baseURL = "https://api.mercadopago.com/v1/";

    // convierto el objeto JSON en un objeto JAVA gracias a la librería Gson
    public DaoInstallments() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        // creo la conexión
        retrofit = builder.client(httpClient.build()).build();
        service = retrofit.create(DaoService.class);
    }

    // necesito de éste método para bajar datos a través del service, en este caso una lista Installments
    public void getAllInstallments(final ResultListener<List<Installment>> listener, Float amount, String methodsId, String issuerId) {
        Call<List<Installment>> retrofitListener = service.getInstallments(key, amount, methodsId, issuerId);
        retrofitListener.enqueue( new Callback<List<Installment>>() {
            @Override
            public void onResponse(Call<List<Installment>> call, Response<List<Installment>> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<List<Installment>> call, Throwable t) {
                listener.finish(new ArrayList<Installment>());
            }
        } );
    }
}
