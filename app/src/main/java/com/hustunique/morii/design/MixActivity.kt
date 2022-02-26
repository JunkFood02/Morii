package com.hustunique.morii.design

import android.content.Intent
import android.annotation.SuppressLint
import com.hustunique.morii.util.BaseActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.hustunique.morii.util.AudioExoPlayerUtil
import android.os.Bundle
import morii.R
import com.bumptech.glide.Glide
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.MyApplication
import com.hustunique.morii.edit.EditActivity
import com.hustunique.morii.database.SoundItemInfo
import android.view.animation.AlphaAnimation
import android.util.Log
import android.view.*
import android.view.View.OnDragListener
import android.view.animation.Animation
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import morii.databinding.ActivityMixBinding
import java.util.ArrayList
import java.util.HashMap

class MixActivity : BaseActivity() {
    private val squareLayoutList: MutableList<ConstraintLayout> = ArrayList(9)
    private val squareList: MutableList<ImageView> = ArrayList(9)
    private val constraintLayoutSoundItemMap: MutableMap<ConstraintLayout, SoundItem?> = HashMap(9)
    private val positionSoundItemIdMap: MutableMap<Int, Int> = HashMap(9)
    private val animationDuration = 150
    private val hints: MutableList<View> = ArrayList()
    private lateinit var binding: ActivityMixBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMixBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initLists()
        val diary = intent.getSerializableExtra("diary") as MusicDiaryItem
        Glide.with(this)
            .load(MyApplication.musicTabList[diary.musicTabId].imageResId)
            .into(binding.imageViewCard)
        binding.helpButton.setOnClickListener { v: View ->
            if (binding.helpMessage.visibility == View.GONE) startFadeIn(
                binding.helpMessage
            ) else startFadeOut(binding.helpMessage)
        }
        binding.helpMessage.setOnClickListener { v: View ->
            if (v.visibility == View.VISIBLE) startFadeOut(
                binding.helpMessage
            )
        }
        binding.playbackButton.setOnClickListener { v: View ->
            if (playbackStatus == 1) {
                AudioExoPlayerUtil.startAllPlayers()
                Glide.with(this).load(R.drawable.outline_pause_24).into(
                    binding.playbackButtonImage
                )
            } else {
                AudioExoPlayerUtil.pauseAllPlayers()
                Glide.with(this).load(R.drawable.round_play_arrow_24).into(
                    binding.playbackButtonImage
                )
            }
            playbackStatus = -playbackStatus
        }
        binding.okayMix.setOnClickListener { v: View ->
            val intent = Intent(this, EditActivity::class.java)
            diary.soundItemInfoList.clear()
            for ((key, value) in positionSoundItemIdMap) {
                diary.soundItemInfoList.add(SoundItemInfo(key, value))
                Log.d("activityData", "key = $key value = $value")
            }
            intent.putExtra("diary", diary)
            startActivity(intent)
        }
        binding.backLayoutSelectMix.setOnClickListener { v: View -> onBackPressed() }
        for (constraintLayout in squareLayoutList) {
            constraintLayoutSoundItemMap[constraintLayout] = null
        }
        for (constraintLayout in constraintLayoutSoundItemMap.keys) {
            constraintLayout.setOnDragListener(label@ OnDragListener { v: View, event: DragEvent ->
                val rmposition: Int
                val position = squareLayoutList.indexOf(constraintLayout)
                val soundItemId: Int
                val iconID: Int
                if (event.action == DragEvent.ACTION_DROP) {
                    rmposition = event.clipData.getItemAt(0).intent.getIntExtra("position", -1)
                    if (rmposition != -1) {
                        Log.d(TAG, "3")
                        stopPlayingSoundItem(positionSoundItemIdMap[rmposition]!!, rmposition)
                        positionSoundItemIdMap.remove(rmposition)
                        constraintLayoutSoundItemMap.remove(squareLayoutList[rmposition])
                        squareLayoutList[rmposition].setOnTouchListener(null)
                    }
                    iconID = event.clipData.getItemAt(0).intent.getIntExtra(
                        "ImageID",
                        R.drawable.outline_air_24
                    )
                    constraintLayout.alpha = 1.0f
                    soundItemId =
                        event.clipData.getItemAt(0).intent.getIntExtra("indexOfSoundItem", 0)
                    squareList[position].setImageResource(iconID)
                    positionSoundItemIdMap[position] = soundItemId
                    constraintLayoutSoundItemMap[constraintLayout] =
                        MyApplication.soundItemList[soundItemId]
                    constraintLayout.setOnTouchListener(StartDrag(position, true, soundItemId))
                    Log.d(TAG, "2")
                    startPlayingSoundItem(soundItemId, position)
                    return@OnDragListener true
                }
                event.action == DragEvent.ACTION_DRAG_STARTED
            })
        }
        binding.deleteArea.setOnDragListener { v: View, event: DragEvent ->
            if (event.action == DragEvent.ACTION_DROP) {
                val rmposition = event.clipData.getItemAt(0).intent.getIntExtra("position", -1)
                if (rmposition != -1) {
                    Log.d(TAG, "1")
                    stopPlayingSoundItem(positionSoundItemIdMap[rmposition]!!, rmposition)
                    positionSoundItemIdMap.remove(rmposition)
                    Glide.with(this).load(R.drawable.square_transparent)
                        .into(squareList[rmposition])
                    constraintLayoutSoundItemMap.remove(squareLayoutList[rmposition])
                    squareLayoutList[rmposition].setOnTouchListener(null)
                    StartDrag.makeVibrate()
                    return@setOnDragListener true
                }
                return@setOnDragListener false
            } else if (event.action == DragEvent.ACTION_DRAG_ENDED) {
                if (!event.result && previousPosition != -1) {
                    squareLayoutList[previousPosition].alpha = 1.0f
                }
                StartDrag.makeVibrate()
                v.alpha = 0f
                binding.deleteArea.startAnimation(fade)
                onItemDropped()
            } else if (event.action == DragEvent.ACTION_DRAG_STARTED) {
                previousPosition = event.localState as Int
                Log.d("debug_drag", "start$previousPosition")
                if (previousPosition != -1) {
                    binding.deleteArea.startAnimation(appear)
                    v.alpha = 1f
                }
                onItemDragged()
            }
            true
        }
        setRecyclerView()
    }

    private fun initLists() {
        squareList.add(binding.squareImage1)
        squareList.add(binding.squareImage2)
        squareList.add(binding.squareImage3)
        squareList.add(binding.squareImage4)
        squareList.add(binding.squareImage5)
        squareList.add(binding.squareImage6)
        squareList.add(binding.squareImage7)
        squareList.add(binding.squareImage8)
        squareList.add(binding.squareImage9)
        squareLayoutList.add(binding.square1)
        squareLayoutList.add(binding.square2)
        squareLayoutList.add(binding.square3)
        squareLayoutList.add(binding.square4)
        squareLayoutList.add(binding.square5)
        squareLayoutList.add(binding.square6)
        squareLayoutList.add(binding.square7)
        squareLayoutList.add(binding.square8)
        squareLayoutList.add(binding.square9)
        hints.add(binding.hintText1)
        hints.add(binding.hintText2)
        hints.add(binding.hintText3)
        hints.add(binding.hintText4)
        hints.add(binding.hintBG)
        for (v in hints) {
            v.visibility = View.INVISIBLE
        }
        appear.duration = animationDuration.toLong()
        appear.fillAfter = true
        fade.duration = animationDuration.toLong()
        fade.fillAfter = true
        fade.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                Log.d(TAG, "onAnimationEnd: ")
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

    }

    private fun setRecyclerView() {
        val adapter = WhiteNoiseAdapter(this)
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.soundItemRecyclerView.apply {
            layoutManager = manager
            setAdapter(adapter)
            setOnDragListener { _, _ -> false }
        }
    }

    private fun stopPlayingSoundItem(soundItemId: Int, position: Int) {
        AudioExoPlayerUtil.stopPlayingSoundItem(position)
    }

    private fun startPlayingSoundItem(soundItemId: Int, position: Int) {
        AudioExoPlayerUtil.setSoundPlayer(soundItemId, position)
        if (playbackStatus == -1) AudioExoPlayerUtil.startSoundPlayer(position)
    }

    override fun onResume() {
        super.onResume()
        if (!AudioExoPlayerUtil.isPlaying) {
            playbackStatus = 1
            Glide.with(this).load(R.drawable.round_play_arrow_24).into(
                binding.playbackButtonImage
            )
        } else {
            playbackStatus = -1
            Glide.with(this).load(R.drawable.outline_pause_24).into(
                binding.playbackButtonImage
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AudioExoPlayerUtil.stopAllSoundPlayers()
        AudioExoPlayerUtil.resetAllSoundPlayers()
    }

    private fun onItemDragged() {
        for (v in hints) {
            startFadeIn(v)
            appear = AlphaAnimation(0f, 1f)
            appear.duration = animationDuration.toLong()
        }
    }

    private fun onItemDropped() {
        for (v in hints) {
            if (v.visibility == View.VISIBLE) {
                startFadeOut(v)
                fade = AlphaAnimation(1f, 0f)
                fade.duration = 150
            }
        }
    }

    private fun startFadeOut(v: View) {
        if (v.visibility == View.VISIBLE) {
            v.startAnimation(fade)
            v.visibility = View.GONE
        }
    }

    private fun startFadeIn(v: View) {
        v.visibility = View.VISIBLE
        v.startAnimation(appear)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var previousPosition = 0
        private const val TAG = "MixActivity"
        private var playbackStatus = -1
        private var appear: Animation = AlphaAnimation(0f, 1f)
        private var fade: Animation = AlphaAnimation(1f, 0f)
    }
}