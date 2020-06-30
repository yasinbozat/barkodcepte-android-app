package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MyStoreActivity extends AppCompatActivity {

    Button btn_addProduct, btn_viewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        btn_addProduct = findViewById(R.id.btn_addProduct);
        btn_viewProduct = findViewById(R.id.btn_viewProduct);

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MyStoreActivity.this,AddProductActivity.class);
                startActivity(i);

            }
        });

        btn_viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MyStoreActivity.this,ViewProductActivity.class);
                startActivity(i);

            }
        });

    }
}