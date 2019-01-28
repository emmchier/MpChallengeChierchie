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
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.PaymentMethods;
import com.example.emmchierchie.mpchallengechierchie.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterRVPaymentMethods extends RecyclerView.Adapter {

    // toda esta lógica la explico en el AdapterInstallments

    public Context context;
    private PaymentMethods method;
    private List<PaymentMethods> methodList;
    public MethodAdapterListener listener;

    public AdapterRVPaymentMethods(MethodAdapterListener listener, Context context) {
        this.methodList = new ArrayList<>();
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View cellView = layoutInflater.inflate( R.layout.cell_cards,parent,false);
        ViewHolder viewHolder = new ViewHolder(cellView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        method = methodList.get(position);
        ViewHolder vh = (ViewHolder) holder;
        vh.loadAllMethods(method);
    }

    @Override
    public int getItemCount() {
        return methodList.size();
    }

    public void refreshMethods(List<PaymentMethods> list) {
        if(list != null){
            methodList.clear();
            methodList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewCreditCard;
        private TextView textViewCreditCardName;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewCreditCard = itemView.findViewById(R.id.imageViewCreditCard);
            textViewCreditCardName = itemView.findViewById(R.id.textViewCreditCardName);
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.methodSelected(methodList.get(getAdapterPosition()));
                }
            } );
        }

        public void loadAllMethods(PaymentMethods paymentMethods) {
            Glide.with(itemView.getContext()).load(paymentMethods.getSecure_thumbnail()).into(imageViewCreditCard);
            textViewCreditCardName.setText(paymentMethods.getName());
        }
    }

    public interface MethodAdapterListener {
        void methodSelected(PaymentMethods method);
    }
}
