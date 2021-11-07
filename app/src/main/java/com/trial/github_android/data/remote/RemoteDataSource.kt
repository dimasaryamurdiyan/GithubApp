package com.trial.github_android.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
): BaseDataSource() {
    suspend fun getUsers() = getResult { apiService.getAllUsers() }
    suspend fun getUser(username: String) = getResult { apiService.getUser(username) }
}