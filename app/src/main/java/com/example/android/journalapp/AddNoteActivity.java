package com.example.android.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.journalapp.Database.AppDatabase;
import com.example.android.journalapp.Database.NoteEntry;

import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "extraNoteId";
    public static final String INSTANCE_NOTE_ID = "instanceNoteId";

    public static final int DEFAULT_TASK_ID = -1;

    private static final String TAG = AddNoteActivity.class.getSimpleName();

    public static int noteId = DEFAULT_TASK_ID;

    private AppDatabase appDatabase;

    private Date old_date;

    private EditText edttitle;
    private TextInputEditText edtbody;
    private Button btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initViews();

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_NOTE_ID))
            noteId = savedInstanceState.getInt(INSTANCE_NOTE_ID,DEFAULT_TASK_ID);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_NOTE_ID)){
//            btnadd.setText("Update");

            AddNoteViewModelFactory factory = new AddNoteViewModelFactory(getApplication(),appDatabase, noteId);
            final AddNoteViewModel viewModel
                    = ViewModelProviders.of(this, factory).get(AddNoteViewModel.class);

            viewModel.getTask().observe(this, new Observer<NoteEntry>() {
                @Override
                public void onChanged(@Nullable NoteEntry noteEntry) {
                    viewModel.getTask().removeObserver(this);
                    populateUI(noteEntry);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_NOTE_ID,noteId);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        edttitle = findViewById(R.id.title);
        edtbody = findViewById(R.id.body);
//        btnadd = findViewById(R.id.btnadd);

//        btnadd.setOnClickListener((view)-> {onSaveButtonClick();});
    }

    private void onSave() {
        String title = edttitle.getText().toString();
        String body = edtbody.getText().toString();
        Date date = new Date();

        final NoteEntry noteEntry = new NoteEntry(title,body,date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (noteId == DEFAULT_TASK_ID) {
                    appDatabase.notesDao().insertNote(noteEntry);
                } else {
                    noteEntry.setId(noteId);
                    noteEntry.setCreated_at(old_date);
                    appDatabase.notesDao().updateNote(noteEntry);
                }
                finish();
            }
        });
    }

    public void populateUI(NoteEntry noteEntry){
        if (noteEntry == null) {
            return;
        }
        edttitle.setText(noteEntry.getTitle());
        edtbody.setText(noteEntry.getNotes());
        old_date = noteEntry.getCreated_at();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            onSave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}