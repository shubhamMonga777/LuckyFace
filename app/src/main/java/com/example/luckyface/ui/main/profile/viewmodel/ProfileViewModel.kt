package com.example.luckyface.ui.main.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.luckyface.data.repositories.AuthUserRepository
import com.example.luckyface.util.Coroutines

class ProfileViewModel(
    val repository: AuthUserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()


    fun deleteUser() {

        Coroutines.io {

            repository.deleteUser()

        }
    }
}