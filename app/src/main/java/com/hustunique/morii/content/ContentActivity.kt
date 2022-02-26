package com.hustunique.morii.content

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.Explode
import android.util.Log
import android.view.*
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.hustunique.morii.database.DiaryInfo
import com.hustunique.morii.home.MainActivity
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.*
import com.hustunique.morii.util.MyApplication.Companion.musicTabList
import morii.R
import morii.databinding.ActivityContentBinding
import java.io.File


class ContentActivity : BaseActivity() {
    private lateinit var binding: ActivityContentBinding
    private lateinit var musicDiaryItem: MusicDiaryItem
    private lateinit var handler: Handler
    private var newItem = 0
    private var position = AudioExoPlayerUtil.currentPosition.toInt()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.sharedElementExitTransition = AutoTransition()
        window.enterTransition = Explode()
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newItem = intent.getIntExtra("NewItem", 0)
        initUI()
        initMusicDiaryContent()
        setCallbacks()
    }

    private fun initUI() {
        if (AudioExoPlayerUtil.isPlaying || newItem == 0) {
            Glide.with(this).load(R.drawable.outline_pause_24)
                .into(binding.progressbarContent.image)
        } else {
            Glide.with(this).load(R.drawable.round_play_arrow_24)
                .into(binding.progressbarContent.image)
        }
        if (newItem == 0) {
            binding.finishButton.visibility = View.GONE
            binding.deleteButton.visibility = View.GONE
            binding.completeLayoutContent.visibility = View.VISIBLE
        } else {
            binding.titleContent.text = "预览"
            Glide.with(this).load(R.drawable.ic_icon_left_design).into(binding.backButtonImage)
            binding.backButtonText.text = "上一步"
        }
    }

    private fun setCallbacks() {

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

        if (newItem == 1) {
            binding.finishButton.setOnClickListener {
                createMusicDiary()
                backToMainActivity()
            }
            binding.deleteButton.setOnClickListener { v: View? ->
                backToMainActivity()
            }
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
        binding.backLayoutContent.setOnClickListener { onBackPressed() }
        binding.completeLayoutContent.setOnClickListener {
            Toast.makeText(this, "正在生成音频文件", Toast.LENGTH_SHORT).show()
            AudioProcessor.makeAudioMix(musicDiaryItem, handler)
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
        handler = Handler(this.mainLooper) {
            val uri = FileProvider.getUriForFile(
                this,
                "com.hustunique.morii.provider",
                File(it.obj as String)
            )
            shareAudioFile(uri)
            return@Handler true
        }
    }

    private fun initMusicDiaryContent() {
        val builder = StringBuilder()
        musicDiaryItem = intent.getSerializableExtra("diary") as MusicDiaryItem
        builder.append(
            musicTabList[musicDiaryItem.musicTabId].emotion
        ).append(" ")

        if (newItem == 0) {
            Log.d(TAG, "initMusicDiaryContent: newItem=0")
            AudioExoPlayerUtil.playMusic(musicDiaryItem.musicTabId)
            for (info in musicDiaryItem.soundItemInfoList) {
                Log.d(TAG, info.soundItemId.toString() + " position:" + info.soundItemPosition)
                AudioExoPlayerUtil.setSoundPlayer(info.soundItemId, info.soundItemPosition)
                AudioExoPlayerUtil.startSoundPlayer(info.soundItemPosition)
                builder.append(
                    MyApplication.soundItemList[info.soundItemId].soundName
                ).append(" ")
            }
        } else {
            for (info in musicDiaryItem.soundItemInfoList) {
                builder.append(
                    MyApplication.soundItemList[info.soundItemId].soundName
                ).append(" ")
            }
            initProgressBar(AudioExoPlayerUtil.getDuration())
        }
        binding.musicDiaryTitle.text = musicDiaryItem.title
        binding.diaryContent.text = musicDiaryItem.article
        binding.musicDiaryDate.text = musicDiaryItem.date
        binding.musicDiaryTag.text = builder.toString()
        if (musicDiaryItem.imagePath != null)
            Glide.with(this).load(musicDiaryItem.imagePath).into(binding.PhotoShow)
        else
            Glide.with(this).load(musicTabList[musicDiaryItem.musicTabId].imageResId)
                .into(binding.PhotoShow)
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
            info.diaryInfoId = diaryInfoId
            DatabaseUtil.insertSoundItemInfo(info)
        }
        MyApplication.musicDiaryList.add(musicDiaryItem)
    }

    private fun shareAudioFile(uri: Uri) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "*/*"
        }
        startActivity(Intent.createChooser(shareIntent, "分享音频文件"))

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
                        if (isPlaying) position = AudioExoPlayerUtil.currentPosition.toInt()
                    }
                    binding.progressbarContent.SeekBar.progress = position
                    binding.progressbarContent.MusicLine.progress = position
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroy() {
        if (newItem == 0) {
            AudioExoPlayerUtil.pauseAllPlayers()
            AudioExoPlayerUtil.resetAllSoundPlayers()
        }
        super.onDestroy()
    }

    private fun backToMainActivity() {
        val backIntent = Intent(this@ContentActivity, MainActivity::class.java)
        startActivity(backIntent)
        AudioExoPlayerUtil.pauseAllPlayers()
        AudioExoPlayerUtil.resetAllSoundPlayers()
    }

    companion object {
        private const val TAG = "ContentActivity"
    }

}