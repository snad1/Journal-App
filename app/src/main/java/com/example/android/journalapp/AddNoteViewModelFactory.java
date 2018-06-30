package com.example.android.journalapp;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.journalapp.Database.AppDatabase;

public class AddNoteViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    // COMPLETED (2) Add two member variables. One for the database and one for the taskId
    private final AppDatabase appDatabase;
    private final int noteId;

    public AddNoteViewModelFactory(@NonNull Application application, AppDatabase appDatabase, int noteId) {
        super(application);
        this.appDatabase = appDatabase;
        this.noteId = noteId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new AddNoteViewModel(appDatabase, noteId);
    }
}
