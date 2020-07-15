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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    private ArrayList<HashMap<String, String>> list;
    public ArrayAdapter<String> adapter;
    public ArrayAdapter<String> arrayAdapter;
    public ArrayList<String> barkod_array = new ArrayList<String>();
    public ArrayList<String> urun_array = new ArrayList<String>();
    public ArrayList<String> urunstok_array = new ArrayList<String>();
    ListView lv_View;
    ListView lv_View2;
    SearchView Search_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        lv_View = findViewById(R.id.lv_View_stock_follow);
        Search_product = findViewById(R.id.search_product);
        Listele();

        lv_View.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewProductActivity.this);
                builder.setTitle("BarkodCepte");
                builder.setMessage("Seçtiğiniz Ürün Silinsin Mi?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {

                            String deger = arrayAdapter.getItem(position).substring(0,arrayAdapter.getItem(position).indexOf(" "));
                            Log.d("deger", arrayAdapter.getItem(position).substring(0,arrayAdapter.getItem(position).indexOf(" ")));
                            Integer intId = Integer.parseInt(deger);
                            Database db = new Database(ViewProductActivity.this);
                            db.Delete(intId);
                            Log.d("DEBUG","Silindi");
                            Listele();

                        } catch (Exception e) {
                            Log.d("DEBUG", "" + e);
                        }
                    }
                });
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            return true;
            }
        });

        final ArrayAdapter<String>[] finalAdapter = new ArrayAdapter[]{arrayAdapter};
        Search_product.onActionViewExpanded(); //new Added line
        Search_product.setIconifiedByDefault(false);
        Search_product.setQueryHint("Ürün İsmi veya Barkod giriniz");

        Search_product.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                // TODO Auto-generated method stub
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {

                 finalAdapter[0] = arrayAdapter;

                 finalAdapter[0].getFilter().filter(text);
                return false;
            }
        });

    }
    public void Listele(){

        final ArrayList<String> list = new ArrayList<String>();
        arrayAdapter= new ArrayAdapter<String>(ViewProductActivity.this,android.R.layout.simple_list_item_1,list);
        try {
            Database d = new Database(ViewProductActivity.this);
            SQLiteDatabase db = d.getReadableDatabase();
            String esittir = "=";
            String sorgu = "SELECT barkod,urunAdi,urunStok FROM urunler ";
            final Cursor c = db.rawQuery(sorgu, null);
            while (c.moveToNext()) {
                arrayAdapter.add(c.getInt(0)+" "+c.getString(1)+" "+c.getString(2));
                arrayAdapter.notifyDataSetChanged();

            }
            arrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, list){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.rgb(242,163,101));

                    // Generate ListView Item using TextView
                    return view;
                }
            };
            c.close();
            arrayAdapter.notifyDataSetChanged();
            lv_View.setAdapter(arrayAdapter);

        }
        catch (Exception e)
        {e.printStackTrace();}


        // Create a List from String Array elements


        // Create an ArrayAdapter from List


    }
   /* public void Listele(){
        try {
            list=new ArrayList<HashMap<String,String>>();
            ListViewAdapter adapter=new ListViewAdapter(ViewProductActivity.this, list);

            Database d = new Database(ViewProductActivity.this);
            SQLiteDatabase db = d.getReadableDatabase();
            String esittir = "=";
            String sorgu = "SELECT barkod,urunAdi,urunStok FROM urunler ";
            final Cursor c = db.rawQuery(sorgu, null);
            while (c.moveToNext()) {
                barkod_array.add(c.getString(0));
                urun_array.add(c.getString(1));
                urunstok_array.add(c.getString(2));
                adapter.notifyDataSetChanged();

            }
            c.close();
            for (int x = 0;x<5;x++)
            {
                HashMap<String,String> hashmap=new HashMap<String, String>();
                hashmap.put(FIRST_COLUMN, barkod_array.get(x));
                hashmap.put(SECOND_COLUMN,urun_array.get(x));
                hashmap.put(THIRD_COLUMN, urunstok_array.get(x));
                list.add(hashmap);
            }
            lv_View.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }
        catch (Exception e)
        {e.printStackTrace();}


    }*/

}