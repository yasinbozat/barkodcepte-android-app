package com.bc.barkodcepte;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
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
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {

    public ArrayAdapter<String> adapter;
    ListView lv_View;
    SearchView viewProduct_search;
    String barkod=null;
    EditText viewProduct_Stock,viewProduct_Price,viewProduct_Name,viewProduct_Barcode,viewProduct_BuyPrice;
    Button viewProduct_Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        lv_View = findViewById(R.id.lv_View_stock_follow);
        viewProduct_search = findViewById(R.id.viewProduct_search);
        viewProduct_Stock = findViewById(R.id.viewProduct_Stock);
        viewProduct_Price = findViewById(R.id.viewProduct_Price);
        viewProduct_Name = findViewById(R.id.viewProduct_Name);
        viewProduct_Barcode = findViewById(R.id.viewProduct_Barcode);
        viewProduct_Save = findViewById(R.id.viewProduct_Save);
        viewProduct_BuyPrice = findViewById(R.id.viewProduct_BuyPrice);

        final ArrayAdapter<String>[] finalAdapter = new ArrayAdapter[]{adapter};
        viewProduct_search.onActionViewExpanded(); //new Added line
        viewProduct_search.setIconifiedByDefault(false);
        viewProduct_search.setQueryHint("Ürün İsmi veya Barkod giriniz");

        viewProduct_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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

        Listele();

        lv_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                barkod = adapter.getItem(position).substring(0,adapter.getItem(position).indexOf("|")).trim();
                Database d = new Database(ViewProductActivity.this);
                SQLiteDatabase db = d.getReadableDatabase();
                String sorgu = "SELECT barkod,urunAdi,urunFiyat,alis,urunStok FROM urunler WHERE barkod='"+barkod+"'";
                Cursor c1 = db.rawQuery(sorgu, null);
                if (c1!=null) {
                    c1.moveToFirst();
                    viewProduct_Barcode.setText(c1.getString(0));
                    viewProduct_Name.setText(c1.getString(1));
                    viewProduct_Price.setText(c1.getString(2));
                    viewProduct_BuyPrice.setText(c1.getString(3));
                    viewProduct_Stock.setText(c1.getString(4));
                    adapter.notifyDataSetChanged();
                }

            }
        });


        lv_View.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewProductActivity.this);
                builder.setTitle("Uyarı!");
                builder.setMessage("Seçtiğiniz Ürün Silinsin Mi?");
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                try {

                    String deger = adapter.getItem(position).substring(0,adapter.getItem(position).indexOf("|")).trim();
                    Database db = new Database(ViewProductActivity.this);
                    db.Delete(deger);
                    Log.d("DEBUG","Ürün Başarıyla Silindi");
                    Toast.makeText(getApplicationContext(),"Ürün Başarıyla Silindi",Toast.LENGTH_LONG).show();
                    Listele();

                } catch (Exception e) {
                    Log.d("DEBUG", "" + e);
                }
                    }
                });
                builder.show();

                return true;
            }
        });

        viewProduct_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Database d = new Database(ViewProductActivity.this);
                    SQLiteDatabase db = d.getReadableDatabase();

                    String sorgu1 = "UPDATE urunler SET urunStok = "+viewProduct_Stock.getText()+", " +
                     "urunAdi = '"+viewProduct_Name.getText()+"', barkod ='"+viewProduct_Barcode.getText()+"'," +
                     " alis='"+viewProduct_BuyPrice.getText()+"', urunFiyat ="+viewProduct_Price.getText()+" " +
                     "WHERE barkod = '"+ barkod+"'";

                    db.execSQL(sorgu1);
                    Toast.makeText(getApplicationContext(),"Ürün Güncellendi",Toast.LENGTH_LONG).show();
                    Listele();

                }
                catch (Exception e)
                {e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Bir Sorun Oluştu Değişiklikleri Kontrol Ediniz!",
                                                                                    Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

    }
    public void Listele(){

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
        Database d = new Database(ViewProductActivity.this);
        SQLiteDatabase db = d.getReadableDatabase();
        String sorgu = "SELECT barkod,urunAdi,urunFiyat,alis,urunStok FROM urunler ";
        final Cursor c = db.rawQuery(sorgu, null);
        while (c.moveToNext()) {
            adapter.add(c.getString(0)+"    |   "+c.getString(1)+"  |   Satış : "+c.getString(2)+" TL   |   Alış: "+c.getString(3)+" TL   |   "+c.getString(4)+" Adet");

            adapter.notifyDataSetChanged();

        }
        lv_View.setAdapter(adapter);

        c.close();
        viewProduct_search.clearFocus();
    }
}