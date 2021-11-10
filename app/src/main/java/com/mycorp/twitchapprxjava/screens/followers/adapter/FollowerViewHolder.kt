package com.mycorp.twitchapprxjava.screens.followers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycorp.twitchapprxjava.GlideApp
import com.mycorp.twitchapprxjava.database.model.FollowerInfo
import com.mycorp.twitchapprxjava.databinding.FollowerItemViewBinding

class FollowerViewHolder(private val binding: FollowerItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(followerInfo: FollowerInfo) {
        with(binding) {
            GlideApp.with(itemView.context).load(followerInfo.photoUrl).into(followerPhoto)
            followerName.text = followerInfo.followerName
        }
    }

    companion object {
        fun from(parent: ViewGroup): FollowerViewHolder {
            return FollowerViewHolder(
                FollowerItemViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
}