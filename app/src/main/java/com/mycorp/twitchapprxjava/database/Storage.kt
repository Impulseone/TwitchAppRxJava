package com.mycorp.twitchapprxjava.database

import androidx.paging.DataSource
import com.mycorp.twitchapprxjava.database.model.FollowerInfo
import com.mycorp.twitchapprxjava.database.model.GameData
import com.mycorp.twitchapprxjava.database.room.entities.FavoriteGameDataEntity
import com.mycorp.twitchapprxjava.database.room.entities.FollowerInfoEntity
import com.mycorp.twitchapprxjava.database.room.entities.GameDataEntity
import io.reactivex.Completable
import io.reactivex.Single

interface Storage {

    fun getGamesDataFromDb(): DataSource.Factory<Int, GameDataEntity>
    fun getGameDataEntityById(id: String): Single<GameDataEntity>
    fun getFollowersFromDbByIds(followerIds: List<String>): Single<List<FollowerInfoEntity>>
    fun getFavoriteGamesFromDb(): DataSource.Factory<Int, FavoriteGameDataEntity>

    fun insertGamesData(gamesData: List<GameData>): Completable
    fun insertFollowersData(followersData: List<FollowerInfo>): Completable

    fun checkIsFavorite(gameId: String): Single<Int>
    fun insertFavoriteGame(gameData: GameData): Completable
    fun deleteByGameId(gameId: String): Completable
}