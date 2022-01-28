package com.hustunique.morii.util;

import static com.hustunique.morii.util.MyApplication.context;

import androidx.room.Room;

import com.hustunique.morii.database.AppDatabase;
import com.hustunique.morii.database.DiaryDao;
import com.hustunique.morii.database.DiaryWithSoundItemInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DatabaseUtil {
    private static final ExecutorService exec = Executors.newCachedThreadPool();
    public static AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "app_database")
            .build();
    public static DiaryDao dao = appDatabase.diaryDao();

    public List<DiaryWithSoundItemInfo> readDataFromRoomDataBase() {
        Future<List<DiaryWithSoundItemInfo>> future = exec.submit(() -> dao.getAllDiaryWithSoundItemInfo());
        List<DiaryWithSoundItemInfo> list = null;
        try {
            list = future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

}
