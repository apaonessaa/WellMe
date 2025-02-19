package com.example.wellme.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.wellme.storage.entities.ActivityStat

@Dao
interface ActivityStatDao {
    @Insert
    fun insert(stat: ActivityStat)
}