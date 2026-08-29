package com.example.todolistapp

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [TodoItem::class],
    version = 1
)

abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}