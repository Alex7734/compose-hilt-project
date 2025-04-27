package com.example.testhilt.todos

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.testhilt.api.Todo
import com.example.testhilt.todos.components.AddTodoDialog
import com.example.testhilt.todos.components.FilterChipsRow
import com.example.testhilt.todos.components.SearchBar
import com.example.testhilt.todos.components.TodoItem
import com.example.testhilt.todos.enums.TodoFilter

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val todos = viewModel.todos.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()
    val focusManager = LocalFocusManager.current

    var showAddDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(TodoFilter.ALL) }

    val filteredTodos = todos.value.filter { todo ->
        (todo.title.contains(searchQuery, ignoreCase = true) ||
                (todo.description?.contains(searchQuery, ignoreCase = true) ?: false))
    }

    if (showAddDialog) {
        AddTodoDialog(
            onDismiss = { showAddDialog = false },
            onAddTodo = { title, description ->
                val emptyTodo = Todo(
                    id = "",
                    title = title,
                    description = description,
                    completed = false,
                    recurringEvery = null,
                    notificationAt = null,
                    notified = false,
                    createdAt = "",
                    updatedAt = ""
                )
                viewModel.addTodo(emptyTodo)
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onClearQuery = { searchQuery = "" },
            )

            FilterChipsRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { filter ->
                    selectedFilter = filter
                    when (filter) {
                        TodoFilter.ALL -> viewModel.fetchTodos()
                        TodoFilter.UPCOMING -> viewModel.loadUpcomingNotifications()
                        TodoFilter.RECURRING -> viewModel.loadRecurringTodos()
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                when {
                    loading.value -> CircularProgressIndicator()
                    error.value != null -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = error.value ?: "An error occurred.",
                                color = MaterialTheme.colorScheme.error
                            )
                            Button(
                                onClick = {
                                    viewModel.clearError()
                                    viewModel.fetchTodos()
                                },
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                    filteredTodos.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = if (searchQuery.isBlank())
                                    "No todos found. Add your first todo!"
                                else
                                    "No todos match your search.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(filteredTodos) { todo ->
                                TodoItem(
                                    todo = todo,
                                    onCompleteClick = { viewModel.completeTodo(it) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}