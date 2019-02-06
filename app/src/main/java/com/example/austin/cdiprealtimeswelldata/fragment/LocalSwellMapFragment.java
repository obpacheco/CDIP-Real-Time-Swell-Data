package com.example.austin.cdiprealtimeswelldata.fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.austin.cdiprealtimeswelldata.R;
import com.example.austin.cdiprealtimeswelldata.utilities.SwellMapData;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.example.austin.cdiprealtimeswelldata.utilities.BuoyIdGetter.getNowTideBuoyId;
import static com.example.austin.cdiprealtimeswelldata.utilities.BuoyIdGetter.getTideBuoyId;

public class LocalSwellMapFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View root;
    private String location;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout swipeLayout;
    private SharedPreferences sharedPreferences;

    public static LocalSwellMapFragment newInstance(String location) {
        LocalSwellMapFragment localSwellMapFragment = new LocalSwellMapFragment();
        localSwellMapFragment.location = location;
        return localSwellMapFragment;
    }


    public LocalSwellMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (location == null) {
            Log.e(TAG, TAG + " was called without location");
        }
        sharedPreferences = getContext().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.swell_map_fragment, container, false);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mProgressBar.setIndeterminate(true);

        setSwellImage();
        setTideText();

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        swipeLayout = getActivity().findViewById(R.id.swipe_container_swell);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));
        swipeLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.progress_circle));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
    }

    @Override
    public void onRefresh() {
        mProgressBar.setEnabled(true);
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
        swipeLayout.setRefreshing(true);
        emptyViews();
        setSwellImage();
        setTideText();
    }

    private void emptyViews() {
        ImageView emptyImage = root.findViewById(R.id.image_local_swell_map);
        emptyImage.setImageResource(android.R.color.transparent);
        TextView emptyText = root.findViewById(R.id.bottom_text);
        emptyText.setText("");
    }

    private void setSwellImage() {
        final SwellMapData swellMapData = new SwellMapData(location);
        Glide.with(getActivity())
                .asBitmap()
                .load(swellMapData.getSwellMapUrl(getContext()))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ImageView swellMapImage = root.findViewById(R.id.image_local_swell_map);
                        swellMapImage.setImageBitmap(swellMapData.getCroppedBitmap(resource));
                    }
                });

    }

    private void setTideText() {
        String url = getNowTideUrl();
        Ion.with(getContext())
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            TextView bottomText = root.findViewById(R.id.bottom_text);
                            if (result != null) {
                                JSONObject json = new JSONObject(result);
                                JSONArray dataArray = json.getJSONArray("data");
                                int last = dataArray.length() - 1;
                                JSONObject lastObject = dataArray.getJSONObject(last);
                                String tide = "  TIDE:   "
                                        + lastObject.getString("v")
                                        + "   FT";
                                bottomText.setText(tide);
                                swipeLayout.setRefreshing(false);
                                mProgressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException jsone) {
                            Log.wtf("failed downloading current tidal information", jsone);
                        }
                    }
                });
    }

    private String getNowTideUrl() {
        String url = getContext().getString(R.string.noaa_endpoint_now_tide_start);
        url += getNowTideBuoyId(location, getContext(), sharedPreferences);
        url += getContext().getString(R.string.noaa_endpoint_now_tide_end);
        return url;
    }


}
