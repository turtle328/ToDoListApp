package com.example.todolistapp

import androidx.room3.Dao
import androidx.room3.Insert

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTask(task: TodoItem)
}