package ru.gb.android_lesson_10.ui.new_note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import ru.gb.android_lesson_10.R;
import ru.gb.android_lesson_10.repository.LocalRepositoryImpl;
import ru.gb.android_lesson_10.repository.Note;
import ru.gb.android_lesson_10.repository.NoteSource;
import ru.gb.android_lesson_10.ui.main_view.NoteListAdapter;

public class NewNoteFragment extends Fragment {

    NoteListAdapter noteListAdapter;
    NoteSource noteData;
    Calendar calendar;
    EditText inputTittle;
    EditText inputBody;
    DatePicker datePicker;

    public static NewNoteFragment newInstance() {
        NewNoteFragment fragment = new NewNoteFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.new_note_menu, menu);
        menu.findItem(R.id.action_new_note).setVisible(false);
        menu.findItem(R.id.action_exit).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        noteListAdapter = new NoteListAdapter((NoteSource)this);
        noteData = new LocalRepositoryImpl(requireContext().getResources()).init();
        return inflater.inflate(R.layout.fragment_new_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setListener(view);
    }

    void setListener(View view) {
        view.findViewById(R.id.addNewNote).setOnClickListener(v -> {
            addNote(view);
        });
        view.findViewById(R.id.goBack).setOnClickListener(v -> {
            goBack();
        });
    }

    void initView(View view) {
        inputTittle = view.findViewById(R.id.inputNewTittle);
        inputBody = view.findViewById(R.id.inputNewBody);
        datePicker = view.findViewById(R.id.inputNewDate);
        if (inputTittle.getText() == null || inputBody.getText() == null) {
            inputTittle.setText(" ");
            inputBody.setText(" ");
        }
    }

    private void addNote(View view) {
        noteData.addNewNote(new Note(inputTittle.getText().toString(), inputBody.getText().toString(), Calendar.getInstance().getTime(), false));
        noteListAdapter.notifyItemInserted(noteData.size());
        requireActivity().onBackPressed();
    }

    void goBack() {
        requireActivity().onBackPressed();
    }

}