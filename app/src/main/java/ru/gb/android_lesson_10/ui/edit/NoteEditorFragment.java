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
import java.util.Date;

import ru.gb.android_lesson_10.MainActivity;
import ru.gb.android_lesson_10.R;
import ru.gb.android_lesson_10.repository.Note;

public class NoteEditorFragment extends Fragment {

    private Note note;
    Calendar calendar;
    //private DatePicker datePicker;

    public static NoteEditorFragment newInstance(Note note) {
        NoteEditorFragment fragment = new NoteEditorFragment();
        Bundle args = new Bundle();
        args.putParcelable("SameNote", note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable("SameNote");
            ((EditText) view.findViewById(R.id.inputTittle)).setText(note.getTittleNote());
            ((EditText) view.findViewById(R.id.inputBody)).setText(note.getBodyNote());
            calendar = Calendar.getInstance();
            calendar.setTime(note.getDateOfCreation());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((DatePicker) view.findViewById(R.id.inputDate)).setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                    }
                });
            }

            view.findViewById(R.id.buttonSaveChanges).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    note.setTittleNote(((EditText) view.findViewById(R.id.inputTittle)).getText().toString());
                    note.setBodyNote(((EditText) view.findViewById(R.id.inputTittle)).getText().toString());

                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                        DatePicker datePicker = ((DatePicker) view.findViewById(R.id.inputDate));
                        calendar.set(Calendar.YEAR, datePicker.getYear());
                        calendar.set(Calendar.MONTH, datePicker.getMonth());
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    }
                    note.setDateOfCreation(calendar.getTime());
                    ((MainActivity) requireActivity()).getPublisher().sendMessage(note);
                    requireActivity().getSupportFragmentManager().popBackStack();
                    view.clearFocus();
                }
            });
        }

    }

    private void initDatePicker(Date date){

    }
}