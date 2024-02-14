package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {
    private TextView userNameTextView;
    //private TextView userEmailTextView;
    private TextView emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        userNameTextView =findViewById(R.id.userName);

        emailTextView =findViewById(R.id.textView27);
        String email = getIntent().getStringExtra("email");
        if(email != null) {
            // Split the email to extract the name part before "@"
            String[] parts = email.split("@");
            if(parts.length > 0) {
                String name = parts[0];
                // Set the extracted name in userNameTextView
                userNameTextView.setText(name);
            }
            //userEmailTextView.setText(email);
            emailTextView.setText(email);
        }
    }
}