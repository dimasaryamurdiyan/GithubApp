package com.trial.github_android.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trial.github_android.data.entities.FollowersEntity
import com.trial.github_android.data.entities.FollowingEntity
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.data.remote.response.UserResponse.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<UserEntity>>

    @Query("SELECT * FROM followers")
    fun getAllUserFollowers() : LiveData<List<FollowersEntity>>

    @Query("SELECT * FROM following")
    fun getAllUserFollowing() : LiveData<List<FollowingEntity>>

    @Query("SELECT * FROM users WHERE login = :username")
    fun getUser(username: String): LiveData<UserEntity>

    @Query("SELECT * FROM users WHERE login = :username")
    fun getUserEntity(username: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowers(users: List<FollowersEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowing(users: List<FollowingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE isFavorite = 1")
    fun getFavoriteUser(): LiveData<List<UserEntity>>
}