package com.example.wellme.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wellme.R
import com.example.wellme.storage.entities.MoodStat
import com.example.wellme.ViewItemActivity

class MoodAdapter : RecyclerView.Adapter<MoodAdapter.EmotionViewHolder>() {
    private var emotionList: List<MoodStat> = emptyList()

    class EmotionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moodName: TextView = itemView.findViewById(R.id.moodName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mood, parent, false)
        return EmotionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        val currentEmotion = emotionList[position]
        holder.moodName.text = currentEmotion.mood
        holder.moodName.setOnClickListener {
            val intent = Intent(holder.itemView.context, ViewItemActivity::class.java).apply {
                putExtra("ID", currentEmotion.id)
                putExtra("Date", currentEmotion.date)
                putExtra("Hour", currentEmotion.hour)
                putExtra("Mood", currentEmotion.mood)
                putExtra("Detail", currentEmotion.detail)
                putExtra("Note", currentEmotion.note)
                putExtra("Cause", currentEmotion.cause)

                putExtra("Dato", "Emozione")
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = emotionList.size

    fun setEmotions(emotions: List<MoodStat>) {
        emotionList = emotions
        notifyDataSetChanged()
    }

    fun getEmotion(id: Int): MoodStat? {
        return emotionList.find { it.id == id }
    }
}
