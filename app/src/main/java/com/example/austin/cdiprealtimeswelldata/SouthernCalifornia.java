package com.example.austin.cdiprealtimeswelldata;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.squareup.picasso.Picasso;
import androidx.appcompat.widget.Toolbar;

public class SouthernCalifornia extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_southern_california);
        setTitle("Southern California");

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setIndeterminate(true);


        imageView = (ImageView) findViewById(R.id.image_southern_california);

        String url = "https://cdip.ucsd.edu/recent/model_images/socal_now.png";
        Picasso.get().load(url).into(imageView);

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
        Intent intent = new Intent(this, SouthernCalifornia.class);
        startActivity(intent);
    }
}

