package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddDoctor extends AppCompatActivity {
    private RequestQueue queue;
    private EditText doctorname;
    private EditText feedback;

    private Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        doctorname=findViewById(R.id.bookname);
        feedback=findViewById(R.id.details);
        add=findViewById(R.id.add);
        Intent intent=getIntent();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doctorname.getText().toString().isEmpty() && !feedback.getText().toString().isEmpty()) {

                    String bookName = doctorname.getText().toString();
                    String detail = feedback.getText().toString();
                    addDoctor(bookName, detail);
                    Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "wrong Data Enterd", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void addDoctor(String doctor,String str){
        String url="http://10.0.2.2:5000/createdoctor";
        RequestQueue queue= Volley.newRequestQueue(AddDoctor.this);
        JSONObject jsonParams=new JSONObject();
        try{
            jsonParams.put("doctorname",doctor);
            jsonParams.put("feedback",str);

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