package com.mycorp.twitchapprxjava.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mycorp.twitchapprxjava.models.FollowerInfo

@Entity
class FollowerInfoEntity(
    @PrimaryKey val id: String,
    val followerName: String,
    val photoUrl: String
) {
    companion object {
        fun fromFollowerInfo(followerInfo: FollowerInfo) = FollowerInfoEntity(
            followerInfo.followerId, followerInfo.followerName, followerInfo.photoUrl
        )
    }
}