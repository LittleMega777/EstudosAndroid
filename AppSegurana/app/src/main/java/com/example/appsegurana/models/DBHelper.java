package com.example.appsegurana.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "security_app.db";
    private static final int DB_VERSION = 1;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
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
}
