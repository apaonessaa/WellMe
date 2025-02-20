package com.example.wellme.storage.entities

import androidx.room.ColumnInfo
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
    @PrimaryKey(autoGenerate = true)    val id:Int = 0,
    @ColumnInfo(name = "type")      val type:String,
    @ColumnInfo(name = "date")      val date:String,
    @ColumnInfo(name = "duration")  val duration:Int,
    @ColumnInfo(name = "distance")  val distance:Double?,
    @ColumnInfo(name = "steps")     val steps:Int?,
    @ColumnInfo(name = "day")       val day:Int,
    @ColumnInfo(name = "latitude")  val latitude:Double,
    @ColumnInfo(name = "longitude") val longitude:Double
)