package com.trial.github_android.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.data.remote.response.UserResponse.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE login = :username")
    fun getUser(username: String): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)
}