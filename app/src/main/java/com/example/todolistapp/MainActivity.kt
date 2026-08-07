package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistapp.ui.theme.ToDoListAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoListRoute(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TodoListRoute(
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = viewModel()
)
{
    ToDoListScreen(
        viewModel.tasks,
        viewModel::addTask,
        viewModel::setTaskCompleted,
        viewModel::deleteTask,
        modifier = modifier
    )
}

@Composable
fun ToDoListScreen(
    tasks: List<TodoItem>,
    onAddTask: (String) -> Unit,
    onTaskCompleted: (Int, Boolean) -> Unit,
    onDeleteTask: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val taskTextState = rememberTextFieldState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    )
    {
        Text(text = "Todo List")
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            onKeyboardAction = { defaultAction ->
                if (taskTextState.text.isBlank()) {
                    defaultAction()
                } else {
                    handleAddTask(taskTextState, onAddTask)
                }
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            state = taskTextState,
            label = { Text("Enter a new task") }
        )
        Button(
            enabled = taskTextState.text.isNotBlank(),
            onClick = {
                handleAddTask(taskTextState, onAddTask)
            }
        ) {
            Text("Add Task")
        }

        LazyColumn {
            items(tasks.size) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = tasks[index].isCompleted, onCheckedChange = {
                        onTaskCompleted(index, it)
                    })
                    Text(
                        modifier = Modifier.weight(1f),
                        color = if (tasks[index].isCompleted) MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.5f
                        ) else MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (tasks[index].isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                        text = tasks[index].title
                    )
                    IconButton(
                        onClick = {
                            onDeleteTask(index)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete task",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoListScreenPreview() {
    ToDoListAppTheme {
        TodoListRoute()
    }
}

fun handleAddTask(taskTextState: TextFieldState, addTaskFunction: (String) -> Unit) {
    val taskString = taskTextState.text.toString()
    addTaskFunction(taskString)
    taskTextState.clearText()
}
