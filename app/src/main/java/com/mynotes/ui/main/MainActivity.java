package com.mynotes.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mynotes.R;
import com.mynotes.data.Notes;
import com.mynotes.data.dao.NotesDao;
import com.mynotes.data.database.NotesDatabase;
import com.mynotes.ui.addnotes.AddNotesActivity;
import com.mynotes.ui.main.adapter.NotesAdapter;
import com.mynotes.ui.main.listener.OptionsListener;
import com.mynotes.ui.notedetails.NoteDetailsDialogFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OptionsListener {

    FloatingActionButton addButton;
    RecyclerView recyclerView;
    NotesDao notesDao;
    List<Notes> listOfNotes;
    ProgressBar progressBar;
    FloatingActionButton refreshButton;
    private static final int REQUEST_CODE = 8926;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getNotesData();
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNotesActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    private void getNotesData() {
        if(notesDao==null){
            NotesDatabase notesDatabase = NotesDatabase.createNoteDatabase(this);
            notesDao = notesDatabase.notesDao();
        }
        class DataExtractor extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                listOfNotes = notesDao.getNotesList();
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                if(!listOfNotes.isEmpty()){
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(new NotesAdapter(listOfNotes, MainActivity.this));
                }
                progressBar.setVisibility(View.GONE);

            }
        }
        DataExtractor dataExtractor = new DataExtractor();
        dataExtractor.execute();

    }

    private void initView() {
        addButton = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.notesRecycler);
        progressBar = findViewById(R.id.notesRetrieverProgress);
        refreshButton = findViewById(R.id.btnUpdate);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotesData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            getNotesData();
        }
    }

    @Override
    public void onLongClicked(int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Do you want to delete this")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteNotesFromId(position);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                }).create();
        alertDialog.show();

    }

    @Override
    public void onNormalClicked(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("note_id", id);
        NoteDetailsDialogFragment noteDetailsDialogFragment = NoteDetailsDialogFragment.createFragment(bundle);
        noteDetailsDialogFragment.show(getSupportFragmentManager(), "DETAILS_DIALOG");
    }

    private void deleteNotesFromId(int position) {

        class DeleteTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                notesDao.delete(position);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                getNotesData();
            }
        }
        DeleteTask deleteTask = new DeleteTask();
        deleteTask.execute();
    }
}