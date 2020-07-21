package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity  {

    private ImageButton btn_sell, btn_myStore,btn_addProduct,btn_viewProduct,btn_addStock;
    Button export,oku;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        btn_sell = findViewById(R.id.btn_sell);
        btn_addProduct = findViewById(R.id.btn_addProduct);
        btn_viewProduct = findViewById(R.id.btn_viewProduct);
        btn_addStock = findViewById(R.id.btn_addStock);
        btn_myStore = findViewById(R.id.btn_myStore);
        export = findViewById(R.id.Export);
        textView = findViewById(R.id.textView12);
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,SellProductActivity.class);
                startActivity(i);

            }
        });
        btn_myStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Çok Yakında Sizlerle",Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(MainActivity.this,MyStoreActivity.class);
                startActivity(i);*/

            }
        });
        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,AddProductActivity.class);
                startActivity(i);

            }
        });
        btn_viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,ViewProductActivity.class);
                startActivity(i);

            }
        });
        btn_addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,AddStockActivity.class);
                startActivity(i);

            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,ExportActivity.class);
                startActivity(i);

            }
        });


    }


}
