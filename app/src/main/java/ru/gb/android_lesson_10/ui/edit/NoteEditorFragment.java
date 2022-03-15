package ru.gb.android_lesson_10.ui.edit;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import ru.gb.android_lesson_10.MainActivity;
import ru.gb.android_lesson_10.R;
import ru.gb.android_lesson_10.repository.Note;

public class NoteEditorFragment extends Fragment {

    private Note note;
    Calendar calendar;
    EditText inputTittle;
    EditText inputBody;
    DatePicker datePicker;
    //private DatePicker datePicker;

    public static NoteEditorFragment newInstance(Note note) {
        NoteEditorFragment fragment = new NoteEditorFragment();
        Bundle args = new Bundle();
        args.putParcelable("SameNote", note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable("SameNote");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_editor, container, false);
        initView(view);
        if (note != null) {
            extractNote();
        }
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners(view);
    }

    private void initView(View view) {
        datePicker = ((DatePicker) view.findViewById(R.id.inputDate));
        inputTittle = ((EditText) view.findViewById(R.id.inputTittle));
        inputBody = ((EditText) view.findViewById(R.id.inputBody));
    }

    private void setListeners(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                    setCalendar(year, month, day);
                }
            });
        }

        view.findViewById(R.id.buttonSaveChanges).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    setCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                }
                saveNote();
                sendMessage();
                view.clearFocus();
            }
        });
    }

    private void setCalendar(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    private void saveNote() {
        note.setTittleNote(inputTittle.getText().toString());
        note.setBodyNote(inputBody.getText().toString());
        note.setDateOfCreation(calendar.getTime());
    }

    private void extractNote() {
        calendar = Calendar.getInstance();

        inputTittle.setText(note.getTittleNote());
        inputBody.setText(note.getBodyNote());
        calendar.setTime(note.getDateOfCreation());
    }

    private void sendMessage() {
        ((MainActivity) requireActivity()).getPublisher().sendMessage(note);
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}