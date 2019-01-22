package com.example.austin.cdiprealtimeswelldata.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.austin.cdiprealtimeswelldata.R;
import com.squareup.picasso.Picasso;
import androidx.appcompat.widget.Toolbar;

import static com.example.austin.cdiprealtimeswelldata.utilities.GetSwellMapUtil.getSwellMapUrl;

public class SwellMapActivity extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar mProgressBar;
    private String mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_swell_map_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mLocation = bundle.getString("Location");
        setTitle(mLocation);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setIndeterminate(true);


        imageView = (ImageView) findViewById(R.id.image_local_swell_map);

        Picasso.get().load(getSwellMapUrl(this, mLocation)).into(imageView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        mProgressBar.setIndeterminate(false);

    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.refresh:
                refreshPicture();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void refreshPicture()
    {
        Intent intent = new Intent(this, SwellMapActivity.class);
        startActivity(intent);
    }
}
