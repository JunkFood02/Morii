package com.hustunique.morii.music

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.view.View
import android.view.Window
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hustunique.morii.design.MixActivity
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.BaseActivity
import com.hustunique.morii.util.MyApplication
import morii.databinding.ActivityMusicSelectBinding

class MusicSelectActivity : BaseActivity(), MusicSelectContract.IView {
    private lateinit var binding: ActivityMusicSelectBinding
    private lateinit var presenter: MusicSelectContract.IPresenter
    private val fadeOut: Animation = AlphaAnimation(0.0f, 1f)
    private val animationDuration: Long = 200
    private var previousPosition = 0
    private var currentPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.enterTransition = AutoTransition()
        binding = ActivityMusicSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MusicSelectPresenter()
        initUI()

        binding.nextstepButton.setOnClickListener { view: View? ->
            val intent = Intent(this@MusicSelectActivity, MixActivity::class.java)
            val diary = MusicDiaryItem()
            diary.musicTabId = currentPosition
            intent.putExtra("diary", diary)
            startActivity(intent)
        }
        binding.backButton.setOnClickListener { view: View? -> onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopMusic()
    }

    private fun initUI() {
        setRecyclerView()
        fadeOut.duration = animationDuration
    }

    private fun setRecyclerView() {
        val adapter = MusicSelectAdapter(this, presenter)
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.musicTabRecyclerView.apply {
            layoutManager = manager
            this.adapter = adapter
            PagerSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        currentPosition = manager.findFirstCompletelyVisibleItemPosition()
                        switchMusicTab()
                    }

                }
            })
        }
        fadeOut.duration = animationDuration
        presenter.switchMusic(0)
        binding.Emotion.text = MyApplication.musicTabList[0].emotion
    }

    companion object {
        private const val TAG = "MusicSelectActivity"
    }

    private fun switchMusicTab() {
        if (currentPosition >= 0 && currentPosition != previousPosition) {
            binding.Emotion.startAnimation(fadeOut)
            binding.Emotion.text =
                MyApplication.musicTabList[currentPosition].emotion
            presenter.switchMusic(currentPosition)
            previousPosition = currentPosition
        }
    }
}