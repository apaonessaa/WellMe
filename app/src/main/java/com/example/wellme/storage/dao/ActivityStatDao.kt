package com.example.wellme.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wellme.storage.entities.ActivityStat

@Dao
interface ActivityStatDao {
    @Query("SELECT * FROM activity_stat")
    fun getAll(): List<ActivityStat>

    @Insert
    fun insertAll(vararg stats: ActivityStat)
}