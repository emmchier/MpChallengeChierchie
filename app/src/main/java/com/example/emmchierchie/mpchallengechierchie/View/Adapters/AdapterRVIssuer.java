package com.example.emmchierchie.mpchallengechierchie.View.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Issuer;
import com.example.emmchierchie.mpchallengechierchie.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterRVIssuer extends RecyclerView.Adapter{

    // toda esta lógica la explico en el AdapterInstallments

    public Context context;

    private Issuer issuer;
    private List<Issuer> issuerList;
    public IssuerAdapterListener listener;

    public AdapterRVIssuer(IssuerAdapterListener listener, Context context) {
        this.issuerList = new ArrayList<>();
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View cellView = layoutInflater.inflate( R.layout.cell_banks,parent,false);
        ViewHolder viewHolder = new ViewHolder(cellView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        issuer = issuerList.get(position);
        ViewHolder vh = (ViewHolder) holder;
        vh.loadAllIssuers(issuer);
    }

    @Override
    public int getItemCount() {
        return issuerList.size();
    }

    public void refreshIssuers(List<Issuer> list) {
        if(list != null){
            issuerList.clear();
            issuerList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewBank;
        private TextView textViewBankName;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewBank = itemView.findViewById(R.id.imageViewBank);
            textViewBankName = itemView.findViewById(R.id.textViewBankName);
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.issuerSelected(issuerList.get(getAdapterPosition()));
                }
            } );
        }

        public void loadAllIssuers(Issuer issuer) {
            Glide.with(itemView.getContext()).load(issuer.getSecure_thumbnail()).into(imageViewBank);
            textViewBankName.setText(issuer.getName());
        }
    }

    public interface IssuerAdapterListener {
        void issuerSelected(Issuer issuerName);
    }
}
