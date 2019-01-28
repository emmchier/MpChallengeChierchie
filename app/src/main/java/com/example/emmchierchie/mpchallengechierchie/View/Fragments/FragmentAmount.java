package com.example.emmchierchie.mpchallengechierchie.View.Fragments;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Purchase;
import com.example.emmchierchie.mpchallengechierchie.R;
import com.example.emmchierchie.mpchallengechierchie.View.Activities.ActivityHome;

public class FragmentAmount extends Fragment {

    // inicio variables

    private AmountListener listener;
    public static String selectedMode;

    private EditText editTextAmount;
    private ImageButton buttonNext;

    private Float amountView;
    private Float finalAmount;

    private String methodID;
    private String issuerID;
    private String reccommendedMessage;

    private Dialog dialog;
    private TextView amountDialog;
    private TextView methodDialog;
    private TextView issuerDialog;
    private TextView installmentDialog;

    private Purchase purchase;

    public FragmentAmount() {
    }

    // le paso al fragment el contexto al que pertenece y le permito comunicarse entre activities y otrs fragments
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (AmountListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_amount, container, false );

        innitViews(view);
        showPurchase();

        return view;
    }

    public void innitViews(View view) {
        editTextAmount = view.findViewById(R.id.editTextAmount);

        // seteo el botón que guarda el importe seleccionado
        buttonNext = view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextButton();
            }
        } );
    }

    public void setNextButton() {

        // valido la infor para que al escribir en el campo no haya errores y funcione bien
        if (!editTextAmount.getText().toString().trim().isEmpty()) {
            amountView = Float.parseFloat(editTextAmount.getText().toString());
            listener.sendAmountData(amountView);
        } else {
            editTextAmount.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            showCustomToast();
        }
    }

    // valido que llegue toda la info desde el activity, siempre que el ID de banco no sea nulo
    public void showPurchase() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            finalAmount = bundle.getFloat( "amount" );
            methodID = bundle.getString( "methodID" );
            if (bundle.getString("bankID") != null) {

            } else {
                issuerID = bundle.getString("bankID");
            }
            reccommendedMessage = bundle.getString( "message" );

            // con éste "modo" activo el diálogo que muestra la info total de la compra
            selectedMode = bundle.getString( "dialogInfo" );
            setDialog();
        }
    }

    // seteo la info de la compra en un diálogo
    private void setDialog() {
        if (selectedMode != null) {
            Toast.makeText( getContext(), R.string.toast, Toast.LENGTH_SHORT ).show();
        } else {
            dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            dialog.setCancelable(true);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            amountDialog = dialog.findViewById(R.id.textViewAmountDialog);
            methodDialog = dialog.findViewById(R.id.textViewMethodDialog);
            issuerDialog = dialog.findViewById(R.id.textViewIssuerDialog);
            installmentDialog = dialog.findViewById(R.id.textViewMessageDialog);

            amountDialog.setText(finalAmount.toString());
            methodDialog.setText(methodID);
            issuerDialog.setText(issuerID);
            installmentDialog.setText(reccommendedMessage);

            (dialog.findViewById(R.id.btnConfirm)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.resetAll();
                    dialog.dismiss();
                }
            });
            (dialog.findViewById(R.id.btnCancel)).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setBackButtons(ActivityHome.DIALOG_BUTTON_BACK);
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }
    }

    // muestro el toast pra validar que no falten valores en el input del primer fragment
    public void showCustomToast() {

        View layout = getLayoutInflater().inflate( R.layout.toast, (ViewGroup) getView().findViewById(R.id.custom_toast_id));
        TextView text = layout.findViewById(R.id.text);
        text.setTextColor(Color.WHITE);
        text.setText(R.string.validate);
        CardView lyt_card = layout.findViewById(R.id.toastCard);
        lyt_card.setCardBackgroundColor(getResources().getColor(R.color.toast));

        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    // escuchador del fragmentAmount
    public interface AmountListener {
        void sendAmountData(Float amount);
        void setBackButtons(Integer backID);
        void resetAll();
    }
}

