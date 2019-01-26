package com.example.austin.cdiprealtimeswelldata.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.austin.cdiprealtimeswelldata.R;
import com.squareup.picasso.Picasso;

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

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
    }

}
