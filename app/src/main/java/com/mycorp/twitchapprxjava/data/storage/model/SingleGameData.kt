package com.mycorp.twitchapprxjava.data.storage.model

import android.os.Parcelable
import com.mycorp.twitchapprxjava.data.storage.room.entities.GameItemDataEntity
import kotlinx.parcelize.Parcelize

@Parcelize
class SingleGameData(
    val id: String,
    val name: String,
    val photoUrl: String,
    var followersIds: List<String> = mutableListOf(),
    var isLiked: Boolean = false
) : Parcelable {
    companion object {
        fun fromGameData(gameData: GameData, followers: List<FollowerInfo>): SingleGameData {
            return SingleGameData(
                gameData.id.toString(),
                gameData.name,
                gameData.logoUrl,
                followers.map { it.followerId }
            )
        }

        fun fromGameItemDataEntity(gameItemDataEntity: GameItemDataEntity): SingleGameData {
            return SingleGameData(
                gameItemDataEntity.id,
                gameItemDataEntity.name,
                gameItemDataEntity.photoUrl,
                gameItemDataEntity.followersIds,
                gameItemDataEntity.isLiked
            )
        }
    }
}