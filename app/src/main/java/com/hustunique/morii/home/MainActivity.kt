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
import morii.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MusicDiaryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.exitTransition = Slide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = MusicDiaryAdapter(this, musicDiaryList)
        binding.musicDiaryRecyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyItemInserted(musicDiaryList.size)
    }

    private fun initUI() {
        Log.d(TAG, "initUI: ")
        binding.buttonMain.setOnClickListener {
            val intent = Intent(this@MainActivity, MusicSelectActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}