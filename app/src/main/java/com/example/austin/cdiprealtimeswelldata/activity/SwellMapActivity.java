package com.example.austin.cdiprealtimeswelldata.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.austin.cdiprealtimeswelldata.R;
import com.example.austin.cdiprealtimeswelldata.fragment.LocalSwellMapFragment;
import com.example.austin.cdiprealtimeswelldata.fragment.LocalTideDataFragment;
import com.viewpagerindicator.CirclePageIndicator;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SwellMapActivity extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar mProgressBar;
    private String mLocation;
    private ViewPager pager;
    private LocalPagerAdapter pagerAdapter;

    private static final int POS_MAP = 0;
    private static final int POS_TIDE = 1;
    private static final int NUM_FRAGMENTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_swell_map_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mLocation = bundle.getString("Location");
        setTitle(mLocation);


        pager = findViewById(R.id.pager);
        pagerAdapter = null;
        pagerAdapter = new LocalPagerAdapter(getSupportFragmentManager(), mLocation);
        pager.setAdapter(pagerAdapter);
        CirclePageIndicator pageIndicator = findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(pager);


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


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
                ActionMenuItemView refreshButton = findViewById(R.id.refresh);
                //refreshButton.setEnabled(false);
                refreshPicture();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void activateRefreshButton() {
        ActionMenuItemView refreshButton = findViewById(R.id.refresh);
        refreshButton.setEnabled(true);
    }


    public void refreshPicture()
    {
        pagerAdapter = null;
        pagerAdapter = new LocalPagerAdapter(getSupportFragmentManager(), mLocation);
        pager.setAdapter(pagerAdapter);
    }

    public class LocalPagerAdapter extends FragmentStatePagerAdapter {

        private String mLocation;


        public LocalPagerAdapter(FragmentManager fm, String location) {
            super(fm);
            mLocation = location;
        }

        private LocalSwellMapFragment localSwellMapFragment;
        private LocalTideDataFragment localTideFragment;


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case POS_MAP:
                    if(localSwellMapFragment == null) {
                        localSwellMapFragment = localSwellMapFragment.newInstance(mLocation);
                    }
                    return localSwellMapFragment;
                case POS_TIDE:
                    if(localTideFragment == null) {
                        localTideFragment = localTideFragment.newInstance(mLocation);
                    }
                    return localTideFragment;
                default:
                    return null;
            }
        }


        @Override
        public int getCount() {
            return NUM_FRAGMENTS;
        }
    }
}
