package com.mynotes.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mynotes.data.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotes(Notes notes);

    @Transaction
    @Query("SELECT * FROM notes_table")
     List<Notes> getNotesList();

    @Query("DELETE FROM notes_table WHERE id = :noteId")
    void delete(int noteId);

    @Query("SELECT * FROM notes_table WHERE id =:nodeId")
    Notes getParticularNote(int nodeId);

    @Update
    void updateNote(Notes notes);

}
