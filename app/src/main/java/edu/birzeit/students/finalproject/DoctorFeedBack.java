package edu.birzeit.students.finalproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorFeedBack extends AppCompatActivity {

    private RequestQueue queue;
    SearchView searchView;
    private ListView lstView;
    private Button addbtn;
    private SwipeRefreshLayout swipeToRefresh;  // Added SwipeRefreshLayout
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    ArrayList<String> descriptions = new ArrayList<>(); // Declare an ArrayList to store the descriptions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_feed_back);
        searchView = findViewById(R.id.search_bar);
        lstView = findViewById(R.id.lstView);
        addbtn = findViewById(R.id.addbtn);
        swipeToRefresh = findViewById(R.id.swipeToRefresh);  // Added SwipeRefreshLayout
        data = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        PlusAction(addbtn);
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

                        adapter = new ArrayAdapter<>(DoctorFeedBack.this,
                                android.R.layout.simple_list_item_1, data);
                        lstView.setAdapter(adapter);

                        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedItem = (String) parent.getItemAtPosition(position);
                                Toast.makeText(DoctorFeedBack.this, "Clicked on: " + selectedItem, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DoctorFeedBack.this, DoctorClick.class);
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
        refreshApp();  // Call the refreshApp function
    }

    // Rest of your code...

    private void refreshApp() {
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Handle refresh action here
                data.clear();
                descriptions.clear();

                String url = "http://10.0.2.2:5000/doctorfeed";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    // Parsing JSON response
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

                                // Notify the adapter that the data has changed
                                adapter.notifyDataSetChanged();

                                // Stop the refreshing animation
                                swipeToRefresh.setRefreshing(false);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley Error", "Error making API request: " + error.toString());
                                swipeToRefresh.setRefreshing(false);
                            }
                        });

                queue.add(jsonArrayRequest);
            }
        });
    }

    public void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DoctorFeedBack.this.adapter.getFilter().filter(query);
                if (DoctorFeedBack.this.adapter.getCount() == 0) {
                    Toast.makeText(DoctorFeedBack.this, "Sorry, no items found", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DoctorFeedBack.this.adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void PlusAction(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorFeedBack.this, AddDoctor.class);

                startActivity(intent);
            }
        });
    }
}