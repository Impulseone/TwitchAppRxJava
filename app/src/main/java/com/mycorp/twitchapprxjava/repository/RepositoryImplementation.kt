package com.mycorp.twitchapprxjava.repository

import com.mycorp.twitchapprxjava.api.controllers.NetworkController
import com.mycorp.twitchapprxjava.database.Storage
import com.mycorp.twitchapprxjava.database.model.FollowerInfo
import com.mycorp.twitchapprxjava.database.model.GameData
import com.mycorp.twitchapprxjava.database.model.SingleGameData
import io.reactivex.Single

class RepositoryImplementation(
    private val networkController: NetworkController,
    private val storage: Storage
) : Repository {

    override fun getGamesDataListFromServer(limit: Int, offset: Int) =
        networkController.getDataFromNetwork(limit, offset).map {
            it.toListOfGameData()
        }

    override fun getFollowersListFromServer(id: String): Single<List<FollowerInfo>> =
        networkController.getGameItemDataFromNetwork(id).map {
            it.follows?.map { FollowerInfo.fromFollowerDto(it!!) }
        }

    override fun getGamesDataFromDb() = storage.getGamesDataFromDb().map {
        it.map { GameData.fromEntity(it) }
    }

    override fun getGameDataById(id: String) =
        storage.getGameDataEntityById(id).map {
            GameData.fromEntity(it)
        }

    override fun getFollowersListFromDbByIds(followerIds: List<String>) =
        storage.getFollowersFromDbByIds(followerIds)
            .map { it.map { FollowerInfo.fromFollowerInfoEntity(it) } }

    override fun getSingleGameDataFromDb(gameId: String) =
        storage.getSingleGameDataEntityById(gameId)
            .map { SingleGameData.fromGameItemDataEntity(it) }

    override fun getFavoriteGamesFromDb() = storage.getFavoriteGamesFromDb()
        .map {
            SingleGameData.fromGameItemDataEntity(it)
        }

    override fun insertGamesDataToDb(gameDataEntities: List<GameData>) =
        storage.insertGamesData(gamesData = gameDataEntities)

    override fun insertFollowersToDb(followersList: List<FollowerInfo>) =
        storage.insertFollowersData(followersList)

    override fun saveSingleGameDataToDb(singleGameData: SingleGameData) =
        storage.saveSingleGameData(singleGameData)


}