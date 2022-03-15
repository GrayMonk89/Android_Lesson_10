package ru.gb.android_lesson_10.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import ru.gb.android_lesson_10.MainActivity;
import ru.gb.android_lesson_10.R;
import ru.gb.android_lesson_10.publisher.Observer;
import ru.gb.android_lesson_10.repository.LocalRepositoryImpl;
import ru.gb.android_lesson_10.repository.Note;
import ru.gb.android_lesson_10.repository.NoteSource;
import ru.gb.android_lesson_10.ui.edit.NoteEditorFragment;

public class NoteListFragment extends Fragment implements OnItemNoteClickListener {

    NoteListAdapter noteListAdapter;
    NoteSource noteData;

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.note_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.action_add): {
                noteData.addNewNote(new Note("Note " + (noteData.size() + 1), "Note body " + (noteData.size() + 1), Calendar.getInstance().getTime(), false));
                noteListAdapter.notifyItemInserted(noteData.size());
                return true;
            }
            case (R.id.action_clear): {
                noteData.clearAllNotes();
                noteListAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.note_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int menuPosition = noteListAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case (R.id.action_edit_list_item): {

                Observer observer = new Observer() {
                    @Override
                    public void receiveMessage(Note note) {
                        ((MainActivity) requireActivity()).getPublisher().unsubscribe(this);
                        noteData.updateNote(menuPosition, note);
                        noteListAdapter.notifyItemChanged(menuPosition);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity())
                        .getNavigation()
                        .addFragment(NoteEditorFragment.newInstance(noteData.getNoteData(menuPosition)), true);
                return false;
            }
            case (R.id.action_delete_list_item): {
                noteData.deleteNote(menuPosition);
                noteListAdapter.notifyItemRemoved(menuPosition);
                return false;
            }
        }
        return super.onContextItemSelected(item);
    }

    void initAdapter() {
        noteListAdapter = new NoteListAdapter(this);
        noteData = new LocalRepositoryImpl(requireContext().getResources()).init();
        noteListAdapter.setData(noteData);
        noteListAdapter.setOnItemNoteClickListener(this);
    }

    void initRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.noteRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(noteListAdapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setChangeDuration(2000);
        animator.setRemoveDuration(2000);
        recyclerView.setItemAnimator(animator);
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