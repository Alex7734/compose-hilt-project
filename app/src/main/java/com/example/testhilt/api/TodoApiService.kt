package com.example.testhilt.api

import retrofit2.Response
import retrofit2.http.*

data class Todo(
    val id: String,
    val name: String,
    val completed: Boolean
)

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>

    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: String): Response<Todo>

    @POST("todos")
    suspend fun createTodo(@Body todo: Todo): Response<Todo>

    @PUT("todos/{id}")
    suspend fun updateTodo(@Path("id") id: String, @Body todo: Todo): Response<Todo>

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: String): Response<Unit>
}