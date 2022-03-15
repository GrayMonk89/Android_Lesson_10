package ru.gb.android_lesson_10.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import ru.gb.android_lesson_10.R;
import ru.gb.android_lesson_10.repository.Note;
import ru.gb.android_lesson_10.repository.NoteSource;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    private NoteSource noteSource;

    OnItemNoteClickListener onItemNoteClickListener;

    Fragment fragment;

    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setOnItemNoteClickListener(OnItemNoteClickListener onItemNoteClickListener) {
        this.onItemNoteClickListener = onItemNoteClickListener;
    }

    public void setData(NoteSource noteSource) {
        this.noteSource = noteSource;
        notifyDataSetChanged();
    }

    public NoteListAdapter(NoteSource noteSource) {
        this.noteSource = noteSource;
    }

    public NoteListAdapter() {
    }

    NoteListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new NoteViewHolder(layoutInflater.inflate(R.layout.fragment_note_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindContentWithLayout(noteSource.getNoteData(position));
    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTittle;
        private final TextView textViewBody;
        private final TextView textViewData;
        private final ToggleButton toggleButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTittle = (TextView) itemView.findViewById(R.id.note_view_item_tittle_text);
            textViewBody = (TextView) itemView.findViewById(R.id.note_view_item_body_note);
            textViewData = (TextView) itemView.findViewById(R.id.note_view_item_date_of_creation);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.note_view_item_is_done);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    return false;
                }
            });

            fragment.registerForContextMenu(itemView);

        }

        public void bindContentWithLayout(Note note) {
            textViewTittle.setText(note.getTittleNote());
            textViewBody.setText(note.getBodyNote());
            textViewData.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(note.getDateOfCreation()));
            toggleButton.setChecked(note.isDone());
        }

    }
}
