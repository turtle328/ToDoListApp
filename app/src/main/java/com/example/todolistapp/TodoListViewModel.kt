package com.example.todolistapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoListViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<TodoItem>()
    private var nextId = 0
    val tasks: List<TodoItem> = _tasks

    fun addTask(title: String) {
        if (title.isBlank()) return
        val task = TodoItem(
            nextId++,
            title
        )
        _tasks.add(task)
    }

    fun deleteTask(taskId: Int) {
        val index = findTask(taskId)
        if (index != -1) {
            _tasks.removeAt(index)
        }
    }

    fun setTaskCompleted(taskId: Int, isCompleted: Boolean) {
        val index = findTask(taskId)
        if (index != -1) {
            _tasks[index] = _tasks[index].copy(isCompleted = isCompleted)
        }
    }

    private fun findTask(taskId: Int): Int {
        return _tasks.indexOfFirst( { task -> task.id == taskId } )
    }
}