package com.example.emmchierchie.mpchallengechierchie.View.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.emmchierchie.mpchallengechierchie.Controller.Controller;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.PaymentMethods;
import com.example.emmchierchie.mpchallengechierchie.Utils.ResultListener;
import com.example.emmchierchie.mpchallengechierchie.R;
import com.example.emmchierchie.mpchallengechierchie.View.Activities.ActivityHome;
import com.example.emmchierchie.mpchallengechierchie.View.Adapters.AdapterRVPaymentMethods;
import java.util.List;

public class FragmentPaymentMethods extends Fragment implements AdapterRVPaymentMethods.MethodAdapterListener {

    private RecyclerView recyclerViewPaymentMethods;
    private AdapterRVPaymentMethods adapter;
    private MethodListener listener;

    // seteo al "tipo de método" que necesito iniciando ésta variable que le paso al filtro del Controller
    private String methodTypeId = "credit_card";

    private ImageButton fabBackButton;
    private ImageButton closeButton;
    private TextView announce;

    public FragmentPaymentMethods() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        this.listener = (MethodListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_payment_methods, container, false );

        innitViews(view);
        setRecyclerView(view);
        setBackButton();
        setCloseButton();

        return view;
    }

    public void innitViews(View view) {
        recyclerViewPaymentMethods = view.findViewById(R.id.recyclerViewPaymentMethods);
        fabBackButton = view.findViewById(R.id.backButton);
        closeButton = view.findViewById(R.id.closeButton);

        announce = view.findViewById(R.id.textViewAnnounce);
        announce.setText(R.string.announceMethod);
    }

    public void setRecyclerView(View v) {
        adapter = new AdapterRVPaymentMethods(this, getContext());
        recyclerViewPaymentMethods.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewPaymentMethods.setLayoutManager(layoutManager);
        recyclerViewPaymentMethods.setHasFixedSize(true);

        loadMethodList();
    }

    private void loadMethodList(){
        Controller controller = new Controller(getContext());
        controller.getMethodResults(new ResultListener<List<PaymentMethods>>() {
            @Override
            public void finish(List<PaymentMethods> results) {
                adapter.refreshMethods(results);
            }
        }, methodTypeId);
    }

    public void setBackButton() {
        fabBackButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setBackButtons(ActivityHome.METHOD_BUTTON_BACK );
            }
        } );
    }

    public void setCloseButton() {
        closeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setBackButtons(ActivityHome.METHOD_BUTTON_BACK);
            }
        } );
    }

    @Override
    public void methodSelected(PaymentMethods method) {
        listener.sendMethodsData(method);
    }

    public interface MethodListener {
        void sendMethodsData(PaymentMethods paymentMethods);
        void setBackButtons(Integer backID);
    }
}
