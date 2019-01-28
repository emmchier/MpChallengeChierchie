package com.example.emmchierchie.mpchallengechierchie.View.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.emmchierchie.mpchallengechierchie.Controller.Controller;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Installment;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.PayerCost;
import com.example.emmchierchie.mpchallengechierchie.Utils.ResultListener;
import com.example.emmchierchie.mpchallengechierchie.R;
import com.example.emmchierchie.mpchallengechierchie.View.Activities.ActivityHome;
import com.example.emmchierchie.mpchallengechierchie.View.Adapters.AdapterRVInstallment;
import java.util.List;

public class FragmentInstallment extends Fragment implements AdapterRVInstallment.PayerCostsAdapterListener{

    private RecyclerView recyclerViewInstallment;
    private AdapterRVInstallment adapter;
    private InstalmentListener listener;

    private Float amountCount;
    private String methodID;
    private String issuerId;

    private ImageButton backButton;
    private ImageButton closeButton;
    private TextView announce;

    public FragmentInstallment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        this.listener = (InstalmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_installment, container, false );

        // inicio vistas, valido resultados que lleguen bien y seteo botones del fragment

        setValidateResults();
        innitViews(view);
        setRecyclerView(view);
        setBackButton();
        setCloseButton();

        return view;
    }
    public void innitViews(View view) {
        recyclerViewInstallment = view.findViewById(R.id.recyclerViewInstallment);
        backButton = view.findViewById(R.id.backButton);
        closeButton = view.findViewById(R.id.closeButton);

        // seteo texto del anuncio del paso en posición
        announce = view.findViewById(R.id.textViewAnnounce);
        announce.setText(R.string.announceInstallments);
    }

    // lleno la lista del recycler a través de su adapter
    public void setRecyclerView(View v) {
        adapter = new AdapterRVInstallment(this, getContext());
        recyclerViewInstallment.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewInstallment.setLayoutManager(layoutManager);

        //y cargo la lista completa
        loadInstallmentList();
    }

    // le pido al controller un objeto Installments, que se dependerá del monto, método y bánco seleccionado.
    // cargo este objeto con una lista de planes de Cuotas
    private void loadInstallmentList(){
        Controller controller = new Controller(getContext());
        controller.getInstallmentResults(new ResultListener<List<Installment>>() {
            @Override
            public void finish(List<Installment> results) {
                adapter.refreshPayerCosts(results.get(0).getPayer_costs());
            }
        }, amountCount, methodID, issuerId);
    }

    // obtengo lo que viene del fragment anterior
    public void setValidateResults() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            amountCount = bundle.getFloat("amount");
            methodID = bundle.getString("methodID");
            issuerId = bundle.getString("bankID");
        } else {
            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT ).show();
        }
    }

    // backButton obteniendo enviando el ID del fragment desde el activity
    public void setBackButton() {
        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setBackButtons(ActivityHome.INSTALLMENT_BUTTON_BACK );
            }
        } );
    }

    // reseteo a cero toda la info al cancelar la operación
    public void setCloseButton() {
        closeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setBackButtons(ActivityHome.METHOD_BUTTON_BACK);
            }
        } );
    }

    // envío por la interfaz un objeto PayerCost entero. Lo mismo hago en el resto de los fragments
    @Override
    public void payerCostSelected(PayerCost payerCost) {
        listener.sendTotalPurchaseData(payerCost);
    }

    public interface InstalmentListener {
        void sendTotalPurchaseData(PayerCost payerCost);
        void setBackButtons(Integer backID);
    }
}
