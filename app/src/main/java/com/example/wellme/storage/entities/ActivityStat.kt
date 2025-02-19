package com.example.wellme.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  id:AUTO_INC
 *  type
 *  date
 *  duration
 *  distance:NULL
 *  steps:NULL
 *  day:Int
 *  location:Location-> <latitude, longitude>
 **/

@Entity(tableName = "activity_stat")
data class ActivityStat (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val type:String,
    val date:String,
    val duration:Int,
    val distance:Double,
    val steps:Int,
    val day:Int,
    val latitude:Double,
    val longitude:Double
)