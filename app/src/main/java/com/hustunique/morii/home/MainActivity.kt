package com.hustunique.morii.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hustunique.morii.music.MusicSelectActivity
import com.hustunique.morii.util.BaseActivity
import com.hustunique.morii.util.MyApplication
import com.hustunique.morii.util.MyApplication.Companion.musicDiaryList
import morii.R

class MainActivity : BaseActivity() {
    private var constraintLayout: ConstraintLayout? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: MusicDiaryAdapter? = null
    private var button: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.exitTransition = Slide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        setRecyclerView(musicDiaryList)
    }

    private fun setRecyclerView(list: MutableList<MusicDiaryItem?>) {
        recyclerView = findViewById(R.id.recyclerView01)
        adapter = MusicDiaryAdapter(this, list)
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.layoutManager = manager
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter!!.notifyItemInserted(MyApplication.musicDiaryList.size)
    }

    private fun initUI() {
        Log.d(TAG, "initUI: ")
        button = findViewById(R.id.arcMain)
        constraintLayout = findViewById(R.id.mainLayout)
        button!!.setOnClickListener {
            val intent = Intent(this@MainActivity, MusicSelectActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}