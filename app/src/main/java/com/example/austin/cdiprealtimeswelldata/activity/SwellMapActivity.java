package com.example.austin.cdiprealtimeswelldata.activity;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.austin.cdiprealtimeswelldata.R;
import com.example.austin.cdiprealtimeswelldata.fragment.LocalSwellMapFragment;
import com.example.austin.cdiprealtimeswelldata.fragment.LocalTideDataFragment;
import com.viewpagerindicator.CirclePageIndicator;

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

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_swell_map_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mLocation = bundle.getString("Location");
        setTitle(mLocation);

        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        if (!sharedPreferences.contains("buoy_northern_california")) {
            editor = sharedPreferences.edit();
            editor.putInt("buoy_northern_california", 0);
            editor.putInt("buoy_monterey", 0);
            editor.putInt("buoy_central_coast", 0);
            editor.putInt("buoy_southern_california", 0);
            editor.commit();
        }

        pager = findViewById(R.id.pager);
        pagerAdapter = null;
        pagerAdapter = new LocalPagerAdapter(getSupportFragmentManager(), mLocation);
        pager.setAdapter(pagerAdapter);
        CirclePageIndicator pageIndicator = findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(pager);


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        inflateMenuOptions(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        editor = sharedPreferences.edit();
        if (mLocation.equals("Northern California")) {
            switch (item.getItemId()) {
                case R.id.refresh:
                    refreshPicture();
                    return true;
                case 0: // San Francisco
                    editor.putInt("buoy_northern_california", 0);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                case 1: // Arena Cove
                    editor.putInt("buoy_northern_california", 1);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                case 2: // Bolinas
                    editor.putInt("buoy_northern_california", 2);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else if (mLocation.equals("Monterey Bay")) {
            switch (item.getItemId()) {
                case R.id.refresh:
                    refreshPicture();
                    return true;
                case 0: // Monterey
                    editor.putInt("buoy_monterey", 0);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else if (mLocation.equals("Central Coast")) {
            switch (item.getItemId()) {
                case R.id.refresh:
                    refreshPicture();
                    return true;
                case 0: // Port San Luis
                    editor.putInt("buoy_central_coast", 0);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                case 1: // Pt Conception
                    editor.putInt("buoy_central_coast", 1);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else if (mLocation.equals("Southern California")) {
            switch (item.getItemId()) {
                case R.id.refresh:
                    refreshPicture();
                    return true;
                case 0: // Santa Monica
                    editor.putInt("buoy_southern_california", 0);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                case 1: // Santa Barbara
                    editor.putInt("buoy_southern_california", 1);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                case 2: // Long Beach
                    editor.putInt("buoy_southern_california", 2);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                case 3: // La Jolla
                    editor.putInt("buoy_southern_california", 3);
                    editor.commit();
                    pagerAdapter.refreshTides();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshPicture()
    {
        pagerAdapter.refreshTides();
        pagerAdapter.refreshImages();
    }

    private void inflateMenuOptions(Menu menu) {
        if (mLocation.equals("Northern California")) {
            menu.add(0, 0, 2, getString(R.string.san_francisco))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menu.add(0, 1, 0, getString(R.string.arena_cove))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menu.add(0, 2, 1, getString(R.string.bolinas))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        } else if (mLocation.equals("Monterey Bay")) {
            menu.add(0, 0, 0, getString(R.string.monterey_buoy))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        } else if (mLocation.equals("Central Coast")) {
            menu.add(0,0, 0, getString(R.string.port_san_luis))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menu.add(0,1, 1, getString(R.string.pt_conception))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        } else if (mLocation.equals("Southern California")) {
            menu.add(0, 0, 1, getString(R.string.santa_monica))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menu.add(0, 1, 0, getString(R.string.santa_barbara))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menu.add(0,2, 2, getString(R.string.long_beach))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menu.add(0,3, 3, getString(R.string.la_jolla))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        }
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

        public void refreshTides() {
            localTideFragment.onRefresh();
        }

        public void refreshImages() {
            localSwellMapFragment.onRefresh();
        }

        @Override
        public int getCount() {
            return NUM_FRAGMENTS;
        }
    }
}
