package ru.gb.android_lesson_10.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.gb.android_lesson_10.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewNoteFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_new_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setHasOptionsMenu(true);
        initView(view);
    }

    void initView(View view){
        view.findViewById(R.id.add_new_note).setOnClickListener(v -> {addNote();});
        view.findViewById(R.id.go_back).setOnClickListener(v ->{goBack();});
    }
    private void addNote() {



        Toast.makeText(requireContext(), "Типо добавил заметку", Toast.LENGTH_SHORT).show();
        requireActivity().onBackPressed();
    }

    void goBack() {
        requireActivity().onBackPressed();
    }

}