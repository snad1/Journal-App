package com.example.android.journalapp.Database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DataConverter {

    @TypeConverter
    public static Date toData(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}
