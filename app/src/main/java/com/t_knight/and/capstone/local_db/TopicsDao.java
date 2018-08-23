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

    @Update() void updateTopic(TopicEntity topicEntity);

    @Insert void insertReadCards(ReadCardEntity... cards);

    @Query("delete from read_cards") void clearCards();

    @Query("select * from read_cards where _id = :id") ReadCardEntity getCard(int id);

    @Query("select * from read_cards") List<ReadCardEntity> getAllCards();

    @Query("select * from read_cards order by _id limit 1") LiveData<ReadCardEntity> widgetUpdate();

    @Query("select * from read_cards order by _id limit 1") ReadCardEntity getFirstCard();
}
