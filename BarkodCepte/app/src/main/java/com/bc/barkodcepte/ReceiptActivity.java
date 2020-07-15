package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ReceiptActivity extends AppCompatActivity {
    ArrayList list;
    TextView receipt_Toplam;
    Button receipt_Sell;
    ListView lv_View;
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public ArrayList<String> barkod_array = new ArrayList<String>();
    public ArrayList<String> urun_array = new ArrayList<String>();
    public ArrayList<String> urunstok_array = new ArrayList<String>();
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
        list = i.getStringArrayListExtra("ArrayList_1");
        receipt_Toplam.setText(toplam+"");
        Listele();
        receipt_Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReceiptActivity.this,SellProductActivity.class);
                finish();
                startActivity(i);
            }
        });



    }
    public void Listele(){
        try {
            list=new ArrayList<HashMap<String,String>>();
            ListViewAdapter adapter=new ListViewAdapter(ReceiptActivity.this, list);

            for (int x = 0;x<list.size();x++)
            {
                HashMap<String,String> hashmap=new HashMap<String, String>();
                hashmap.put(FIRST_COLUMN, barkod_array.get(x));
                list.add(hashmap);
            }
            lv_View.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }
        catch (Exception e)
        {e.printStackTrace();}


    }
}