package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;

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

public class ItemClick extends AppCompatActivity {
    private TextView txt;
    String item;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_click);
        txt = findViewById(R.id.txt);

        item = getIntent().getStringExtra("Items");
        desc = getIntent().getStringExtra("feedback");

        // Log.d("Debug", "Received id in whenItemClick: " + bookID);
        txt.setText(" " + item+" :" + " \n \n" + desc);


    }
}