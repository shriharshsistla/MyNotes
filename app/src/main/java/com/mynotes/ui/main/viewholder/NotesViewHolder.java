package com.mynotes.ui.main.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mynotes.R;
import com.mynotes.data.Notes;
import com.mynotes.ui.main.listener.OptionsListener;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView description;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tvTitle);
        description = itemView.findViewById(R.id.tvDescription);

    }

    public void bindNotes(Notes notes, OptionsListener optionsListener) {
        title.setText(notes.getTitle());
        description.setText(notes.getDescription());
        itemView.setOnLongClickListener(v -> {
            optionsListener.onLongClicked(notes.getId());
            return false;
        });
        itemView.setOnClickListener(v ->
                optionsListener.onNormalClicked(notes.getId()));
    }
}
