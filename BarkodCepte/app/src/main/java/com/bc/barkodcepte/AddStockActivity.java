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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        kaydet =  findViewById(R.id.btn_Kaydet);
        stok  =    findViewById(R.id.edit_stok);
        final ArrayList<String> list = new ArrayList<String>();
        adapter= new ArrayAdapter<String>(AddStockActivity.this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  try {
                    Database d = new Database(AddStockActivity.this);
                    SQLiteDatabase db = d.getReadableDatabase();
                    String esittir = "=";
                    String sorgu = "SELECT urunAdi,urunFiyat FROM urunler WHERE barkod" + esittir + stok.getText().toString();
                    Cursor c = db.rawQuery(sorgu, null);
                    if (c != null) {
                        c.moveToFirst();

                        urun_isim.setText(c.getString(0));
                        urun_fiyat.setText(c.getString(1));
                        adapter.add(c.getString(0));
                        urun_toplam += c.getFloat(1);
                        float a =  c.getFloat(1);
                        adapter_toplam.add(String.valueOf(a));
                        toplam.setText(urun_toplam+"");
                    }

                    c.close();

                }
                catch (Exception e)
                {e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"KAYITSIZ ÜRÜN",Toast.LENGTH_SHORT).show();
                } */
            }
        });

        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                stok.setText(lv.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
