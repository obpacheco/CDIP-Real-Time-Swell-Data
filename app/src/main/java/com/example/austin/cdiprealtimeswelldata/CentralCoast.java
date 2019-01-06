package com.example.austin.cdiprealtimeswelldata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

public class CentralCoast extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_coast);
        setTitle("Central Coast");

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setIndeterminate(true);


        imageView = (ImageView) findViewById(R.id.image_central_coast);

        String url = "https://cdip.ucsd.edu/recent/model_images/conception.png";
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
        Intent intent = new Intent(this, CentralCoast.class);
        startActivity(intent);
    }
}
