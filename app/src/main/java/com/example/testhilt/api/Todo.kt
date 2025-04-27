package com.example.testhilt.api

/**
 * API response wrapper class.
 *
 * @param T The type of the data returned in the response.
 * @property apiVersion The version of the API.
 * @property timestamp The timestamp of the response.
 * @property data The data returned in the response.
 */
data class ApiResponse<T>(
    val apiVersion: String,
    val timestamp: String,
    val data: T
)

/**
 * Data class representing a Todo item.
 *
 * @property id The unique identifier of the Todo item.
 * @property title The title of the Todo item.
 * @property description The description of the Todo item.
 * @property completed Indicates whether the Todo item is completed.
 * @property recurringEvery The interval for recurring tasks in milliseconds.
 * @property notificationAt The time to send a notification for the Todo item.
 * @property notified Indicates whether a notification has been sent for the Todo item.
 * @property createdAt The creation timestamp of the Todo item.
 * @property updatedAt The last updated timestamp of the Todo item.
 */
data class Todo(
    val id: String,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val recurringEvery: Long?,
    val notificationAt: String?,
    val notified: Boolean,
    val createdAt: String,
    val updatedAt: String
)

/**
 * Data class representing a request to create a Todo item.
 *
 * @property title The title of the Todo item.
 * @property description The description of the Todo item.
 * @property completed Indicates whether the Todo item is completed.
 * @property recurringEvery The interval for recurring tasks in milliseconds.
 * @property notificationAt The time to send a notification for the Todo item.
 */
data class CreateTodoRequest(
    val title: String,
    val description: String? = null,
    val completed: Boolean = false,
    val recurringEvery: Long? = null,
    val notificationAt: String? = null
)

/**
 * Data class representing a request to update a Todo item.
 *
 * @property title The title of the Todo item.
 * @property description The description of the Todo item.
 * @property completed Indicates whether the Todo item is completed.
 * @property recurringEvery The interval for recurring tasks in milliseconds.
 * @property notificationAt The time to send a notification for the Todo item.
 */
data class UpdateTodoRequest(
    val title: String? = null,
    val description: String? = null,
    val completed: Boolean? = null,
    val recurringEvery: Long? = null,
    val notificationAt: String? = null
)