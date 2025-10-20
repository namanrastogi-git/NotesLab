package com.example.noteslab;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DeleteNoteActivity extends AppCompatActivity {

    private ListView lvDelete;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        lvDelete = findViewById(R.id.lvDelete);
        btnDelete = findViewById(R.id.btnDelete);

        lvDelete.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        loadNotes();

        btnDelete.setOnClickListener(v -> {
            int pos = lvDelete.getCheckedItemPosition();
            if (pos == ListView.INVALID_POSITION) {
                Toast.makeText(this, getString(R.string.toast_no_selection), Toast.LENGTH_SHORT).show();
                return;
            }
            String name = (String) lvDelete.getAdapter().getItem(pos);
            boolean ok = NotesRepository.delete(this, name);
            if (ok) {
                Toast.makeText(this, getString(R.string.toast_deleted), Toast.LENGTH_SHORT).show();
                loadNotes();
            } else {
                Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNotes() {
        List<String> notes = NotesRepository.listNotes(this);
        lvDelete.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, notes));
        if (notes.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_no_notes), Toast.LENGTH_SHORT).show();
        }
    }
}
