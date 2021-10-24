package com.mycorp.twitchapprxjava.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import com.mycorp.twitchapprxjava.data.storage.model.GameItemDataDto
import com.mycorp.twitchapprxjava.domain.use_cases.GetFromDbUseCase
import com.mycorp.twitchapprxjava.domain.use_cases.GetFromServerUseCase
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GameItemFragmentVM(
    private val getFromServerUseCase: GetFromServerUseCase,
    private val getFromDbUseCase: GetFromDbUseCase
) : BaseViewModel() {

    private var gameItemLiveData: MutableLiveData<GameDataViewState<GameItemDataDto>> =
        MutableLiveData()

    fun gameItemLiveData() = gameItemLiveData

    fun getGameItemDataFromServer(id: String) {
        getFromServerUseCase.getGameItemData(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(gameDataObserver(sourceType = SourceType.SERVER))
    }

    private fun gameDataObserver(sourceType: SourceType): SingleObserver<GameItemDataDto> {
        return object : SingleObserver<GameItemDataDto> {
            override fun onSuccess(gameData: GameItemDataDto) {
                showToast("get data success")
                gameItemLiveData.postValue(
                    GameDataViewState.success(
                        data = gameData,
                    )
                )
            }

            override fun onError(e: Throwable) {
                showToast(e.message!!)
                gameItemLiveData.postValue(
                    GameDataViewState.error()
                )
                handleException(e as Exception)
            }

            override fun onSubscribe(d: Disposable) {
                gameItemLiveData.postValue(
                    GameDataViewState.loading()
                )
            }
        }
    }
}