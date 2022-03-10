package ru.gb.android_lesson_10.publisher;

import ru.gb.android_lesson_10.repository.Note;

public interface Observer {
    void receiveMessage(Note note);
}
