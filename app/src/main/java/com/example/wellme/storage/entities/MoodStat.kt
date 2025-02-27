package com.example.wellme.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  id:AUTO_INC
 *  date
 *  hour:NULL
 *  mood
 *  <details:Detail> -> string, separator _ :NULL
 *  <causes:Cause> -> string, separator _ :NULL
 *  note: NULL
 **/

@Entity(tableName = "mood_stat")
data class MoodStat (
    @PrimaryKey(autoGenerate = true)    val id: Int = 0,
    @ColumnInfo(name = "weekday")   val weekday: Int,
    @ColumnInfo(name = "date")      val date: String,
    @ColumnInfo(name = "hour")      val hour: String?,
    @ColumnInfo(name = "mood")      val mood:String,
    @ColumnInfo(name = "detail")    val detail:String?,
    @ColumnInfo(name = "cause")     val cause:String?,
    @ColumnInfo(name = "note")      val note:String?
)