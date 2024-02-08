package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddBook extends AppCompatActivity {
    private EditText bookname;
    private EditText details;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        bookname=findViewById(R.id.bookname);
        details=findViewById(R.id.details);
        add=findViewById(R.id.add);
        Intent intent=getIntent();
        String cat= getIntent().getStringExtra("category");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bookname.getText().toString().isEmpty() && !details.getText().toString().isEmpty()) {
                    String bookName = bookname.getText().toString();
                    String detail = details.getText().toString();
                    addBook(bookName, cat, detail);
                    Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "wrong Data Enterd", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void addBook(String bok,String cat,String str){
        String url="http://10.0.2.2:5000/create1";
        RequestQueue queue= Volley.newRequestQueue(AddBook.this);
        JSONObject jsonParams=new JSONObject();
        try{
            jsonParams.put("name",bok);
            jsonParams.put("Description",str);
            jsonParams.put("category",cat);

        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str="";
                        // Handle response
                        // This part depends on how your server responds to the POST request
                        // For example, you might get back the details of the added book
                        try {
                            str=response.getString("result");


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

}