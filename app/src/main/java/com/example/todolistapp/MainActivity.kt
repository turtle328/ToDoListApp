package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ToDoListScreen(modifier: Modifier = Modifier) {
    val taskTextState = rememberTextFieldState()
    val tasks = remember { mutableStateListOf<TodoItem>() }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(24.dp))
    {
        Text(text = "Todo List")
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            onKeyboardAction = { defaultAction ->
                if (taskTextState.text.isBlank()){
                    defaultAction()
                } else {
                    handleAddTask(taskTextState, tasks)
                }
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            state = taskTextState,
            label = { Text("Enter a new task") }
        )
        Button(
            onClick = {
                handleAddTask(taskTextState, tasks)
            }
        ) {
            Text("Add Task")
        }

        LazyColumn {
            items(tasks.size) { index ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = tasks[index].isCompleted, onCheckedChange = {
                        tasks[index] = tasks[index].copy(isCompleted = it)
                    })
                    Text(text = tasks[index].title)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoListScreenPreview() {
    ToDoListAppTheme {
        ToDoListScreen()
    }
}

fun handleAddTask(taskTextState: TextFieldState, tasks: MutableList<TodoItem>) {
    val taskString = taskTextState.text.toString()

    if (taskString.isBlank())
    {
        return // Do nothing.
    }

    val todoItem = TodoItem(taskString)

    tasks.add(todoItem)
    taskTextState.clearText()
}

data class TodoItem(val title: String, val isCompleted: Boolean = false)
