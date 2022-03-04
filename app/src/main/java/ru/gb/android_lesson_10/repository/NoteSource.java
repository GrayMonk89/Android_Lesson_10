package ru.gb.android_lesson_10.repository;

import java.util.List;

public interface NoteSource {
    int size();

    List<Note> getAllNotesData();

    Note getNoteData(int position);
}
