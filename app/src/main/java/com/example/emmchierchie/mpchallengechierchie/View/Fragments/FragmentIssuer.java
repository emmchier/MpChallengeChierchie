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
import android.widget.Toast;
import com.example.emmchierchie.mpchallengechierchie.Controller.Controller;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Issuer;
import com.example.emmchierchie.mpchallengechierchie.Utils.ResultListener;
import com.example.emmchierchie.mpchallengechierchie.R;
import com.example.emmchierchie.mpchallengechierchie.View.Activities.ActivityHome;
import com.example.emmchierchie.mpchallengechierchie.View.Adapters.AdapterRVIssuer;
import java.util.List;

public class FragmentIssuer extends Fragment implements AdapterRVIssuer.IssuerAdapterListener{

    // esta lógica la explico similar en el FragmentInstallments. Cada fragment necesita diferentes datos a mantener
    private RecyclerView recyclerViewIssuer;
    private AdapterRVIssuer adapter;
    private IssuerListener listener;

    private String methodId;
    private Issuer noBank;

    private ImageButton backButton;
    private ImageButton closeButton;
    private TextView announce;

    public FragmentIssuer() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        this.listener = (IssuerListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_issuer, container, false );

        setValidateResults();
        innitViews(view);
        setRecyclerView(view);
        setBackButton();
        setCloseButton();

        return view;
    }
    public void innitViews(View view) {
        recyclerViewIssuer = view.findViewById(R.id.recyclerViewIssuer);
        backButton = view.findViewById(R.id.backButton);
        closeButton = view.findViewById(R.id.closeButton);

        announce = view.findViewById(R.id.textViewAnnounce);
        announce.setText(R.string.announceIssuer);
    }

    public void setRecyclerView(View v) {
        adapter = new AdapterRVIssuer(this, getContext());
        recyclerViewIssuer.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewIssuer.setLayoutManager(layoutManager);
        recyclerViewIssuer.setHasFixedSize(true);

        loadIssuerList();
    }

    private void loadIssuerList(){
        Controller controller = new Controller(getContext());
        controller.getIssuerResults(new ResultListener<List<Issuer>>() {
            @Override
            public void finish(List<Issuer> results) {
                if (results.isEmpty()) {
                    listener.sendIssuersData(noBank);
                } else {
                    adapter.refreshIssuers(results);
                }
            }
        }, methodId);
    }

    public void setValidateResults() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            methodId = bundle.getString("methodID");
        } else {
            Toast.makeText( getContext(), R.string.error, Toast.LENGTH_SHORT ).show();
        }
    }

    public void setBackButton() {
        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setBackButtons(ActivityHome.ISSUER_BUTTON_BACK);
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
    public void issuerSelected(Issuer bank) {
        listener.sendIssuersData(bank);
    }

    public interface IssuerListener {
        void sendIssuersData(Issuer bank);
        void setBackButtons(Integer backID);
    }
}
