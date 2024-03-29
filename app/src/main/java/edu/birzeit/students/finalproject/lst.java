package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
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

public class lst extends AppCompatActivity {
    private RequestQueue queue;
    SearchView searchView;
    private ListView lstView;
    private Button addbtn;
    private SwipeRefreshLayout swipeToRefresh;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    ArrayList<Integer> bookIds = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();
    String cat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst);

        swipeToRefresh = findViewById(R.id.swipeToRefresh);
        searchView = findViewById(R.id.search_bar);
        lstView = findViewById(R.id.lstView);
        addbtn = findViewById(R.id.addbtn);

        data = new ArrayList<>();
        cat = getIntent().getStringExtra("category");
        queue = Volley.newRequestQueue(this);

        PlusAction(addbtn, cat);
        refreshData(); // Initial data fetch

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // Data refresh on swipe
                swipeToRefresh.setRefreshing(false);
            }
        });

        search(searchView);
    }

    private void refreshData() {
        String url = "http://10.0.2.2:5000/getbook/" + cat;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Clear existing data before adding new data
                        data.clear();
                        bookIds.clear();
                        descriptions.clear();

                        try {
                            // Parsing JSON response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String name = obj.getString("name");
                                String description = obj.getString("Description");
                                int currentId = obj.getInt("id");
                                data.add(name);
                                bookIds.add(currentId);
                                descriptions.add(description);
                            }
                        } catch (JSONException e) {
                            Log.e("JSON Error", "Error parsing JSON array: " + e.toString());
                        }

                        // Set up the adapter and list view
                        adapter = new ArrayAdapter<>(lst.this, android.R.layout.simple_list_item_1, data);
                        lstView.setAdapter(adapter);

                        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Handle item click
                                String selectedItem = (String) parent.getItemAtPosition(position);
                                Toast.makeText(lst.this, "Clicked on: " + selectedItem, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(lst.this, whenItemClick.class);
                                intent.putExtra("Items", selectedItem);
                                intent.putExtra("description", descriptions.get(position));
                                intent.putExtra("cat", cat);
                                intent.putExtra("id", bookIds.get(position));
                                Log.d("Debug", "Received id : " + bookIds.get(position));
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
    }

    public void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lst.this.adapter.getFilter().filter(query);
                if (lst.this.adapter.getCount() == 0) {
                    Toast.makeText(lst.this, "Sorry, no items found", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lst.this.adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void PlusAction(Button btn, String cat) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lst.this, AddBook.class);
                intent.putExtra("category", cat);
                startActivity(intent);
            }
        });
    }
}