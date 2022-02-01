package com.hustunique.morii.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class SoundItemInfo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long soundItemInfoId;
    public long diaryInfoId;
    public int soundItemId;
    public int soundItemPosition;

    public SoundItemInfo(long soundItemInfoId, long diaryInfoId, int soundItemId, int soundItemPosition) {
        this.soundItemInfoId = soundItemInfoId;
        this.diaryInfoId = diaryInfoId;
        this.soundItemId = soundItemId;
        this.soundItemPosition = soundItemPosition;
    }

    @Ignore
    public SoundItemInfo(long diaryInfoId, int soundItemPosition, int soundItemId) {
        this.diaryInfoId = diaryInfoId;
        this.soundItemId = soundItemId;
        this.soundItemPosition = soundItemPosition;
    }

    @Ignore
    public SoundItemInfo(int soundItemPosition, int soundItemId) {
        this.soundItemId = soundItemId;
        this.soundItemPosition = soundItemPosition;
    }
}
