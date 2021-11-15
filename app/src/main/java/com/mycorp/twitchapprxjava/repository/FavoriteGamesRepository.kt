package com.mycorp.twitchapprxjava.repository

import androidx.paging.DataSource
import com.mycorp.twitchapprxjava.models.FavoriteGameData
import com.mycorp.twitchapprxjava.models.GameData
import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteGamesRepository {
    fun getFavoriteGames(): DataSource.Factory<Int, FavoriteGameData>
    fun checkIsFavorite(gameId: String): Single<Int>
    fun insertFavoriteGame(gameData: GameData): Completable
    fun deleteByGameId(gameId: String): Completable
}