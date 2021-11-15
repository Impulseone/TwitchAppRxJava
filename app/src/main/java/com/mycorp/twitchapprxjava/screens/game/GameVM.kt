package com.mycorp.twitchapprxjava.screens.game

import androidx.annotation.DrawableRes
import com.mycorp.twitchapprxjava.R
import com.mycorp.twitchapprxjava.common.Data
import com.mycorp.twitchapprxjava.common.TCommand
import com.mycorp.twitchapprxjava.common.helpers.GameDataViewState
import com.mycorp.twitchapprxjava.common.viewModel.BaseViewModel
import com.mycorp.twitchapprxjava.database.model.FollowerInfo
import com.mycorp.twitchapprxjava.database.model.GameData
import com.mycorp.twitchapprxjava.repository.FavoriteGamesRepository
import com.mycorp.twitchapprxjava.repository.FollowersRepository
import com.mycorp.twitchapprxjava.repository.GamesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GameVM(
    private val followersRepository: FollowersRepository,
    private val gamesRepository: GamesRepository,
    private val favoriteGamesRepository: FavoriteGamesRepository
) : BaseViewModel() {

    private var gameId: String? = null

    val gameLiveData = Data<GameDataViewState<GameData>>()
    val followersIdLiveData = Data<List<String>>()
    private val isFavoriteLiveData = Data<Boolean>()
    val favoriteResLiveData = Data<@DrawableRes Int>()
    val launchFollowerScreenCommand = TCommand<String?>()

    fun init(gameId: String) {
        this.gameId = gameId
        getGameData()
        getFollowersListFromServer()
        checkIsFavorite()
    }

    private fun getGameData() {
        gameId?.let {
            gamesRepository.getGameDataById(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    gameLiveData.value = GameDataViewState.success(it)
                }, {
                    handleException(it)
                })
                .addToSubscription()
        }
    }

    private fun getFollowersListFromServer() {
        gameId?.let {
            followersRepository.getFollowersListFromServer(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    followersIdLiveData.value = it.map {
                        it.followerId
                    }
                    saveFollowersToDb(it)
                }, {
                    handleException(it)
                    getFollowersFromDb()
                }).addToSubscription()
        }
    }

    private fun saveFollowersToDb(followersInfo: List<FollowerInfo>) {
        gameId?.let {
            followersRepository.insertFollowersToDb(followersInfo, it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }

    private fun getFollowersFromDb() {
        gameId?.let {
            followersRepository.getFollowersIdFromDbByGameId(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    followersIdLiveData.value = it
                }, {
                    handleException(it as Exception)
                }).addToSubscription()
        }
    }

    private fun checkIsFavorite() {
        gameId?.let {
            favoriteGamesRepository.checkIsFavorite(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isFavoriteLiveData.value = it > 0
                    favoriteResLiveData.value =
                        if (isFavoriteLiveData.value!!) R.drawable.like_filled_icon else R.drawable.like_outlined_icon
                }, {
                    handleException(it)
                }).addToSubscription()
        }
    }

    fun onLikeClicked() {
        isFavoriteLiveData.value = !isFavoriteLiveData.value!!
        if (isFavoriteLiveData.value!!) {
            favoriteResLiveData.value = R.drawable.like_filled_icon
            gameId?.let {
                favoriteGamesRepository.deleteByGameId(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {
                        handleException(it)
                    }).addToSubscription()
            }
        } else {
            favoriteResLiveData.value = R.drawable.like_outlined_icon
            favoriteGamesRepository.insertFavoriteGame(gameLiveData.value?.data!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    handleException(it)
                }).addToSubscription()
        }

    }

    fun launchFollowerScreen() {
        launchFollowerScreenCommand.value = gameId
    }

}