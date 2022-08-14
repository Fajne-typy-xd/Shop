package com.fajne_typy_xd.shop;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button btnStartscan;
    FloatingActionButton btnStartAdd;
    FloatingActionButton btnactivityAddAccept;
    FloatingActionButton btmActivityAddHome;
    Switch switchLimitedSale;
    EditText productNameAdd;
    EditText productPriceAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartscan = findViewById(R.id.btn_scan);
        btnStartscan.setOnClickListener(v ->
        {
            scanCode();
        });
        btnStartAdd = findViewById(R.id.btn_add);
        btnStartAdd.setOnClickListener(v ->
        {
            addCode();
        });

    }

    private void goHome() {
        switchLimitedSale.setChecked(false);
        productNameAdd.setText("");
        productPriceAdd.setText("");

        setContentView(R.layout.activity_main);
        btnStartscan = findViewById(R.id.btn_scan);
        btnStartscan.setOnClickListener(v ->
        {
            scanCode();
        });
        btnStartAdd = findViewById(R.id.btn_add);
        btnStartAdd.setOnClickListener(v ->
        {
            addCode();
        });
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Podgłośnij aby włączyć latarkę");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Wynik");
            builder.setMessage(result.getContents());

            if (products.containsKey(result.getContents())) {
                builder.setMessage("Nazwa: " + Objects.requireNonNull(products.get(result.getContents())).name);
                builder.setMessage("Cena: " + Objects.requireNonNull(products.get(result.getContents())).price);

                builder.setPositiveButton("Dodaj do koszyka", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

                builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            } else {
                builder.setMessage("Brak przedmiotu w cenniku, dodaj go");

                builder.setPositiveButton("Dodaj cenę", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).show();

                builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }


        }
    });

    private void addCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Podgłośnij aby włączyć latarkę");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barAddLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barAddLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            setContentView(R.layout.activity_add);
            switchLimitedSale = findViewById(R.id.switchLimitedSale);
            productNameAdd = findViewById(R.id.productNameAdd);
            productPriceAdd = findViewById(R.id.productPriceAdd);
            btnactivityAddAccept = findViewById(R.id.btnactivityAddAccept);
            btnactivityAddAccept.setOnClickListener(v ->
            {
                products.put(result.getContents(), new Product(productPriceAdd.getText(), productNameAdd.getText().toString(), switchLimitedSale.getShowText()));
                goHome();

            });
            btmActivityAddHome = findViewById(R.id.btmActivityAddHome);
            btmActivityAddHome.setOnClickListener(v ->
            {
                goHome();
            });
        }
    });

    public static HashMap<String, Product> products = new HashMap<>();
    public static HashMap<Product, Double> shoppingList = new HashMap<>();
}