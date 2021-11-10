package com.mycorp.twitchapprxjava.database.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mycorp.twitchapprxjava.database.room.entities.FavoriteGameDataEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteGameDataDao {
    @Insert(onConflict = REPLACE)
    fun insert(favoriteGameDataEntity: FavoriteGameDataEntity): Completable

    @Query("SELECT COUNT() FROM FavoriteGameDataEntity WHERE id = :id")
    fun checkExist(id: String): Single<Int>

    @Query("select * from FavoriteGameDataEntity")
    fun getAll(): DataSource.Factory<Int, FavoriteGameDataEntity>

    @Query("DELETE FROM FavoriteGameDataEntity WHERE id = :gameId")
    fun deleteByGameId(gameId: String): Completable
}