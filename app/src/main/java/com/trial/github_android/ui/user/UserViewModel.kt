package com.trial.github_android.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.trial.github_android.data.repository.UserRepository

class UserViewModel @ViewModelInject constructor(
    private val repository: UserRepository
): ViewModel() {
    val users = repository.getUsers()

//    val usersFavorite = repository.getFavorites()
}