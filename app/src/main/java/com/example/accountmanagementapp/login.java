package com.example.accountmanagementapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

   private EditText email,password;
    private Button btn_login;
    private TextView link_register;
    private static String URL_LOGIN =Config.BASE_URL + "login.php";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sessionManager = new SessionManager(getApplicationContext());
        setContentView(R.layout.login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        link_register  = findViewById(R.id.link_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();



                if (!e.isEmpty() || !p.isEmpty()) {
                    login(e,p);
                } else {
                    email.setError("Please enter email");
                    password.setError("Please enter password");
                }
            }
        });


        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,registration.class));
            }
        });




    }
    private void login(String email,String password){
        btn_login.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i <jsonArray.length();
                                i++){

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String username = object.getString("username").trim();

                                    String email = object.getString("email").trim();
                                    Toast.makeText(login.this, "login successful. \nYour Name:"+username+"",Toast.LENGTH_SHORT).show();
                                    sessionManager.createLoginSession(email,password);

                                    Intent intent = new Intent(login.this, home.class);
                                    startActivity(intent);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(login.this, " error"+e,Toast.LENGTH_SHORT).show();
                        }
                    }

                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btn_login.setVisibility(View.VISIBLE);

                        Toast.makeText(login.this, "error"+error,Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
