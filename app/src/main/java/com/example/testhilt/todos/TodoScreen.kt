package com.example.testhilt.todos

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testhilt.api.Todo

@Composable
fun TodoScreen(viewModel: TodoViewModel = hiltViewModel()) {
    val todos = viewModel.todos.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()
    val focusManager = LocalFocusManager.current

    var newTodoName by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredTodos = todos.value.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTodoName,
                onValueChange = { newTodoName = it },
                label = { Text("New Todo") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            Button(
                onClick = {
                    if (newTodoName.isNotBlank()) {
                        viewModel.addTodo(Todo(id = "", name = newTodoName, completed = false))
                        newTodoName = ""
                    }
                },
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text("Add")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Todos") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            when {
                loading.value -> CircularProgressIndicator()
                error.value != null -> Text(text = error.value ?: "An error occurred.")
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    items(filteredTodos) { todo ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${todo.name} - Completed: ${todo.completed}",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            if (!todo.completed) {
                                Button(
                                    onClick = { viewModel.completeTodo(todo) },
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text("Complete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}