package com.example.austin.cdiprealtimeswelldata;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class CentralCoast extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_coast);

        setTitle("Central Coast");

        imageView = (ImageView) findViewById(R.id.image_central_coast);

        String url = "https://cdip.ucsd.edu/recent/model_images/conception.png";

        Picasso.get().load(url).into(imageView);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshCentralCoast);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent()
    {
        String url = "https://cdip.ucsd.edu/recent/model_images/conception.png";
        Picasso.get().load(url).into(imageView);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
