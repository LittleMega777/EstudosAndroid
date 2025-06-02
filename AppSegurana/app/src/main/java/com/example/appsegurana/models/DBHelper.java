package com.example.appsegurana.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "security_app.db";
    private static final int DB_VERSION = 2;
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_LOC_ID = "id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT UNIQUE, " +
                "password_hash TEXT)"
        );
        db.execSQL(
                "CREATE TABLE locations (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "latitude REAL, " +
                        "longitude REAL, " +
                        "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS locations");
        onCreate(db);
    }

    // Salvar usuário (e-mail + senha hash)
    public boolean registerUser(String email, String hash) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", email);
        values.put("password_hash", hash);

        long result = db.insert("user", null, values);

        return result != -1;
    }
    public boolean checkUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM user WHERE email = ?",
                new String[]{email}
        );
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public boolean verifyLogin(String email, String password) {
        String hashInput = SecurityUtils.sha256(password);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM user WHERE email = ? AND password_hash = ?",
                new String[]{email, hashInput}
        );
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }

    public boolean saveLocation(double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        // O timestamp será adicionado automaticamente pelo DEFAULT CURRENT_TIMESTAMP

        long result = db.insert(TABLE_LOCATIONS, null, values);
        db.close();
        return result != -1; // Retorna true se a inserção foi bem-sucedida
    }

    public Cursor getLastLocation() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query para pegar o último registro ordenado por ID ou timestamp
        return db.rawQuery("SELECT * FROM " + TABLE_LOCATIONS + " ORDER BY " + COLUMN_LOC_ID + " DESC LIMIT 1", null);
    }
}
