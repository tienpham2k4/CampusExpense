package com.example.campusstage2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "campusexpense.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        createUsersTable(db);
        createCategoriesTable(db);
        dumpCategoriesData(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
    public void createUsersTable(SQLiteDatabase db){
        String CREATE_USER_TABLE = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "phone TEXT, " +
                "email TEXT, " +
                "name TEXT, " +
                "avatar TEXT, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_USER_TABLE);
    }
    public void createCategoriesTable(SQLiteDatabase db){
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "parent_id INTEGER, " +
                "user_id INTEGER DEFAULT NULL, " +
                "FOREIGN KEY (parent_id)" +
                " REFERENCES categories (id))";
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }
    public void dumpCategoriesData(SQLiteDatabase db)
    {
        String INSERT_CATEGORIES_DATA = "INSERT INTO categories (name, parent_id) VALUES " +
                "('Food', NULL), " +
                "('Restaurants', 1), " +
                "('Cafe', 1), " +
                "('Shoping', NULL), " +
                "('Clothing', 4), " +
                "('Electronics', 4)";
        db.execSQL(INSERT_CATEGORIES_DATA);
    }
}
