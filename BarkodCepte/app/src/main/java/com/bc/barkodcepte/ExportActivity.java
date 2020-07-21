package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class ExportActivity extends AppCompatActivity {
    ArrayList<String> barcod_array = new ArrayList<String>();
    ArrayList<String> isim_array = new ArrayList<String>();
    ArrayList<String> fiyat_array = new ArrayList<String>();
    ArrayList<String> stok_array = new ArrayList<String>();
    Button export,read;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        export = findViewById(R.id.export);
        read = findViewById(R.id.read);
        textView = findViewById(R.id.tx);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Workbook wb=new HSSFWorkbook();
                Cell cell=null;
                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                Database d = new Database(ExportActivity.this);
                SQLiteDatabase db = d.getReadableDatabase();
                String sorgu = "SELECT barkod,urunAdi,urunFiyat,urunStok FROM urunler ";
                final Cursor c = db.rawQuery(sorgu, null);
                while (c.moveToNext()) {

                    barcod_array.add(c.getString(0));
                    isim_array.add(c.getString(1));
                    fiyat_array.add(c.getString(2));
                    stok_array.add(c.getString(2));
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

                    cell = row.createCell(3);
                    cell.setCellValue(stok_array.get(i));
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
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readExcelFileFromAssets();
            }
        });

    }
    public void readExcelFileFromAssets() {

        try {

            // initialize asset manager
            File inputFile = new File(getExternalFilesDir(null) + "/barkodcepte.xls");
            //  open excel sheet
            InputStream myInput = new FileInputStream(inputFile);
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno = 0;
            textView.append("\n");
            while (rowIter.hasNext()) {
                Log.e("TAG", " row no " + rowno);
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if (rowno != 0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno = 0;

                    String barkod = "", productName = "", productPrice = "", productStok = "";

                    while (cellIter.hasNext()) {
                        if (true) {
                            HSSFCell myCell = (HSSFCell) cellIter.next();

                            Database d = new Database(ExportActivity.this);
                            SQLiteDatabase db = d.getReadableDatabase();
                            String sorgu = "SELECT barkod FROM urunler where barkod ='" + myCell.toString() + "'";
                            final Cursor c = db.rawQuery(sorgu, null);
                            String gelenveri;
                            while (c.moveToNext()) {

                            }
                         
                                if (colno == 0) {
                                    barkod = myCell.toString();
                                } else if (colno == 1) {
                                    productName = myCell.toString();
                                } else if (colno == 2) {
                                    productPrice = myCell.toString();
                                } else if (colno == 3) {
                                    productStok = myCell.toString();
                                }

                                colno++;

                                Log.e("TAG", " Index :" + myCell.getColumnIndex() + " -- " + myCell.toString());

                        }

                    }
                    rowno++;
                }

            } }catch(Exception e){
                Log.e("TAG", "error " + e.toString());
            }

    }
}