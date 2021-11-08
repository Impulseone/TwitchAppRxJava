package com.mycorp.twitchapprxjava.database.room

import com.mycorp.twitchapprxjava.database.Storage
import com.mycorp.twitchapprxjava.database.model.FollowerInfo
import com.mycorp.twitchapprxjava.database.model.GameData
import com.mycorp.twitchapprxjava.database.model.SingleGameData
import com.mycorp.twitchapprxjava.database.room.dao.FollowersDao
import com.mycorp.twitchapprxjava.database.room.dao.GameDataDao
import com.mycorp.twitchapprxjava.database.room.dao.SingleGameDataDao
import com.mycorp.twitchapprxjava.database.room.entities.FollowerInfoEntity
import com.mycorp.twitchapprxjava.database.room.entities.GameDataEntity
import com.mycorp.twitchapprxjava.database.room.entities.SingleGameDataEntity
import io.reactivex.Single

class RoomStorage(
    private val gameDataDao: GameDataDao,
    private val followersDao: FollowersDao,
    private val singleGameDataDao: SingleGameDataDao
) :
    Storage {

    override fun getGamesDataFromDb() = gameDataDao.getAllGames()

    override fun getGameDataEntityById(id: String) = gameDataDao.getGameById(id)

    override fun getFollowersFromDbByIds(followerIds: List<String>) =
        followersDao.getByIds(followerIds)

    override fun getSingleGameDataEntityById(gameId: String) = singleGameDataDao.getById(gameId)

    override fun getFavoriteGamesFromDb() = singleGameDataDao.getFavorites()

    override fun insertGamesData(gamesData: List<GameData>) =
        gameDataDao.insertAll(gamesData.map { GameDataEntity.fromGameData(it) })

    override fun insertFollowersData(followersData: List<FollowerInfo>) =
        followersDao.insertAll(followersData.map { FollowerInfoEntity.fromFollowerInfo(it) })

    override fun saveSingleGameData(singleGameData: SingleGameData) =
        singleGameDataDao.insert(SingleGameDataEntity.fromSingleGameData(singleGameData))

}