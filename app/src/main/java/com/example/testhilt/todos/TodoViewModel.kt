package com.example.testhilt.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhilt.api.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing the state of the Todo list.
 * This ViewModel interacts with the TodoRepository to fetch, add, update, and search for todos.
 *
 * @param repository The repository to interact with the API.
 */
@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _searchResult = MutableStateFlow<Todo?>(null)
    val searchResult: StateFlow<Todo?> = _searchResult

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val todos = repository.getTodos()
                if (todos != null) {
                    _todos.value = todos
                } else {
                    _error.value = "Failed to load todos."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unknown error occurred."
            } finally {
                _loading.value = false
            }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                val newTodo = repository.createTodo(todo)
                if (newTodo != null) {
                    _todos.value = _todos.value + newTodo
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add todo."
            }
        }
    }

    fun completeTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                val updatedTodo = repository.updateTodo(todo.copy(completed = true))
                if (updatedTodo != null) {
                    _todos.value = _todos.value.map {
                        if (it.id == updatedTodo.id) updatedTodo else it
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to complete todo."
            }
        }
    }

    fun searchTodoById(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchTodoById(id)
                _searchResult.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to search todo."
            }
        }
    }
}