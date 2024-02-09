package edu.birzeit.students.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class getDoctorFeedBack extends AppCompatActivity {

    private RequestQueue queue;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle acbt;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    SearchView searchView;
    private ListView lstView;

    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    ArrayList<String> descriptions = new ArrayList<>(); // Declare an ArrayList to store the descriptions

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_doctor_feed_back);
        searchView = findViewById(R.id.search_bar);
        lstView = findViewById(R.id.lstView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.userName);
        userEmailTextView = headerView.findViewById(R.id.userEmail);
        acbt=new ActionBarDrawerToggle(this,drawerLayout,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(acbt);
        acbt.syncState();
        data = new ArrayList<>();
        queue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:5000/doctorfeed";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String name = obj.getString("doctorname");
                                String Feedback = obj.getString("feedback");

                                data.add(name);
                                descriptions.add(Feedback);
                            }
                        } catch (JSONException e) {
                            Log.e("JSON Error", "Error parsing JSON array: " + e.toString());
                        }

                        adapter = new ArrayAdapter<>(getDoctorFeedBack.this,
                                android.R.layout.simple_list_item_1, data);
                        lstView.setAdapter(adapter);

                        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedItem = (String) parent.getItemAtPosition(position);
                                Toast.makeText(getDoctorFeedBack.this, "Clicked on: " + selectedItem, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getDoctorFeedBack.this, DoctorClick.class);
                                intent.putExtra("Items", selectedItem.toString());
                                intent.putExtra("feedback", descriptions.get(position)); // Use the correct description for the clicked item
                                startActivity(intent);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error making API request: " + error.toString());
                    }
                });

        queue.add(jsonArrayRequest);
        search(searchView);
        //Intent in=getIntent();
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
                        intent = new Intent(getDoctorFeedBack.this, SettingsActivity.class);
                        intent.putExtra("email", email);

                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        intent = new Intent(getDoctorFeedBack.this, AccountActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        break;
                    case R.id.nav_about:
                        intent = new Intent(getDoctorFeedBack.this, AboutActivity.class); // Assuming you have an AboutActivity
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:

                        logout(getDoctorFeedBack.this);
                        break;
                    case R.id.nav_feedback:
                        intent = new Intent(getDoctorFeedBack.this, FeedBackApp.class); // Assuming you have an AboutActivity
                        startActivity(intent);
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START); // Close the drawer
                return true;
            }


        });
    }
    private void logout(getDoctorFeedBack getDoctorFeedBack) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getDoctorFeedBack);
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you want to Logout?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getDoctorFeedBack.finishAffinity();
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
    public void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getDoctorFeedBack.this.adapter.getFilter().filter(query);
                if (getDoctorFeedBack.this.adapter.getCount() == 0) {
                    Toast.makeText(getDoctorFeedBack.this, "Sorry, no items found", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getDoctorFeedBack.this.adapter.getFilter().filter(newText);
                return false;
            }
        });
    }


}