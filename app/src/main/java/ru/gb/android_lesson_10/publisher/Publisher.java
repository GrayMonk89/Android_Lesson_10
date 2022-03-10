package ru.gb.android_lesson_10.publisher;

import java.util.ArrayList;
import java.util.List;

import ru.gb.android_lesson_10.repository.Note;

public class Publisher {
    private List<Observer> observerList;

    public Publisher(){
        observerList = new ArrayList<>();
    }
    public void subscribe(Observer observer) {
        observerList.add(observer);
    }
    public void unsubscribe(Observer observer) {
        observerList.remove(observer);
    }

    public void sendMessage(Note note){
        for (Observer observer: observerList){
            observer.receiveMessage(note);
        }
    }
}
