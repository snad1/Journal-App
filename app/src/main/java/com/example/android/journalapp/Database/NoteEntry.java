package com.example.android.journalapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity (tableName = "note")
@TypeConverters(DataConverter.class)
public class NoteEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String notes;
    @ColumnInfo(name = "created_at")
    private Date created_at;

    public NoteEntry(int id, String title, String notes, Date created_at) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.created_at = created_at;
    }

    @Ignore
    public NoteEntry(String title, String notes) {
        this.title = title;
        this.notes = notes;
    }

    @Ignore
    public NoteEntry(String title, String notes, Date created_at) {
        this.title = title;
        this.notes = notes;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
