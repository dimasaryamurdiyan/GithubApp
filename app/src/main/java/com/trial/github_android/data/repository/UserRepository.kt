package com.trial.github_android.data.repository

import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.data.local.UserDao
import com.trial.github_android.data.remote.RemoteDataSource
import com.trial.github_android.utils.performGetOperation
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: UserDao
) {
    fun getUser(username: String) = performGetOperation(
        databaseQuery = { localDataSource.getUser(username) },
        networkCall = { remoteDataSource.getUser(username) },
        saveCallResult = { response ->
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
            )
            localDataSource.insert(user) }
    )

    fun getUsers() = performGetOperation(
        databaseQuery = { localDataSource.getAllUsers() },
        networkCall = { remoteDataSource.getUsers() },
        saveCallResult = {
            val list = ArrayList<UserEntity>()
            for(response in it){
                val movie = UserEntity(
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
                )
                list.add(movie)
            }
            localDataSource.insertAll(list) }
    )
}