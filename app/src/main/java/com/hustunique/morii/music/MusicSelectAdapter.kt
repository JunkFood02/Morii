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

class MusicSelectAdapter     //类型待定
//构造方法，传入数据
    (private val context: Context, presenter: MusicSelectContract.IPresenter?) :
    RecyclerView.Adapter<MusicSelectAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //创建ViewHolder，返回每一项的布局
        val inflater = LayoutInflater.from(context).inflate(R.layout.musictab_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //将数据和控件绑定
        //
        Glide.with(holder.itemView)
            .load(MyApplication.musicTabList[position].imageResId)
            .into(holder.imageView)
        Log.d("RECYCLER", position.toString())
        holder.itemView.setOnClickListener { v: View? -> }
    }

    override fun getItemCount(): Int {
        //返回Item总条数
        return MyApplication.Companion.musicTabList.size
    }

    //内部类，绑定控件
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.EmotionPhoto)
        }
    }
}