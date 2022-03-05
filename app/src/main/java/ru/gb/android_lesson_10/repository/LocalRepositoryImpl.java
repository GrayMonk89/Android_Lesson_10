package ru.gb.android_lesson_10.repository;


import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import ru.gb.android_lesson_10.R;

public class LocalRepositoryImpl implements NoteSource {

    private List<Note> notesDataSource;
    private Resources resources;

    public LocalRepositoryImpl(Resources resources) {
        notesDataSource = new ArrayList<Note>();
        this.resources = resources;
    }

    public LocalRepositoryImpl init() {
        String[] tittles = resources.getStringArray(R.array.note_list);
        String[] body = resources.getStringArray(R.array.the_note);
        for (int i = 0; i < tittles.length; i++) {
            notesDataSource.add(new Note(tittles[i], body[i], false));
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