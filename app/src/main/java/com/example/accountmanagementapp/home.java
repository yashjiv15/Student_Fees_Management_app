package com.example.accountmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(getApplicationContext());

        // Find buttons by their IDs
        Button btnDetails = findViewById(R.id.btn_details);
        Button btnFees = findViewById(R.id.btn_fees);

        Button logout = findViewById(R.id.logout);
        Button btnsearch = findViewById(R.id.btn_search);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnadmission = findViewById(R.id.btn_admission);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnparent = findViewById(R.id.btn_parent);

        // Set click listeners for each button
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity for Student Details
                Intent intent = new Intent(home.this, search_details.class);
                startActivity(intent);
            }
        });

        btnFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity for Fees Structure
                Intent intent = new Intent(home.this, search_fees.class);
                startActivity(intent);
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity for admission Details
                Intent intent = new Intent(home.this, search_records.class);
                startActivity(intent);
            }
        });

        btnadmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity for Admission Details
                Intent intent = new Intent(home.this, search_admission.class);
                startActivity(intent);
            }
        });

        btnparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity for Parent Details
                Intent intent = new Intent(home.this, search_parent.class);
                startActivity(intent);
            }
        });

      logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            logout();
        }
    });
}

    // Function to handle logout
    private void logout() {
        // Clear any session data or user preferences
        sessionManager.logoutUser();

        // Redirect to the login activity
        Intent intent = new Intent(home.this, login.class);
        startActivity(intent);
        finish(); // Close the current activity

        // Display a success message using a Toast
        Toast.makeText(home.this, "Logout successful", Toast.LENGTH_SHORT).show();
    }
}




