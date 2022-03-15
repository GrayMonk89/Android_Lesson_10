package ru.gb.android_lesson_10;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.gb.android_lesson_10.publisher.Publisher;
import ru.gb.android_lesson_10.ui.Navigation;
import ru.gb.android_lesson_10.ui.NewNoteFragment;
import ru.gb.android_lesson_10.ui.NoteListFragment;

public class MainActivity extends AppCompatActivity {

    private Publisher publisher;
    private Navigation navigation;

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        publisher = new Publisher();
        navigation = new Navigation(getSupportFragmentManager());

        if (savedInstanceState == null) {
            navigation.replaceFragment(NoteListFragment.newInstance(), false);
        }
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_new_note): {
                getSupportFragmentManager().beginTransaction().replace(R.id.notesContainer, NewNoteFragment.newInstance()).addToBackStack("").commit();
                return true;
            }

            case (R.id.action_exit): {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}