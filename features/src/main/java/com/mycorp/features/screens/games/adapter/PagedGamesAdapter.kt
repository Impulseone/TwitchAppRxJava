package com.mycorp.features.screens.games.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.mycorp.common.helpers.BaseItemCallback
import com.mycorp.model.ListItemData

class PagedGamesAdapter(
    private val itemClicked: (Int) -> Unit
) : PagedListAdapter<ListItemData<GameListItem>, GameViewHolder>(
    BaseItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameViewHolder.from(parent, itemClicked)

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it.data)
        }
    }
}