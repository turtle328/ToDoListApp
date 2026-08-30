package com.example.todolistapp

import android.app.Application
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

class TodoApplication : Application() {
    val database: TodoDatabase by lazy {
        Room.databaseBuilder(
            this,
            TodoDatabase::class.java,
            "todo_database"
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}