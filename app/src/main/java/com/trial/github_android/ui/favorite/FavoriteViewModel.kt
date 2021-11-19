package com.trial.github_android.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.trial.github_android.data.repository.UserRepository

class FavoriteViewModel @ViewModelInject constructor(
    private val repository: UserRepository
): ViewModel() {
    val favorites = repository.getFavoriteUser()
}