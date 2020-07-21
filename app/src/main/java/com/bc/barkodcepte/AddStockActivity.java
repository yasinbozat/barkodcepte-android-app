package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddStockActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String > adapter;
    EditText stok;
    Button kaydet;
    String barkod=null;
    TextView secilen_Urun;
    SearchView search_AddProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        kaydet =  findViewById(R.id.btn_Kaydet);
        stok  =    findViewById(R.id.edit_stok);
        lv = findViewById(R.id.stok_list);
        search_AddProduct = findViewById(R.id.search_AddProduct);
        secilen_Urun = findViewById(R.id.addStock_txv);
        final ArrayList<String> list = new ArrayList<String>();
        adapter= new ArrayAdapter<String>(AddStockActivity.this,android.R.layout.simple_list_item_1,list);

        goruntule();


        final ArrayAdapter<String>[] finalAdapter = new ArrayAdapter[]{adapter};
        search_AddProduct.onActionViewExpanded(); //new Added line
        search_AddProduct.setIconifiedByDefault(false);
        search_AddProduct.setQueryHint("Ürün İsmi veya Barkod giriniz");

        search_AddProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                // TODO Auto-generated method stub
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {

                finalAdapter[0] = adapter;

                finalAdapter[0].getFilter().filter(text);
                return false;
            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Database d = new Database(AddStockActivity.this);
                    SQLiteDatabase db = d.getReadableDatabase();
                    String sorgu1 = "UPDATE urunler SET urunStok = "+stok.getText()+" WHERE barkod = '"+ barkod+"'";
                    db.execSQL(sorgu1);
                    Toast.makeText(getApplicationContext(),"STOK GÜNCELLENDİ",Toast.LENGTH_SHORT).show();
                    goruntule();

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

                barkod = adapter.getItem(position).substring(0,adapter.getItem(position).indexOf(" - "));
                Database d = new Database(AddStockActivity.this);
                SQLiteDatabase db = d.getReadableDatabase();
                String sorgu = "SELECT urunStok FROM urunler WHERE barkod='"+barkod+"'";
                Cursor c1 = db.rawQuery(sorgu, null);
                if (c1!=null) {
                    c1.moveToFirst();
                    stok.setText(c1.getString(0));
                    adapter.notifyDataSetChanged();
                }
                String take_name = adapter.getItem(position);
                take_name = take_name.replace(adapter.getItem(position).substring(0,adapter.getItem(position).indexOf(" - ")),"");
                take_name = take_name.substring(2,take_name.length());
                take_name = take_name.substring(0,take_name.indexOf("-"));
                Log.d("TAG",  take_name);
                secilen_Urun.setText(take_name);
            }
        });
    }
    public void goruntule(){

        final ArrayList<String> list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.rgb(255,255,255));

                // Generate ListView Item using TextView
                return view;
            }
        };

        adapter.clear();
        Database d = new Database(AddStockActivity.this);
        SQLiteDatabase db = d.getReadableDatabase();
        String sorgu = "SELECT barkod,urunAdi,urunStok FROM urunler ";
        final Cursor c = db.rawQuery(sorgu, null);
        while (c.moveToNext()) {
            adapter.add(c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2));

            adapter.notifyDataSetChanged();

        }
        lv.setAdapter(adapter);

        c.close();
        search_AddProduct.clearFocus();
    }
}
