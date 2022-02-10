package com.hustunique.morii.design

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.hustunique.morii.util.MyApplication
import morii.R

class WhiteNoiseAdapter     //类型待定
//构造方法，传入数据
    (private val context: Context) : RecyclerView.Adapter<WhiteNoiseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //创建ViewHolder，返回每一项的布局
        val inflater =
            LayoutInflater.from(context).inflate(R.layout.musicdesign_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //将数据和控件绑定
        //
        holder.Select = false
        holder.imageView.setImageResource(
            MyApplication.Companion.soundItemList.get(position).iconResId
        )
        holder.textView.setText(MyApplication.Companion.soundItemList.get(position).soundName)
        Log.d("RECYCLER2", position.toString())
        //holder.itemView.setOnTouchListener(new Drag(soundItemList.get(position % 7).getIconResId()));
        holder.itemView.setOnTouchListener(StartDrag(position))
        holder.itemView.setOnClickListener { v: View? ->
            if (!holder.Select) holder.textView.setTextColor(Color.parseColor("#FF0000")) else holder.textView.setTextColor(
                Color.parseColor("#FF888888")
            )
            holder.Select = !holder.Select
        }
    }

    override fun getItemCount(): Int {
        //返回Item总条数
        return MyApplication.Companion.soundItemList.size
    }

    //内部类，绑定控件
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ShapeableImageView
        var textView: TextView
        var Select = false

        init {
            imageView = itemView.findViewById(R.id.RoundIcon)
            textView = itemView.findViewById(R.id.IconName)
        }
    }
}