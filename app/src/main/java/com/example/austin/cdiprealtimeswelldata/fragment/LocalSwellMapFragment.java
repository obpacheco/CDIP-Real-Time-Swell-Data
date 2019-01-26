package com.example.austin.cdiprealtimeswelldata.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.austin.cdiprealtimeswelldata.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.example.austin.cdiprealtimeswelldata.utilities.GetSwellMapUtil.getSwellMapUrl;


public class LocalSwellMapFragment extends Fragment {


    private View root;
    private String location;
    private ImageView swellMapImage;
    private ProgressBar mProgressBar;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        root = inflater.inflate(R.layout.swell_map_fragment, container, false);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mProgressBar.setIndeterminate(true);

        swellMapImage = root.findViewById(R.id.image_local_swell_map);
        Picasso.get().load(getSwellMapUrl(getContext(), location)).into(swellMapImage);

        String url = getNowTideUrl();
        final TextView bottomText = root.findViewById(R.id.bottom_text);
        bottomText.setTextColor(Color.WHITE);
        bottomText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        Ion.with(getContext())
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject json = new JSONObject(result);
                            JSONArray dataArray = json.getJSONArray("data");
                            int last = dataArray.length() - 1;
                            JSONObject lastObject = dataArray.getJSONObject(last);
                            String tide = "  TIDE:   "
                                    + lastObject.getString("v")
                                    + "   FT"
                                    + "   AT   ";
                            String time = lastObject.getString("t");
                            tide += time.substring(11);


                            bottomText.setText(tide);
                        } catch (JSONException jsone){
                            Log.wtf("failed downloading current tidal information", jsone);
                        }
                    }
                });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
    }

    private String getNowTideUrl() {
        String url = "Error Finding Tide URL";
        if (location.equals("Northern California"))
            url = getContext().getString(R.string.northern_california_now_tide_url);
        else if (location.equals("Monterey Bay"))
            url = getContext().getString(R.string.monterey_tide_now_tide_url);
        else if (location.equals("Central Coast"))
            url = getContext().getString(R.string.central_coast_now_tide_url);
        else if (location.equals("Southern California"))
            url = getContext().getString(R.string.southern_california_now_tide_url);
        return url;
    }


}
