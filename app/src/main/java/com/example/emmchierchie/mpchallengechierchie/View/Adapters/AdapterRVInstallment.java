package com.example.emmchierchie.mpchallengechierchie.View.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.PayerCost;
import com.example.emmchierchie.mpchallengechierchie.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterRVInstallment extends RecyclerView.Adapter {

    public Context context;

    private PayerCost payerCost;
    private List<PayerCost> payerCostsList;
    private PayerCostsAdapterListener listener;

    // inicio la lista de Cuotas en el constructor, le paso el escuchador y el contexto
    public AdapterRVInstallment(PayerCostsAdapterListener listener, Context context) {
        this.payerCostsList = new ArrayList<>();
        this.listener = listener;
        this.context = context;
    }

    // info la celda del recycler y le asigno el ViewHolder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View cellView = layoutInflater.inflate( R.layout.cell_recommended_message,parent,false);
        ViewHolder viewHolder = new ViewHolder(cellView);

        return viewHolder;
    }

    // cargo el recycler con la lista de Cuotas
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        payerCost = payerCostsList.get(position);
        ViewHolder vh = (ViewHolder) holder;
        vh.loadAllPayerCosts(payerCost);
    }

    // recorro la lista de cuotas
    @Override
    public int getItemCount() {
        return payerCostsList.size();
    }

    // si la info no viene nula, limpio la lista en el fragment, la lleno de todos los datos y chequeo la info
    public void refreshPayerCosts(List<PayerCost> list) {
        if(list != null){
            payerCostsList.clear();
            payerCostsList.addAll(list);
            notifyDataSetChanged();
        }
    }

    // esta clase ViewHolder contiene la infomación de la celda a reciclar
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRecMessage;

        public ViewHolder(final View itemView) {
            super(itemView);
            // al hacer click sobre la celda se sobrescribe un método enviado desde la interfaz al activity
            textViewRecMessage = itemView.findViewById(R.id.textViewRecMessage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.payerCostSelected(payerCostsList.get(getAdapterPosition()));
                }
            } );
        }
        // seteo las vistas de la celda con la info del objeto descargado
        public void loadAllPayerCosts(PayerCost payerCost) {
            textViewRecMessage.setText(payerCost.getRecommended_message());
        }
    }

    // escuchador del fragment Installments
    public interface PayerCostsAdapterListener {
        void payerCostSelected(PayerCost payerCost);
    }
}
