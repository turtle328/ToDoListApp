package com.example.todolistapp

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)