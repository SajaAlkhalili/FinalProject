package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DoctorClick extends AppCompatActivity {

    private TextView txt;
    String item;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_click);
        txt = findViewById(R.id.txt);

        item = getIntent().getStringExtra("Items");
        desc = getIntent().getStringExtra("feedback");

        // Log.d("Debug", "Received id in whenItemClick: " + bookID);
        txt.setText(" " + item + " \n " + desc);


    }
}