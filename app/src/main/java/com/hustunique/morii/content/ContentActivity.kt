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
import java.lang.StringBuilder

class ContentActivity : BaseActivity() {
    private var deleteButton: CardView? = null
    private var finishButton: CardView? = null
    private var pauseMusic: CardView? = null
    private var goBack: CardView? = null
    private var titleBarText: TextView? = null
    private var musicDiaryItem: MusicDiaryItem? = null
    private lateinit var intentTemp: Intent
    var newItem = 0
    private var imageView2: ImageView? = null
    private var startTime: TextView? = null
    private var progressBar: ProgressBar? = null
    private var seekBar: SeekBar? = null
    private var Position = AudioExoPlayerUtil.currentPosition.toInt()
    private var title: TextView? = null
    private var article: TextView? = null
    private var date: TextView? = null
    private var tag: TextView? = null
    private var endTime: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.sharedElementExitTransition = AutoTransition()
        window.enterTransition = Explode()
        setContentView(R.layout.activity_content)
        Log.d(TAG, "onCreate: ")
        intentTemp = intent
        newItem = intentTemp.getIntExtra("NewItem", 0)
        viewBinding()
        initMusicDiaryContent()
        setCallbacks()
    }

    private fun viewBinding() {
        pauseMusic = findViewById(R.id.MusicPlay)
        imageView2 = findViewById(R.id.music_pause)
        startTime = findViewById(R.id.StartTime)
        progressBar = findViewById(R.id.MusicLine)
        seekBar = findViewById(R.id.SeekBar)
        titleBarText = findViewById(R.id.title_content)
        finishButton = findViewById(R.id.finishButton)
        deleteButton = findViewById(R.id.deleteButton)
        goBack = findViewById(R.id.backLayout_content)
        title = findViewById(R.id.musicDiaryTitle)
        article = findViewById(R.id.diaryContent)
        date = findViewById(R.id.musicDiaryDate)
        tag = findViewById(R.id.musicDiaryTag)
        endTime = findViewById(R.id.EndTime)
        progressBar = findViewById(R.id.MusicLine)
        seekBar = findViewById(R.id.SeekBar)
    }

    private fun setCallbacks() {
        if (AudioExoPlayerUtil.isPlaying || newItem == 0) {
            Glide.with(this).load(R.drawable.outline_pause_24).into(imageView2!!)
            Log.d(TAG, "Playing")
        } else {
            Log.d(TAG, "not Playing")
            Glide.with(this).load(R.drawable.round_play_arrow_24).into(imageView2!!)
        }
        pauseMusic!!.setOnClickListener {
            if (AudioExoPlayerUtil.isPlaying) {
                AudioExoPlayerUtil.pauseAllPlayers()
                Glide.with(this).load(R.drawable.round_play_arrow_24).into(imageView2!!)
            } else {
                AudioExoPlayerUtil.startAllPlayers()
                play()
                Glide.with(this).load(R.drawable.outline_pause_24).into(imageView2!!)
            }
        }
        if (newItem == 0) {
            finishButton!!.visibility = View.GONE
            deleteButton!!.visibility = View.GONE
        } else {
            titleBarText!!.text = "预览"
            finishButton!!.setOnClickListener { v: View? ->
                newItem = 0
                createMusicDiary()
                val backIntent = Intent(this@ContentActivity, MainActivity::class.java)
                startActivity(backIntent)
            }
            deleteButton!!.setOnClickListener { v: View? ->
                newItem = 0
                val backIntent = Intent(this@ContentActivity, MainActivity::class.java)
                startActivity(backIntent)
            }
        }
        goBack!!.setOnClickListener { view: View? -> onBackPressed() }
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
        seekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var sss: String
                var mmm: String
                sss = (progress / 1000 % 60).toString()
                mmm = (progress / 60 / 1000).toString()
                if (sss.length < 2) sss = "0$sss"
                if (mmm.length < 2) mmm = "0$mmm"
                startTime!!.text = "$mmm:$sss"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun initMusicDiaryContent() {
        val builder = StringBuilder()
        musicDiaryItem = intentTemp.getSerializableExtra("diary") as MusicDiaryItem?
        builder.append(
            MyApplication.Companion.musicTabList[musicDiaryItem?.musicTabId!!].emotion
        ).append(" ")
        if (newItem == 0) {
            Log.d(TAG, "initMusicDiaryContent: newItem=0")
            AudioExoPlayerUtil.playMusic(musicDiaryItem!!.musicTabId)
            val list = musicDiaryItem!!.soundItemInfoList
            for (info in list!!) {
                Log.d(TAG, info!!.soundItemId.toString() + " position:" + info.soundItemPosition)
                AudioExoPlayerUtil.setSoundPlayer(info.soundItemId, info.soundItemPosition)
                AudioExoPlayerUtil.startSoundPlayer(info.soundItemPosition)
                builder.append(
                    MyApplication.Companion.soundItemList.get(info.soundItemId).soundName
                ).append(" ")
            }
        } else {
            for (info in musicDiaryItem?.soundItemInfoList!!) {
                builder.append(
                    MyApplication.Companion.soundItemList.get(info!!.soundItemId).soundName
                ).append(" ")
            }
            initProgressBar(AudioExoPlayerUtil.getDuration())
        }
        title?.text = musicDiaryItem?.title
        article?.text = musicDiaryItem?.article
        date?.text = musicDiaryItem?.date
        tag!!.text = builder.toString()
        // get the information
        val imageView = findViewById<ImageView>(R.id.PhotoShow)
        val imagePath = musicDiaryItem!!.imagePath
        Log.d(TAG, "onCreate: $imagePath")
        if (imagePath != null) {
            Glide.with(this).load(imagePath).into(imageView)
        } else {
            Glide.with(this).load(
                MyApplication.musicTabList[musicDiaryItem?.musicTabId!!]
                    .imageResId
            ).into(imageView)
        }
    }

    private fun initProgressBar(Duration: Long) {
        progressBar!!.max = Duration.toInt()
        seekBar!!.max = Duration.toInt()
        var sss: String
        var mmm: String
        sss = (Duration / 1000 % 60).toString()
        mmm = (Duration / 60 / 1000).toString()
        if (sss.length < 2) sss = "0$sss"
        if (mmm.length < 2) mmm = "0$mmm"
        endTime!!.text = "$mmm:$sss"
        play()
    }

    private fun createMusicDiary() {
        val diaryInfo = DiaryInfo(musicDiaryItem)
        val diaryInfoId = DatabaseUtil.insertDiaryInfo(diaryInfo)
        musicDiaryItem?.itemID = diaryInfoId
        for (info in musicDiaryItem?.soundItemInfoList!!) {
            info!!.diaryInfoId = diaryInfoId
            DatabaseUtil.insertSoundItemInfo(info)
        }
        MyApplication.Companion.musicDiaryList.add(musicDiaryItem)
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
                    seekBar!!.progress = Position
                    progressBar!!.progress = Position
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