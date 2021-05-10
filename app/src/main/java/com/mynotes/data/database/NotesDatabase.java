package com.mynotes.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mynotes.BuildConfig;
import com.mynotes.data.Notes;
import com.mynotes.data.dao.NotesDao;

@Database(
        entities = Notes.class,
        version = BuildConfig.VERSION_CODE,
        exportSchema = false
)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();
    private static NotesDatabase notesDatabase;

    public static NotesDatabase createNoteDatabase(Context context) {
        if(notesDatabase == null){

            notesDatabase = Room.databaseBuilder(context, NotesDatabase.class, "NOTES_DB").build();
        }
        return  notesDatabase;
    }
}
