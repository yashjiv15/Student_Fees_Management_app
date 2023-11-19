package com.example.accountmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity {

    private ImageView imageView;
    private String imageUrl = "http://192.168.1.4/fetch.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView = findViewById(R.id.imageView);

        // Fetch images from PHP server
        new FetchImageTask().execute(imageUrl);
    }

    private class FetchImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(MainActivity2.this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}