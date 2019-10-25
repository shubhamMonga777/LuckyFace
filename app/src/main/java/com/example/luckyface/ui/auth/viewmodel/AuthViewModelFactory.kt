@file:Suppress("UNCHECKED_CAST")

package com.example.luckyface.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.luckyface.data.repositories.AuthUserRepository

class AuthViewModelFactory(
    private val repository: AuthUserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }

}