package com.example.campusstage2.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.campusstage2.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
    private DatabaseHelper dbHelper;
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer userId;

    public Category(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Category(Integer id, String name, Integer parentId, Integer userId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.userId = userId;
    }

    public void insertCategory(String name, Integer parentId, Integer userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("parent_id", parentId);
        values.put("user_id", userId);
        db.insert("categories", null, values);
    }

    @SuppressLint("Range")
    public Category getCategoryById(int categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("categories", null, "id = ?",
                new String[]{String.valueOf(categoryId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            this.id = cursor.getInt(cursor.getColumnIndex("id"));
            this.name = cursor.getString(cursor.getColumnIndex("name"));
            this.parentId = cursor.isNull(cursor.getColumnIndex("parent_id")) ? null : cursor.getInt(cursor.getColumnIndex("parent_id"));
            this.userId = cursor.isNull(cursor.getColumnIndex("user_id")) ? null : cursor.getInt(cursor.getColumnIndex("user_id"));
            cursor.close();
        }
        return this;
    }

    @SuppressLint("Range")
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("categories", new String[]{"id", "name", "parent_id", "user_id"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Integer parentId = cursor.isNull(cursor.getColumnIndex("parent_id")) ? null : cursor.getInt(cursor.getColumnIndex("parent_id"));
                Integer userId = cursor.isNull(cursor.getColumnIndex("user_id")) ? null : cursor.getInt(cursor.getColumnIndex("user_id"));
                categories.add(new Category(id, name, parentId, userId));
            }
            cursor.close();
        }
        return categories;
    }


    @Override
    public String toString() {
        if (this.parentId != null) {
            return name; // Subcategory format
        }
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
