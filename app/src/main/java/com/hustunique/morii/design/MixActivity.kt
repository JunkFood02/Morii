package com.hustunique.morii.design

import android.content.Intent
import android.annotation.SuppressLint
import com.hustunique.morii.util.BaseActivity
import androidx.cardview.widget.CardView
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
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import java.util.HashMap

class MixActivity : BaseActivity() {
    private var recyclerView: RecyclerView? = null
    private var backLayout: CardView? = null
    private var completeLayout: CardView? = null
    private var helpMessage: ImageView? = null
    private var cardImage: ImageView? = null
    private var helpButton: ImageView? = null
    private val squareLayoutList: MutableList<ConstraintLayout> = ArrayList(9)
    private val squareList: MutableList<ImageView> = ArrayList(9)
    private val constraintLayoutSoundItemMap: MutableMap<ConstraintLayout, SoundItem?> = HashMap(9)
    private val positionSoundItemIdMap: MutableMap<Int, Int> = HashMap(9)
    private var playbackIcon: ImageView? = null
    private var playbackButton: CardView? = null
    private val animationDuration = 150
    private val hints: MutableList<View> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mix)
        initUI()
    }

    private fun initUI() {
        viewBinding()
        val diary = intent.getSerializableExtra("diary") as MusicDiaryItem?
        Glide.with(this)
            .load(MyApplication.Companion.musicTabList.get(diary?.musicTabId!!).imageResId)
            .into(cardImage!!)
        helpButton!!.setOnClickListener { v: View? ->
            if (helpMessage!!.visibility == View.GONE) startFadeIn(
                helpMessage
            ) else startFadeOut(helpMessage)
        }
        helpMessage!!.setOnClickListener { v: View ->
            if (v.visibility == View.VISIBLE) startFadeOut(
                helpMessage
            )
        }
        playbackButton!!.setOnClickListener { v: View? ->
            if (playbackStatus == 1) {
                AudioExoPlayerUtil.startAllPlayers()
                Glide.with(this).load(R.drawable.outline_pause_24).into(playbackIcon!!)
            } else {
                AudioExoPlayerUtil.pauseAllPlayers()
                Glide.with(this).load(R.drawable.round_play_arrow_24).into(playbackIcon!!)
            }
            playbackStatus = -playbackStatus
        }
        completeLayout!!.setOnClickListener { v: View? ->
            val intent = Intent(this, EditActivity::class.java)
            diary?.soundItemInfoList?.clear()
            for ((key, value) in positionSoundItemIdMap) {
                diary?.soundItemInfoList?.add(SoundItemInfo(key, value))
                Log.d("activityData", "key = $key value = $value")
            }
            intent.putExtra("diary", diary)
            startActivity(intent)
        }
        backLayout!!.setOnClickListener { v: View? -> onBackPressed() }
        for (constraintLayout in squareLayoutList) {
            constraintLayoutSoundItemMap[constraintLayout] = null
        }
        for (constraintLayout in constraintLayoutSoundItemMap.keys) {
            constraintLayout.setOnDragListener(label@ OnDragListener { v: View?, event: DragEvent ->
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
        delete_area!!.setOnDragListener { v: View, event: DragEvent ->
            if (event.action == DragEvent.ACTION_DROP) {
                val rmposition = event.clipData.getItemAt(0).intent.getIntExtra("position", -1)
                if (rmposition != -1) {
                    Log.d(TAG, "1")
                    stopPlayingSoundItem(positionSoundItemIdMap[rmposition]!!, rmposition)
                    positionSoundItemIdMap.remove(rmposition)
                    Glide.with(this).load(R.drawable.square_transparent)
                        .into(squareList[rmposition])
                    //squareList.get(rmposition).setImageResource(R.drawable.square_transparent);
                    constraintLayoutSoundItemMap.remove(squareLayoutList[rmposition])
                    squareLayoutList[rmposition].setOnTouchListener(null)
                    StartDrag.makeVibrate()
                    return@setOnDragListener true
                }
                return@setOnDragListener false
            } else if (event.action == DragEvent.ACTION_DRAG_ENDED) {
                if (!event.result && _last_drag_position != -1) {
                    squareLayoutList[_last_drag_position].alpha = 1.0f
                }
                StartDrag.Companion.makeVibrate()
                v.alpha = 0f
                delete_area!!.startAnimation(fade)
                onItemDropped()
            } else if (event.action == DragEvent.ACTION_DRAG_STARTED) {
                _last_drag_position = event.localState as Int
                Log.d("debug_drag", "start" + _last_drag_position)
                if (_last_drag_position != -1) {
                    delete_area!!.startAnimation(appear)
                    v.alpha = 1f
                }
                onItemDragged()
            }
            true
        }
        setRecyclerView()
    }

    private fun viewBinding() {
        recyclerView = findViewById(R.id.recyclerView03)
        cardImage = findViewById(R.id.imageView_card)
        delete_area = findViewById(R.id.delete_area)
        backLayout = findViewById(R.id.backLayout_select_mix)
        completeLayout = findViewById(R.id.okay_mix)
        squareList.add(findViewById(R.id.square_image1))
        squareList.add(findViewById(R.id.square_image2))
        squareList.add(findViewById(R.id.square_image3))
        squareList.add(findViewById(R.id.square_image4))
        squareList.add(findViewById(R.id.square_image5))
        squareList.add(findViewById(R.id.square_image6))
        squareList.add(findViewById(R.id.square_image7))
        squareList.add(findViewById(R.id.square_image8))
        squareList.add(findViewById(R.id.square_image9))
        squareLayoutList.add(findViewById(R.id.square1))
        squareLayoutList.add(findViewById(R.id.square2))
        squareLayoutList.add(findViewById(R.id.square3))
        squareLayoutList.add(findViewById(R.id.square4))
        squareLayoutList.add(findViewById(R.id.square5))
        squareLayoutList.add(findViewById(R.id.square6))
        squareLayoutList.add(findViewById(R.id.square7))
        squareLayoutList.add(findViewById(R.id.square8))
        squareLayoutList.add(findViewById(R.id.square9))
        hints.add(findViewById(R.id.hintText1))
        hints.add(findViewById(R.id.hintText2))
        hints.add(findViewById(R.id.hintText3))
        hints.add(findViewById(R.id.hintText4))
        hints.add(findViewById(R.id.hintBG))
        for (v in hints) {
            v.visibility = View.INVISIBLE
        }
        playbackButton = findViewById(R.id.playbackButton)
        playbackIcon = findViewById(R.id.playbackButton_image)
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
        helpButton = findViewById(R.id.helpButton)
        helpMessage = findViewById(R.id.helpMessage)
    }

    private fun setRecyclerView() {
        val adapter = WhiteNoiseAdapter(this)
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView!!.layoutManager = manager
        recyclerView!!.adapter = adapter
        recyclerView!!.setOnDragListener { v, event -> false }
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
            Glide.with(this).load(R.drawable.round_play_arrow_24).into(playbackIcon!!)
        } else {
            playbackStatus = -1
            Glide.with(this).load(R.drawable.outline_pause_24).into(playbackIcon!!)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AudioExoPlayerUtil.stopAllSoundPlayers()
        AudioExoPlayerUtil.resetAllSoundPlayers()
    }

    override fun onDestroy() {
        super.onDestroy()
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

    private fun startFadeOut(v: View?) {
        if (v!!.visibility == View.VISIBLE) {
            v.startAnimation(fade)
            v.visibility = View.GONE
        }
    }

    private fun startFadeIn(v: View?) {
        v!!.visibility = View.VISIBLE
        v.startAnimation(appear)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var _last_drag_position = 0
        private var delete_area: ConstraintLayout? = null
        private const val TAG = "MixActivity"
        private var playbackStatus = -1
        private var appear: Animation = AlphaAnimation(0f, 1f)
        private var fade: Animation = AlphaAnimation(1f, 0f)
    }
}