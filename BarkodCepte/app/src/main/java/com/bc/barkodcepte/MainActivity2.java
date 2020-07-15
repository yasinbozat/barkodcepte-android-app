package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements TextWatcher {
EditText e1;
ListView l1;
String[] name={"hello","asdasd","hello"};
ArrayList<SingleRow> mylist;
MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        e1 = findViewById(R.id.edittext);
        l1 = findViewById(R.id.listview);
        e1.addTextChangedListener(this);
        mylist = new ArrayList<>();
        SingleRow singleRow;
        for (int i = 0;i<name.length;i++)
        {
            singleRow = new SingleRow(name[i]);
            mylist.add(singleRow);
        }
        myAdapter = new MyAdapter(this,mylist);
        l1.setAdapter(myAdapter);

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.myAdapter.getFilter().filter(charSequence);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}