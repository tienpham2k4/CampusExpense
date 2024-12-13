//package com.example.campusstage2;
//
//import androidx.annotation.NonNull;
//import androidx.work.Worker;
//import androidx.work.WorkerParameters;
//import android.content.Context;
//import com.example.campusstage2.DatabaseHelper;
//import com.example.campusstage2.model.RecurringExpense;
//import java.time.LocalDate;
//import java.util.List;
//
//public class RecurringExpenseWorker extends Worker {
//
//    public RecurringExpenseWorker(@NonNull Context context, @NonNull WorkerParameters params) {
//        super(context, params);
//    }
//
////    @NonNull
////    @Override
////    public Result doWork() {
////        String userId = getInputData().getString("user_id");
////        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
////
////        List<RecurringExpense> recurringExpenses = dbHelper.getRecurringExpenses(userId);
////        LocalDate today = LocalDate.now();
////
////        for (RecurringExpense expense : recurringExpenses) {
////            if ((expense.getEndDate() == null || expense.getEndDate().isAfter(today)) &&
////                    expense.getStartDate().isBefore(today)) {
////                // Logic to add to monthly budget here
////                dbHelper.addExpenseToBudget(expense); // Implement method to add expense to budget
////            }
////        }
////
////        return Result.success();
////    }
////}
