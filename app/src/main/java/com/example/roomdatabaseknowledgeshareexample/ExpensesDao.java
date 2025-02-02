package com.example.roomdatabaseknowledgeshareexample;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpensesDao {
    @Query("SELECT * FROM Expenses")
    List<Expense> getAll();

    // Example of parameters with queries
    // Returns list of expenses that are >= given price
    @Query("SELECT * FROM Expenses WHERE price >= (:price)")
    List<Expense> loadPriceGTE(float price);

    @Insert
    void insertOne(Expense expense);
}
