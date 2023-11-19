package com.example.accountmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditAdmissionActivity extends AppCompatActivity {

        private EditText editName,editcourse,edittype,edityear,editenroll,editroll,editinstitute;
        private Button saveButton,deleteButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admission);

        editName = findViewById(R.id.editName);
        editcourse = findViewById(R.id.editcourse);
        edittype = findViewById(R.id.edittype);
        edityear = findViewById(R.id.edityear);
        editenroll= findViewById(R.id.editenroll);
        editroll = findViewById(R.id.editroll);
        editinstitute= findViewById(R.id.editinstitute);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        String jsonData = intent.getStringExtra("jsonData");

        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            if (jsonObject.has("details")) {
                JSONObject detailsObject = jsonObject.getJSONArray("details").getJSONObject(0);

                // Assuming these keys exist in your JSON response; modify as per your actual keys
                String Name = detailsObject.getString("Name");
                String course = detailsObject.getString("course");
                String type = detailsObject.getString("type");
                String year = detailsObject.getString("year");
                String enroll = detailsObject.getString("enroll");
                String roll = detailsObject.getString("roll");
                String institute = detailsObject.getString("institute");

                editName.setText(Name);


                editcourse.setText(course);
                edittype.setText(type);
                edityear.setText(year);
                editenroll.setText(enroll);
                editroll.setText(roll);

                editinstitute.setText(institute);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get the details to be deleted
                        String rollToDelete = editroll.getText().toString();

                        // Send a request to delete details on the server
                        new EditAdmissionActivity.DeleteDetailsAsyncTask().execute(rollToDelete);
                    }
                });

                // Handle the "Save" button click
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the updated details from EditText fields
                        String updatedName = editName.getText().toString();
                        String updatedcourse = editcourse.getText().toString();
                        String updatedtype = edittype.getText().toString();
                        String updatedyear = edityear.getText().toString();

                        String updatedenroll = editenroll.getText().toString();
                        String updatedroll = editroll.getText().toString();
                        String updatedinstitute = editinstitute.getText().toString();



                        // Send a request to update details on the server
                        new EditAdmissionActivity.UpdateDetailsAsyncTask().execute(updatedName,
                                updatedcourse,updatedtype,updatedyear,updatedenroll,updatedroll,updatedinstitute);
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(EditAdmissionActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }
    class DeleteDetailsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String rollToDelete = params[0];

            try {
                // Replace this URL with your server's delete endpoint
                String deleteUrl = (Config.BASE_URL + "delete_admission.php");
                URL url = new URL(deleteUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");  // Change to POST
                httpURLConnection.setDoOutput(true);

                // Create the query parameters
                String postData = "roll=" + rollToDelete;

                // Write the parameters to the output stream
                httpURLConnection.getOutputStream().write(postData.getBytes("UTF-8"));

                // Read the response from the server
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

                bufferedReader.close();
                httpURLConnection.disconnect();

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Print the server response for debugging
            System.out.println("Server Response (Delete): " + result);

            if (result != null && !result.isEmpty()) {
                // Handle the response from the server
                try {
                    JSONObject responseJson = new JSONObject(result);
                    // Process the response JSON as needed
                    // For example, check if the delete was successful
                    if (responseJson.has("success") && responseJson.getBoolean("success")) {
                        Toast.makeText(EditAdmissionActivity.this, "Details deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditAdmissionActivity.this, search_admission.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(EditAdmissionActivity.this, "Failed to delete details", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditAdmissionActivity.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EditAdmissionActivity.this, "Empty or invalid server response", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    class UpdateDetailsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String updatedName = params[0];
            String updatedcourse = params[1];
            String updatedtype = params[2];
            String updatedyear = params[3];
            String updatedenroll = params[4];
            String updatedroll = params[5];
            String updatedinstitute = params[6];

            try {
                // Replace this URL with your server's update endpoint
                String updateUrl = (Config.BASE_URL + "update_admission.php");
                URL url = new URL(updateUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");  // Change to POST
                httpURLConnection.setDoOutput(true);

                // Create the query parameters
                String postData = "Name=" + updatedName +"&course=" + updatedcourse +"&type=" + updatedtype+
                        "&year=" + updatedyear + "&enroll=" + updatedenroll + "&roll=" + updatedroll +"&institute=" + updatedinstitute ;

                // Write the parameters to the output stream
                httpURLConnection.getOutputStream().write(postData.getBytes("UTF-8"));

                // Read the response from the server
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

                bufferedReader.close();
                httpURLConnection.disconnect();

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Print the server response for debugging
            System.out.println("Server Response: " + result);

            if (result != null) {
                // Handle the response from the server
                try {
                    JSONObject responseJson = new JSONObject(result);
                    // Process the response JSON as needed
                    // For example, check if the update was successful
                    if (responseJson.has("success") && responseJson.getBoolean("success")) {
                        Toast.makeText(EditAdmissionActivity.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditAdmissionActivity.this, search_admission.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditAdmissionActivity.this, "Failed to update details", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditAdmissionActivity.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EditAdmissionActivity.this, "Error updating details", Toast.LENGTH_SHORT).show();
            }
        }

    }}
