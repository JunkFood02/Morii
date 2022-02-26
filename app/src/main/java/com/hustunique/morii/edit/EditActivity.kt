package com.hustunique.morii.edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import com.bumptech.glide.Glide
import com.hustunique.morii.content.ContentActivity
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.AudioExoPlayerUtil
import com.hustunique.morii.util.BaseActivity
import com.hustunique.morii.util.MyApplication
import com.hustunique.morii.util.MyApplication.Companion.musicTabList
import morii.R
import morii.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : BaseActivity(), EditContract.IView {
    private lateinit var binding: ActivityEditBinding
    private var content = ""
    private var title = ""
    private var imagePath: String? = null
    private lateinit var currentDate: String
    private lateinit var presenter: EditContract.IPresenter
    private var position = AudioExoPlayerUtil.currentPosition.toInt()
    private lateinit var diary: MusicDiaryItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = EditPresenter(this)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        checkPlayStatus()
    }

    override fun setAddPhoto(path: String) {
        imagePath = path
        Glide.with(this).load(path).placeholder(musicTabList[diary.musicTabId].imageResId)
            .into(binding.BigPhoto)
    }

    private fun initUI() {
        diary = intent.getSerializableExtra("diary") as MusicDiaryItem
        val Duration = AudioExoPlayerUtil.getDuration()
        initProgressBar(Duration)
        checkPlayStatus()
        binding.progressbar.button.setOnClickListener {
            if (AudioExoPlayerUtil.isPlaying) {
                AudioExoPlayerUtil.pauseAllPlayers()
            } else {
                AudioExoPlayerUtil.startAllPlayers()
                play()
            }
            checkPlayStatus()
        }
        binding.progressbar.SeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var sss: String
                var mmm: String
                sss = (progress / 1000 % 60).toString()
                mmm = (progress / 60 / 1000).toString()
                if (sss.length < 2) sss = "0$sss"
                if (mmm.length < 2) mmm = "0$mmm"
                binding.progressbar.StartTime.text = "$mmm:$sss"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        Glide.with(this).load(musicTabList[diary.musicTabId].imageResId)
            .into(binding.BigPhoto)
        currentDate = getDate()
        binding.currentDateText.text = "# $currentDate"
        binding.addPhotoButton.setOnClickListener { presenter?.picture }
        Log.d(
            TAG,
            "initUI: " + musicTabList[diary.musicTabId].imageResId
        )
        binding.nextstepButtonEdit.setOnClickListener { v: View? ->
            Log.d(TAG, "initUI: next")
            title = Objects.requireNonNull(binding.editTextTitle.text).toString()
            content = Objects.requireNonNull(binding.editTextContent.text).toString()
            if (title.isNotEmpty() && content.isNotEmpty()) {
                diary.title = title
                diary.article = content
                diary.date = currentDate
                if (imagePath != null) diary.imagePath = imagePath as String
                val intentToNextActivity = Intent(this, ContentActivity::class.java)
                intentToNextActivity.putExtra("diary", diary)
                intentToNextActivity.putExtra("NewItem", 1)
                startActivity(intentToNextActivity)
            } else Toast.makeText(this, "未输入完全（^.^）", Toast.LENGTH_SHORT).show()
        }
        binding.backButtonEdit.setOnClickListener { v: View? -> onBackPressed() }
    }


    private fun initProgressBar(Duration: Long) {
        binding.progressbar.MusicLine.max = Duration.toInt()
        binding.progressbar.SeekBar.max = Duration.toInt()
        var sss: String
        var mmm: String
        sss = (Duration / 1000 % 60).toString()
        mmm = (Duration / 60 / 1000).toString()
        if (sss.length < 2) sss = "0$sss"
        if (mmm.length < 2) mmm = "0$mmm"
        binding.progressbar.EndTime.text = "$mmm:$sss"
        play()
    }

    private fun checkPlayStatus() {
        if (AudioExoPlayerUtil.isPlaying) Glide.with(this).load(R.drawable.outline_pause_24).into(
            binding.progressbar.image
        ) else Glide.with(this).load(R.drawable.round_play_arrow_24).into(
            binding.progressbar.image
        )
    }

    private fun getDate(): String {
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
                        if (isPlaying) position = AudioExoPlayerUtil.currentPosition.toInt()
                    }
                    binding.progressbar.SeekBar.progress = position
                    binding.progressbar.MusicLine.progress = position
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