//package edu.birzeit.students.finalproject;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//
//import android.os.Bundle;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FeedBackDoctor extends AppCompatActivity {
//
//        private TextView textViewFeedback;
//        private ListView listViewFeedback;
//        private ArrayAdapter<String> adapter;
//        private List<String> courseList = new ArrayList<>();
//        private ImageButton imageButtonAddFeedback;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_feed_back_doctor);
//            imageButtonAddFeedback = findViewById(R.id.imageButtonAddFeedback);
//            imageButtonAddFeedback.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(FeedBackDoctor.this, MainActivity3.class);
//                    startActivity(intent);
//                }
//            });
//            textViewFeedback = findViewById(R.id.textViewFeedback);
//            listViewFeedback = findViewById(R.id.listViewFeedback);
//            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
//            listViewFeedback.setAdapter(adapter);
//
//            ImageButton addButton = findViewById(R.id.imageButtonAddFeedback);
//            addButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    textViewFeedback.setVisibility(View.VISIBLE);
//                }
//            });
//
//            ImageButton searchButton = findViewById(R.id.imageButtonSearch);
//            searchButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String query = ((TextView) findViewById(R.id.editTextCourseCode)).getText().toString().trim();
//                    searchCourse(query);
//                }
//            });
//        }
//
//
//        private void searchCourse(String query) {
//            ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
//            Call<List<Doctor>> call = apiService.searchdoctor(query);
//            call.enqueue(new Callback<List<Doctor>>() {
//                @Override
//                public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
//                    if (response.isSuccessful()) {
//                        courseList.clear();
//                        for (Doctor doctor : response.body()) {
//                            courseList.add(doctor.getFirstName()+ doctor.getLastName()+ " - " + doctor.getFeedback());
//                        }
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(FeedBackDoctor.this, "Failed to search doctors", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<Doctor>> call, Throwable t) {
//                    Toast.makeText(FeedBackDoctor.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }