package ru.gb.android_lesson_10.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ru.gb.android_lesson_10.R;

public class Navigation {

    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public void replaceFragment(Fragment fragment, boolean useBackStack) {
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notesContainer, fragment);
        if (useBackStack) {
            fragmentTransaction.addToBackStack("");
        }
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }
    public void addFragment(Fragment fragment, boolean useBackStack) {
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.notesContainer, fragment);
        if (useBackStack) {
            fragmentTransaction.addToBackStack("");
        }
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }
}
