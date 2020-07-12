package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class ReceiptActivity extends AppCompatActivity {

    TextView receipt_Toplam;
    Button receipt_Sell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        receipt_Toplam = findViewById(R.id.receipt_Toplam);
        receipt_Sell = findViewById(R.id.receipt_Sell);


        Intent i=getIntent();
        double toplam=i.getDoubleExtra("toplam",0);
        receipt_Toplam.setText(toplam+"");

        receipt_Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReceiptActivity.this,SellProductActivity.class);
                finish();
                startActivity(i);
            }
        });

    }
}