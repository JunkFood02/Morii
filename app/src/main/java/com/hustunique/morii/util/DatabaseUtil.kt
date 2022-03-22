package com.hustunique.morii.util

import com.hustunique.morii.database.SoundItemInfo
import com.hustunique.morii.database.DiaryInfo
import com.hustunique.morii.database.DiaryWithSoundItemInfo
import com.hustunique.morii.database.AppDatabase
import androidx.room.Room
import java.io.File
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future

object DatabaseUtil {
    private const val TAG = "DatabaseUtil"
    private val exec = Executors.newCachedThreadPool()
    private val appDatabase = Room.databaseBuilder(
        MyApplication.context,
        AppDatabase::class.java,
        "app_database"
    )
        .build()
    private val dao = appDatabase.diaryDao()
    fun readDataFromRoomDataBase(): List<DiaryWithSoundItemInfo> {
        val future: Future<List<DiaryWithSoundItemInfo>> = exec.submit(
            Callable { dao.allDiaryWithSoundItemInfo })
        lateinit var list: List<DiaryWithSoundItemInfo>
        try {
            list = future.get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return list
    }

    fun insertDiaryInfo(diaryInfo: DiaryInfo): Long {
        val future = exec.submit<Long> { dao.insertDiaryInfo(diaryInfo) }
        var id: Long = 0
        try {
            id = future.get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return id
    }

    fun insertSoundItemInfo(info: SoundItemInfo) {
        Thread { dao.insertSoundItemInfo(info) }.start()
    }

    fun deleteDiary(id: Long) {
        Thread { dao.deleteInfoById(id) }.start()
    }

    fun deleteAudioFile(title: String, date: String) {
        File(getAudioFilePath(title, date)).delete()
    }

    fun getAudioFilePath(title: String, date: String): String {
        return MyApplication.externalPath + "/%s_%s.aac".format(
            title.replace('/', '_'), date
        )
    }
}