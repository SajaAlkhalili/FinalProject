package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class whenItemClick extends AppCompatActivity {
    private TextView txt;
    private Button reservebtn;
    private Button canclebtn;
    String item;
    String desc;
    String cate;
    int bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_when_item_click);
        txt = findViewById(R.id.txt);
        reservebtn = findViewById(R.id.reservebtn);
        item = getIntent().getStringExtra("Items");
        desc = getIntent().getStringExtra("description");
        cate=getIntent().getStringExtra("cat");
        bookID=getIntent().getIntExtra("id",-1);
        Log.d("Debug", "Received id in whenItemClick: " + bookID);
        txt.setText(" "+item + " \n " + desc);
        ReceveClick(reservebtn,bookID);

    }
    public void ReceveClick(Button btn, int bookId) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceveClick();
                removeFromFirstDataBase(bookId);
            }
        });
    }

    public void ReceveClick() {//to add in student list

        String url = "http://10.0.2.2:5000/create2";
        RequestQueue queue = Volley.newRequestQueue(whenItemClick.this);
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("name", item);
            jsonParams.put("Description", desc);
            jsonParams.put("category",cate);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str = "";
                        // Handle response
                        // This part depends on how your server responds to the POST request
                        // For example, you might get back the details of the added book
                        try {
                            str = response.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error making API request: " + error.toString());
                    }
                });

        queue.add(jsonObjectRequest);



    }
    public void removeFromFirstDataBase(int bookId) {

        String url = "http://10.0.2.2:5000/delete/" + bookId;
        RequestQueue queue = Volley.newRequestQueue(whenItemClick.this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        Toast.makeText(whenItemClick.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error making DELETE request: " + error.toString());
                    }
                });

        queue.add(stringRequest);


    }
}