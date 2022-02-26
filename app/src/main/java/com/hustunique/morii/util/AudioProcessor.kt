package com.hustunique.morii.util

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.MyApplication.Companion.context
import com.hustunique.morii.util.MyApplication.Companion.externalPath
import com.hustunique.morii.util.MyApplication.Companion.musicTabList
import com.hustunique.morii.util.MyApplication.Companion.soundItemList
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import kotlin.collections.ArrayList


object AudioProcessor {
    private const val TAG = "AudioProcessor"
    private lateinit var handler: Handler;
    private val listener: OnProgressListener = object : OnProgressListener() {
        override fun onProcessFinished(code: Int, path: String) {
            super.onProcessFinished(code, path)
            Looper.prepare()
            if (code != 0) {
                Toast.makeText(context, "音频文件创建失败", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(context, "音频文件创建成功", Toast.LENGTH_SHORT).show()
            cleanTemp()
            val message = Message()
            message.obj = path
            handler.sendMessage(message)
        }
    }
    val tempList: MutableList<String> = ArrayList()

    fun makeAudioMix(item: MusicDiaryItem, handler: Handler) {
        Thread {
            this.handler = handler
            val commands: MutableList<String> = ArrayList()
            val outputPath: String =
                externalPath + "/%s_%s.aac".format(
                    item.title, item.date
                )
            createTempFiles(musicTabList[item.musicTabId].musicResId)
            item.soundItemInfoList.forEach { createTempFiles(soundItemList[it.soundItemId].getSoundResIds()[it.soundItemPosition % 3]) }
            commands.add("ffmpeg")
            commands.add("-i")
            commands.add(tempList.first())
            if (tempList.size > 1) {
                commands.add("-stream_loop")
                commands.add("-1")
                for (i in 1..item.soundItemInfoList.size) {
                    commands.add("-i")
                    commands.add(tempList[i])
                }
                commands.add("-filter_complex")
                val builder = StringBuilder()
                builder.append(
                    "amix=inputs=%d:duration=first:dropout_transition=3:weights=10".format(
                        item.soundItemInfoList.size + 1
                    )
                )
                for (info in item.soundItemInfoList) {
                    builder.append(" " + (AudioExoPlayerUtil.volumes[info.soundItemPosition / 3] * 10).toString())
                }
                commands.add(builder.toString())
            }
            commands.add("-y")
            commands.add(outputPath)
            FFmpegUtil.run(commands.toTypedArray(), listener)
        }.start()
    }

    private fun createTempFiles(resId: Int) {
        val song: InputStream = context.resources.openRawResource(resId)
        val tempPath =
            externalPath + "/" + context.resources.getResourceEntryName(resId) + "_tmp" + ".aac"
        val file =
            File(tempPath)
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
        Thread {
            tempList.run {
                forEach { File(it).run { if (exists()) delete() } }
                clear()
            }
        }.start()
    }
}