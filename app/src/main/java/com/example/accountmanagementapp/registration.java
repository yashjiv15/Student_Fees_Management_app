package com.example.accountmanagementapp;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

public class registration extends AppCompatActivity {

     private EditText username,email,password,cpassword;
     private Button btn_signup;
    private TextView link_login;

     private static String URL_REGISTER =Config.BASE_URL + "register.php";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager;

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        cpassword = findViewById(R.id.cpassword);
        btn_signup = findViewById(R.id.btn_signup);
        link_login = findViewById(R.id.link_login);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });


        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registration.this,login.class));
            }
        });

    }


    public void Register() {
        btn_signup.setVisibility(View.GONE);


        final String username = this.username.getText().toString().trim();
        final String email = this.email.getText().toString().trim();

        final String password = this.password.getText().toString().trim();
        final String cpassword = this.cpassword.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Registration Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(registration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(registration.this, login.class);
                                startActivity(intent);
                            } else if (success.equals("0")) {
                                String message = jsonObject.getString("message");
                                Toast.makeText(registration.this, message, Toast.LENGTH_SHORT).show();
                                btn_signup.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(registration.this, "Email/password already exist", Toast.LENGTH_SHORT).show();
                            btn_signup.setVisibility(View.VISIBLE);
                        }
                    }

                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registration.this, "registration error"+error .toString(),Toast.LENGTH_SHORT).show();
                        btn_signup.setVisibility(View.VISIBLE);
                    }
                })
        {
           @Override
            protected Map<String, String> getParams() throws AuthFailureError{
               Map<String, String> params = new HashMap<>();
               params.put("username",username);
               params.put("email",email);
               params.put("password",password);
               params.put("cpassword",cpassword);
               return params;

           }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}