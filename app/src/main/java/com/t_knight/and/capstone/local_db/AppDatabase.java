package com.t_knight.and.capstone.local_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {TopicEntity.class, ReadCardEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "LangQuiz";

    private static AppDatabase instance;

    public abstract TopicsDao topicsDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class, DB_NAME)
                    .build();
        }
        return instance;
    }

}
