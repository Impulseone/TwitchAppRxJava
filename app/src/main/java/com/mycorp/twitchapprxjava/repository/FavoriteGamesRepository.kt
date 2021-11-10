package com.mycorp.twitchapprxjava.repository

import androidx.paging.DataSource
import com.mycorp.twitchapprxjava.database.model.FavoriteGameData
import com.mycorp.twitchapprxjava.database.model.GameData
import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteGamesRepository {
    fun getFavoriteGamesFromDb(): DataSource.Factory<Int, FavoriteGameData>
    fun checkIsFavorite(gameId: String): Single<Int>
    fun insertFavoriteGame(gameData: GameData): Completable
    fun deleteByGameId(gameId: String): Completable
}