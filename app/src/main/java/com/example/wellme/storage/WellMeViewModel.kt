package com.example.wellme.storage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wellme.storage.dao.MoodStatDao
import com.example.wellme.storage.entities.ActivityStat
import com.example.wellme.storage.entities.MoodStat
import kotlinx.coroutines.launch

class WellMeViewModel(application: Application) : AndroidViewModel(application) {
    private val moodStatDao = WellMeDatabase.getDatabase(application).moodStatDao()
    private val activityStatDao = WellMeDatabase.getDatabase(application).activityStatDao()

    fun insertMoodStat(stat: MoodStat) {
        Thread {
            moodStatDao.insert(stat)
        }.start()
    }

    fun getAllMoodStat(): LiveData<List<MoodStat>> {
        return moodStatDao.getAllMoodStat()
    }

    fun insertActivityStat(stat: ActivityStat) {
        Thread {
            activityStatDao.insert(stat)
        }.start()
    }
}