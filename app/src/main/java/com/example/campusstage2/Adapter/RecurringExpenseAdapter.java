package com.example.campusstage2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.campusstage2.R;
import com.example.campusstage2.model.RecurringExpense;
import com.example.campusstage2.DatabaseHelper; // Import DatabaseHelper

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecurringExpenseAdapter extends RecyclerView.Adapter<RecurringExpenseAdapter.ViewHolder> {
    private List<RecurringExpense> recurringExpenses;
    private Context context;
    private RecurringExpense recurringExpenseModel;
    private DatabaseHelper databaseHelper; // Sử dụng DatabaseHelper

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RecurringExpenseAdapter(List<RecurringExpense> recurringExpenses, Context context) {
        this.recurringExpenses = recurringExpenses;
        this.context = context;
        this.recurringExpenseModel = new RecurringExpense(context);
        this.databaseHelper = DatabaseHelper.getInstance(context); // Tạo đối tượng DatabaseHelper với context
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recurring_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecurringExpense expense = recurringExpenses.get(position);

        holder.categoryTextView.setText(databaseHelper.getCategoryNameById(expense.getCategoryId())); // Hiển thị tên danh mục thay vì ID
        holder.amountTextView.setText(String.valueOf(expense.getAmount()));
        holder.startDateTextView.setText(expense.getStartDate().format(dateFormatter));
        holder.endDateTextView.setText(expense.getEndDate() != null ? expense.getEndDate().format(dateFormatter) : "N/A");
        holder.repeatedChoiceTextView.setText(expense.getRepeatedChoice());

        holder.repeatedChoiceTextView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                showRepeatedChoiceDialog(expense, adapterPosition);
            }
        });

        holder.deleteAmountButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                showDeleteConfirmationDialog(expense, adapterPosition); // Hiển thị hộp thoại xác nhận xóa
            }
        });
    }

    @Override
    public int getItemCount() {
        return recurringExpenses.size();
    }

    private void showRepeatedChoiceDialog(RecurringExpense recurringExpense, int position) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Chọn Tùy Chọn Lặp lại");

        final String[] repeatChoices = context.getResources().getStringArray(R.array.repeated_choices);
        int selectedItemIndex = getIndexOfChoice(recurringExpense.getRepeatedChoice(), repeatChoices);

        builder.setSingleChoiceItems(repeatChoices, selectedItemIndex, (dialog, which) -> {
            String selectedChoice = repeatChoices[which];
            recurringExpense.setRepeatedChoice(selectedChoice);
            notifyItemChanged(position);
            dialog.dismiss();
        });
        builder.create().show();
    }

    private int getIndexOfChoice(String choice, String[] choices) {
        for (int i = 0; i < choices.length; i++) {
            if (choices[i].equals(choice)) {
                return i;
            }
        }
        return 0;
    }

    private void showDeleteConfirmationDialog(RecurringExpense recurringExpense, int position) {
        new android.app.AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa mục này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteRecurringExpense(recurringExpense.getId());  // Gọi phương thức xóa
                    recurringExpenses.remove(position);  // Xóa khỏi danh sách
                    notifyItemRemoved(position);  // Cập nhật RecyclerView
                })
                .setNegativeButton("Hủy", null)
                .create()
                .show();
    }

    // Sử dụng phương thức deleteRecurringExpense từ RecurringExpense
    private void deleteRecurringExpense(int id) {
        recurringExpenseModel.deleteRecurringExpense(id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView, amountTextView, startDateTextView, endDateTextView, repeatedChoiceTextView;
        Button deleteAmountButton, editButton; // Ánh xạ các nút

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            startDateTextView = itemView.findViewById(R.id.startDateTextView);
            endDateTextView = itemView.findViewById(R.id.endDateTextView);
            repeatedChoiceTextView = itemView.findViewById(R.id.repeatedChoiceTextView);
            deleteAmountButton = itemView.findViewById(R.id.deleteAmountButton); // Ánh xạ nút delete
            editButton = itemView.findViewById(R.id.editButton); // Ánh xạ nút edit (nếu cần sử dụng sau này)
        }
    }
}
