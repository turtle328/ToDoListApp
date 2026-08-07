package com.example.todolistapp

data class TodoItem(
    val title: String,
    val isCompleted: Boolean = false
)