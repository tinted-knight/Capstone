package com.t_knight.and.capstone.local_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.t_knight.and.capstone.model.SingleCard;

@Entity(tableName = "read_cards")
public class ReadCardEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public int id;

    public final String from;

    public final String to;

    public ReadCardEntity(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public ReadCardEntity(SingleCard card) {
        from = card.getFrom();
        to = card.getTo();
    }

}
