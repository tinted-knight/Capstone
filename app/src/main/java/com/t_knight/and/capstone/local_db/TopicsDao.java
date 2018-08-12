package com.t_knight.and.capstone.local_db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TopicsDao {

    @Query("select * from topics") List<TopicEntity> getAll();

    @Insert void insertTopic(TopicEntity... entities);

    @Delete() void clear(TopicEntity... entities);
}
