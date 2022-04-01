package ru.gb.android_lesson_10.repository;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class LocalSharedPreferenceRepositoryImpl implements NoteSource{
    private List<Note> notesDataSource;
    private SharedPreferences sharedPreferences;
    public static final String KEY_CELL_1 = "cell_2";
    public static final String KEY_SP_2 = "key_sp_2";

    public LocalSharedPreferenceRepositoryImpl(SharedPreferences sharedPreferences) {
        notesDataSource = new ArrayList<Note>();
        this.sharedPreferences = sharedPreferences;
    }


    public LocalSharedPreferenceRepositoryImpl init() {
        String savedNote= sharedPreferences.getString(KEY_CELL_1,null);
        if (savedNote != null){
            Type type = new TypeToken<ArrayList<Note>>(){}.getType();
            notesDataSource = (new GsonBuilder().create().fromJson(savedNote, type));
        }

        return this;
    }

    @Override
    public int size() {
        return notesDataSource.size();
    }

    @Override
    public List<Note> getAllNotesData() {
        return notesDataSource;
    }

    @Override
    public Note getNoteData(int position) {
        return notesDataSource.get(position);
    }

    @Override
    public void clearAllNotes() {
        notesDataSource.clear();
    }

    @Override
    public void addNewNote(Note note) {
        notesDataSource.add(note);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1,new GsonBuilder().create().toJson(notesDataSource));
        editor.apply();
    }

    @Override
    public void deleteNote(int position) {
        notesDataSource.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        notesDataSource.set(position, note);
    }
}
