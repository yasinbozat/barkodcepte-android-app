package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class SelledActivity extends AppCompatActivity {

    public ArrayAdapter<String> adapter;
    ListView lv_Selled;
    SearchView selled_Search;
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
        String sorgu = "SELECT id,date,price FROM receipts ";
        final Cursor c = db.rawQuery(sorgu, null);
        while (c.moveToNext()) {
           String tarih = c.getString(1);
            adapter.add(""+tarih+"  |   "+c.getString(2)+" TL");

            adapter.notifyDataSetChanged();

        }
        lv_Selled.setAdapter(adapter);

        c.close();
        selled_Search.clearFocus();
    }


}