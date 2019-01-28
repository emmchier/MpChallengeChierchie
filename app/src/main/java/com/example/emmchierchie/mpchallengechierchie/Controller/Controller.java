package com.example.emmchierchie.mpchallengechierchie.Controller;
import android.content.Context;
import com.example.emmchierchie.mpchallengechierchie.Model.Dao.DaoInstallments;
import com.example.emmchierchie.mpchallengechierchie.Model.Dao.DaoIssuer;
import com.example.emmchierchie.mpchallengechierchie.Model.Dao.DaoPaymentMethods;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Installment;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Issuer;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.PaymentMethods;
import com.example.emmchierchie.mpchallengechierchie.Utils.ResultListener;
import com.example.emmchierchie.mpchallengechierchie.R;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public Context context;

    public Controller(Context context) {
        this.context = context;
    }

    // a través del controlador pido al service la lista de objetos que necesito

    // pido métodos de pago
    public void getMethodResults(final ResultListener<List<PaymentMethods>> viewListener, final String methodType){
        ResultListener<List<PaymentMethods>> methodsListener = new ResultListener<List<PaymentMethods>>() {
            @Override
            public void finish(List<PaymentMethods> methodResults) {
                // si el método de pago coincide con "All", descargo todos los tipos, y sino le paso el método de filtro
                if(methodType.equals(R.string.all)){
                    viewListener.finish(methodResults);
                } else {
                    List<PaymentMethods> filteredList = creditCardFilter(methodResults,methodType);
                    viewListener.finish(filteredList);
                }
            }
        };
        //le pido al dao toda la lista de métodos de pago
        DaoPaymentMethods dao = new DaoPaymentMethods();
        dao.getAllPaymentMethods(methodsListener);
    }

    // creo un método genérico para filtrar solo por el tipo que quiero y devolver los resultados.
    // Inicio esta variable en FragmentPaymentMethods

    private List<PaymentMethods> creditCardFilter(List<PaymentMethods> methodsList, String methodType) {
        List<PaymentMethods> filteredList = new ArrayList<>();
        for (PaymentMethods method : methodsList) {
            if (method.getPayment_type_id().equals(methodType)) {
               filteredList.add(method);
            }
        }
        return filteredList;
    }

    // pido al service una lista de bancos. Necesito pasarle el id del método seleccionado
    public void getIssuerResults(final ResultListener<List<Issuer>> viewListener, String methodsId){
        ResultListener<List<Issuer>> issuerListener = new ResultListener<List<Issuer>>() {
            @Override
            public void finish(List<Issuer> issuerResults) {
                viewListener.finish(issuerResults);
            }
        };
        DaoIssuer dao = new DaoIssuer();
        dao.getAllIssuers(issuerListener, methodsId);
    }

    // pido al service una lista de Installments. Para mostrar la info debo pasarle monto, id del método + id del banco
    public void getInstallmentResults(final ResultListener<List<Installment>> viewListener, Float amount,
                                      String methodsId, String issuerId){
        ResultListener<List<Installment>> installmentListener = new ResultListener<List<Installment>>() {
            @Override
            public void finish(List<Installment> results) {
                viewListener.finish(results);
            }
        };
        DaoInstallments dao = new DaoInstallments();
        dao.getAllInstallments(installmentListener, amount, methodsId, issuerId);
    }
}

