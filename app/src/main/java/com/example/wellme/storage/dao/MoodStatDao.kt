package com.example.wellme.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wellme.storage.entities.MoodStat

@Dao
interface MoodStatDao {
    @Query("SELECT * FROM mood_stat")
    fun getAll(): List<MoodStat>

    @Insert
    suspend fun insertAll(vararg stats: MoodStat)

    @Query("SELECT * FROM mood_stat")
    fun getAllEmotions(): LiveData<List<MoodStat>>

    @Query("SELECT * FROM mood_stat WHERE id = :id")
    fun getEmotionById(id: Int): LiveData<MoodStat>
}