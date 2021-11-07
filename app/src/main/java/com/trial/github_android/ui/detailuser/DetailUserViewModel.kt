package com.trial.github_android.ui.detailuser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.trial.github_android.data.repository.UserRepository

class DetailUserViewModel @ViewModelInject constructor(
    private val repository: UserRepository
): ViewModel() {
    private val _username = MutableLiveData<String>()

    private val _user = _username.switchMap {
        repository.getUser(it)
    }

    val user = _user

    fun selectUsername(username: String){
        _username.value = username
    }
}