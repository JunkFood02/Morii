package com.hustunique.morii.util

import android.os.Environment
import android.util.Log
import com.hustunique.morii.home.MusicDiaryItem
import com.hustunique.morii.util.AudioExoPlayerUtil.UriParser
import com.hustunique.morii.util.MyApplication.Companion.context
import com.hustunique.morii.util.MyApplication.Companion.musicTabList
import com.hustunique.morii.util.MyApplication.Companion.soundItemList
import morii.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


object AudioProcessor {
    private const val TAG = "AudioProcessor"

    @JvmStatic
    fun makeAudioMix(item: MusicDiaryItem) {
        val commands: MutableList<String> = ArrayList()
        commands.add("ffmpeg")
        commands.add("-i")
        commands.add(temp(musicTabList[item.musicTabId].musicResId))
        commands.add("-stream_loop")
        commands.add("-1")
        MyApplication.context.resources.openRawResource(R.raw.rain)
        item.soundItemInfoList.forEach {
            commands.add("-i")
            commands.add(temp(soundItemList[it!!.soundItemId].getSoundResIds()[0]))
        }
        commands.add("-filter_complex")
        commands.add("amix=inputs=%d:duration=first:dropout_transition=3".format(item.soundItemInfoList.size + 1))
        commands.add(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath + "/output.aac")
        FFmpegUtil.run(commands.toTypedArray())
    }

    @JvmStatic
    private fun temp(resId: Int): String {
        val song: InputStream = context.resources.openRawResource(resId)
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
            context.resources.getResourceEntryName(resId) + ".aac"
        )
        val copySong: OutputStream = FileOutputStream(file)

        val buffer = ByteArray(1024)
        var readvalue = 0
        readvalue = song.read(buffer)
        while (readvalue > 0) {
            copySong.write(buffer, 0, readvalue)
            readvalue = song.read(buffer)
        }
        copySong.close()
        Log.d(TAG, "temp: " + file.absolutePath)
        return file.absolutePath;
    }
}