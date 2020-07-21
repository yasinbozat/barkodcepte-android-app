package com.bc.barkodcepte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    //DATABASE INFORMATION -------------------------------------------------------------------------
    private static final String DATABASE_NAME = "CepteBarkod";
    private static final int DATABASE_VERSION = 1;
    //----------------------------------------------------------------------------------------------

    //TABLE_URUNLER INFORMATION --------------------------------------------------------------------
    private static final String TABLE_URUNLER = "urunler";
    private static final String ROW_ID = "id";
    private static final String ROW_BARKOD = "barkod";
    private static final String ROW_URUNADI = "urunAdi";
    private static final String ROW_FIYAT = "urunFiyat";
    private static final String ROW_STOK = "urunStok";
    private static final String ROW_ALIS = "alis";
    //----------------------------------------------------------------------------------------------
    //TABLE_RECEIPTS INFORMATION --------------------------------------------------------------------
    private static final String TABLE_RECEIPTS = "receipts";
    private static final String RECEIPTS_ID = "id";
    private static final String RECEIPTS_DATE = "date";
    private static final String RECEIPTS_PRICE = "price";
    private static final String RECEIPTS_GAIN = "gain";
    //----------------------------------------------------------------------------------------------

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_URUNLER + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_BARKOD + " TEXT NOT NULL,"
                + ROW_URUNADI + " TEXT NOT NULL,"
                + ROW_FIYAT + " TEXT NOT NULL,"
                + ROW_ALIS + " TEXT NOT NULL,"
                + ROW_STOK + " INTEGER NOT NULL )"
        );
        db.execSQL("CREATE TABLE " + TABLE_RECEIPTS + "("
                + RECEIPTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RECEIPTS_DATE + " TEXT NOT NULL,"
                + RECEIPTS_GAIN + " TEXT NOT NULL,"
                + RECEIPTS_PRICE + " DOUBLE NOT NULL)"
        );
        Log.d("DEBUG", "Veritabanı oluşturuldu!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_URUNLER);
        onCreate(db);
    }

    public void InsertData(String urunBarkod, String urunAdi, String fiyat, String alis,String stok) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues cv = new ContentValues();

            cv.put(ROW_BARKOD, urunBarkod);
            cv.put(ROW_URUNADI, urunAdi);
            cv.put(ROW_FIYAT, fiyat);
            cv.put(ROW_ALIS, alis);
            cv.put(ROW_STOK, stok);


            db.insert(TABLE_URUNLER, null, cv);
            Log.d("DEBUG", "maşarılı mro");
        } catch (Exception e) {
            Log.d("DEBUG", "Veritabanı oluşturma hatası!");
        }
        db.close();
    }

    public void Delete(String barkod) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String where = ROW_BARKOD + " = " + barkod;
            db.delete(TABLE_URUNLER, where, null);
        } catch (Exception e) {
        }
        db.close();
    }



    public List<String> SelectData() {
        List<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] columns = {ROW_ID, ROW_BARKOD, ROW_URUNADI, ROW_FIYAT, ROW_STOK};
            Cursor cursor = db.query(TABLE_URUNLER, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                data.add(cursor.getInt(0)
                        + "   -   "
                        + cursor.getString(1)
                        + "   -   "
                        + cursor.getString(2)
                        + "   -   "
                        + cursor.getString(3)
                        + "   -   "
                        + cursor.getInt(4));
            }
        } catch (Exception e) {
        }
        db.close();
        return data;
    }

    // RECEIPT -------------------------------------------------------------------------------------
    public void AddReceipt(String tarih, String fiyat, String kar) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues cv = new ContentValues();
            cv.put(RECEIPTS_DATE, tarih);
            cv.put(RECEIPTS_PRICE, fiyat);
            cv.put(RECEIPTS_GAIN, kar);

            db.insert(TABLE_RECEIPTS, null, cv);
            Log.d("DEBUG", "maşarılı mro");
        } catch (Exception e) {
            Log.d("DEBUG", "Veritabanı oluşturma hatası!");
        }
        db.close();
    }
    public void DeleteReceipt(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String where = RECEIPTS_ID + " = " + id;
            db.delete(TABLE_RECEIPTS, where, null);
        } catch (Exception e) {
        }
        db.close();
    }
    // RECEIPT -------------------------------------------------------------------------------------

       /*public List<String> getProductType() {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + ROW_URUNTURU + " FROM " + TABLE_URUNLER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<String> list_productType = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                list_productType.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list_productType;
    }*/

}