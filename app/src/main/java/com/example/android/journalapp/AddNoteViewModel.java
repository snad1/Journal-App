package com.example.android.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.journalapp.Database.AppDatabase;
import com.example.android.journalapp.Database.NoteEntry;

public class AddNoteViewModel extends ViewModel {

    private LiveData<NoteEntry> note;

    public AddNoteViewModel(AppDatabase appDatabase, int taskId) {
        note = appDatabase.notesDao().loadNoteById(taskId);
    }

    public LiveData<NoteEntry> getTask() {
        return note;
    }
}
