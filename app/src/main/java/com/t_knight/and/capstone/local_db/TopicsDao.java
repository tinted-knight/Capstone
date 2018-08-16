package com.t_knight.and.capstone.local_db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TopicsDao {

    @Query("select * from topics") List<TopicEntity> getAllForWidget();

    @Query("select * from topics") LiveData<List<TopicEntity>> getAll();

    @Insert void insertTopic(TopicEntity... entities);

    @Delete() void clear(TopicEntity... entities);

    @Query("update topics set pinned = 0 where pinned = 1") void unpinAll();

    @Query("select * from topics where pinned = 1") TopicEntity getPinned();

    @Update() void updateTopic(TopicEntity topicEntity);
}
