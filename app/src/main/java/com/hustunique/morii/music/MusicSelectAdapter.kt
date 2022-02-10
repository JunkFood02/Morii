package com.hustunique.morii.music

import android.annotation.SuppressLint
import morii.R
import com.bumptech.glide.Glide
import com.hustunique.morii.util.MyApplication
import android.content.*
import android.view.ViewGroup
import android.view.LayoutInflater
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MusicSelectAdapter(private val context: Context) :
    RecyclerView.Adapter<MusicSelectAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context).inflate(R.layout.musictab_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Glide.with(holder.itemView)
            .load(MyApplication.musicTabList[position].imageResId)
            .into(holder.imageView)
        Log.d("RECYCLER", position.toString())
        holder.itemView.setOnClickListener { v: View? -> }
    }

    override fun getItemCount(): Int {
        return MyApplication.musicTabList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.EmotionPhoto)

    }
}