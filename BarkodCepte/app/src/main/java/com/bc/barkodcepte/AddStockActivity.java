package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddStockActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String > adapter;
    EditText stok;
    Button kaydet;
    String urunadi=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        kaydet =  findViewById(R.id.btn_Kaydet);
        stok  =    findViewById(R.id.edit_stok);
        lv = findViewById(R.id.stok_list);
        final ArrayList<String> list = new ArrayList<String>();
        adapter= new ArrayAdapter<String>(AddStockActivity.this,android.R.layout.simple_list_item_1,list);


        Database d = new Database(AddStockActivity.this);
        SQLiteDatabase db = d.getReadableDatabase();
        String esittir = "=";
        String sorgu = "SELECT urunAdi,urunStok FROM urunler ";
        final Cursor c = db.rawQuery(sorgu, null);
        while (c.moveToNext()) {
            adapter.add(c.getString(0)+" "+c.getString(1));

            adapter.notifyDataSetChanged();

        }
        lv.setAdapter(adapter);
        c.close();

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Database d = new Database(AddStockActivity.this);
                    SQLiteDatabase db = d.getReadableDatabase();
                    String esittir = "=";

                    String sorgu1 = "UPDATE urunler SET urunStok"+esittir+stok.getText()+" WHERE urunAdi" + esittir +"'"+ urunadi+"'";
                    db.execSQL(sorgu1);
                    Toast.makeText(getApplicationContext(),"STOK GÜNCELLENDİ",Toast.LENGTH_SHORT).show();
                    

                }
                catch (Exception e)
                {e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"BİR SORUN OLUŞTU",Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                urunadi = adapter.getItem(position).substring(0,adapter.getItem(position).indexOf(' '));
                Database d = new Database(AddStockActivity.this);
                SQLiteDatabase db = d.getReadableDatabase();
                String esittir = "=";
                String sorgu = "SELECT urunStok FROM urunler WHERE urunAdi='"+urunadi+"'";
                Cursor c1 = db.rawQuery(sorgu, null);
                if (c1!=null) {
                    c1.moveToFirst();
                    stok.setText(c1.getString(0));
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }
}
