package ru.gb.android_lesson_10.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.gb.android_lesson_10.R;
import ru.gb.android_lesson_10.repository.LocalRepositoryImpl;

public class NoteListFragment extends Fragment implements OnItemNoteClickListener {

    NoteListAdapter noteListAdapter;

    public static NoteListFragment newInstance() {
        NoteListFragment fragment = new NoteListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRecycler(view);
    }

    void initAdapter(){
        noteListAdapter = new NoteListAdapter();
        noteListAdapter.setData((new LocalRepositoryImpl(requireContext().getResources())).init());
        noteListAdapter.setOnItemNoteClickListener(this);
    }

    void initRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.noteRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(noteListAdapter);
    }

    private String[] getData() {
        String[] data = getResources().getStringArray(R.array.note_list);
        return data;
    }

    @Override
    public void onNoteClick(int position) {
        String[] data = getData();
        Toast.makeText(requireContext(), "Жамкнули на " + data[position], Toast.LENGTH_SHORT).show();
    }
}