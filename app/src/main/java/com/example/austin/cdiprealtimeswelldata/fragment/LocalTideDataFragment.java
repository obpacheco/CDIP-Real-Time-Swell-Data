package com.example.austin.cdiprealtimeswelldata.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.austin.cdiprealtimeswelldata.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class LocalTideDataFragment extends Fragment {

    private View root;
    private String location;
    private ProgressBar mProgressBar;
    private final int TIME_START;
    private final int TIME_END;

    public static LocalTideDataFragment newInstance(String location) {
        LocalTideDataFragment localTideDataFragment = new LocalTideDataFragment();
        localTideDataFragment.location = location;

        return localTideDataFragment;
    }


    public LocalTideDataFragment() {
        TIME_START = 6;
        TIME_END = 20;
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
        root = inflater.inflate(R.layout.local_tide_data_fragment, container, false);
        final LinearLayout linearLayout1 = root.findViewById(R.id.linearLayout1);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mProgressBar.setIndeterminate(true);

        GetTideDataUtil getTideDataUtil = new GetTideDataUtil();
        String url = getTideDataUtil.GetTideDataURL(getContext());

        Ion.with(getContext())
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject json = new JSONObject(result);
                            JSONArray predictionsArray = json.getJSONArray("predictions");

                            TextView date = new TextView(getContext());
                            date.setText(getTideDate(predictionsArray.getJSONObject(0)));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            params.weight = 1.0f;
                            date.setTextColor(Color.WHITE);
                            date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
                            linearLayout1.addView(date);
                            TextView blankLine = new TextView(getContext());
                            linearLayout1.addView(blankLine);
                            TextView units = new TextView(getContext());
                            String unitString = "    "
                                    + "TIME"
                                    + "        "
                                    + "FT"
                                    + "            "
                                    + "H/L";
                            units.setText(unitString);
                            units.setTextColor(Color.WHITE);
                            units.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
                            linearLayout1.addView(units);

                            Calendar calendar = Calendar.getInstance();
                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            boolean highlight = true;
                            int previousTidesHour = 0;

                            for (int i = 0; i < predictionsArray.length(); i++ ) {
                                TextView tide = new TextView(getContext());
                                tide.setLayoutParams(params);
                                String tideText = getTideString(predictionsArray, i);
                                tide.setText(tideText);
                                tide.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
                                tide.setTextColor(Color.WHITE);
                                int tidesHour = Integer.parseInt(tideText.substring(4,6));
                                if (highlight && hour <= tidesHour || highlight && previousTidesHour > tidesHour){
                                    tide.setBackgroundColor(getResources().getColor(R.color.highlight_trueblack));
                                    highlight = false;
                                }
                                previousTidesHour = tidesHour;
                                linearLayout1.addView(tide);
                            }
                            mProgressBar.setVisibility(View.GONE);
                        } catch (JSONException jsone){
                            Log.wtf("failed downloading tidal information", jsone);
                        }
                    }
                });
        return root;
    }

    private String getTideString(JSONArray array, int index)
    {
        String tide = "";
        try{
            JSONObject nowTide = array.getJSONObject(index);
            tide = nowTide.getString("t")
                    + "       "
                    + nowTide.getString("v")
                    + "       "
                    + nowTide.getString("type");

            // this is for whether the tide is rising or falling
        } catch (JSONException jsone){
            Log.wtf("failed downloading tidal information", jsone);
        }
        return "    " + tide.substring(11);
    }

    private String getTideDate(JSONObject object)
    {
        String objectsDate = "";
        try {
            objectsDate = object.getString("t");
        } catch (JSONException jsone){
            Log.wtf("failed downloading tidal information", jsone);
        }
        return "    DATE:    " + objectsDate.substring(0,10);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
    }

    private class GetTideDataUtil {

        private GetTideDataUtil(){}

        private String GetTideDataURL(Context context){
            String url = "Error Finding Tide URL";
            if (location.equals("Northern California"))
                url = context.getString(R.string.northern_california_tide_url);
            else if (location.equals("Monterey Bay"))
                url = context.getString(R.string.monterey_tide_url);
            else if (location.equals("Central Coast"))
                url = context.getString(R.string.central_coast_tide_url);
            else if (location.equals("Southern California"))
                url = context.getString(R.string.southern_california_tide_url);

            return url;
        }
    }


}
