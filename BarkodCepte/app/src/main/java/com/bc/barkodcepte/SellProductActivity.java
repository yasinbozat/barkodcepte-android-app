package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SellProductActivity extends AppCompatActivity {

    //--Değişkenler--//
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private TextView barcodeText2;

    private String barcodeData = "";
    String barcode_s;
    TextView urun_isim,urun_fiyat,toplam;
    //--Değişkenler--//
    ListView lv;
    ArrayAdapter<String > adapter;
    ArrayAdapter<String > adapter_toplam;
    Button sil;
    double urun_toplam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);


        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = findViewById(R.id.surface_view);
        barcodeText = findViewById(R.id.barcode_text);
        barcodeText2 = findViewById(R.id.barcode_text2);
        urun_isim =(TextView) findViewById(R.id.urun_isim);
        urun_fiyat =(TextView) findViewById(R.id.urun_fiyat);
        toplam = (TextView) findViewById(R.id.toplam);
        lv = findViewById(R.id.listview);
        sil = findViewById(R.id.sil);
        toplam.setText("0");
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<String> list2 = new ArrayList<String>();
        adapter= new ArrayAdapter<String>(SellProductActivity.this,android.R.layout.simple_list_item_multiple_choice,list);
        adapter_toplam= new ArrayAdapter<String>(SellProductActivity.this,android.R.layout.simple_list_item_multiple_choice,list2);
        lv.setAdapter(adapter);
        initialiseDetectorsAndSources();


        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
double set_toplam = 0;

                SparseBooleanArray positioncheck = lv.getCheckedItemPositions();
                int count = lv.getCount();
                for (int item=count-1;item>=0;item--)
                {
                    if (positioncheck.get(item))
                    {
                        adapter.remove(list.get(item));
                        adapter_toplam.remove(list2.get(item));

                    }
                }
                for (int sayac=0;sayac<list2.size();sayac++){
                    set_toplam+=Double.parseDouble(list2.get(sayac));
                }

                Log.d("TAG", set_toplam+"");
                urun_toplam = set_toplam;
                toplam.setText(urun_toplam+"");
                positioncheck.clear();

                adapter.notifyDataSetChanged();
                adapter_toplam.notifyDataSetChanged();
            }
        });

    }

    private void initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    if (ActivityCompat.checkSelfPermission(SellProductActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(SellProductActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override

                        public void run() {


                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData += barcodes.valueAt(0).email.address + "\n";
                                barcode_s = barcodeData;

                                barcodeText.setText(barcodeData);
                                barcodeText.setText(barcodes.valueAt(0).email.address + "\n");
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {
                                barcodeData += barcodes.valueAt(0).displayValue + "\n";
                                barcode_s = barcodes.valueAt(0).displayValue;

                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                        try {
                                  Database d = new Database(SellProductActivity.this);
                                  SQLiteDatabase db = d.getReadableDatabase();
                                  String esittir = "=";
                                  String sorgu = "SELECT urunAdi,urunFiyat FROM urunler WHERE barkod" + esittir + barcodes.valueAt(0).displayValue;
                                  Cursor c = db.rawQuery(sorgu, null);
                                  if (c != null) {
                                   c.moveToFirst();

                                   urun_isim.setText(c.getString(0));
                                   urun_fiyat.setText(c.getString(1));
                                   adapter.add(c.getString(0));
                                   urun_toplam += c.getFloat(1);
                                   float a =  c.getFloat(1);
                                   adapter_toplam.add(String.valueOf(a));
                                   toplam.setText(urun_toplam+"");
                                 }



                           barcodeText2.setText(barcodes.valueAt(0).displayValue);

                           c.close();
                           adapter.notifyDataSetChanged();
                          }
                          catch (Exception e)
                          {e.printStackTrace();
                              Toast.makeText(getApplicationContext(),"KAYITSIZ ÜRÜN",Toast.LENGTH_SHORT).show();
                          }

                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initialiseDetectorsAndSources();
    }
}