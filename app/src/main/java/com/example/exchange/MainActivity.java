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
    public EditText from, to, fromVal, toVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        from = findViewById(R.id.fromCurInput);
        to = findViewById(R.id.toCurInput);
        fromVal = findViewById(R.id.toValInput);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                // API BASEURL
                String BASEURL = "https://api.apilayer.com/exchangerates_data/convert?to="+ to.getText().toString() +"&from="+ from.getText().toString() +"&amount=" + fromVal.getText().toString();
                // String url = "https://v2.jokeapi.dev/joke/Programming";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, BASEURL, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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