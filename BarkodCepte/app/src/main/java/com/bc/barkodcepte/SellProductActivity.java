package com.bc.barkodcepte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

public class SellProductActivity extends AppCompatActivity {

    //--Değişkenler--//
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private TextView barcodeText2;

    private String barcodeData="";
    private boolean izin = true;

    String barcode_s;
    TextView urun_isim,urun_fiyat,toplam;
    //--Değişkenler--//
    ListView lv;
    ArrayAdapter<String > adapter;
    ArrayAdapter<String > adapter_toplam;
    Button sil,bitti;
    public double urun_toplam = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);
        //FULL SCREEN CODES ------------------------------------------------------------------------
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //------------------------------------------------------------------------------------------

        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = findViewById(R.id.surface_view);
        barcodeText = findViewById(R.id.barcode_text);
        barcodeText2 = findViewById(R.id.barcode_text2);
        urun_isim =(TextView) findViewById(R.id.urun_isim);
        urun_fiyat =(TextView) findViewById(R.id.urun_fiyat);
        toplam = (TextView) findViewById(R.id.toplam);

        lv = findViewById(R.id.listview);
        sil = findViewById(R.id.sil);
        bitti = findViewById(R.id.bitti);
        toplam.setText("0");
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<String> list2 = new ArrayList<String>();
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, list){
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
        adapter_toplam= new ArrayAdapter<String>(SellProductActivity.this,
                                            android.R.layout.simple_list_item_multiple_choice,list2);
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
        bitti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // UPDATE personel SET prim=prim+50
                        String urunadi = null;
                        for (int i = 0;i<adapter.getCount();i++) {
                            urunadi = adapter.getItem(i).substring(0, adapter.getItem(i).indexOf(" - "))
                                                                                                    .trim();
                            try {
                                Database d = new Database(SellProductActivity.this);
                                SQLiteDatabase db = d.getReadableDatabase();
                                String esittir = "=";

                                String sorgu1 = "UPDATE urunler SET urunStok" + esittir + "urunStok-1"
                                                + " WHERE urunAdi" + esittir + "'" + urunadi + "'";
                                db.execSQL(sorgu1);



                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Bir Sorun Oluştu",
                                                                            Toast.LENGTH_SHORT).show();
                            }

                        }
                Toast.makeText(getApplicationContext(), "Satış Başarıyla Gerçekleştirildi",
                                                                            Toast.LENGTH_SHORT).show();
              
                // Activity Reset Kodları
                izin = false;
                Intent i = new Intent(SellProductActivity.this,ReceiptActivity.class);
                i.putExtra("toplam",urun_toplam);
                i.putStringArrayListExtra("ArrayList_1",list2);
                finish();
                startActivity(i);

                //-----------------------
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
                    if (ActivityCompat.checkSelfPermission(SellProductActivity.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override

                        public void run() {

                            if (izin == true) {
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
                                        String sorgu = "SELECT urunAdi,urunFiyat,barkod FROM urunler " +
                                                "WHERE barkod" + esittir + barcodes.valueAt(0).displayValue;
                                        Cursor c = db.rawQuery(sorgu, null);
                                        if (c != null) {
                                            c.moveToFirst();

                                            urun_isim.setText(c.getString(0));
                                            urun_fiyat.setText(c.getString(1));
                                            adapter.add(c.getString(0)+" - "+
                                                                    c.getString(1)+" TL");
                                            urun_toplam += c.getFloat(1);
                                            float a = c.getFloat(1);
                                            adapter_toplam.add(String.valueOf(a));
                                            toplam.setText(urun_toplam + "");
                                        }


                                        barcodeText2.setText(barcodes.valueAt(0).displayValue);

                                        c.close();
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "KAYITSIZ ÜRÜN",
                                                                            Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }
                    });



                }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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