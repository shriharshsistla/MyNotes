package com.mynotes.ui.addnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mynotes.R;
import com.mynotes.data.Notes;
import com.mynotes.data.dao.NotesDao;
import com.mynotes.data.database.NotesDatabase;

import java.util.Calendar;

public class AddNotesActivity extends AppCompatActivity implements View.OnClickListener{

    NotesDao notesDao;
    EditText titleEditText;
    EditText descriptionEdittext;
    Button saveButton;
    ProgressBar progressBar;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        initView();
        createDatabase();
    }

    private void createDatabase() {

        NotesDatabase notesDatabase = NotesDatabase.createNoteDatabase(this);
        notesDao = notesDatabase.notesDao();
    }

    private void initView() {
        titleEditText = findViewById(R.id.ed_title);
        descriptionEdittext = findViewById(R.id.ed_desc);
        saveButton = findViewById(R.id.button);
        progressBar = findViewById(R.id.notesProgressBar);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            if(!titleEditText.getText().toString().isEmpty() && !descriptionEdittext.getText().toString().isEmpty()){
                addData();
            }
        }

    }

    private void addData() {
        Notes notes = new Notes();
        notes.setTitle(titleEditText.getText().toString());
        notes.setDescription(descriptionEdittext.getText().toString());

        class DataSaver extends AsyncTask<Void, Void, Void>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                notesDao.insertNotes(notes);
                return null;
            }



            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                progressBar.setVisibility(View.GONE);
                setResult(RESULT_OK);
                finish();

            }
        }

        DataSaver dataSaver = new DataSaver();
        dataSaver.execute();
    }
}