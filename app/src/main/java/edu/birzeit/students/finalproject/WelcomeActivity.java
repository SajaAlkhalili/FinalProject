package edu.birzeit.students.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class WelcomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    ActionBarDrawerToggle acbt;
    NavigationView navigationView;
    CardView cardView;
    CardView cardView2;
    CardView cardView4;
    CardView cardView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ViewSetup();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                Toast.makeText(WelcomeActivity.this, "Exchange Book Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(WelcomeActivity.this, chooceCollege.class);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WelcomeActivity.this, "Doctor FeedBack Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(WelcomeActivity.this, DoctorFeedBack.class);
                startActivity(intent);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                Toast.makeText(WelcomeActivity.this, "Show my Book Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(WelcomeActivity.this, showMybooks.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                Toast.makeText(WelcomeActivity.this, "Course FeedBack Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(WelcomeActivity.this, coursefeedbacks.class);
                startActivity(intent1);
            }
        });
        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the drawer on the start side (left side in LTR languages)
                drawerLayout.openDrawer(android.view.Gravity.LEFT);
            }
        });

        String email = getIntent().getStringExtra("email");
        if(email != null) {
            // Split the email to extract the name part before "@"
            String[] parts = email.split("@");
            if(parts.length > 0) {
                String name = parts[0];
                // Set the extracted name in userNameTextView
                userNameTextView.setText(name);
            }
            userEmailTextView.setText(email);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()

        {
            @Override

            public boolean onNavigationItemSelected (@NonNull MenuItem item){
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        // Assuming Home is the current Activity, you might not need to do anything, or you can refresh it
                        // intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                        // startActivity(intent);

                        break;
                    case R.id.nav_settings:
                        intent = new Intent(WelcomeActivity.this, SettingsActivity.class);
                        intent.putExtra("email", email);

                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        intent = new Intent(WelcomeActivity.this, AccountActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        break;
                    case R.id.nav_about:
                        intent = new Intent(WelcomeActivity.this, AboutActivity.class); // Assuming you have an AboutActivity
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:

                        logout(WelcomeActivity.this);
                        break;
                    case R.id.nav_feedback:
                        intent = new Intent(WelcomeActivity.this, FeedBackApp.class); // Assuming you have an AboutActivity
                        startActivity(intent);
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START); // Close the drawer
                return true;
            }


        });
    }

//    private void logout(WelcomeActivity welcomeActivity) {
//        AlertDialog.Builder alert=new AlertDialog.Builder(welcomeActivity);
//        alert.setTitle("Logout");
//        alert.setMessage("Are you sure you want to Logout?");
//        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                WelcomeActivity.finish();
//            }
//        });
//        alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        alert.show();
//
//    }

    private void logout(WelcomeActivity welcomeActivity) {
        AlertDialog.Builder alert = new AlertDialog.Builder(welcomeActivity);
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you want to Logout?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                welcomeActivity.finishAffinity();
               // welcomeActivity.finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog and remain in the current activity
                dialogInterface.dismiss();
            }
        });

       // AlertDialog dialog = alert.create();
        alert.show();
//        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        positiveButton.setTextColor(welcomeActivity.getResources().getColor(R.color.purple)); // Use ContextCompat.getColor if you target API level < 23
//
//        // Change the button color for "No"
//        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//        negativeButton.setTextColor(welcomeActivity.getResources().getColor(R.color.purple));
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(acbt.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ViewSetup() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        cardView3=findViewById(R.id.cardView3);
        //navigationView.setNavigationItemSelectedListener(this);
         cardView = findViewById(R.id.cardView);
         cardView2=findViewById(R.id.cardView2);
       cardView4=findViewById(R.id.cardView4);

        // Initialize views
        View headerView = navigationView.getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.userName);
        userEmailTextView = headerView.findViewById(R.id.userEmail);
        acbt=new ActionBarDrawerToggle(this,drawerLayout,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(acbt);
        acbt.syncState();
    }

}

