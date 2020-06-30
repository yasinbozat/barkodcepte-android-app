package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AddProductActivity extends AppCompatActivity {

    private Button btn_AddProduct;
    private EditText edt_ProductBardcode, edt_ProductName, edt_ProductPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        btn_AddProduct = findViewById(R.id.btn_AddProduct);
        edt_ProductBardcode = findViewById(R.id.edt_ProductBardcode);
        edt_ProductName = findViewById(R.id.edt_ProductName);
        edt_ProductPrice = findViewById(R.id.edt_ProductPrice);

        Database db = new Database(AddProductActivity.this);

        btn_AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Database db = new Database(AddProductActivity.this);
                    String productBarcode = edt_ProductBardcode.getText().toString();
                    String productName = edt_ProductName.getText().toString();
                    String productPrice = edt_ProductPrice.getText().toString();

                    db.InsertData(productBarcode, productName, productPrice);
                    Log.d("DEBUG","eklendi");
                }catch (Exception e){
                    Log.d("DEBUG", "aaaaaaaaaa"+String.valueOf(e));
                }

            }
        });


    }
}