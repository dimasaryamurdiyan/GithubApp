package com.trial.github_android.data.remote

import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.data.remote.response.UserResponse.GetUserResponse
import com.trial.github_android.data.remote.response.UserResponse.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getAllUsers() : Response<GetUserResponse>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<User>
}