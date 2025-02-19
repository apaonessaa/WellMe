package com.example.wellme.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wellme.storage.entities.MoodStat

@Dao
interface MoodStatDao {
    @Insert
    fun insert(stat: MoodStat)

    @Query("SELECT * FROM mood_stat")
    fun getAllMoodStat(): LiveData<List<MoodStat>>
}