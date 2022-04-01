package ru.gb.android_lesson_10.ui.main_view;


import static ru.gb.android_lesson_10.repository.LocalSharedPreferenceRepositoryImpl.KEY_SP_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
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
import ru.gb.android_lesson_10.repository.LocalSharedPreferenceRepositoryImpl;
import ru.gb.android_lesson_10.repository.Note;
import ru.gb.android_lesson_10.repository.NoteSource;
import ru.gb.android_lesson_10.ui.edit.NoteEditorFragment;

public class NoteListFragment extends Fragment implements OnItemNoteClickListener{

    NoteListAdapter noteListAdapter;
    NoteSource noteData;

    final static int SOURCE_ARRAY = 1;
    final static int SOURCE_SP = 2;
    final static int SOURCE_GF = 3;

    final static String SELECTION_KEY_RG = "Select RadioButton";
    final static String SELECTION_KEY_RG_SOURCE = "Selected RadioButton";

    public static NoteListFragment newInstance(){
        return new NoteListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        choiceSource();
        initRecycler(view);
        setHasOptionsMenu(true);
        initRadioGroup(view);
    }

    void choiceSource(){
        switch(getCurrentSource()){
            case SOURCE_ARRAY:
                noteData = new LocalRepositoryImpl(requireContext().getResources()).init();
                initAdapter();
                break;
            case SOURCE_SP:
                noteData = new LocalSharedPreferenceRepositoryImpl(requireContext().getSharedPreferences(KEY_SP_2, Context.MODE_PRIVATE)).init();
                initAdapter();
                break;
            case SOURCE_GF:
                //noteData = new RemoteFireStoreImpl(requireContext().getResources()).init();
                initAdapter();
                break;
        }
    }

    private void initRadioGroup(View view){
        view.findViewById(R.id.sourceArrays).setOnClickListener(radioGroupListener);
        view.findViewById(R.id.sourceSP).setOnClickListener(radioGroupListener);
        view.findViewById(R.id.sourceGF).setOnClickListener(radioGroupListener);

        switch(getCurrentSource()){
            case SOURCE_ARRAY:
                ((RadioButton)view.findViewById(R.id.sourceArrays)).setChecked(true);
                break;
            case SOURCE_SP:
                ((RadioButton)view.findViewById(R.id.sourceSP)).setChecked(true);
                break;
            case SOURCE_GF:
                ((RadioButton)view.findViewById(R.id.sourceGF)).setChecked(true);
                break;
        }

    }

    View.OnClickListener radioGroupListener = new View.OnClickListener(){

        @Override
        public void onClick(View view){
            switch(view.getId()){
                case (R.id.sourceArrays):{
                    setCurrentSource(SOURCE_ARRAY);
                    break;
                }
                case (R.id.sourceSP):{
                    setCurrentSource(SOURCE_SP);
                    break;
                }
                case (R.id.sourceGF):{
                    setCurrentSource(SOURCE_GF);
                    break;
                }
            }
            choiceSource();
        }
    };

    void setCurrentSource(int currentSource){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SELECTION_KEY_RG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SELECTION_KEY_RG_SOURCE, currentSource);
        editor.apply();
    }

    int getCurrentSource(){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SELECTION_KEY_RG, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SELECTION_KEY_RG_SOURCE, SOURCE_ARRAY);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.note_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case (R.id.action_add):{
                noteData.addNewNote(new Note("Note " + (noteData.size() + 1), "Note body " + (noteData.size() + 1), Calendar.getInstance().getTime(), false));
                noteListAdapter.notifyItemInserted(noteData.size());
                return true;
            }
            case (R.id.action_clear):{
                noteData.clearAllNotes();
                noteListAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.note_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        int menuPosition = noteListAdapter.getMenuPosition();
        switch(item.getItemId()){
            case (R.id.action_edit_list_item):{

                Observer observer = new Observer(){
                    @Override
                    public void receiveMessage(Note note){
                        ((MainActivity)requireActivity()).getPublisher().unsubscribe(this);
                        noteData.updateNote(menuPosition, note);
                        noteListAdapter.notifyItemChanged(menuPosition);
                    }
                };
                ((MainActivity)requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity)requireActivity()).getNavigation().addFragment(NoteEditorFragment.newInstance(noteData.getNoteData(menuPosition)), true);
                return false;
            }
            case (R.id.action_delete_list_item):{
                noteData.deleteNote(menuPosition);
                noteListAdapter.notifyItemRemoved(menuPosition);
                return false;
            }
        }
        return super.onContextItemSelected(item);
    }

    void initAdapter(){
        if(noteListAdapter == null){
            noteListAdapter = new NoteListAdapter(this);
            //setCurrentSource(SOURCE_ARRAY);
        }


        noteListAdapter.setData(noteData);
        noteListAdapter.setOnItemNoteClickListener(this);
    }

    void initRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.noteRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(noteListAdapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setChangeDuration(2000);
        animator.setRemoveDuration(2000);
        recyclerView.setItemAnimator(animator);
    }

    private String[] getData(){
        String[] data = getResources().getStringArray(R.array.note_list);
        return data;
    }

    @Override
    public void onNoteClick(int position){
        String[] data = getData();
        Toast.makeText(requireContext(), "Жамкнули на " + data[position], Toast.LENGTH_SHORT).show();
    }
}