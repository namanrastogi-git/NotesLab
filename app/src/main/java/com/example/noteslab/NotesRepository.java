package com.example.noteslab;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesRepository {

    private static String sanitize(String name) {
        // keep simple safe filenames
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    public static List<String> listNotes(Context ctx) {
        String[] files = ctx.fileList();
        if (files == null) return new ArrayList<>();
        // all files = notes; alternatively prefix like "note_" if you want
        return new ArrayList<>(Arrays.asList(files));
    }

    public static boolean exists(Context ctx, String name) {
        String[] files = ctx.fileList();
        if (files == null) return false;
        name = sanitize(name);
        for (String f : files) if (f.equals(name)) return true;
        return false;
    }

    public static void save(Context ctx, String name, String content) throws IOException {
        name = sanitize(name);
        try (OutputStreamWriter w = new OutputStreamWriter(ctx.openFileOutput(name, Context.MODE_PRIVATE))) {
            w.write(content);
        }
    }

    public static String read(Context ctx, String name) throws IOException {
        name = sanitize(name);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(ctx.openFileInput(name)))) {
            String line;
            while ((line = r.readLine()) != null) sb.append(line).append('\n');
        }
        return sb.toString();
    }

    public static boolean delete(Context ctx, String name) {
        name = sanitize(name);
        return ctx.deleteFile(name);
    }
}
