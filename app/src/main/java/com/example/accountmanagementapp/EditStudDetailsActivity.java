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

public class EditStudDetailsActivity extends AppCompatActivity {

    private EditText editName, editroll,editphone,editemail,editaadhar,editsection;
    private Button saveButton,deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stud_details);

        editName = findViewById(R.id.editName);

        editroll = findViewById(R.id.editroll);
        editemail = findViewById(R.id.editemail);
        editphone = findViewById(R.id.editphone);
        editemail = findViewById(R.id.editemail);
        saveButton = findViewById(R.id.saveButton);
        editaadhar = findViewById(R.id.editaadhar);
        editsection = findViewById(R.id.editsection);
        deleteButton = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        String jsonData = intent.getStringExtra("jsonData");

        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            if (jsonObject.has("details")) {
                JSONObject detailsObject = jsonObject.getJSONArray("details").getJSONObject(0);

                // Assuming these keys exist in your JSON response; modify as per your actual keys
                String Name = detailsObject.getString("Name");

                String roll = detailsObject.getString("roll");
                String email = detailsObject.getString("email");
                String phone = detailsObject.getString("phone");
                String aadhar = detailsObject.getString("aadhar");
                String section = detailsObject.getString("section");



                // Populate EditText fields with existing details
                editName.setText(Name);

                editroll.setText(roll);
                editemail.setText(email);
                editphone.setText(phone);
                editaadhar.setText(aadhar);
                editsection.setText(section);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get the details to be deleted
                        String rollToDelete = editroll.getText().toString();

                        // Send a request to delete details on the server
                        new EditStudDetailsActivity.DeleteDetailsAsyncTask().execute(rollToDelete);
                    }
                });

                // Handle the "Save" button click
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the updated details from EditText fields
                        String updatedName = editName.getText().toString();

                        String updatedroll = editroll.getText().toString();
                        String updateemail = editemail.getText().toString();
                        String updatephone = editphone.getText().toString();
                        String updateaadhar =editaadhar.getText().toString();
                        String updatesection = editsection.getText().toString();
                        // Send a request to update details on the server
                        new EditStudDetailsActivity.UpdateDetailsAsyncTask().execute(updatedName,
                                updatedroll,updateemail,updatephone,updateaadhar,updatesection);
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(EditStudDetailsActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }
    class DeleteDetailsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String rollToDelete = params[0];

            try {
                // Replace this URL with your server's delete endpoint
                String deleteUrl = (Config.BASE_URL + "delete_stud.php");
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
                        Toast.makeText(EditStudDetailsActivity.this, "Details deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditStudDetailsActivity.this, search_details.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(EditStudDetailsActivity.this, "Failed to delete details", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditStudDetailsActivity.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EditStudDetailsActivity.this, "Empty or invalid server response", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    class UpdateDetailsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String updatedName = params[0];
            String updatedroll = params[1];
            String updatedemail = params[2];
            String updatedphone = params[3];
            String updatedaadhar = params[4];
            String updatedsection = params[5];




            try {
                // Replace this URL with your server's update endpoint
                String updateUrl = (Config.BASE_URL + "student_update.php");
                URL url = new URL(updateUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");  // Change to POST
                httpURLConnection.setDoOutput(true);

                // Create the query parameters
                String postData = "Name=" + updatedName + "&roll=" + updatedroll + "&email=" + updatedemail+ "&phone=" + updatedphone+ "&aadhar=" + updatedaadhar+ "&section=" + updatedsection;

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
                        Toast.makeText(EditStudDetailsActivity.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditStudDetailsActivity.this, search_details.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditStudDetailsActivity.this, "Failed to update details", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditStudDetailsActivity.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EditStudDetailsActivity.this, "Error updating details", Toast.LENGTH_SHORT).show();
            }
        }

    }}
