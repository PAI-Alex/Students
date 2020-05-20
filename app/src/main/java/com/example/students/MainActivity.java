package com.example.students;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    StudentRepository repository;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    final static String FIO [] = new String[5];
    final Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper helper = new DBHelper(getApplicationContext());
        database = helper.getWritableDatabase();
        database.delete("Students", null, null);
        random();

        for(int i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            values.put("FullName", FIO[i]);
            values.put("AddingDate", Calendar.getInstance().getTime().toString());
            database.insert("Students", null, values);
        }
        studentAdapter = new StudentAdapter(getApplicationContext());
        repository = StudentRepository.createInstance(getApplicationContext());

        Button Button = findViewById(R.id.button);
        Button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (MainActivity.this, Students.class));
            }
        } );

        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("FullName", String.valueOf (random.nextInt()));
                values.put("AddingDate", Calendar.getInstance().getTime().toString());
                database.insert("Students", null, values);
                repository.notifyDBChanged();
                studentAdapter.notifyDataSetChanged();
            }
        });

        Button changeButton = findViewById(R.id.changeButton);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("FullName", "Иванов Иван Иванович");
                String selection = "ID = " + repository.getStudents()
                        .get(repository.getStudents().size() - 1).getId();
                database.update("Students", values, selection, null);
                repository.notifyDBChanged();
                studentAdapter.notifyDataSetChanged();
            }
        });
    }
    void random(){
        for(int i=0; i<5; i++){
            StringBuilder builder = new StringBuilder();
            StringBuilder builder1 = new StringBuilder();
            StringBuilder builder2 = new StringBuilder();
            for (int j = 0; j < 12; j++) {
                char code = (char) (random.nextInt(25)+97);
                char code1 = (char) (random.nextInt(25)+97);
                char code2 = (char) (random.nextInt(25)+97);
                builder.append(Character.toString(code));
                builder1.append(Character.toString(code1));
                builder2.append(Character.toString(code2));
            }
            FIO[i]= builder.toString()+" "+builder1.toString()+" " +builder2.toString();

        }
    }
}
