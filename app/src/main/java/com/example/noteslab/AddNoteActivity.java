package com.example.noteslab;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText txtNoteName;
    private EditText txtNoteContent;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        txtNoteName = findViewById(R.id.txtNoteName);
        txtNoteContent = findViewById(R.id.txtNoteContent);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String name = txtNoteName.getText().toString().trim();
            String content = txtNoteContent.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_empty_name), Toast.LENGTH_SHORT).show();
                return;
            }
            if (content.trim().isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_empty_content), Toast.LENGTH_SHORT).show();
                return;
            }
            if (NotesRepository.exists(this, name)) {
                Toast.makeText(this, getString(R.string.toast_exists), Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                NotesRepository.save(this, name, content);
                Toast.makeText(this, getString(R.string.toast_saved), Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
