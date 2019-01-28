package com.example.emmchierchie.mpchallengechierchie.View.Activities;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Issuer;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.PayerCost;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.PaymentMethods;
import com.example.emmchierchie.mpchallengechierchie.Model.Pojo.Purchase;
import com.example.emmchierchie.mpchallengechierchie.R;
import com.example.emmchierchie.mpchallengechierchie.View.Fragments.FragmentAmount;
import com.example.emmchierchie.mpchallengechierchie.View.Fragments.FragmentInstallment;
import com.example.emmchierchie.mpchallengechierchie.View.Fragments.FragmentIssuer;
import com.example.emmchierchie.mpchallengechierchie.View.Fragments.FragmentPaymentMethods;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.emmchierchie.mpchallengechierchie.R.*;
import static com.example.emmchierchie.mpchallengechierchie.R.id.*;

public class ActivityHome extends AppCompatActivity implements FragmentAmount.AmountListener,
        FragmentPaymentMethods.MethodListener, FragmentIssuer.IssuerListener, FragmentInstallment.InstalmentListener {

    // Utilizo la librería ButterKnife para llamar a vista e inicializarla en pocas lineas
    @BindView(countBar)
    LinearLayout navigation;

    @BindView(id.totalAmount)
    TextView totalAmount;

    @BindView(id.toolbar)
    Toolbar toolbar;

    private FragmentManager fragmentManager;

    // creo un objeto Purchase en la activity para ir guardando los valores de cada paso
    private Purchase purchase = new Purchase();
    private String dialogInfo;

    // inicializo los ID de cada fragment para setear el backButton
    public final static Integer METHOD_BUTTON_BACK = 0;
    public final static Integer ISSUER_BUTTON_BACK = 1;
    public final static Integer INSTALLMENT_BUTTON_BACK = 2;
    public final static Integer DIALOG_BUTTON_BACK = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        // buena práctica es llamar métodos en la vista y modular el desarrollo fuera del OnCreate
        loadFragmentAmount();
        initToolbar();
    }

    // creo un método genérico para cargar fragments en el container de fragments de la activity.
    public void loadFragments(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer,fragment);
        fragmentTransaction.commit();
    }

    // ActivityHome inicia con el fragment donde seteamos el importe inicial a pagar
    public void loadFragmentAmount() {
        FragmentAmount fragmentAmount = new FragmentAmount();
        loadFragments(fragmentAmount);
    }

    // envío el monto seteado en el fragment actual por una interfaz para que la activity envíe esta info al siguiente fragment.
    // Al pasar por el activity, los valores se van cargando en el objeto Purchase.

    //de la primer pantalla (fragment) envío el monto al segundo.
    @Override
    public void sendAmountData(Float amount) {
        purchase.setAmount(amount);
        totalAmount.setText(amount.toString());
        FragmentPaymentMethods fragmentPaymentMethods = new FragmentPaymentMethods();
        Bundle bundle = new Bundle();
        bundle.putFloat("amount", amount);
        fragmentPaymentMethods.setArguments(bundle);
        loadFragments(fragmentPaymentMethods);
    }

    // del segundo fragment envío el método seleccionado al tercero.
    @Override
    public void sendMethodsData(PaymentMethods method) {
        purchase.setMethod(method);
        FragmentIssuer fragmentIssuer = new FragmentIssuer();
        Bundle bundle = new Bundle();
        bundle.putString("methodID", purchase.getMethod().getMethodId());
        fragmentIssuer.setArguments(bundle);
        loadFragments(fragmentIssuer);
    }

    // del tercer fragment, envío el monto, el método seleccionado y el ID del banco al cuarto fragment
    // algunas tarjetas de crédito no estan asociadas a banco, por lo que debo consultar que al enviar la info,
    // el banco no llegue nulo
    @Override
    public void sendIssuersData(Issuer bank) {
        purchase.setBank(bank);
        FragmentInstallment fragmentInstallment = new FragmentInstallment();
        Bundle bundle = new Bundle();
        bundle.putFloat("amount", purchase.getAmount());
        bundle.putString("methodID", purchase.getMethod().getMethodId());
        if (purchase.getBank() != null) {
            bundle.putString("bankID", purchase.getBank().getIssuerdId());
        }
        fragmentInstallment.setArguments(bundle);
        loadFragments(fragmentInstallment);
    }

    // del cuarto fragment, envío toda la info cargada en el objeto Purchase de vuelta al primer fragment, incluyendo
    // el mensaje de los planes de cuota. Para que funcione, el ID banco no debe llegar nulo.
    @Override
    public void sendTotalPurchaseData(PayerCost payerCost) {
        purchase.setPayerCost( payerCost );
        FragmentAmount fragmentAmount = new FragmentAmount();
        Bundle bundle = new Bundle();
        bundle.putFloat( "amount", purchase.getAmount() );
        bundle.putString( "methodID", purchase.getMethod().getName());
        if (purchase.getBank() != null) {
            bundle.putString( "bankID", purchase.getBank().getName());
        }
        bundle.putString( "message", purchase.getPayerCost().getRecommended_message() );
        bundle.putString( "dialogInfo", dialogInfo );
        fragmentAmount.setArguments( bundle );
        loadFragments(fragmentAmount);
    }

    // toolbar
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    // creo el menú del toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    // seteo el ícono para ingresar a mi perfil
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case getProfile:
                Intent intent = new Intent(this, ActivityAbout.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // obtengo los ID de cada fragment desde la interfaz de cada uno al clickear el backButton.
    // Inicio los ID en constantes. Volver a la pantalla anterior significa devolver el valor anterior guardado
    @Override
    public void setBackButtons(Integer buttonId) {
        switch (buttonId) {
            case 0:
                resetAll();
                break;
            case 1:
                backToMethods();
                break;
            case 2:
                backToIssuers();
                break;
            case 3:
                backToInstallments();
                break;
            default:
                break;
        }
    }

    // al primer fragment vuelvo solo reseteando a 0 la info del objeto Purchase
    // vuelvo al segundo fragment, vuelvo devolviendo el método seleccionado
    public void backToMethods() {
        FragmentPaymentMethods fragmentPaymentMethod = new FragmentPaymentMethods();
        Bundle bundleIssuers = new Bundle();
        bundleIssuers.putString("methodID", purchase.getMethod().getMethodId());
        fragmentPaymentMethod.setArguments(bundleIssuers);
        loadFragments(fragmentPaymentMethod);
    }

    // para volver al tercer fragment, mantengo el método seleccionado en el segundo, y siempre que no sea nulo, devuelvo el ID del manco
    public void backToIssuers() {
        if (purchase.getBank() != null) {
            FragmentIssuer fragmentIssuer = new FragmentIssuer();
            Bundle bundleInstallment = new Bundle();
            bundleInstallment.putString( "methodID", purchase.getMethod().getMethodId() );
            bundleInstallment.putString( "bankID", purchase.getBank().getIssuerdId() );
            fragmentIssuer.setArguments( bundleInstallment );
            loadFragments( fragmentIssuer );
        } else {
            backToMethods();
        }
    }

    // para volver del diálogo del primer fragment al ultimo fragment, mantengo todos los datos del objeto Purchase
    // cuidando que el ID del banco no sea nulo
    public void backToInstallments() {
        FragmentInstallment fragmentInstallment = new FragmentInstallment();
        Bundle dialog = new Bundle();
        dialog.putFloat( "amount", purchase.getAmount() );
        dialog.putString("methodID", purchase.getMethod().getMethodId());
        if (purchase.getBank() != null) {
            dialog.putString( "bankID", purchase.getBank().getIssuerdId());
        }
        dialog.putString( "message", purchase.getPayerCost().getRecommended_message());
        fragmentInstallment.setArguments(dialog);
        loadFragments(fragmentInstallment);
    }

    // reseteo todos los valores de Purchase a 0, incluyendo el importe en el contador de la vista
    @Override
    public void resetAll() {
        purchase.setAmount(0.0f);
        purchase.setMethod(null);
        if (purchase.getBank() != null) {
            purchase.setBank(null);
        }
        purchase.setPayerCost(null);
        totalAmount.setText("0.00");
        FragmentAmount fragmentAmount = new FragmentAmount();
        loadFragments(fragmentAmount);
    }
}
