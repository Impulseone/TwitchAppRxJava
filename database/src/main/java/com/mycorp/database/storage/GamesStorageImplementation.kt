package com.mycorp.database.storage

import com.mycorp.database.dao.GameDataDao
import com.mycorp.database.entities.GameDataEntity
import com.mycorp.model.GameData

class GamesStorageImplementation(
    private val gameDataDao: GameDataDao,
) :
    GamesStorage {

    override fun getGamesData() = gameDataDao.getAllGames()

    override fun getGameDataEntityById(id: String) = gameDataDao.getGameById(id)

    override fun insertGamesData(gamesData: List<GameData>) =
        gameDataDao.insertAll(gamesData.map { GameDataEntity(it) })
}