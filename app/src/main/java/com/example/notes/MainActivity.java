package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    public static final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.d("MyTag", "" + (0.1 + 0.2));

        recyclerView = findViewById(R.id.recyclerViewNotes);

        if (notes.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                notes.add(getRandomNote());
            }
        }

        adapter = new NotesAdapter(notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "onNoteClick" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    void remove(int position){
        notes.remove(position);
        adapter.notifyDataSetChanged();  
    } 
    
    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private Note getRandomNote() {

//        int num = ThreadLocalRandom.current().nextInt(1, 3 + 1);
//        return new Note("title" + num, "description" + num, "dayOfWeek" + num, num);

        Random r = new Random();
        int lowRange = 1;
        int highRange = 4;
        int num = r.nextInt(highRange -lowRange ) + lowRange ;

        return new Note("title" + num, "description" + num, "dayOfWeek" + num, num);
    }
}

