package com.example.testhilt.todos

import com.example.testhilt.api.ApiResponse
import com.example.testhilt.api.CreateTodoRequest
import com.example.testhilt.api.RetrofitInstance
import com.example.testhilt.api.Todo
import com.example.testhilt.api.UpdateTodoRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

/**
 * Repository for managing Todo items.
 * This repository interacts with the API to perform CRUD operations on Todo items.
 */
class TodoRepository @Inject constructor() {
    private val api = RetrofitInstance.api
    private val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    suspend fun getTodos(): List<Todo>? {
        val response = api.getTodos()
        return if (response.isSuccessful) response.body()?.data else null
    }

    suspend fun getTodoById(id: String): Todo? {
        val response = api.getTodoById(id)
        return if (response.isSuccessful) response.body()?.data else null
    }

    suspend fun createTodo(todo: Todo): Todo? {
        val request = CreateTodoRequest(
            title = todo.title,
            description = todo.description,
            completed = todo.completed
        )
        val response = api.createTodo(request)
        return if (response.isSuccessful) response.body()?.data else null
    }

    suspend fun updateTodo(todo: Todo): Todo? {
        val request = UpdateTodoRequest(
            title = todo.title,
            description = todo.description,
            completed = todo.completed
        )
        val response = api.updateTodo(todo.id, request)
        return if (response.isSuccessful) response.body()?.data else null
    }

    suspend fun updateTodoWithNotification(id: String, completed: Boolean? = null, recurringEvery: Long? = null, notificationAt: Date? = null): Todo? {
        val request = UpdateTodoRequest(
            completed = completed,
            recurringEvery = recurringEvery,
            notificationAt = notificationAt?.let { isoDateFormat.format(it) }
        )
        val response = api.updateTodo(id, request)
        return if (response.isSuccessful) response.body()?.data else null
    }

    suspend fun deleteTodo(id: String): Boolean {
        val response = api.deleteTodo(id)
        return response.isSuccessful
    }

    suspend fun getUpcomingNotifications(days: Int? = null): List<Todo>? {
        val response = api.getUpcomingNotifications(days)
        return if (response.isSuccessful) response.body()?.data else null
    }

    suspend fun getOverdueNotifications(): List<Todo>? {
        val response = api.getOverdueNotifications()
        return if (response.isSuccessful) response.body()?.data else null
    }

    suspend fun getRecurringTodos(): List<Todo>? {
        val response = api.getRecurringTodos()
        return if (response.isSuccessful) response.body()?.data else null
    }
}