package com.example.exchange;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText from, to, fromVal, toVal;
    private TextView exchangeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting widgets
        from = findViewById(R.id.fromCurInput);
        to = findViewById(R.id.toCurInput);
        fromVal = findViewById(R.id.fromValInput);
        toVal = findViewById(R.id.toValInput);
        exchangeInfo = findViewById(R.id.exchangeInfo);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make loader visible
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                // validate the data
                if(fromVal.getText().toString().equals("") || from.getText().toString().equals("") || to.getText().toString().equals("")){
                    findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Enter all the params", Toast.LENGTH_SHORT).show();
                    return;
                }

                // API BASEURL
                String BASEURL = "https://api.apilayer.com/exchangerates_data/convert?to="+ to.getText().toString() +"&from="+ from.getText().toString() +"&amount=" + fromVal.getText().toString();

                // creating JSON request
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, BASEURL, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                try{
                                    // displaying result
                                    toVal.setText(response.getString("result"));
                                    // displaying other information
                                    exchangeInfo.setText(fromVal.getText().toString() + " " + from.getText().toString() + " is " + response.getString("result") + " " + to.getText().toString() + " on " + response.getString("date"));
                                } catch (Exception e){
                                    toVal.setText("");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                toVal.setText("");
                                Toast.makeText(MainActivity.this, "Try Again...", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json; charset=UTF-8");
                        params.put("apikey", "G2XHwBOHNKOeHp6A6fNOO9MeNscPN5Ny");
                        return params;
                    }};
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}