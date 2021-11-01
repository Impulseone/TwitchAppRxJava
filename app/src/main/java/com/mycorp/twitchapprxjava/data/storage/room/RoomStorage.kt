package com.mycorp.twitchapprxjava.data.storage.room

import androidx.paging.DataSource
import com.mycorp.twitchapprxjava.data.storage.Storage
import com.mycorp.twitchapprxjava.data.storage.model.FollowerInfo
import com.mycorp.twitchapprxjava.data.storage.model.GameData
import com.mycorp.twitchapprxjava.data.storage.model.SingleGameData
import com.mycorp.twitchapprxjava.data.storage.room.dao.FollowersDao
import com.mycorp.twitchapprxjava.data.storage.room.dao.GameDataDao
import com.mycorp.twitchapprxjava.data.storage.room.dao.SingleGameDataDao
import com.mycorp.twitchapprxjava.data.storage.room.entities.FollowerInfoEntity
import com.mycorp.twitchapprxjava.data.storage.room.entities.GameDataEntity
import com.mycorp.twitchapprxjava.data.storage.room.entities.SingleGameDataEntity

class RoomStorage(
    private val gameDataDao: GameDataDao,
    private val followersDao: FollowersDao,
    private val singleGameDataDao: SingleGameDataDao
) :
    Storage {

    override fun getGamesDataFromDb() = gameDataDao.getAllGames()

    override fun getFollowersFromDbByIds(followerIds: List<String>) =
        followersDao.getByIds(followerIds)

    override fun getGameItemData(gameId: String) = singleGameDataDao.getById(gameId)

    override fun getFavoriteGamesFromDb() = singleGameDataDao.getFavorites()

    override fun insertGamesData(gamesData: List<GameData>) =
        gameDataDao.insertAll(gamesData.map { GameDataEntity.fromGameData(it) })

    override fun insertFollowersData(followersData: List<FollowerInfo>) =
        followersDao.insertAll(followersData.map { FollowerInfoEntity.fromFollowerInfo(it) })

    override fun saveSingleGameData(singleGameData: SingleGameData) =
        singleGameDataDao.insert(SingleGameDataEntity.fromSingleGameData(singleGameData))

}