package com.example.campusstage2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.campusstage2.model.RecurringExpense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "campusexpense2.db";
    private static final int DATABASE_VERSION = 2;
    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUsersTable(db);
        createCategoriesTable(db);
        dumpCategoriesData(db);
        createBudgetTable(db);
        createExpenseTable(db);
        createRecurringExpenseTable(db);
        createBudgetExpensesTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recurring_expenses");
        db.execSQL("DROP TABLE IF EXISTS expense");
        db.execSQL("DROP TABLE IF EXISTS budgets");
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void createUsersTable(SQLiteDatabase db) {
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

    public void createCategoriesTable(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "parent_id INTEGER, " +
                "user_id INTEGER DEFAULT NULL, " +
                "FOREIGN KEY (parent_id) REFERENCES categories (id))";
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    public void dumpCategoriesData(SQLiteDatabase db) {
        String INSERT_CATEGORIES_DATA = "INSERT INTO categories (name, parent_id) VALUES " +
                "('Food', NULL), " +
                "('Restaurants', 1), " +
                "('Cafe', 1), " +
                "('Shopping', NULL), " +
                "('Clothing', 4), " +
                "('Electronics', 4)";
        db.execSQL(INSERT_CATEGORIES_DATA);
    }

    public void createBudgetTable(SQLiteDatabase db) {
        String CREATE_BUDGET_TABLE = "CREATE TABLE budgets (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_id INTEGER NOT NULL, " +
                "user_id INTEGER NOT NULL, " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL, " +
                "amount INTEGER NOT NULL, " +
                "remaining INTEGER NOT NULL, " +
                "FOREIGN KEY (category_id) REFERENCES categories (id), " +
                "FOREIGN KEY (user_id) REFERENCES users (id))";
        db.execSQL(CREATE_BUDGET_TABLE);
    }

    public void createExpenseTable(SQLiteDatabase db) {
        String CREATE_EXPENSE_TABLE = "CREATE TABLE expense (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_id INTEGER NOT NULL, " +
                "user_id INTEGER NOT NULL, " +
                "date DATE NOT NULL, " +
                "amount INTEGER NOT NULL, " +
                "note TEXT," +
                "FOREIGN KEY (category_id) REFERENCES categories (id), " +
                "FOREIGN KEY (user_id) REFERENCES users (id))";
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    public void createBudgetExpensesTable(SQLiteDatabase db) {
        String CREATE_BUDGET_EXPENSES_TABLE = "CREATE TABLE budget_expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "budget_id INTEGER NOT NULL, " +
                "expense_id INTEGER NOT NULL, " +
                "FOREIGN KEY (budget_id) REFERENCES budgets (id), " +
                "FOREIGN KEY (expense_id) REFERENCES expense (id))";
        db.execSQL(CREATE_BUDGET_EXPENSES_TABLE);
    }

    public void createRecurringExpenseTable(SQLiteDatabase db) {
        String CREATE_RECURRING_EXPENSE_TABLE = "CREATE TABLE recurring_expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_id INTEGER NOT NULL, " +
                "user_id TEXT NOT NULL, " +
                "amount REAL NOT NULL, " +
                "start_date DATE NOT NULL, " +
                "end_date DATE, " +
                "repeated_choice TEXT NOT NULL CHECK (repeated_choice IN ('daily', 'weekly', 'monthly', 'yearly')), " +
                "FOREIGN KEY (category_id) REFERENCES categories (id), " +
                "FOREIGN KEY (user_id) REFERENCES users (id))";
        db.execSQL(CREATE_RECURRING_EXPENSE_TABLE);
    }

//    @SuppressLint("Range")
//    public List<RecurringExpense> getRecurringExpenses(String userId) {
//        List<RecurringExpense> recurringExpenses = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(
//                "recurring_expenses",   // Tên bảng
//                null,                   // chọn tất cả cột
//                "user_id = ?",           // điều kiện WHERE
//                new String[]{userId},    // giá trị điều kiện
//                null,
//                null,
//                null
//        );
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    RecurringExpense recurringExpense = new RecurringExpense(this);
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                    recurringExpense.setId(cursor.getInt(cursor.getColumnIndex("id")));
//                    recurringExpense.setCategoryId(cursor.getInt(cursor.getColumnIndex("category_id")));
//                    recurringExpense.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
//                    recurringExpense.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
//                    recurringExpense.setStartDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex("start_date")), formatter));  // Sử dụng formatter đúng
//                    recurringExpense.setEndDate(cursor.isNull(cursor.getColumnIndex("end_date")) ? null : LocalDate.parse(cursor.getString(cursor.getColumnIndex("end_date")), formatter));  // Sử dụng formatter đúng
//                    recurringExpense.setRepeatedChoice(cursor.getString(cursor.getColumnIndex("repeated_choice")).toLowerCase()); // Chuyển thành chữ thường để kiểm tra
//
//                    recurringExpenses.add(recurringExpense);
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//        }
//
//        db.close();
//        return recurringExpenses;
//    }

    public String getCategoryNameById(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String categoryName = null;

        // Truy vấn tên danh mục dựa trên ID
        Cursor cursor = db.rawQuery("SELECT name FROM categories WHERE id = ?", new String[]{String.valueOf(categoryId)});
        if (cursor != null && cursor.moveToFirst()) {
            categoryName = cursor.getString(0); // Lấy cột "name"
            cursor.close();
        }
        return categoryName != null ? categoryName : "Không xác định"; // Trả về "Không xác định" nếu không tìm thấy
    }
    public void addExpenseToBudget(RecurringExpense expense) {
        SQLiteDatabase db = this.getWritableDatabase();  // Lấy đối tượng database trong phương thức này

        ContentValues values = new ContentValues();
        values.put("user_id", expense.getUserId());
        values.put("category_id", expense.getCategoryId());
        values.put("amount", expense.getAmount());
        values.put("start_date", expense.getStartDate().toString());
        values.put("repeated_choice", expense.getRepeatedChoice().toLowerCase()); // Chuyển thành chữ thường
        values.put("end_date", expense.getEndDate() != null ? expense.getEndDate().toString() : null);

        db.insert("budget_expenses", null, values);
        db.close();  // Đóng kết nối với cơ sở dữ liệu
    }
}
