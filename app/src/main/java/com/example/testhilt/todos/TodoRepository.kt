package com.example.testhilt.todos

import com.example.testhilt.api.RetrofitInstance
import com.example.testhilt.api.Todo
import javax.inject.Inject

/**
 * Repository for managing Todo items.
 * This repository interacts with the API to perform CRUD operations on Todo items.
 */
class TodoRepository @Inject constructor() {
    private val api = RetrofitInstance.api

    suspend fun getTodos(): List<Todo>? {
        val response = api.getTodos()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createTodo(todo: Todo): Todo? {
        val response = api.createTodo(todo)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateTodo(todo: Todo): Todo? {
        val response = api.updateTodo(todo.id, todo)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun searchTodoById(id: String): Todo? {
        val response = api.getTodoById(id)
        return if (response.isSuccessful) response.body() else null
    }
}