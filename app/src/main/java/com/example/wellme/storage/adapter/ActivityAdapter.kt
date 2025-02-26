package com.example.wellme.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wellme.R
import com.example.wellme.ViewItemActivity
import com.example.wellme.storage.entities.ActivityStat

class ActivityAdapter : RecyclerView.Adapter<ActivityAdapter.ExerciseViewHolder>() {
    private var exerciseList: List<ActivityStat> = emptyList()

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseName = itemView.findViewById<TextView>(R.id.activityName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity, parent,false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExercise = exerciseList[position]
        holder.exerciseName.text = currentExercise.id.toString()+". "+currentExercise.type
        holder.exerciseName.setOnClickListener {
            val intent = Intent(holder.itemView.context, ViewItemActivity::class.java).apply {
                putExtra("ID", currentExercise.id)
                putExtra("Type", currentExercise.type)
                putExtra("Date", currentExercise.date)
                putExtra("Duration", currentExercise.duration)
                putExtra("Distance", currentExercise.distance)
                putExtra("Steps", currentExercise.steps)
                putExtra("Day", currentExercise.day)
                putExtra("Latitude", currentExercise.latitude)
                putExtra("Longitude", currentExercise.longitude)

                putExtra("Dato", "Esercizio")
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = exerciseList.size

    fun setExercices(exercices: List<ActivityStat>) {
        exerciseList = exercices
        notifyDataSetChanged()
    }

    fun getExercise(id: Int) : ActivityStat {
        return exerciseList[id]
    }
}