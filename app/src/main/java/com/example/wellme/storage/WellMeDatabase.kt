package com.example.wellme.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wellme.storage.dao.ActivityStatDao
import com.example.wellme.storage.dao.MoodStatDao
import com.example.wellme.storage.entities.ActivityStat
import com.example.wellme.storage.entities.MoodStat

@Database(entities = [MoodStat::class, ActivityStat::class], version = 1)
abstract class WellMeDatabase:RoomDatabase() {
    abstract fun moodStatDao() : MoodStatDao
    abstract fun activityStatDao() : ActivityStatDao

    companion object {
        @Volatile
        private var INSTANCE: WellMeDatabase ?= null

        fun getDatabase(context: Context) : WellMeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WellMeDatabase::class.java, "wellme_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}