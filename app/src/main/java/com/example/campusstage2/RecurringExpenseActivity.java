package com.example.campusstage2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.campusstage2.model.RecurringExpense;
import com.example.campusstage2.Adapter.RecurringExpenseAdapter;
import java.util.List;

public class RecurringExpenseActivity extends AppCompatActivity {
    private Button btnAddRecurringTransaction;
    private RecyclerView recyclerView;
    private RecurringExpenseAdapter adapter;
    private Auth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_expense);

        auth = new Auth(this);  // Khởi tạo Auth

        btnAddRecurringTransaction = findViewById(R.id.btnAddRecurringTransaction);
        btnAddRecurringTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng đến màn hình AddRecurringExpense
                startActivity(new Intent(RecurringExpenseActivity.this, AddRecurringExpense.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerViewRecurringExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lấy UserID hiện tại từ Auth
        int userId = auth.getUserId();

        // Lấy danh sách chi tiêu định kỳ từ cơ sở dữ liệu
        RecurringExpense recurringExpense = new RecurringExpense(this);  // Tạo đối tượng RecurringExpense và truyền context
        List<RecurringExpense> recurringExpenses = recurringExpense.getRecurringExpenses(String.valueOf(userId));  // Truyền userId vào

        if (recurringExpenses == null || recurringExpenses.isEmpty()) {
            Log.d("RecurringExpenseActivity", "No recurring expenses found.");
            // Hiển thị thông báo hoặc xử lý trường hợp không có dữ liệu
        } else {
            Log.d("RecyclerView", "Data size: " + recurringExpenses.size());
            // Cập nhật constructor cho RecurringExpenseAdapter
            adapter = new RecurringExpenseAdapter(recurringExpenses, this);  // Truyền context vào constructor
            recyclerView.setAdapter(adapter);  // Gắn adapter vào RecyclerView
        }
    }
}
