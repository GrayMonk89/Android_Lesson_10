package ru.gb.android_lesson_10.repository;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {

    private String tittleNote;
    private String bodyNote;
    private boolean isDone;
    private Date dateOfCreation;

    public void setTittleNote(String tittleNote) {
        this.tittleNote = tittleNote;
    }

    public void setBodyNote(String bodyNote) {
        this.bodyNote = bodyNote;
    }


    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Note(String tittleNote, String bodyNote, Date date, boolean isDone) {
        this.tittleNote = tittleNote;
        this.bodyNote = bodyNote;
        this.isDone = isDone;
        this.dateOfCreation = date;
    }

    protected Note(Parcel in) {
        tittleNote = in.readString();
        bodyNote = in.readString();
        isDone = in.readByte() != 0;
        dateOfCreation = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tittleNote);
        dest.writeString(bodyNote);
        dest.writeByte((byte) (isDone ? 1 : 0));
        dest.writeLong(dateOfCreation.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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
