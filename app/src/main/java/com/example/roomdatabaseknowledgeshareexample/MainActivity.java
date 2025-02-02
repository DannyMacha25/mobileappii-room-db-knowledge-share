package com.example.roomdatabaseknowledgeshareexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView expenseScrollView;
    private EditText editNameInput;
    private EditText editPriceInput;
    private Button submitButton;
    private AppDatabase db;
    private ExpensesDao expensesDao;
    private ExecutorService executorService;
    private List<Expense> allExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get scroll container
        expenseScrollView = findViewById(R.id.expenseScrollView);

        // Find inputs
        editNameInput = findViewById(R.id.editName);
        editPriceInput = findViewById(R.id.editPrice);
        submitButton = findViewById(R.id.submitButton);

        executorService = Executors.newSingleThreadExecutor();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "expense-app-database").build();
        expensesDao = db.expensesDao();

        // Call the executor function containing the getAll() function
        getAllExpenses();

        // Button Listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Init started values
                float priceInput = 1;

                // Get inputs
                String nameInput = editNameInput.getText().toString();
                try {
                    priceInput = Float.parseFloat(editPriceInput.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }

                // Add the new expense
                Expense newExpense = new Expense();
                newExpense.price = priceInput;
                newExpense.name = nameInput;

                addExpense(newExpense); // Executor function
                addExpenseToView(newExpense); // Update UI
            }
        });
    }

    private void addExpenseToView(Expense expense) {
        TextView label = new TextView((this));
        label.setText(expense.name + " | $" + expense.price);

        expenseScrollView.addView(label);
    }

    // Executor Service
    private void addExpense(Expense expense) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                expensesDao.insertOne(expense);

            }
        });
    }
    private void getAllExpenses() {
        executorService.execute(new Runnable() {
        @Override
        public void run() {
            allExpenses = expensesDao.getAll();
            // Iniitally add all expenses to scroll
            for (Expense e:
                 allExpenses) {
                addExpenseToView(e);
                }

            }
        });
    }
}