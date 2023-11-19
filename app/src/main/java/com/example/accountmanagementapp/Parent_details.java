package com.example.accountmanagementapp;

import android.annotation.SuppressLint;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

public class Parent_details extends AppCompatActivity {

    private EditText Name,father,mother,address,phone,occupation,income,roll;
    private Button next,home;


    private static String URL_parent = Config.BASE_URL + "parent.php";






    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_details);

        Name = findViewById(R.id.Name);

        father = findViewById(R.id.father);
        mother = findViewById(R.id.mother);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        occupation = findViewById(R.id.occupation);
        income = findViewById(R.id.income);
        roll = findViewById(R.id.roll);
        next = findViewById(R.id.next);
        home = findViewById(R.id.home);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parent_details.this, home.class);
                startActivity(intent);

            }
        });

    }


    public void Register() {
        next.setVisibility(View.GONE);

        final String Name = this.Name.getText().toString().trim();
        final String father = this.father.getText().toString().trim();
        final String mother = this.mother.getText().toString().trim();

        final String address = this.address.getText().toString().trim();
        final String occupation = this.occupation.getText().toString().trim();

        final String phone = this.phone.getText().toString().trim();
        final String income = this.income.getText().toString().trim();
        final String roll = this.roll.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_parent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");


                            if (success.equals("1")) {
                                Toast.makeText(Parent_details.this, "Insertion successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Parent_details.this, home.class);
                                startActivity(intent);
                            }

                        }


                        catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(Parent_details.this, "Parsing Error",Toast.LENGTH_SHORT).show();

                            next.setVisibility(View.VISIBLE);
                        }


                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Parent_details.this, "registration error"+error .toString(),Toast.LENGTH_SHORT).show();
                       next.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("Name",Name);
                params.put("father",father);
                params.put("mother",mother);
                params.put("address",address);
                params.put("phone",phone);
                params.put("occupation",occupation);
                params.put("income",income);
                params.put("roll",roll);
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}