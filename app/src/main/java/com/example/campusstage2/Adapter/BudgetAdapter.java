package com.example.campusstage2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusstage2.R;
import com.example.campusstage2.model.Budget;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    private final List<Budget> budgets;

    public BudgetAdapter(List<Budget> budgets) {
        this.budgets = budgets;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = budgets.get(position);
        holder.amountTextView.setText("Amount: $" + budget.getAmount());
        holder.remainingTextView.setText("Remaining: $" + budget.getRemaining());
        holder.categoryTextView.setText("Category: " + budget.getCategoryName());
        holder.startDateTextView.setText("Start Date: " + budget.getStartDate());
        holder.endDateTextView.setText("End Date: " + budget.getEndDate());
    }


    @Override
    public int getItemCount() {
        return budgets.size();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView amountTextView,categoryTextView, startDateTextView, remainingTextView, endDateTextView;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            remainingTextView = itemView.findViewById(R.id.remainingTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            startDateTextView = itemView.findViewById(R.id.startDateTextView);
            endDateTextView = itemView.findViewById(R.id.endDateTextView);
        }
    }
}
