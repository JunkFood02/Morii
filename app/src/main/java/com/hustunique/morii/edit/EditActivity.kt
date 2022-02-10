package com.hustunique.morii.edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.hustunique.morii.content.ContentActivity
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.AudioExoPlayerUtil
import com.hustunique.morii.util.BaseActivity
import com.hustunique.morii.util.MyApplication
import morii.R
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : BaseActivity(), EditContract.IView {
    private var content = ""
    private var title = ""
    private var textTitle: EditText? = null
    private var textContent: EditText? = null
    private var showPhoto: ImageView? = null
    private var imageView: ImageView? = null
    private var ImagePath: String? = null
    private var addPhoto: CardView? = null
    private var editCardLayout: ConstraintLayout? = null
    private var currentDate: String? = null
    private var presenter: EditContract.IPresenter? = null
    private var musicTabId = 0
    private var progressBar: ProgressBar? = null
    private var seekBar: SeekBar? = null
    private var startTime: TextView? = null
    private var Position = AudioExoPlayerUtil.currentPosition.toInt()
    private var complete_layout: CardView? = null
    private var back_layout: CardView? = null
    private var endTime: TextView? = null
    private var currentTime: TextView? = null
    private var pauseMusic: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        presenter = EditPresenter(this)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        checkPlayStatus()
    }

    override fun setAddPhoto(path: String?) {
        ImagePath = path
        Glide.with(this).load(path).into(showPhoto!!)
    }

    private fun initUI() {
        viewBinding()
        val diary = intent.getSerializableExtra("diary") as MusicDiaryItem?
        musicTabId = diary!!.musicTabId
        val Duration = AudioExoPlayerUtil.getDuration()
        initProgressBar(Duration)
        if (AudioExoPlayerUtil.isPlaying) Glide.with(this).load(R.drawable.outline_pause_24).into(
            imageView!!
        ) else Glide.with(this).load(R.drawable.round_play_arrow_24).into(
            imageView!!
        )
        pauseMusic!!.setOnClickListener {
            if (AudioExoPlayerUtil.isPlaying) {
                AudioExoPlayerUtil.pauseAllPlayers()
                Glide.with(this).load(R.drawable.round_play_arrow_24).into(imageView!!)
            } else {
                AudioExoPlayerUtil.startAllPlayers()
                play()
                Glide.with(this).load(R.drawable.outline_pause_24).into(imageView!!)
            }
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
        Glide.with(this).load(MyApplication.musicTabList.get(musicTabId).imageResId)
            .into(showPhoto!!)
        currentDate = time
        currentTime!!.text = "# $currentDate"
        Log.d(TAG, "initUI: $currentDate")
        addPhoto!!.setOnClickListener { presenter?.picture }
        Log.d(
            TAG,
            "initUI: " + MyApplication.musicTabList[musicTabId].imageResId
        )
        complete_layout!!.setOnClickListener { v: View? ->
            Log.d(TAG, "initUI: next")
            title = Objects.requireNonNull(textTitle!!.text).toString()
            content = Objects.requireNonNull(textContent!!.text).toString()
            if (title.isNotEmpty() && content.isNotEmpty()) {
                diary.title = title
                diary.article = content
                diary.date = currentDate
                diary.musicTabId = musicTabId
                if (ImagePath != null) diary.imagePath = ImagePath
                val intentToNextActivity = Intent(this, ContentActivity::class.java)
                intentToNextActivity.putExtra("diary", diary)
                intentToNextActivity.putExtra("NewItem", 1)
                startActivity(intentToNextActivity)
            } else Toast.makeText(this, "未输入完全（^.^）", Toast.LENGTH_SHORT).show()
        }
        back_layout!!.setOnClickListener { v: View? -> onBackPressed() }
    }

    private fun viewBinding() {
        textContent = findViewById(R.id.editTextContent)
        textTitle = findViewById(R.id.editTextTitle)
        complete_layout = findViewById(R.id.completeLayout_edit)
        back_layout = findViewById(R.id.backLayout_edit)
        addPhoto = findViewById(R.id.addPhotoIcon)
        addPhoto = findViewById(R.id.addPhotoIcon)
        showPhoto = findViewById(R.id.BigPhoto)
        editCardLayout = findViewById(R.id.editCardLayout)
        imageView = findViewById(R.id.image)
        startTime = findViewById(R.id.StartTime)
        progressBar = findViewById(R.id.MusicLine)
        seekBar = findViewById(R.id.SeekBar)
        endTime = findViewById(R.id.EndTime)
        currentTime = findViewById(R.id.currentTime)
        pauseMusic = findViewById(R.id.button)
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

    private fun checkPlayStatus() {
        if (AudioExoPlayerUtil.isPlaying) Glide.with(this).load(R.drawable.outline_pause_24).into(
            imageView!!
        ) else Glide.with(this).load(R.drawable.round_play_arrow_24).into(
            imageView!!
        )
    }

    private val time: String
        private get() {
            val simpleDateFormat = SimpleDateFormat("MM月dd日 E HH:mm", Locale.CHINA)
            return simpleDateFormat.format(Date())
        }

    private fun play() {
        val thread: Thread = Thread(goThread())
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
        private const val TAG = "EditActivity"
    }
}