package com.hustunique.morii.home

import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hustunique.morii.music.MusicSelectActivity
import com.hustunique.morii.util.BaseActivity
import com.hustunique.morii.util.MyApplication
import com.hustunique.morii.util.MyApplication.Companion.musicDiaryList
import morii.R
import morii.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MusicDiaryAdapter
    private var showAssistantDialog: Boolean = true
    private var sortByDateAscending: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.exitTransition = Slide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showAssistantDialog()
        initUI()
        setRecyclerView()
    }

    private fun showAssistantDialog() {
        val sharedPref = this.getSharedPreferences(
            MyApplication.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )
        showAssistantDialog = sharedPref.getBoolean("showAssistantDialog", true);
        if (showAssistantDialog) {
            val builder = MaterialAlertDialogBuilder(this);
            builder.setMessage("用户指引").setTitle("欢迎使用Morii音乐日记").setNegativeButton(
                "关闭且不再显示"
            ) { dialog, which ->
                with(sharedPref.edit()) {
                    putBoolean("showAssistantDialog", false)
                    apply()
                }
            }
                .setPositiveButton(
                    "完成"
                ) { _, _ -> }.show()

        }
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
        binding.reverseSwitchButton.setOnClickListener { reverseRecyclerView() }
    }

    private fun reverseRecyclerView() {
        if (sortByDateAscending) {
            binding.sortButtonText.setText("正序排列")
            binding.musicDiaryRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true).apply {
                    stackFromEnd = true
                }
        } else {
            binding.sortButtonText.setText("倒序排列")
            binding.musicDiaryRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        sortByDateAscending = sortByDateAscending.not()
    }


    companion object {
        private const val TAG = "MainActivity"
    }

}