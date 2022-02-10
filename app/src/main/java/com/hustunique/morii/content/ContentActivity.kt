package com.hustunique.morii.content

import android.content.Intent
import com.hustunique.morii.util.BaseActivity
import androidx.cardview.widget.CardView
import com.hustunique.morii.util.AudioExoPlayerUtil
import android.os.Bundle
import morii.R
import com.bumptech.glide.Glide
import com.hustunique.morii.home.MusicDiaryItem
import android.widget.SeekBar.OnSeekBarChangeListener
import com.hustunique.morii.util.MyApplication
import com.hustunique.morii.home.MainActivity
import com.hustunique.morii.database.DiaryInfo
import com.hustunique.morii.util.DatabaseUtil
import com.hustunique.morii.util.OnReadyListener
import android.transition.AutoTransition
import android.transition.Explode
import android.util.Log
import android.view.*
import android.widget.*
import morii.databinding.ActivityContentBinding
import java.lang.StringBuilder

class ContentActivity : BaseActivity() {
    private lateinit var binding: ActivityContentBinding
    private lateinit var musicDiaryItem: MusicDiaryItem
    private lateinit var intentTemp: Intent
    var newItem = 0
    private var Position = AudioExoPlayerUtil.currentPosition.toInt()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.sharedElementExitTransition = AutoTransition()
        window.enterTransition = Explode()
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: ")
        intentTemp = intent
        newItem = intentTemp.getIntExtra("NewItem", 0)
        initMusicDiaryContent()
        setCallbacks()
    }

    private fun setCallbacks() {
        if (AudioExoPlayerUtil.isPlaying || newItem == 0) {
            Glide.with(this).load(R.drawable.outline_pause_24)
                .into(binding.progressbarContent.image)
            Log.d(TAG, "Playing")
        } else {
            Log.d(TAG, "not Playing")
            Glide.with(this).load(R.drawable.round_play_arrow_24)
                .into(binding.progressbarContent.image)
        }
        binding.progressbarContent.button
            .setOnClickListener {
                if (AudioExoPlayerUtil.isPlaying) {
                    AudioExoPlayerUtil.pauseAllPlayers()
                    Glide.with(this).load(R.drawable.round_play_arrow_24)
                        .into(binding.progressbarContent.image)
                } else {
                    AudioExoPlayerUtil.startAllPlayers()
                    play()
                    Glide.with(this).load(R.drawable.outline_pause_24)
                        .into(binding.progressbarContent.image)
                }
            }
        if (newItem == 0) {
            binding.finishButton.visibility = View.GONE
            binding.deleteButton.visibility = View.GONE
        } else {
            binding.titleContent.text = "预览"
            binding.finishButton.setOnClickListener {
                newItem = 0
                createMusicDiary()
                val backIntent = Intent(this@ContentActivity, MainActivity::class.java)
                startActivity(backIntent)
            }
            binding.deleteButton.setOnClickListener { v: View? ->
                newItem = 0
                val backIntent = Intent(this@ContentActivity, MainActivity::class.java)
                startActivity(backIntent)
            }
        }
        binding.backLayoutContent.setOnClickListener { onBackPressed() }
        if (newItem == 1) {
            initProgressBar(AudioExoPlayerUtil.getDuration())
        } else {
            AudioExoPlayerUtil.setListener(object : OnReadyListener() {
                override fun onReady(duration: Long) {
                    super.onReady(duration)
                    Log.d(TAG, "onReady: $duration")
                    initProgressBar(duration)
                }
            })
        }
        binding.progressbarContent.SeekBar.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var sss: String
                var mmm: String
                sss = (progress / 1000 % 60).toString()
                mmm = (progress / 60 / 1000).toString()
                if (sss.length < 2) sss = "0$sss"
                if (mmm.length < 2) mmm = "0$mmm"
                binding.progressbarContent.StartTime.text = "$mmm:$sss"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun initMusicDiaryContent() {
        val builder = StringBuilder()
        musicDiaryItem = intentTemp.getSerializableExtra("diary") as MusicDiaryItem
        builder.append(
            MyApplication.musicTabList[musicDiaryItem.musicTabId].emotion
        ).append(" ")
        if (newItem == 0) {
            Log.d(TAG, "initMusicDiaryContent: newItem=0")
            AudioExoPlayerUtil.playMusic(musicDiaryItem.musicTabId)
            val list = musicDiaryItem.soundItemInfoList
            for (info in list) {
                Log.d(TAG, info!!.soundItemId.toString() + " position:" + info.soundItemPosition)
                AudioExoPlayerUtil.setSoundPlayer(info.soundItemId, info.soundItemPosition)
                AudioExoPlayerUtil.startSoundPlayer(info.soundItemPosition)
                builder.append(
                    MyApplication.Companion.soundItemList[info.soundItemId].soundName
                ).append(" ")
            }
        } else {
            for (info in musicDiaryItem.soundItemInfoList) {
                builder.append(
                    MyApplication.soundItemList[info!!.soundItemId].soundName
                ).append(" ")
            }
            initProgressBar(AudioExoPlayerUtil.getDuration())
        }
        binding.musicDiaryTitle.text = musicDiaryItem.title
        binding.diaryContent.text = musicDiaryItem.article
        binding.musicDiaryDate.text = musicDiaryItem.date
        binding.musicDiaryTag.text = builder.toString()
        val imagePath = musicDiaryItem.imagePath
        Log.d(TAG, "onCreate: $imagePath")
        if (imagePath != null) {
            Glide.with(this).load(imagePath).into(binding.PhotoShow)
        } else {
            Glide.with(this).load(
                MyApplication.musicTabList[musicDiaryItem.musicTabId]
                    .imageResId
            ).into(binding.PhotoShow)
        }
    }

    private fun initProgressBar(Duration: Long) {
        binding.progressbarContent.MusicLine.max = Duration.toInt()
        binding.progressbarContent.SeekBar.max = Duration.toInt()
        var sss: String
        var mmm: String
        sss = (Duration / 1000 % 60).toString()
        mmm = (Duration / 60 / 1000).toString()
        if (sss.length < 2) sss = "0$sss"
        if (mmm.length < 2) mmm = "0$mmm"
        binding.progressbarContent.EndTime.text = "$mmm:$sss"
        play()
    }

    private fun createMusicDiary() {
        val diaryInfo = DiaryInfo(musicDiaryItem)
        val diaryInfoId = DatabaseUtil.insertDiaryInfo(diaryInfo)
        musicDiaryItem.itemID = diaryInfoId
        for (info in musicDiaryItem.soundItemInfoList) {
            info!!.diaryInfoId = diaryInfoId
            DatabaseUtil.insertSoundItemInfo(info)
        }
        MyApplication.musicDiaryList.add(musicDiaryItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (newItem == 0) {
            AudioExoPlayerUtil.pauseAllPlayers()
            AudioExoPlayerUtil.resetAllSoundPlayers()
        }
    }

    private fun play() {
        val thread = Thread(goThread())
        thread.start()
    }

    internal inner class goThread : Runnable {
        var isPlaying = true
        override fun run() {
            while (isPlaying) {
                try {
                    runOnUiThread {
                        isPlaying = AudioExoPlayerUtil.isPlaying
                        if (isPlaying) Position = AudioExoPlayerUtil.currentPosition.toInt()
                    }
                    binding.progressbarContent.SeekBar.progress = Position
                    binding.progressbarContent.MusicLine.progress = Position
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        private const val TAG = "ContentActivity"
    }
}