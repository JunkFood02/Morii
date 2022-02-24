package com.hustunique.morii.util

import android.os.Environment
import android.os.Looper
import android.widget.Toast
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.MyApplication.Companion.context
import com.hustunique.morii.util.MyApplication.Companion.musicTabList
import com.hustunique.morii.util.MyApplication.Companion.soundItemList
import java.io.*


object AudioProcessor {
    private const val TAG = "AudioProcessor"
    private val listener: OnProgressListener = object : OnProgressListener() {
        override fun onProcessFinished(code: Int, path: String) {
            super.onProcessFinished(code, path)
            Looper.prepare()
            Toast.makeText(context, "音频文件创建成功，路径:$path", Toast.LENGTH_SHORT).show()
            cleanTemp()
        }
    }
    val tempList: MutableList<String> = ArrayList()

    @JvmStatic
    fun makeAudioMix(item: MusicDiaryItem) {
        Thread {
            val commands: MutableList<String> = ArrayList()
            val path: String =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath + "/audioMix_%s.aac".format(
                    item.title
                )
            createTempFiles(musicTabList[item.musicTabId].musicResId)
            item.soundItemInfoList.forEach { createTempFiles(soundItemList[it!!.soundItemId].getSoundResIds()[0]) }
            commands.add("ffmpeg")
            commands.add("-i")
            commands.add(tempList.first())
            commands.add("-stream_loop")
            commands.add("-1")
            for (i in 1..item.soundItemInfoList.size) {
                commands.add("-i")
                commands.add(tempList[i])
            }
            commands.add("-filter_complex")
            commands.add(
                "amix=inputs=%d:duration=first:dropout_transition=3".format(
                    item.soundItemInfoList.size + 1
                )
            )
            commands.add("-y")
            commands.add(path)
            FFmpegUtil.run(commands.toTypedArray(), listener)
        }.start()
    }

    private fun createTempFiles(resId: Int) {
        val song: InputStream = context.resources.openRawResource(resId)
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            context.resources.getResourceEntryName(resId) + "_temp" + ".aac"
        )
        if (file.exists())
            file.delete()
        val copySong: OutputStream = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var readvalue = 0
        readvalue = song.read(buffer)
        while (readvalue > 0) {
            copySong.write(buffer, 0, readvalue)
            readvalue = song.read(buffer)
        }
        copySong.close()
        tempList.add(file.absolutePath)
    }

    private fun cleanTemp() {
        tempList.forEach { if (File(it).exists()) File(it).delete() }
        tempList.clear()
    }
}