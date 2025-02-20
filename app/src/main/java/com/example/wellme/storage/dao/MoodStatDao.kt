package com.example.wellme.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wellme.storage.entities.MoodStat

@Dao
interface MoodStatDao {
    @Query("SELECT * FROM mood_stat")
    fun getAll(): List<MoodStat>

    @Insert
    fun insertAll(vararg stats: MoodStat)
}