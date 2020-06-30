package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ViewProductActivity extends AppCompatActivity {

    ListView lv_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        lv_View = findViewById(R.id.lv_View);
        Listele();

    }
    public void Listele(){
        Database db = new Database(ViewProductActivity.this);
        List<String> view = db.SelectData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewProductActivity.this,
                android.R.layout.simple_list_item_1,android.R.id.text1,view);
        lv_View.setAdapter(adapter);
    }
}