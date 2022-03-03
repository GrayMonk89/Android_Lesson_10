package ru.gb.android_lesson_10.repository;

public class Note {
    private String tittleNote;
    private String bodyNote;
    private boolean isDone;

    public Note(String tittleNote, String bodyNote, boolean isDone) {
        this.tittleNote = tittleNote;
        this.bodyNote = bodyNote;
        this.isDone = isDone;
    }

    public String getTittleNote() {
        return tittleNote;
    }

    public String getBodyNote() {
        return bodyNote;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
