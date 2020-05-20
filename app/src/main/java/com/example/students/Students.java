package com.example.students;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Students extends AppCompatActivity {
    StudentRepository repository;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_students );
        DBHelper helper = new DBHelper(getApplicationContext());
        database = helper.getWritableDatabase();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager (getApplicationContext()));
        studentAdapter = new StudentAdapter(getApplicationContext());
        recyclerView.setAdapter(studentAdapter);
        repository = StudentRepository.createInstance(getApplicationContext());
    }
}
