package edu.birzeit.students.finalproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class LoginActivity extends AppCompatActivity {
    public static String EMAIL = "EMAIL";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private EditText edtEmail;
    private EditText edtPassword;
    private CheckBox chk;
    private SharedPreferences prefs;

    private SharedPreferences.Editor editor;
    private ImageView imageViewLogin;
    private RequestQueue queue;
    // Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewSetup();
//        ArrayAdapter<CharSequence> adap=ArrayAdapter.createFromResource(this,R.array.UserType, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        spinner.setAdapter(adap);
        //spinnerSetup();


        setupSharedPrefs();
        checkPrefs();

    }

    private void ViewSetup() {
        //spinner = findViewById(R.id.spnuser);
        imageViewLogin = findViewById(R.id.imgenter);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        queue = Volley.newRequestQueue(this);
        chk = findViewById(R.id.chk);
    }
    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
//        prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
//        editor = getSharedPreferences(String.valueOf(prefs), MODE_PRIVATE).edit();



    }
    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if (flag) {
            String name = prefs.getString(EMAIL, "");
            String password = prefs.getString(PASS, "");
            edtEmail.setText(name);
            edtPassword.setText(password);
            chk.setChecked(true);
        }
    }
//    private void spinnerSetup() {
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this,
//                R.array.UserType,
//                android.R.layout.simple_spinner_item
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//    }

    public void enterButton(View view) {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        String url = "http://10.0.2.2:5000/getuser/" + email;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String userEmail = response.getString("email"); // Adjust these keys if necessary
                            String userPassword = response.getString("password");
                            Log.d("FetchData", "Email: " + userEmail + ", Password: " + userPassword);
                            if (email.equals(userEmail) && password.equals(userPassword)) {
                                if (chk.isChecked()) {
                                    if(!flag) {
                                        editor.putString(EMAIL, email);
                                        editor.putString(PASS, password);
                                        editor.putBoolean(FLAG, true);
                                        editor.commit();
                                    }
                                }
                                if(email.endsWith("@Admin.birzeit.edu")) {
                                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                } else if (email.endsWith("@Student.birzeit.edu")) {
                                    Intent intent = new Intent(LoginActivity.this,  WelcomeActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                } else if (email.endsWith("@Doctor.birzeit.edu")) {
//                                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);//change this to just get activity
//                                intent.putExtra("email", email);
//                                startActivity(intent);
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "The entry does not match. Please enter the values again!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException exception) {
                            Log.d("Error", exception.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        // Assuming 'queue' is already initialized somewhere as RequestQueue
        queue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        editor.putString("email",email);
        editor.putString("password",password);
        Log.d("Email",email);
        editor.apply();
    }


}