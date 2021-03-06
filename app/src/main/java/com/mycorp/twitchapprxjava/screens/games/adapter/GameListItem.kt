package com.mycorp.twitchapprxjava.screens.games.adapter

import android.content.Context
import com.mycorp.twitchapprxjava.R
import com.mycorp.twitchapprxjava.models.GameData

data class GameListItem(
    val id: String,
    val name: String,
    val logoUrl: String,
    val channels: String,
    val watchers: String
) {
    constructor(context: Context, game: GameData) : this(
        id = game.id,
        name = game.name,
        logoUrl = game.logoUrl,
        channels = context.getString(R.string.scr_favorite_game_list_item_gameName, game.channelsCount.toString()),
        watchers = context.getString(R.string.scr_favorite_game_list_item_watchersCount, game.watchersCount.toString())
    )
}