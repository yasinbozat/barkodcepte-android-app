package com.bc.barkodcepte;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelledActivity extends AppCompatActivity {

    public ArrayAdapter<String> adapter;
    ListView    lv_Selled;
    SearchView  selled_Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selled);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------
        lv_Selled = findViewById(R.id.lv_Selled);
        selled_Search = findViewById(R.id.selled_Search);

        Listele();

        final ArrayAdapter<String>[] finalAdapter = new ArrayAdapter[]{adapter};
        selled_Search.onActionViewExpanded(); //new Added line
        selled_Search.setIconifiedByDefault(false);
        selled_Search.setQueryHint("Tarih Giriniz");

        selled_Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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


        lv_Selled.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SelledActivity.this);
                builder.setTitle("Uyarı!");
                builder.setMessage("Seçtiğiniz Satış Kaydı Silinsin mi?");
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {

                            String id = adapter.getItem(position).substring(0,adapter.getItem(position).indexOf(")")).trim();
                            Database db = new Database(SelledActivity.this);
                            db.DeleteReceipt(id);
                            Log.d("DEBUG","Satış Kaydı Başarıyla Silindi");
                            Toast.makeText(getApplicationContext(),"Satış Kaydı Silindi",Toast.LENGTH_LONG).show();
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
        Database d = new Database(SelledActivity.this);
        SQLiteDatabase db = d.getReadableDatabase();
        String sorgu = "SELECT id,date,price,gain FROM receipts ";
        final Cursor c = db.rawQuery(sorgu, null);
        while (c.moveToNext()) {
           String tarih = c.getString(1);
            adapter.add(c.getString(0)+" )   "+tarih+"  |   Satış : "+c.getString(2)+" TL"+"  |   Kâr : "+c.getString(3)+" TL");

            adapter.notifyDataSetChanged();

        }
        lv_Selled.setAdapter(adapter);

        c.close();
        selled_Search.clearFocus();
    }


}