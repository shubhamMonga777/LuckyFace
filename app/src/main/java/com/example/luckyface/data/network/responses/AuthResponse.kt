package com.example.luckyface.data.network.responses

import com.example.luckyface.data.db.entities.User

data class AuthResponse(
    val status: Boolean?,
    val message: String?,
    val data: User?
)