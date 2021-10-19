package com.mycorp.twitchapprxjava.data.storage.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mycorp.twitchapprxjava.data.storage.model.GameDataTable

@Database(entities = [GameDataTable::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val gameDataDao : GameDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "games_database")
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}