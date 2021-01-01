package com.example.nasadsc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView title;
    ImageView image;
    String image_url;
    private final static String TAG = MainActivity.class.getSimpleName();
    private NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.image);
        title=findViewById(R.id.title);
        LinearLayout linearLayout = findViewById(R.id.layout_app);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
        fetchdata();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("title",title.getText().toString());
                startActivity(intent);
            }
        });

    }

    private void getImagefromUrl() {
        //Picasso.get().load(image_url).into(image);


        if (VolleyApplication.getInstance() != null) {
            ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();
            //networkImageView.setImageUrl(image_url, imageLoader);
            //networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);

            // If you are using normal ImageView
            imageLoader.get(image_url, new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Image Load Error: " + error.getMessage());
                    image.setImageResource(R.mipmap.ic_launcher);
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        image.setImageBitmap(response.getBitmap());
                    }
                }
            });
        }
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

                            // Creating object of JSONObject
                            JSONObject jsonObject
                                    = new JSONObject(
                                    response.toString());
                            /*explain.setText(
                                    jsonObject.getString("explanation"));*/
                            title.setText(jsonObject.getString("title"));
                            image_url = jsonObject.getString("url");
                            getImagefromUrl();

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
                                MainActivity.this,
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