package com.example.accountmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class search_details extends AppCompatActivity {
    private EditText editText;
    private Button details, edetails,edit,home;
    private TableLayout detailsTableLayout;
    private TextView details_heading;
    private ImageView imageView;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);

        editText = findViewById(R.id.editText);
        details_heading = findViewById(R.id.details_heading);
        details = findViewById(R.id.details);
        edetails = findViewById(R.id.edetails);
        imageView = findViewById(R.id.myImageView);
        detailsTableLayout = findViewById(R.id.detailsTableLayout);
        edit = findViewById(R.id.edit);
        home = findViewById(R.id.home);
        String jsonData = getIntent().getStringExtra("jsonData");

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = editText.getText().toString();
                String roll = editText.getText().toString();
                new SearchAsyncTask().execute(Name, roll);
            }
        });

        edetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search_details.this, Student_details.class);
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search_details.this, EditStudDetailsActivity.class);
                startActivity(intent);

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search_details.this, home.class);
                startActivity(intent);

            }
        });
    }

    class SearchAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String Name = params[0];
            String roll = params[1];
            String result = "";
            try {
                URL url = new URL(Config.BASE_URL + "search.php?Name=" + Name + "&roll=" + roll);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStreamReader.close();
                httpURLConnection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(search_details.this, "No records found", Toast.LENGTH_SHORT).show();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                Log.d("JSON Response", jsonObject.toString());

                if (jsonObject.has("details")) {
                    JSONArray detailsArray = jsonObject.getJSONArray("details");
                    details_heading.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(search_details.this, EditStudDetailsActivity.class);
                            intent.putExtra("jsonData", result); // Pass the whole JSON response
                            startActivity(intent);
                        }
                    });

                    displayTable(detailsTableLayout, "Details Table", detailsArray);

                    if (jsonObject.has("base64_image")) {
                        String base64Image = jsonObject.getString("base64_image");
                        Log.d("Base64 Image", base64Image);

                        new LoadImageTask().execute(base64Image);
                    } else {
                        // Handle the case where the image key is not found
                        Toast.makeText(search_details.this, "No image found", Toast.LENGTH_SHORT).show();
                        imageView.setVisibility(View.GONE); // Hide the imageView
                    }
                } else {
                    Toast.makeText(search_details.this, "No details found", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(search_details.this, "No data Found", Toast.LENGTH_SHORT).show();
            }
        }

        private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... params) {
                String base64Image = params[0];
                try {
                    byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
                    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Image Decoding Error", e.getMessage());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setImageResource(R.drawable.yash);

                }
            }
        }

        private void displayTable(TableLayout tableLayout, String tableName, JSONArray jsonArray) throws JSONException {
            TableRow headersRow = new TableRow(search_details.this);

            for (int i = 0; i < jsonArray.getJSONObject(0).length(); i++) {
                String columnHeader = jsonArray.getJSONObject(0).names().getString(i);
                TextView headerTextView = createTextView(columnHeader, true);
                headersRow.addView(headerTextView);
            }

            tableLayout.addView(headersRow);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                TableRow row = new TableRow(search_details.this);

                for (int j = 0; j < jsonObject.length(); j++) {
                    String columnValue = jsonObject.getString(jsonObject.names().getString(j));

                    TextView cellTextView = createTextView(columnValue, false);
                    row.addView(cellTextView);
                }

                tableLayout.addView(row);
            }
        }

        private TextView createTextView(String text, boolean isHeader) {
            TextView textView = new TextView(search_details.this);
            textView.setText(text);
            textView.setPadding(16, 16, 16, 16);

            if (isHeader) {
                textView.setBackgroundResource(R.color.colorHeaderBackground);
                textView.setTextColor(getResources().getColor(R.color.colorHeaderText));
            } else {
                textView.setBackgroundResource(R.color.colorCellBackground);
                textView.setTextColor(getResources().getColor(R.color.colorCellText));
            }

            return textView;
        }
    }
}
