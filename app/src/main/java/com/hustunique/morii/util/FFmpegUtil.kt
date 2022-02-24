package com.hustunique.morii.util

import android.widget.Toast
import com.hustunique.morii.util.MyApplication.Companion.context

object FFmpegUtil {
    private lateinit var listener: OnProgressListener
    private lateinit var path: String

    @JvmStatic
    external fun run(commands: Array<String>)

    @JvmStatic
    fun run(commands: Array<String>, listener: OnProgressListener) {
        path = commands.last()
        this.listener = listener
        run(commands)
    }

    @JvmStatic
    private fun onProcessResult(code: Int) {
        listener.onProcessFinished(code, path)
    }

    init {
        System.loadLibrary("x264")
        System.loadLibrary("morii")
    }
}