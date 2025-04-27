package com.example.testhilt.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhilt.api.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
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

    private val _selectedTodo = MutableStateFlow<Todo?>(null)
    val selectedTodo: StateFlow<Todo?> = _selectedTodo

    init {
        fetchTodos()
    }

    fun fetchTodos() {
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
            _loading.value = true
            try {
                val newTodo = repository.createTodo(todo)
                if (newTodo != null) {
                    fetchTodos()
                } else {
                    _error.value = "Failed to create todo."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add todo."
            } finally {
                _loading.value = false
            }
        }
    }

    fun completeTodo(todo: Todo) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val updatedTodo = repository.updateTodo(todo.copy(completed = true))
                if (updatedTodo != null) {
                    _todos.value = _todos.value.map {
                        if (it.id == updatedTodo.id) updatedTodo else it
                    }
                    if (_selectedTodo.value?.id == updatedTodo.id) {
                        _selectedTodo.value = updatedTodo
                    }
                } else {
                    _error.value = "Failed to update todo."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to complete todo."
            } finally {
                _loading.value = false
            }
        }
    }

    fun getTodoById(id: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getTodoById(id)
                if (result != null) {
                    _selectedTodo.value = result
                } else {
                    _error.value = "Todo not found."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to get todo."
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateTodoWithNotification(id: String, completed: Boolean? = null, recurringEvery: Long? = null, notificationAt: Date? = null) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.updateTodoWithNotification(id, completed, recurringEvery, notificationAt)
                if (result != null) {
                    fetchTodos()
                    if (_selectedTodo.value?.id == id) {
                        _selectedTodo.value = result
                    }
                } else {
                    _error.value = "Failed to update todo with notification."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update todo."
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val success = repository.deleteTodo(id)
                if (success) {
                    // Remove the deleted todo from the list
                    _todos.value = _todos.value.filter { it.id != id }
                    // Clear selected todo if it was deleted
                    if (_selectedTodo.value?.id == id) {
                        _selectedTodo.value = null
                    }
                } else {
                    _error.value = "Failed to delete todo."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete todo."
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadUpcomingNotifications(days: Int? = null) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val notifications = repository.getUpcomingNotifications(days)
                if (notifications != null) {
                    _todos.value = notifications
                } else {
                    _error.value = "Failed to load upcoming notifications."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load upcoming notifications."
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadOverdueNotifications() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val notifications = repository.getOverdueNotifications()
                if (notifications != null) {
                    _todos.value = notifications
                } else {
                    _error.value = "Failed to load overdue notifications."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load overdue notifications."
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadRecurringTodos() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val todos = repository.getRecurringTodos()
                if (todos != null) {
                    _todos.value = todos
                } else {
                    _error.value = "Failed to load recurring todos."
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load recurring todos."
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}