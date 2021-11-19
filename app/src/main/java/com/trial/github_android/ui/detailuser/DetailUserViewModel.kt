package com.trial.github_android.ui.detailuser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.data.repository.UserRepository
import timber.log.Timber

class DetailUserViewModel @ViewModelInject constructor(
    private val repository: UserRepository
): ViewModel() {
    private val _username = MutableLiveData<String>()

    private val _user = _username.switchMap {
        repository.getUser(it)!!
    }

    private val _userFollowers = _username.switchMap {
        repository.getUserFollowers(it)!!
    }

    private val _userFollowing = _username.switchMap {
        repository.getUserFollowing(it)!!
    }

    val user = _user
    val userFollowers = _userFollowers
    val userFollowing = _userFollowing

    fun selectUsername(username: String){
        _username.value = username
    }

    fun setFavoriteUser(){
        val mUser = user.value
        if(mUser != null){
            val userEntity: UserEntity? = mUser.data
            if(userEntity != null){
                val newState = !userEntity.isFavorite!!
                repository.setFavoriteUser(userEntity, newState)
            }
        }
    }

}