package com.example.roomdatabaseknowledgeshareexample;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity (tableName = "Expenses")
public class Expense {
    @PrimaryKey (autoGenerate = true)
    public int id;

    @ColumnInfo (name = "name")
    public String name;

    @ColumnInfo (name = "price")
    public float price;
}
