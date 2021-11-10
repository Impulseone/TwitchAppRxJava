package com.mycorp.twitchapprxjava.screens.game

import androidx.lifecycle.MutableLiveData
import com.mycorp.twitchapprxjava.common.TCommand
import com.mycorp.twitchapprxjava.common.viewModel.BaseViewModel
import com.mycorp.twitchapprxjava.database.model.FollowerInfo
import com.mycorp.twitchapprxjava.database.model.SingleGameData
import com.mycorp.twitchapprxjava.common.helpers.GameDataViewState
import com.mycorp.twitchapprxjava.database.model.GameData
import com.mycorp.twitchapprxjava.repository.FollowersRepository
import com.mycorp.twitchapprxjava.repository.GamesRepository
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GameVM(
    private val followersRepository: FollowersRepository,
    private val gamesRepository: GamesRepository,
) : BaseViewModel() {

    private val gameLiveData = MutableLiveData<GameDataViewState<GameData>>()
    private val followersIdLiveData = MutableLiveData<List<String>>()
    private val isFavoriteLiveData = MutableLiveData<Boolean>()

    fun singleGameLiveData() = gameLiveData
    fun followersIdLiveData() = followersIdLiveData
    fun favoriteStateLiveData() = isFavoriteLiveData

    fun init(gameId: String) {
        isFavoriteLiveData.value = false
        getGameData(gameId)
        getFollowersListFromServer(gameId)
    }

    private fun getGameData(gameId: String) {
        gamesRepository.getGameDataById(gameId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                gameLiveData.value = GameDataViewState.success(it)
            }, {
                handleException(it)
            })
            .addToSubscription()
    }

    private fun getFollowersListFromServer(gameId: String) {
        followersRepository.getFollowersListFromServer(gameId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                followersIdLiveData.value = it.map {
                    it.followerId
                }
            }, {
                handleException(it)
            }).addToSubscription()
    }

    fun onLikeClicked() {
        isFavoriteLiveData
        isFavoriteLiveData.value = !isFavoriteLiveData.value!!
        showToast("Like Clicked")
    }
}