package com.example.android.journalapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.journalapp.Database.AppDatabase;
import com.example.android.journalapp.Database.NoteEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<NoteEntry>> notes;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        notes = appDatabase.notesDao().loadAllNotes();
    }

    public LiveData<List<NoteEntry>> getTasks() {
        return notes;
    }
}
