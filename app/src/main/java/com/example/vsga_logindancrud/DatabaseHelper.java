package com.example.vsga_logindancrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lari.db";
    private static final int DATABASE_VERSION = 2; // naikin versi kalau ada perubahan tabel

    // Nama tabel & kolom
    private static final String TABLE_NAME = "catatan_lari";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_JENIS = "jenis_lari";
    private static final String COLUMN_JARAK = "jarak";
    private static final String COLUMN_WAKTU = "waktu";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_JENIS + " TEXT, "
                + COLUMN_JARAK + " REAL, "
                + COLUMN_WAKTU + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tabel lama kalau ada update struktur
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Tambah data lari
    public boolean addRunRecord(String jenisLari, double jarak, double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JENIS, jenisLari);
        values.put(COLUMN_JARAK, jarak);
        values.put(COLUMN_WAKTU, waktu);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // Ambil semua data lari
    public List<Run> getAllRunRecords() {
        List<Run> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String jenis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JENIS));
                double jarak = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_JARAK));
                double waktu = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WAKTU));

                list.add(new Run(id, jenis, jarak, waktu));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // Update data lari
    public boolean updateRunRecord(int id, String jenisLari, double jarak, double waktu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JENIS, jenisLari);
        values.put(COLUMN_JARAK, jarak);
        values.put(COLUMN_WAKTU, waktu);

        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Hapus data lari
    public boolean deleteRunRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}
