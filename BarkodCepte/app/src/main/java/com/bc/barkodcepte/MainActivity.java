package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ImageButton btn_sell, btn_myStore,btn_addProduct,btn_viewProduct,btn_addStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------
      
        btn_sell = findViewById(R.id.btn_sell);
        btn_addProduct = findViewById(R.id.btn_addProduct);
        btn_viewProduct = findViewById(R.id.btn_viewProduct);
        btn_addStock = findViewById(R.id.btn_addStock);
        btn_myStore = findViewById(R.id.btn_myStore);

        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,SellProductActivity.class);
                startActivity(i);

            }
        });
        btn_myStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Çok Yakında Sizlerle",Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(MainActivity.this,MyStoreActivity.class);
                startActivity(i);*/

            }
        });
        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,AddProductActivity.class);
                startActivity(i);

            }
        });
        btn_viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,ViewProductActivity.class);
                startActivity(i);

            }
        });
        btn_addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,AddStockActivity.class);
                startActivity(i);

            }
        });

    }
}
