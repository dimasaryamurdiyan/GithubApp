package com.trial.github_android.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
): BaseDataSource() {
    suspend fun getUsers() = getResult { apiService.getAllUsers() }
    suspend fun getUser(username: String) = getResult { apiService.getUser(username) }
    suspend fun getUserFollowing(username: String) = getResult { apiService.getUserFollowing(username) }
    suspend fun getUserFollowers(username: String) = getResult { apiService.getUserFollowers(username) }
}