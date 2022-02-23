package com.hustunique.morii.util

import android.widget.Toast
import com.hustunique.morii.util.MyApplication.Companion.context

object FFmpegUtil {
    @JvmStatic
    private external fun run(commands: Array<String>)

    @JvmStatic
    private fun onProcessResult(code: Boolean) {
        if (code)
            Toast.makeText(context, "Process succeeded.", Toast.LENGTH_SHORT).show()
    }

    init {
        System.loadLibrary("galleryview")
    }
}