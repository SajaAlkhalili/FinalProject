package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

public class chooceCollege extends AppCompatActivity {

    private CardView cardViewIT;
    private CardView cardViewScience;
    private CardView cardViewEngineering;
    private CardView cardViewLiterature;
    private CardView cardViewPharmacy;
    private CardView cardViewEducation;
    private CardView cardViewLaw;
    private CardView cardViewBusiness;
    private String str;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooce_college);

        // Initialize CardViews
        cardViewIT = findViewById(R.id.IT);
        cardViewScience = findViewById(R.id.sc);
        cardViewEngineering = findViewById(R.id.eng);
        cardViewLiterature = findViewById(R.id.Literature);
        cardViewPharmacy = findViewById(R.id.Pharm);
        cardViewEducation = findViewById(R.id.education);
        cardViewLaw = findViewById(R.id.low);
        cardViewBusiness = findViewById(R.id.Business);

        // Set click listeners for each CardView
        setCardViewClickListeners();
        intent = new Intent(chooceCollege.this, lst.class);


    }

    private void setCardViewClickListeners() {
        cardViewIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="IT";
                showToast(str);
                intent.putExtra("category", str);
                startActivity(intent);
            }
        });

        cardViewScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str="Science";
                showToast(str);
                intent.putExtra("category", str);
                startActivity(intent);
            }
        });

        cardViewEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="Engineering";
                showToast(str);
                intent.putExtra("category", str);
                startActivity(intent);
            }
        });

        cardViewLiterature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="Literature";
                showToast(str);
                intent.putExtra("category", str);
                startActivity(intent);
            }
        });

        cardViewPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="Pharmacy";
                showToast(str);
                intent.putExtra("category", str);
                startActivity(intent);
            }
        });

        cardViewEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="Education";
                showToast(str);
                intent.putExtra("category", str);
                startActivity(intent);
            }
        });

        cardViewLaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="Law";
                showToast(str);
                intent.putExtra("category", str);
                startActivity(intent);
            }
        });

        cardViewBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="Business";
                showToast(str);
                intent.putExtra("category", str);
                startActivity(intent);
            }
        });

    }



    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}