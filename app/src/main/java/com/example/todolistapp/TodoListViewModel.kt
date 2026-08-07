package com.example.todolistapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoListViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<TodoItem>()
    val tasks: List<TodoItem> = _tasks

    fun addTask(title: String) {
        if (title.isBlank()) return
        val task = TodoItem(title)
        _tasks.add(task)
    }

    fun deleteTask(index: Int) {
        _tasks.removeAt(index)
    }

    fun setTaskCompleted(index: Int, isCompleted: Boolean) {
        _tasks[index] = _tasks[index].copy(isCompleted = isCompleted)
    }
}