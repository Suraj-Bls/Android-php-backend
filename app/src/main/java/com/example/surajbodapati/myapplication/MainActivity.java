package com.example.surajbodapati.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText username, emailid, password;
    private Button registerButton;
    //ProgressDialog progressDialogue;
    private TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }

        username = findViewById(R.id.username);
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        registerTextView= findViewById(R.id.registerTextView);

        registerButton = findViewById(R.id.registerButton);

        // progressDialogue = new ProgressDialog(this);
        //progressBar.setCancelable(true);
        //progressDialogue.setMessage("Loading... ");
        //progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //progressDialogue.setProgress(0);
        //progressDialogue.setMax(100);
        //  progressDialogue.show();
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        final String Username = username.getText().toString().trim();
        final String EmailID = emailid.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //                progressDialogue.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //      progressDialogue.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", Username);
                params.put("email", EmailID);
                params.put("password", Password);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

       /* RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
    }
}

