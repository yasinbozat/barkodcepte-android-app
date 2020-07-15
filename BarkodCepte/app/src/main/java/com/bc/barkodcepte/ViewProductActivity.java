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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {

    public ArrayAdapter<String> adapter;
    ListView lv_View;
    SearchView viewProduct_search;
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
                    Log.d("DEBUG","Silindi");
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
        String sorgu = "SELECT barkod,urunAdi,urunFiyat FROM urunler ";
        final Cursor c = db.rawQuery(sorgu, null);
        while (c.moveToNext()) {
            adapter.add(c.getString(0)+"    |   "+c.getString(1)+"  |   "+c.getString(2)+" TL");

            adapter.notifyDataSetChanged();

        }
        lv_View.setAdapter(adapter);

        c.close();
        viewProduct_search.clearFocus();
    }
}