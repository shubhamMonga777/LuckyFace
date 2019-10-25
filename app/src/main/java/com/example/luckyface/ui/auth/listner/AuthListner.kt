package com.example.luckyface.ui.auth.listner

interface AuthListner {
    fun onStarted()
    fun onSucess()
    fun onFailure(message: String)

}