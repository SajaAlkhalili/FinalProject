package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    private AppCompatButton loginButton;
    private AppCompatButton signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Authentication

        // Initialize buttons
        loginButton = findViewById(R.id.btnlogin);
        signUpButton = findViewById(R.id.btnsignup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  handle login button click
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }


        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle sign up button click
                Intent signUpIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}