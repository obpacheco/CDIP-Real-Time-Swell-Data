package com.example.austin.cdiprealtimeswelldata;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class MontereyBay extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monterey_bay);

        setTitle("Monterey Bay");

        imageView = (ImageView) findViewById(R.id.image_monterey_bay);

        String url = "https://cdip.ucsd.edu/recent/model_images/monterey.png";

        Picasso.get().load(url).into(imageView);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshMontereyBay);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent()
    {
        String url = "https://cdip.ucsd.edu/recent/model_images/monterey.png";
        Picasso.get().load(url).into(imageView);
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
