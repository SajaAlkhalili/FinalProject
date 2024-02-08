package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class clickmyBooks extends AppCompatActivity {
    private TextView txt;
    private Button btn;
    private String item;
    private String desc;
    private String cate;
    private int bookID;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickmy_books);
        txt = findViewById(R.id.txt);
        btn = findViewById(R.id.deletebtn);
        item = getIntent().getStringExtra("Items");
        desc = getIntent().getStringExtra("description");
        cate = getIntent().getStringExtra("cat");
        bookID = getIntent().getIntExtra("id", -1);
        Log.d("Debug", "Received id in clickmyBooks: " + bookID);
        builder = new AlertDialog.Builder(this);
        txt.setText(" " + item + " \n " + desc);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        builder.setTitle("Warning!")
                .setMessage("Are you sure you want to delete?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFromDatabase(bookID);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void deleteFromDatabase(int bookId) {
        String url = "http://10.0.2.2:5000/delete2/" + bookId;
        RequestQueue queue = Volley.newRequestQueue(clickmyBooks.this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        Toast.makeText(clickmyBooks.this, response, Toast.LENGTH_SHORT).show();
                        // Optionally, you may navigate back or perform other actions upon successful deletion.
                        // For example, you can use Intent to navigate back to the previous activity.
                        // Intent intent = new Intent(clickmyBooks.this, PreviousActivity.class);
                        // startActivity(intent);
                        // finish(); // Optionally finish the current activity
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