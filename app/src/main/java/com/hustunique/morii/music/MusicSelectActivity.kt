package com.hustunique.morii.music

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hustunique.morii.design.MixActivity
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.BaseActivity
import com.hustunique.morii.util.MyApplication
import morii.R

class MusicSelectActivity : BaseActivity(), MusicSelectContract.IView {
    private var presenter: MusicSelectContract.IPresenter? = null
    private var textView: TextView? = null
    private var previousPosition = 0
    private var currentPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.enterTransition = AutoTransition()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_select)
        presenter = MusicSelectPresenter(this)
        val nextStepButton = findViewById<CardView>(R.id.okay)
        val backButton = findViewById<CardView>(R.id.backLayout_select)
        initUI()
        nextStepButton.measure(0, 0)
        Log.d(TAG, "onCreate: " + nextStepButton.measuredWidth)
        nextStepButton.setOnClickListener { view: View? ->
            val intent = Intent(this@MusicSelectActivity, MixActivity::class.java)
            val diary = MusicDiaryItem()
            diary.musicTabId = currentPosition
            intent.putExtra("diary", diary)
            startActivity(intent)
        }
        backButton.setOnClickListener { view: View? -> onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.stopMusic()
    }

    private fun initUI() {
        textView = findViewById(R.id.Emotion)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.musicSelectLayout)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView02)
        val adapter = MusicSelectAdapter(this)
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recyclerView)
        val fadeOut: Animation = AlphaAnimation(0.0f, 1f)
        val animationDuration: Long = 200
        fadeOut.duration = animationDuration
        presenter!!.switchMusic(0)
        textView?.text = MyApplication.musicTabList[0].emotion
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    currentPosition = manager.findFirstCompletelyVisibleItemPosition()
                    Log.d(TAG, "onScrollStateChanged: currentPosition=$currentPosition")
                    if (currentPosition < 0) return
                    if (currentPosition != previousPosition) {
                        textView!!.startAnimation(fadeOut)
                        textView!!.text =
                            MyApplication.musicTabList[currentPosition].emotion
                        presenter!!.switchMusic(currentPosition)
                        previousPosition = currentPosition
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "MusicSelectActivity"
    }
}