package ru.gb.android_lesson_10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    String[] data;

    OnItemNoteClickListener onItemNoteClickListener;

    public void setOnItemNoteClickListener(OnItemNoteClickListener onItemNoteClickListener){
        this.onItemNoteClickListener = onItemNoteClickListener;
    }

    public void setData(String[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public NoteListAdapter(String[] data) {
        this.data = data;
    }

    public NoteListAdapter() {}

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new NoteViewHolder(layoutInflater.inflate(R.layout.fragment_note_list_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindContentWithLayout(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemNoteClickListener != null){
                        onItemNoteClickListener.onNoteClick(getLayoutPosition());
                    }
                }
            });
        }
        public void bindContentWithLayout(String content){
            textView.setText(content);
        }

    }
}
