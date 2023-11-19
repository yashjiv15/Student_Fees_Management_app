    package com.example.accountmanagementapp;
    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.graphics.Typeface;
    import android.os.AsyncTask;
    import android.os.Bundle;

    import android.view.Gravity;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TableLayout;
    import android.widget.TableRow;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;

    public class search_records extends AppCompatActivity {

        private EditText editText;
        private Button details,home;
        private TableLayout detailsTableLayout,feesTableLayout,parentTableLayout,admissionTableLayout;
        private TextView details_heading,fees_heading;


        @SuppressLint({"WrongViewCast", "MissingInflatedId"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_records);

            editText = findViewById(R.id.editText);

             fees_heading= findViewById(R.id.fees_heading);
            details_heading= findViewById(R.id.details_heading);
            details = findViewById(R.id.details);
            home = findViewById(R.id.home);

            detailsTableLayout = findViewById(R.id.detailsTableLayout);
            feesTableLayout = findViewById(R.id.feesTableLayout);
            parentTableLayout = findViewById(R.id.parentTableLayout);
            admissionTableLayout = findViewById(R.id.admissionTableLayout);
            String jsonData = getIntent().getStringExtra("jsonData");


            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Name = editText.getText().toString();
                    String roll = editText.getText().toString();
                    new SearchAsyncTask().execute(Name,roll);
                }
            });
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(search_records.this, home.class);
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
                    URL url = new URL(Config.BASE_URL + "search_details.php?Name=" + Name + "&roll=" + roll);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.connect();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    httpURLConnection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(search_records.this, "No records found", Toast.LENGTH_SHORT).show();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    // Check if 'admission' data exists
                    if (jsonObject.has("details")) {
                        JSONArray detailsArray = jsonObject.getJSONArray("details");
                        details_heading.setVisibility(View.VISIBLE);
                        displayTable(detailsTableLayout, "Details Table", detailsArray);
                    }
                        // Check if 'admission' data exists
                        if (jsonObject.has("fees")) {
                            fees_heading.setVisibility(View.VISIBLE);
                            JSONArray feesArray = jsonObject.getJSONArray("fees");

                            displayTable(feesTableLayout,"Fees Table", feesArray);
                        }
                    // Check if 'admission' data exists
                    if (jsonObject.has("parent")) {

                        JSONArray admissionArray = jsonObject.getJSONArray("parent");

                        displayTable(parentTableLayout,"Parent Table", admissionArray);
                    }
                    if (jsonObject.has("admission")) {

                        JSONArray admissionArray = jsonObject.getJSONArray("admission");

                        displayTable(admissionTableLayout,"admission Table", admissionArray);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(search_records.this, "No records found", Toast.LENGTH_SHORT).show();
                }
            }


            private void displayTable(TableLayout tableLayout, String tableName, JSONArray jsonArray) throws JSONException {
                // Create a new TableRow for headers

                TableRow headersRow = new TableRow(search_records.this);

                // Create and add header cells dynamically
                for (int i = 0; i < jsonArray.getJSONObject(0).length(); i++) {
                    String columnHeader = jsonArray.getJSONObject(0).names().getString(i);
                    TextView headerTextView = createTextView(columnHeader, true);
                    headersRow.addView(headerTextView);
                }

                tableLayout.addView(headersRow);

                // Create and add rows dynamically
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    TableRow row = new TableRow(search_records.this);

                    for (int j = 0; j < jsonObject.length(); j++) {
                        String columnValue = jsonObject.getString(jsonObject.names().getString(j));

                        TextView cellTextView = createTextView(columnValue, false);
                        row.addView(cellTextView);
                    }

                    // Add the data TableRow to the TableLayout
                    tableLayout.addView(row);
                }
            }
            private TextView createTextView(String text, boolean isHeader) {
                TextView textView = new TextView(search_records.this);
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
            private TextView createHeadingTextView(String text) {
                TextView headingTextView = new TextView(search_records.this);
                headingTextView.setText(text);
                headingTextView.setTextSize(18);
                headingTextView.setTypeface(null, Typeface.BOLD);
                headingTextView.setPadding(16, 16, 16, 16);
                headingTextView.setTextColor(getResources().getColor(R.color.black));
                return headingTextView;
            }
        }}