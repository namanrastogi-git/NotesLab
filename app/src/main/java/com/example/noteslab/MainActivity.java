package com.example.noteslab;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvNotes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvNotes = findViewById(R.id.lvNotes);

        lvNotes.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            String name = adapter.getItem(position);
            if (name == null) return;
            try {
                String content = NotesRepository.read(this, name);
                new AlertDialog.Builder(this)
                        .setTitle(name)
                        .setMessage(content.isEmpty() ? "(empty note)" : content)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            } catch (IOException e) {
                Toast.makeText(this, "Error reading note", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        List<String> notes = NotesRepository.listNotes(this);
        if (notes.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_no_notes), Toast.LENGTH_SHORT).show();
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        lvNotes.setAdapter(adapter);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (id == R.id.action_delete) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
