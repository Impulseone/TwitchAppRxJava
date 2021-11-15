package com.mycorp.twitchapprxjava.repository

import com.mycorp.twitchapprxjava.model.FollowerInfo
import io.reactivex.Single

interface FollowersRepository {
    fun fetchFollowers(id: String): Single<List<FollowerInfo>>

    fun getFollowersByIds(followerIds: List<String>): Single<List<FollowerInfo>>
    fun getFollowersIdByGameId(gameId: String): Single<List<String>>
}