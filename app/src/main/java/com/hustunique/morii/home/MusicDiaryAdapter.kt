package com.hustunique.morii.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hustunique.morii.content.ContentActivity
import com.hustunique.morii.home.MusicDiaryAdapter.MyViewHolder
import com.hustunique.morii.util.DatabaseUtil
import com.hustunique.morii.util.MyApplication.Companion.externalPath
import com.hustunique.morii.util.MyApplication.Companion.musicTabList
import morii.R
import morii.databinding.CardviewItemBinding
import java.io.File


class MusicDiaryAdapter
    (private val activity: Activity, private val list: MutableList<MusicDiaryItem>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: CardviewItemBinding =
            CardviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val musicDiaryItem = list[position]
        musicDiaryItem.imagePath

        if (musicDiaryItem.imagePath != null)
            Glide.with(holder.itemView).load(musicDiaryItem.imagePath)
                .into(holder.itemBinding.PhotoTitle)
        else
            Glide.with(holder.itemView).load(musicTabList[musicDiaryItem.musicTabId].imageResId)
                .into(holder.itemBinding.PhotoTitle)

        holder.itemBinding.TextTitle.text = musicDiaryItem.title
        holder.itemBinding.TextDate.text = "# " + musicDiaryItem.date
        Log.d("RECYCLER", position.toString())
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, ContentActivity::class.java)
            intent.putExtra("diary", musicDiaryItem)
            val options = ActivityOptions
                .makeSceneTransitionAnimation(
                    activity,
                    Pair.create(holder.itemBinding.PhotoTitle, "photo")
                )
            activity.startActivity(intent, options.toBundle())
        }
        holder.itemView.setOnLongClickListener {
            val builder = MaterialAlertDialogBuilder(activity)
            builder.run {
                setItems(arrayOf("操作 1", "操作 2", "删除日记")) { _, which ->
                    if (which != 2) {
                        Toast.makeText(activity, "Not yet implemented.", Toast.LENGTH_SHORT).show()
                    } else if (which == 2) {
                        MaterialAlertDialogBuilder(activity).setTitle("确定要删除这个音乐日记吗？")
                            .setMessage("这个操作不可被撤销。")
                            .setNegativeButton("取消") { _: DialogInterface?, _: Int -> }
                            .setPositiveButton("确认") { _: DialogInterface?, _: Int ->
                                DatabaseUtil.deleteDiary(musicDiaryItem.itemID)
                                DatabaseUtil.deleteAudioFile(
                                    musicDiaryItem.title,
                                    musicDiaryItem.date
                                )
                                Log.d(TAG, "deletePosition: " + holder.layoutPosition)
                                notifyItemRemoved(holder.layoutPosition)
                                list.removeAt(holder.layoutPosition)
                            }
                            .show()
                    }
                }
                show()
            }

            true
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    //内部类，绑定控件
    inner class MyViewHolder(val itemBinding: CardviewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    companion object {
        private const val TAG = "MusicDiaryAdapter"
    }
}