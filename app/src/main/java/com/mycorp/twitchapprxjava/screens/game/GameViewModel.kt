package com.mycorp.twitchapprxjava.screens.game

import androidx.annotation.DrawableRes
import com.mycorp.twitchapprxjava.R
import com.mycorp.twitchapprxjava.common.Data
import com.mycorp.twitchapprxjava.common.helpers.GameDataViewState
import com.mycorp.twitchapprxjava.common.viewModel.BaseViewModel
import com.mycorp.twitchapprxjava.models.GameData
import com.mycorp.twitchapprxjava.repository.FavoriteGamesRepository
import com.mycorp.twitchapprxjava.repository.FollowersRepository
import com.mycorp.twitchapprxjava.repository.GamesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GameViewModel(
    private val followersRepository: FollowersRepository,
    private val gamesRepository: GamesRepository,
    private val favoriteGamesRepository: FavoriteGamesRepository
) : BaseViewModel() {

    private var gameId: String? = null
    private val isFavoriteLiveData = Data<Boolean>()

    val gameLiveData = Data<GameDataViewState<GameData>>()
    val followersIdLiveData = Data<List<String>>()
    val favoriteResLiveData = Data<@DrawableRes Int>()

    fun init(gameId: String) {
        this.gameId = gameId
        getGameData()
        fetchFollowers()
    }

    override fun getDataFromDb() {
        getFollowers()
    }

    private fun getGameData() {
        gameId?.let { gameId ->
            gamesRepository.getGameDataById(gameId)
                .toObservable()
                .flatMap({
                    favoriteGamesRepository.checkIsFavorite(gameId).toObservable()
                }, { gameData: GameData, isFavorite: Int ->
                    Pair(gameData, isFavorite)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    gameLiveData.value = GameDataViewState.success(result.first)
                    isFavoriteLiveData.value = result.second > 0
                    favoriteResLiveData.value =
                        if (isFavoriteLiveData.value!!) R.drawable.like_filled_icon else R.drawable.like_outlined_icon
                },
                    { throwable ->
                        handleException(throwable)
                    })
                .addToSubscription()
        }
    }

    private fun fetchFollowers() {
        gameId?.let {
            followersRepository.fetchFollowers(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    followersIdLiveData.value = list.map { followerInfo ->
                        followerInfo.followerId
                    }
                }, { throwable ->
                    handleException(throwable)
                }).addToSubscription()
        }
    }

    private fun getFollowers() {
        gameId?.let {
            followersRepository.getFollowersIdByGameId(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ list ->
                    followersIdLiveData.value = list
                }, { throwable ->
                    handleException(throwable as Exception)
                }).addToSubscription()
        }
    }

    fun onLikeClicked() {
        isFavoriteLiveData.value = !isFavoriteLiveData.value!!
        favoriteResLiveData.value =
            if (isFavoriteLiveData.value!!) R.drawable.like_filled_icon else R.drawable.like_outlined_icon
        (if (isFavoriteLiveData.value!!) {
            favoriteGamesRepository.insertFavoriteGame(gameLiveData.value?.data!!)
        } else {
            favoriteGamesRepository.deleteByGameId(gameId!!)
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                handleException(it)
            }).addToSubscription()

    }

    fun launchFollowerScreen() {
        openFragmentCommand.value = gameId
    }
}