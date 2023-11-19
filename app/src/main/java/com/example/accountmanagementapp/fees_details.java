package com.example.accountmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class fees_details extends AppCompatActivity {

    private EditText Name,roll,amount;
    private Button next,home;
    private Spinner spinner, spinner2,spinner3,spinner4;
    private EditText amountEditText;


    private static String URL_parent =Config.BASE_URL + "fees.php";





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_details);

        Name = findViewById(R.id.Name);


        roll = findViewById(R.id.roll);
        amount= findViewById(R.id.amount);
        next = findViewById(R.id.next);
        home = findViewById(R.id.home);

        amountEditText = findViewById(R.id.amount);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fees_details.this, home.class);
                startActivity(intent);

            }
        });
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> spinner2Adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner2_items, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> spinner3Adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner3_items, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> spinner4Adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner4_items, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        spinner.setAdapter(spinnerAdapter);
        spinner2.setAdapter(spinner2Adapter);
        spinner3.setAdapter(spinner3Adapter);
        spinner4.setAdapter(spinner4Adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            updateAmount();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // Do nothing here
        }
    });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
        }


    private void updateAmount() {
        // Implement logic to determine the amount based on the selected items from both spinners
        // For simplicity, let's assume a fixed amount for each selected option
        int amount = getAmountForOption("spinner", spinner.getSelectedItem().toString()) +
                getAmountForOption("spinner2", spinner2.getSelectedItem().toString()) +
                getAmountForOption("spinner3", spinner3.getSelectedItem().toString())+
                getAmountForOption("spinner4", spinner4.getSelectedItem().toString());

        // Update the amountEditText with the calculated amount
        amountEditText.setText(String.valueOf(amount));
    }


    private int getAmountForOption(String spinnerName, String selectedOption) {
        // Implement logic to map each option to a predefined amount
        // For simplicity, let's use a switch statement for illustration
        switch (spinnerName) {
            case "spinner":
                switch (selectedOption) {
                    case "-->CAP-CET":
                        return 00;
                    case "-->Management":
                        return 100000;
                    case "-->Quota":
                        return 80000;
                    default:
                        return 0;
                }
            case "spinner2":
                switch (selectedOption) {

                    case "-->OPEN":
                        return 50000;
                    case "-->SC":
                        return 5000;
                    case "-->Scholarship":
                        return 25000;
                    default:
                        return 0;
                }
            case "spinner3":
                switch (selectedOption) {
                    case "-->Late fees":
                        return 2000;
                    case "-->Insurance":
                        return 1000;
                    case "-->Miscellaneous":
                        return 8000;
                    default:
                        return 0;
                }
            case "spinner4":
                switch (selectedOption) {
                    case "-->Uniform":
                        return 4000;
                    case "-->I-card":
                        return 100;
                    default:
                        return 0;
                }

            default:
                return 0;
        }
    }


    public void Register() {
        next.setVisibility(View.GONE);


        final String Name = this.Name.getText().toString().trim();

        final String roll = this.roll.getText().toString().trim();
        final String amount = this.amount.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_parent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");


                            if (success.equals("1")) {
                                Toast.makeText(fees_details.this, "Insertion successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(fees_details.this, home.class);
                                startActivity(intent);
                            }

                        }


                        catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(fees_details.this, "Parsing error",Toast.LENGTH_SHORT).show();

                            next.setVisibility(View.VISIBLE);
                        }


                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(fees_details.this, "registration error"+error .toString(),Toast.LENGTH_SHORT).show();
                        next.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("Name",Name);
                params.put("amount",amount);
                params.put("roll",roll);

                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}