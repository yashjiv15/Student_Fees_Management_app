package com.example.accountmanagementapp;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class admission_details extends AppCompatActivity {
    private EditText Name,course,type,year,enroll,roll,institute;
    private Button next,home;
    private static String URL_admission =Config.BASE_URL + "admission.php";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_details);
        Name = findViewById(R.id.Name);
        course = findViewById(R.id.course);
        type = findViewById(R.id.type);
        year = findViewById(R.id.year);
        enroll= findViewById(R.id.enroll);
        roll = findViewById(R.id.roll);
        institute= findViewById(R.id.institute);
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
                Intent intent = new Intent(admission_details.this, home.class);
                startActivity(intent);

            }
        });
    }

    public void Register() {
        next.setVisibility(View.GONE);

        final String Name = this.Name.getText().toString().trim();
        final String course = this.course.getText().toString().trim();
        final String type = this.type.getText().toString().trim();

        final String year = this.year.getText().toString().trim();
        final String enroll = this.enroll.getText().toString().trim();

        final String roll = this.roll.getText().toString().trim();
        final String institute = this.institute.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_admission,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");


                            if (success.equals("1")) {
                                Toast.makeText(admission_details.this, "inserted successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(admission_details.this,home.class);
                                startActivity(intent);
                            }

                        }


                        catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(admission_details.this, "Insertion error",Toast.LENGTH_SHORT).show();

                            next.setVisibility(View.VISIBLE);
                        }


                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(admission_details.this, "Insertion error"+error .toString(),Toast.LENGTH_SHORT).show();
                        next.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Name",Name);
                params.put("course",course);
                params.put("type",type);
                params.put("enroll",enroll);
                params.put("year",year);
                params.put("roll",roll);
                params.put("institute",institute);

                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
