package com.example.wellme.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wellme.storage.entities.ActivityStat

@Dao
interface ActivityStatDao {
    @Query("SELECT * FROM activity_stat")
    fun getAll(): List<ActivityStat>

    @Query("SELECT * FROM activity_stat")
    fun getAllExercises(): LiveData<List<ActivityStat>>

    @Query("SELECT * FROM activity_stat WHERE id = :id")
    fun getExerciseById(id: Int): LiveData<ActivityStat>

    @Insert
    suspend fun insertAll(vararg stats: ActivityStat)
}