package com.example.accountmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accountmanagementapp.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Student_details extends AppCompatActivity {

    private EditText NameEditText, emailEditText, phoneEditText, rollEditText, aadharEditText, sectionEditText;
    private ImageView imageView;
    private Bitmap selectedImage;
    private Button home;

    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        NameEditText = findViewById(R.id.Name);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        rollEditText = findViewById(R.id.roll);
        aadharEditText = findViewById(R.id.aadhar);
        sectionEditText = findViewById(R.id.section);
        imageView = findViewById(R.id.imageView);
        home = findViewById(R.id.home);

        Button chooseImageButton = findViewById(R.id.buttonChoose);
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the image gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student_details.this, home.class);
                startActivity(intent);

            }
        });
        Button nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve data from EditText fields
                String Name = NameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String roll = rollEditText.getText().toString();
                String aadhar = aadharEditText.getText().toString();
                String section = sectionEditText.getText().toString();

                // Execute AsyncTask to insert data into MySQL database
                new InsertDataTask().execute(Name, email, phone, roll, aadhar, section);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                selectedImage = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(selectedImage);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private class InsertDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d("InsertDataTask", "doInBackground");
            String Name = params[0];
            String email = params[1];
            String phone = params[2];
            String roll = params[3];
            String aadhar = params[4];
            String section = params[5];

            // Convert the image to a Base64 encoded string
            String imageData = "";
            if (selectedImage != null) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageData = Base64.encodeToString(imageBytes, Base64.DEFAULT);


                } catch (Exception e) {
                    Log.e("Image Encoding", "Error encoding image: " + e.getMessage());
                    return "Error: " + e.getMessage();
                }
            }

            try {
                // Set up the URL and open the connection
                URL url = new URL(Config.BASE_URL + "details.php");
// Replace with your actual server URL
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                // Create the data to send to the server
                String data = "Name=" + Name + "&email=" + email + "&phone=" + phone + "&roll=" + roll +
                        "&aadhar=" + aadhar + "&section=" + section + "&image=" + imageData;

                // Write the data to the server
                OutputStream out = urlConnection.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();

                // Get the server response
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Return the server response
                return response.toString();

            } catch (Exception e) {
                Log.e("Server Connection", "Error connecting to the server: " + e.getMessage());
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("InsertDataTask", "onPostExecute: " + result);

            if (result.contains("Success")) {
                Toast.makeText(Student_details.this, "Inserted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Student_details.this, home.class);
                startActivity(intent);
            } else {
                // Error or other response from the server
                showToast("Error: " + result);
            }
        }

        private void showToast(String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}