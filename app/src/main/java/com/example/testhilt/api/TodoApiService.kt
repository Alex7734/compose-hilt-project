package com.example.testhilt.api

import retrofit2.Response
import retrofit2.http.*

interface TodoApiService {
    @GET("api/todos")
    suspend fun getTodos(): Response<ApiResponse<List<Todo>>>

    @GET("api/todos/{id}")
    suspend fun getTodoById(@Path("id") id: String): Response<ApiResponse<Todo>>

    @POST("api/todos")
    suspend fun createTodo(@Body todo: CreateTodoRequest): Response<ApiResponse<Todo>>

    @PUT("api/todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: String,
        @Body todo: UpdateTodoRequest
    ): Response<ApiResponse<Todo>>

    @DELETE("api/todos/{id}")
    suspend fun deleteTodo(@Path("id") id: String): Response<Unit>

    @GET("api/todos/notifications/upcoming")
    suspend fun getUpcomingNotifications(@Query("days") days: Int? = null): Response<ApiResponse<List<Todo>>>

    @GET("api/todos/notifications/overdue")
    suspend fun getOverdueNotifications(): Response<ApiResponse<List<Todo>>>

    @GET("api/todos/recurring")
    suspend fun getRecurringTodos(): Response<ApiResponse<List<Todo>>>
}