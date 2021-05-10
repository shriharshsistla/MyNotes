package com.mynotes.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mynotes.R;
import com.mynotes.data.Notes;
import com.mynotes.ui.main.listener.OptionsListener;
import com.mynotes.ui.main.viewholder.NotesViewHolder;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    List<Notes> listOfNotes;
    OptionsListener optionsListener;

    public NotesAdapter(List<Notes> listOfNotes, OptionsListener optionsListener) {
        this.listOfNotes = listOfNotes;
        this.optionsListener = optionsListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes notes = listOfNotes.get(position);
        holder.bindNotes(notes,optionsListener);
    }

    @Override
    public int getItemCount() {
        return listOfNotes.size();
    }
}
