package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {

    private ImageButton btn_sell, btn_myStore,btn_addProduct,btn_viewProduct,btn_addStock;
    Button excel;
    ArrayList<String> barcod_array = new ArrayList<String>();
    ArrayList<String> isim_array = new ArrayList<String>();
    ArrayList<String> fiyat_array = new ArrayList<String>();

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
        excel = findViewById(R.id.excel);
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
        excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Workbook wb=new HSSFWorkbook();
                    Cell cell=null;
                    CellStyle cellStyle=wb.createCellStyle();
                    cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
                    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                Database d = new Database(MainActivity.this);
                SQLiteDatabase db = d.getReadableDatabase();
                String sorgu = "SELECT barkod,urunAdi,urunFiyat FROM urunler ";
                final Cursor c = db.rawQuery(sorgu, null);
                while (c.moveToNext()) {

                    barcod_array.add(c.getString(0));
                    isim_array.add(c.getString(1));
                    fiyat_array.add(c.getString(2));
                }

                    //Now we are creating sheet
                    Sheet sheet=null;
                    sheet = wb.createSheet("Name of sheet");
                    //Now column and row
                for (int i = 0 ; i<barcod_array.size();i++) {
                    Row row = sheet.createRow(i);

                    cell = row.createCell(0);
                    cell.setCellValue(barcod_array.get(i));
                    cell.setCellStyle(cellStyle);

                    cell = row.createCell(1);
                    cell.setCellValue(isim_array.get(i));
                    cell.setCellStyle(cellStyle);

                    cell = row.createCell(2);
                    cell.setCellValue(fiyat_array.get(i));
                    cell.setCellStyle(cellStyle);
                }
                    sheet.setColumnWidth(0,(10*200));
                    sheet.setColumnWidth(1,(10*200));

                    File file = new File(getExternalFilesDir(null),"barkodcepte.xls");
                    FileOutputStream outputStream =null;

                    try {
                        outputStream=new FileOutputStream(file);
                        wb.write(outputStream);
                        Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();

                        Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
                        try {
                            outputStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }


                }

        });

    }

}
