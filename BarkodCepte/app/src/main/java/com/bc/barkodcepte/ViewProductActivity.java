package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ViewProductActivity extends AppCompatActivity {

    public ArrayAdapter<String> adapter;
    ListView lv_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        lv_View = findViewById(R.id.lv_View);
        Listele();

        lv_View.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    String deger = adapter.getItem(position).substring(0,4).replace(" ","");
                    Integer intId = Integer.parseInt(deger);
                    Database db = new Database(ViewProductActivity.this);
                    db.Delete(intId);
                    Log.d("DEBUG","Silindi");
                    Listele();

                } catch (Exception e) {
                    Log.d("DEBUG", "" + e);
                }
            return true;
            }
        });

    }
    public void Listele(){
        Database db = new Database(ViewProductActivity.this);
        List<String> view = db.SelectData();
        adapter = new ArrayAdapter<String>(ViewProductActivity.this,
                android.R.layout.simple_list_item_1,android.R.id.text1,view);
        lv_View.setAdapter(adapter);

    }
}