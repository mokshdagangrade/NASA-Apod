package com.example.nasadsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    ImageView image,like1,save1,view1;
    TextView explain,photo,like2,like3,save2,save3,view2,view3;
    String image_url;
    String title;
    //JSONObject containerObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        image=findViewById(R.id.imageView);
        photo=findViewById(R.id.photo);
        explain=findViewById(R.id.explain);
        explain.setMovementMethod(new ScrollingMovementMethod());
        like1=findViewById(R.id.like1);
        like2=findViewById(R.id.like2);
        like3=findViewById(R.id.like3);
        save1=findViewById(R.id.save1);
        save2=findViewById(R.id.save2);
        save3=findViewById(R.id.save3);
        view1=findViewById(R.id.view1);
        view2=findViewById(R.id.view2);
        view3=findViewById(R.id.view3);
        fetchdata();
    }

    private void fetchdata() {
        String url = "https://api.nasa.gov/planetary/apod?api_key=ZlNn6a4JRQ7qGgvWIup4yCL4d2WoFibxcWKSCMsj";

        StringRequest request
                = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                                JSONObject jsonObject
                                        = new JSONObject(
                                        response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                explain.setText(
                                        jsonObject.getString("explanation"));
                                image_url = jsonObject.getString("url");
                                Picasso.get().load(image_url).into(image);
                                photo.setText(jsonObject.getString("copyright"));
                                like1.setVisibility(View.VISIBLE);
                                like2.setVisibility(View.VISIBLE);
                                like3.setVisibility(View.VISIBLE);
                                save1.setVisibility(View.VISIBLE);
                                save2.setVisibility(View.VISIBLE);
                                save3.setVisibility(View.VISIBLE);
                                view1.setVisibility(View.VISIBLE);
                                view2.setVisibility(View.VISIBLE);
                                view3.setVisibility(View.VISIBLE);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(
                            VolleyError error) {
                        Toast.makeText(
                                DetailsActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        RequestQueue requestQueue
                = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    }
