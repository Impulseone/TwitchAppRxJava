package com.mycorp.twitchapprxjava.domain.repository

import androidx.paging.PagedList
import androidx.paging.PagingData
import com.mycorp.twitchapprxjava.data.storage.model.FollowerInfo
import com.mycorp.twitchapprxjava.data.storage.model.GameData
import com.mycorp.twitchapprxjava.data.storage.model.SingleGameData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getGamesDataFromServer(): Single<List<GameData>>
    fun getFollowersListFromServer(id: String): Single<List<FollowerInfo>>

    fun getGamesDataFromDb(): Single<List<GameData>>
    fun getFollowersListFromDbByIds(followerIds: List<String>): Single<List<FollowerInfo>>
    fun getSingleGameDataFromDb(gameId: String): Single<SingleGameData>
    fun getFavoriteGamesFromDb(): Single<List<SingleGameData>>
    fun getPagedFavoriteGamesFromDb(): Observable<PagingData<SingleGameData>>

    fun insertGamesDataToDb(gameDataEntities: List<GameData>): Completable
    fun insertFollowersToDb(followersList: List<FollowerInfo>): Completable
    fun saveSingleGameDataToDb(singleGameData: SingleGameData): Completable

}