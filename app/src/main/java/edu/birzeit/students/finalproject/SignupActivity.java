package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText editTextEmail, editTextPassword, editTextPassword2;
    private ImageView imageViewSignUp;
    private TextView loginTextView;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    Spinner spinner;
    // private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ViewSetup();
        spinnerSetup();
        setupSharedPrefs();


        imageViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                handle login text click
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    private void spinnerSetup() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.UserType,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void ViewSetup() {
        editTextEmail = findViewById(R.id.edtemail);
        editTextPassword = findViewById(R.id.edtPassword);
        editTextPassword2 = findViewById(R.id.edtPassword2);
        imageViewSignUp = findViewById(R.id.imgenter);
        spinner = findViewById(R.id.spnuser);
        loginTextView = findViewById(R.id.txtlogin);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ondestroy");
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("Password2", password2);
        editor.apply();
        Log.d("Emai", email);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LC888", "OnStart()");
        String email = prefs.getString("Email", "");
        String password = prefs.getString("Password", "");
        String password2 = prefs.getString("Password2", "");
        if (email != null || password != null || password2 != null) {
            editTextEmail.setText(email);
            editTextPassword.setText(password);
            editTextPassword2.setText(password2);
        }
    }

    private void signUp() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();
        String item = spinner.getSelectedItem().toString();


        if (email.isEmpty() || password.isEmpty() || password2.isEmpty() || item.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignupActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*+=?-].*")) {
            Toast.makeText(SignupActivity.this, "Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers, and special characters", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(password2)) {
            Toast.makeText(SignupActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean emailDomainCheck = false;
        switch (item) {
            case "Admin":
                emailDomainCheck = email.endsWith("@Admin.birzeit.edu");
                break;
            case "Student":
                emailDomainCheck = email.endsWith("@Student.birzeit.edu");
                break;
            case "Doctor":
                emailDomainCheck = email.endsWith("@Doctor.birzeit.edu");
                break;
            default:
                Toast.makeText(SignupActivity.this, "Invalid role selected", Toast.LENGTH_SHORT).show();
                return;
        }

        if (!emailDomainCheck) {
            Toast.makeText(SignupActivity.this, "Email must end with the appropriate domain (@Admin.birzeit.edu, @Doctor.birzeit.edu, @Student.birzeit.edu)", Toast.LENGTH_LONG).show();
            return;
        }
        Add_user(email,password,password2,item);
//        editor.putString("Email",email);
//        editor.putString("Password",password);
//        editor.putString("Password2",password2);
//        editor.apply();
        // String Url="http://10.0.2.2:5000/"
        // Toast.makeText(SignupActivity.this, "user registered.", Toast.LENGTH_LONG).show();
    }

    public void Add_user(String email, String password, String re_password, String role) {
        String Url = "http://10.0.2.2:5000/create";
        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("Repassword", re_password);
            jsonObject.put("role", role);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str="";
                        // Handle the JSON response here
                        try {
                            str = response.getString("result");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(SignupActivity.this, str, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("VolleyError", error.toString());
//                    }
                    public void onErrorResponse(VolleyError error) {
                        // Log the error message or inspect the error object
                        Log.d("VolleyError", error.toString());
                        if (error.networkResponse != null) {
                            Log.d("Status code", String.valueOf(error.networkResponse.statusCode));
                            // You can further inspect the networkResponse object for more details
                        }
                    }
                }
        );
        int timeout = 30000; // 30 seconds - Adjust as needed
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // below line is to make
        // a json object request.
        queue.add(request);
    }

}
