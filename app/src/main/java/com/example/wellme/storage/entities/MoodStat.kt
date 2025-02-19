package com.example.wellme.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  id:AUTO_INC
 *  date
 *  hour:NULL
 *  mood
 *  <details:Detail> -> string, separator _
 *  <causes:Cause> -> string, separator _
 *  note
 **/

@Entity(tableName = "mood_stat")
data class MoodStat (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val date:String,
    val hour:String,
    val mood:String,
    val detail:String,
    val cause:String,
    val note:String
)