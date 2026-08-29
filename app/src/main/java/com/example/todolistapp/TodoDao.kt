package com.example.todolistapp

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTask(task: TodoItem)

    @Query("SELECT * FROM TodoItem")
    suspend fun getAllTasks(): Flow<List<TodoItem>>

    @Query("UPDATE TodoItem SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateTask(id: Int, isCompleted: Boolean)
}