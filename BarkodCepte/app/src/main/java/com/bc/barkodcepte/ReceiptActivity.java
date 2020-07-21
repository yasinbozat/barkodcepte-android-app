package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ReceiptActivity extends AppCompatActivity {
    ArrayList list;
    TextView receipt_Toplam,receipt_Kar;
    Button receipt_Sell;
    ListView lv_View;
    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public ArrayList<String> fiyat_array = new ArrayList<String>();
    public ArrayList<String> urun_array = new ArrayList<String>();
    double kar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        receipt_Toplam = findViewById(R.id.receipt_Toplam);
        receipt_Kar = findViewById(R.id.receipt_Kar);
        receipt_Sell = findViewById(R.id.receipt_Sell);
        lv_View = findViewById(R.id.lv_receip);


        Intent i = getIntent();
        double toplam = i.getDoubleExtra("toplam", 0);
        double kar = i.getDoubleExtra("kar", 0);



        receipt_Toplam.setText(toplam + "");
        receipt_Kar.setText(kar+"");
        Listele();
        receipt_Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReceiptActivity.this, SellProductActivity.class);
                finish();
                startActivity(i);
            }
        });


    }

    public void Listele() {


            ArrayList<String> list1 = new ArrayList<String>();
            Intent i = getIntent();
            list1 = i.getStringArrayListExtra("ArrayList_1");

            try {
                ArrayList list = new ArrayList<HashMap<String, String>>();
                ListViewAdapter adapter = new ListViewAdapter(ReceiptActivity.this, list);
                for (int x = 0; x<list1.size();x++)
                {
                    urun_array.add(list1.get(x).substring(0,list1.get(x).indexOf("-")));
                    fiyat_array.add(list1.get(x).substring(list1.get(x).indexOf("-")+1));
                }

                for (int x = 0; x < list1.size(); x++) {
                    HashMap<String, String> hashmap = new HashMap<String, String>();
                    hashmap.put(FIRST_COLUMN, urun_array.get(x));
                    hashmap.put(SECOND_COLUMN, fiyat_array.get(x));
                    list.add(hashmap);
                }
                lv_View.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}
