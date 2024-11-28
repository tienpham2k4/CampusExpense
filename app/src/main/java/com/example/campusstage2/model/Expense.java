package com.example.campusstage2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.campusstage2.DatabaseHelper;

public class Expense {
    private DatabaseHelper dbHelper;
    private Integer id;
    private int amount;
    private Integer categoryId;
    private Integer userId;
    private String date;
    private String note;
    private String categoryName;
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Expense(Integer id, int amount, Integer categoryId, Integer userId, String date) {
        this.setId(id);
        this.setAmount(amount);
        this.setCategoryId(categoryId);
        this.setUserId(userId);
        this.setDate(date);
    }

    public void insertExpense(int amount, Integer categoryId, Integer userId,
                              String date, String note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("category_id", categoryId);
        values.put("user_id", userId);
        values.put("date", date);
        values.put("note", note);
        db.insert("expense", null, values);
    }


    public Expense(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
