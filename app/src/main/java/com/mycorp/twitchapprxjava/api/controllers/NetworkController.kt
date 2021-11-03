package com.mycorp.twitchapprxjava.api.controllers

import com.mycorp.twitchapprxjava.database.model.gameItemDataResponse.GameItemDataDto
import com.mycorp.twitchapprxjava.database.model.topGamesResponse.TopGamesResponseDto
import io.reactivex.Single

interface NetworkController {
   fun getDataFromNetwork(): Single<TopGamesResponseDto>
   fun getGameItemDataFromNetwork(id:String): Single<GameItemDataDto>
}