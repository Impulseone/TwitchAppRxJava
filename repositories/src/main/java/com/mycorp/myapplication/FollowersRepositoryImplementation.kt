package com.mycorp.myapplication

import com.mycorp.api.controllers.FollowersController
import com.mycorp.database.storage.FollowersStorage
import com.mycorp.model.FollowerInfo
import io.reactivex.Single

class FollowersRepositoryImplementation(
    private val followersController: FollowersController,
    private val followersStorage: FollowersStorage
) : FollowersRepository {
    override fun fetchFollowers(id: String): Single<List<FollowerInfo>> {
        return followersController.getGameItemDataFromNetwork(id)
            .map {
                it.follows ?: listOf()
            }
            .map { list ->
                list.filterNotNull().map { it.toModel() }
            }
            .flatMap {
                followersStorage.insertFollowersData(it, id)
                    .andThen(Single.just(it))
            }
    }

    override fun getFollowersByGameId(gameId: String) =
        followersStorage.getFollowersByGameId(gameId)
}