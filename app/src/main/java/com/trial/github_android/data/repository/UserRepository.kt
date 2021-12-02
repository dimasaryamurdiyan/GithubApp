package com.trial.github_android.data.repository

import androidx.lifecycle.liveData
import com.trial.github_android.data.entities.FollowersEntity
import com.trial.github_android.data.entities.FollowingEntity
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.data.local.UserDao
import com.trial.github_android.data.remote.RemoteDataSource
import com.trial.github_android.utils.Resource
import com.trial.github_android.utils.performGetOperation
import com.trial.github_android.utils.performGetOperationLocal
import dagger.Module
import dagger.hilt.InstallIn
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: UserDao
) {
    fun getUser(username: String) = performGetOperation(
        databaseQuery = { localDataSource.getUser(username) },
        networkCall = { remoteDataSource.getUser(username) },
        saveCallResult = { response ->
            var favorite = localDataSource.getUser(username).value?.isFavorite
            val test = localDataSource.getUser(username)
            Timber.d("$test")
            Timber.d("favorite: $favorite")
            if(favorite == null){
                favorite = false
            }
            val user = UserEntity(
                response.id,
                response.login,
                response.nodeId,
                response.avatarUrl,
                response.gravatarId,
                response.url,
                response.htmlUrl,
                response.followersUrl,
                response.followingUrl,
                response.gistsUrl,
                response.starredUrl,
                response.subscriptionsUrl,
                response.organizationsUrl,
                response.reposUrl,
                response.eventsUrl,
                response.receivedEventsUrl,
                response.type,
                response.siteAdmin,
                response.name,
                response.company,
                response.blog,
                response.location,
                response.email,
                response.hireable,
                response.bio,
                response.twitterUsername,
                response.publicRepos,
                response.publicGists,
                response.followers,
                response.following,
                response.createdAt,
                response.updatedAt,
                favorite
            )
            localDataSource.insert(user) }
    )

    fun getUsers() = performGetOperation(
        databaseQuery = { localDataSource.getAllUsers() },
        networkCall = { remoteDataSource.getUsers() },
        saveCallResult = {
            val list = ArrayList<UserEntity>()
            for(response in it){
                var favorite = localDataSource.getUser(response.login).value?.isFavorite
                Timber.d("favorite-user: $favorite")
                if(favorite == null){
                    favorite = false
                }
                val user = UserEntity(
                    response.id,
                    response.login,
                    response.nodeId,
                    response.avatarUrl,
                    response.gravatarId,
                    response.url,
                    response.htmlUrl,
                    response.followersUrl,
                    response.followingUrl,
                    response.gistsUrl,
                    response.starredUrl,
                    response.subscriptionsUrl,
                    response.organizationsUrl,
                    response.reposUrl,
                    response.eventsUrl,
                    response.receivedEventsUrl,
                    response.type,
                    response.siteAdmin,
                    response.name,
                    response.company,
                    response.blog,
                    response.location,
                    response.email,
                    response.hireable,
                    response.bio,
                    response.twitterUsername,
                    response.publicRepos,
                    response.publicGists,
                    response.followers,
                    response.following,
                    response.createdAt,
                    response.updatedAt,
                    favorite
                )
                list.add(user)
            }
            localDataSource.insertAll(list) }
    )

//    fun getUserFollowing(username: String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(Resource.success(data = remoteDataSource.getUserFollowing(username)))
//            Timber.d(remoteDataSource.getUserFollowing(username).toString())
//        }catch (exception: Exception){
//            emit(Resource.error(exception.message ?: "Error Occurred!"))
//        }
//    }

    fun getUserFollowers(username: String) = performGetOperation(
        databaseQuery = { localDataSource.getAllUserFollowers() },
        networkCall = { remoteDataSource.getUserFollowers(username) },
        saveCallResult = {
            val list = ArrayList<FollowersEntity>()
            for(response in it){
                val user = FollowersEntity(
                    response.id,
                    response.login,
                    response.nodeId,
                    response.avatarUrl,
                    response.gravatarId,
                    response.url,
                    response.htmlUrl,
                    response.followersUrl,
                    response.followingUrl,
                    response.gistsUrl,
                    response.starredUrl,
                    response.subscriptionsUrl,
                    response.organizationsUrl,
                    response.reposUrl,
                    response.eventsUrl,
                    response.receivedEventsUrl,
                    response.type,
                    response.siteAdmin,
                )
                list.add(user)
            }
            localDataSource.insertFollowers(list) }
    )

    fun getUserFollowing(username: String) = performGetOperation(
        databaseQuery = { localDataSource.getAllUserFollowing() },
        networkCall = { remoteDataSource.getUserFollowing(username) },
        saveCallResult = {
            val list = ArrayList<FollowingEntity>()
            for(response in it){
                val user = FollowingEntity(
                    response.id,
                    response.login,
                    response.nodeId,
                    response.avatarUrl,
                    response.gravatarId,
                    response.url,
                    response.htmlUrl,
                    response.followersUrl,
                    response.followingUrl,
                    response.gistsUrl,
                    response.starredUrl,
                    response.subscriptionsUrl,
                    response.organizationsUrl,
                    response.reposUrl,
                    response.eventsUrl,
                    response.receivedEventsUrl,
                    response.type,
                    response.siteAdmin,
                )
                list.add(user)
            }
            localDataSource.insertFollowing(list) }
    )


//    fun getUserFollowers(username: String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(Resource.success(data = remoteDataSource.getUserFollowers(username)))
//            Timber.d(remoteDataSource.getUserFollowers(username).toString())
//        }catch (exception: Exception){
//            emit(Resource.error(exception.message ?: "Error Occurred!"))
//        }
//    }

    fun setFavoriteUser(user: UserEntity, newState: Boolean){
        user.isFavorite = newState
        localDataSource.updateUser(user)
    }

    fun getFavoritesAllUsers() = performGetOperationLocal(databaseQuery = {localDataSource.getFavoriteUser()})
}