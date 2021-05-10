package com.mynotes.ui.notedetails;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mynotes.R;
import com.mynotes.data.Notes;
import com.mynotes.data.dao.NotesDao;
import com.mynotes.data.database.NotesDatabase;

import org.w3c.dom.Text;

public class NoteDetailsDialogFragment extends DialogFragment implements View.OnClickListener{

    NotesDao notesDao;
    TextView title;
    TextView description;
    Notes notes;
    ImageButton editButton;
    Button saveButton;
    int noteId;

   public static NoteDetailsDialogFragment createFragment(Bundle bundle) {
       NoteDetailsDialogFragment noteDetailsDialogFragment = new NoteDetailsDialogFragment();
       noteDetailsDialogFragment.setArguments(bundle);
       return noteDetailsDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotesDatabase notesDatabase = NotesDatabase.createNoteDatabase(getContext());
        notesDao = notesDatabase.notesDao();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_fragment);
        initView(view);
        getDetails();
    }

    private void initView(View view) {
       title = view.findViewById(R.id.textView3);
       description = view.findViewById(R.id.textView2);
       editButton = view.findViewById(R.id.btnEdit);
       saveButton = view.findViewById(R.id.btnSaveFromEdit);
       editButton.setOnClickListener(this);
       saveButton.setOnClickListener(this);
    }

    private void getDetails() {
       if(getArguments()!=null){
            noteId = getArguments().getInt("note_id");
           getDataFromDb(noteId);
       }
    }

    private void getDataFromDb(int noteId) {
       class DataExtractor extends AsyncTask<Void, Void, Void>{

           @Override
           protected Void doInBackground(Void... voids) {
               notes =  notesDao.getParticularNote(noteId);
               return null;
           }

           @Override
           protected void onPostExecute(Void unused) {
               super.onPostExecute(unused);
               if(notes!=null){
                   title.setText(notes.getTitle());
                   description.setText(notes.getDescription());
               }
           }
       }
       DataExtractor dataExtractor = new DataExtractor();
       dataExtractor.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEdit:
                title.setEnabled(true);
                description.setEnabled(true);
                editButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.VISIBLE);
                break;
                
            case R.id.btnSaveFromEdit:
                updateNote();
                break;
        }
    }

    private void updateNote() {

       Notes notes = new Notes();
       notes.setId(noteId);
       notes.setTitle(title.getText().toString());
       notes.setDescription(description.getText().toString());

       class UpdaterClass extends AsyncTask<Void, Void, Void>{

           @Override
           protected Void doInBackground(Void... voids) {
               notesDao.updateNote(notes);
               return null;
           }

           @Override
           protected void onPostExecute(Void unused) {
               super.onPostExecute(unused);
               Toast.makeText(getContext(), "Note Updated Successfully", Toast.LENGTH_SHORT).show();
           }
       }
       UpdaterClass updaterClass = new UpdaterClass();
       updaterClass.execute();
    }


}



