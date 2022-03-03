package ru.gb.android_lesson_10.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.android_lesson_10.R;
import ru.gb.android_lesson_10.repository.Note;
import ru.gb.android_lesson_10.repository.NoteSource;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    private NoteSource noteSource;

    OnItemNoteClickListener onItemNoteClickListener;

    public void setOnItemNoteClickListener(OnItemNoteClickListener onItemNoteClickListener){
        this.onItemNoteClickListener = onItemNoteClickListener;
    }

    public void setData(NoteSource noteSource) {
        this.noteSource = noteSource;
        notifyDataSetChanged();
    }

    public NoteListAdapter(NoteSource noteSource) {
        this.noteSource = noteSource;
    }

    public NoteListAdapter() {}

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

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTittle;
        private TextView textViewBody;
        private TextView textViewData;
        private CheckBox checkBox;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTittle = (TextView) itemView.findViewById(R.id.note_view_item_tittle_text);
            textViewBody = (TextView) itemView.findViewById(R.id.note_view_item_body_note);
            textViewData = (TextView) itemView.findViewById(R.id.note_view_item_date_of_creation);
            checkBox = (CheckBox) itemView.findViewById(R.id.note_view_item_is_done);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(onItemNoteClickListener != null){
//                        onItemNoteClickListener.onNoteClick(getLayoutPosition());
//                    }
//                }
//            });
        }
        public void bindContentWithLayout(Note content){

            textViewTittle.setText(content.getTittleNote());
            textViewBody.setText(content.getBodyNote());
            textViewData.setText("1.1.1999");
        }

    }
}
