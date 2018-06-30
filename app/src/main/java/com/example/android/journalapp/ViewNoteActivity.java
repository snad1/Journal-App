package com.example.android.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.journalapp.Database.AppDatabase;
import com.example.android.journalapp.Database.NoteEntry;

import static com.example.android.journalapp.AddNoteActivity.DEFAULT_TASK_ID;
import static com.example.android.journalapp.AddNoteActivity.EXTRA_NOTE_ID;
import static com.example.android.journalapp.AddNoteActivity.noteId;

public class ViewNoteActivity extends AppCompatActivity {

    AppDatabase appDatabase;

    TextView txttitle, txtbody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        init();

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_NOTE_ID)){

            noteId = intent.getIntExtra(EXTRA_NOTE_ID, DEFAULT_TASK_ID);

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

    private void init(){
        txttitle = findViewById(R.id.txttitle);
        txtbody = findViewById(R.id.txtbody);
//        btnupdate = findViewById(R.id.btnupdate);

//        btnupdate.setOnClickListener((view)->{onUpdateButtonClick();});
    }

    private void onUpdate(){
        Intent intent = new Intent(ViewNoteActivity.this, AddNoteActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, DEFAULT_TASK_ID);
        startActivity(intent);
        finish();
    }

    public void populateUI(NoteEntry noteEntry){
        if (noteEntry == null) {
            return;
        }
        txttitle.setText(noteEntry.getTitle());
        txtbody.setText(noteEntry.getNotes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {
            onUpdate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
