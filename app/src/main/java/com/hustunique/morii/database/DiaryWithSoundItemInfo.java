package com.hustunique.morii.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

public class DiaryWithSoundItemInfo {
    @Embedded
    public DiaryInfo diaryInfo;

    @Relation(
            parentColumn = "id",
            entityColumn = "diaryInfoId"
    )
    public List<SoundItemInfo> soundItemInfoList;
}
