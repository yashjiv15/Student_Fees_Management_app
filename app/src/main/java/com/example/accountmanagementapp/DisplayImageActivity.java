package com.example.accountmanagementapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class DisplayImageActivity extends AppCompatActivity {

    static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        imageView = findViewById(R.id.imageView);

        // Replace "your_server.com" with your actual server URL
        int imageId = 17; // Replace with the desired image ID
        String getImageUrl = "http://192.168.1.4/upload.php?id=" + imageId;

        // Fetch and display the image
        new LoadImageTask().execute(getImageUrl);
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String getImageUrl = params[0];

            try {
                // Fetch image path from the server
                URL url = new URL(getImageUrl);
                InputStream inputStream = url.openStream();
                String imagePath = convertStreamToString(inputStream);

                // Load and return the image
                InputStream imageInputStream = new URL(imagePath).openStream();
                return BitmapFactory.decodeStream(imageInputStream);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

        private String convertStreamToString(InputStream is) {
            // Convert InputStream to String using BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return stringBuilder.toString();
        }
    }
}
