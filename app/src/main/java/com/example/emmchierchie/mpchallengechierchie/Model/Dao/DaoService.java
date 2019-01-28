package com.example.emmchierchie.mpchallengechierchie.Model.Dao;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Issuer;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Installment;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.PaymentMethods;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DaoService {

    // realizo todos los llamados a través de Retrofit para descargar información en formato JSON
    // mediante queries construyo las URL que necesito de donde voy a descargar los datos

    // pido métodos de pago
    @GET("payment_methods")
    Call<List<PaymentMethods>> getPaymentMethods(@Query("public_key") String key);

    // pido ID de los bancos
    @GET("payment_methods/card_issuers")
    Call<List<Issuer>> getIssuers(@Query("public_key") String key,
                                  @Query("payment_method_id") String methodsId);

    // pido Installments con los mensajes de lo planes de cuota
    @GET("payment_methods/installments")
    Call<List<Installment>>getInstallments (@Query("public_key") String key,
                                      @Query("amount") Float amount,
                                      @Query("payment_method_id") String methodsId,
                                      @Query("issuer.id") String issuerId);
}

