package com.example.luckyface.ui.main.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.luckyface.data.repositories.AuthUserRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val repository: AuthUserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }

}