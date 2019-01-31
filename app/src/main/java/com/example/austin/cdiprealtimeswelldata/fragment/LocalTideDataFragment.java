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

    public static LocalTideDataFragment newInstance(String location) {
        LocalTideDataFragment localTideDataFragment = new LocalTideDataFragment();
        localTideDataFragment.location = location;

        return localTideDataFragment;
    }


    public LocalTideDataFragment() { }

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
                            TextView test = new TextView(getContext());
                            JSONObject testObject = predictionsArray.getJSONObject(0);
                            String testDate = testObject.getString("t");
                            String monthS = testDate.substring(5,7);
                            String dayS = testDate.substring(8,10);
                            String hourS = testDate.substring(11,13);
                            String minuteS = testDate.substring(14,16);

                            String sampleDate = monthS + "/"
                                    + dayS + "/"
                                    + hourS + "/"
                                    + minuteS;
                            test.setLayoutParams(params);
                            test.setText(sampleDate);
                            test.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
                            test.setTextColor(Color.WHITE);
                            linearLayout1.addView(test);

                            TextView test2 = new TextView(getContext());
                            int monthS2 = calendar.get(Calendar.MONTH) + 1;
                            int dayS2 = calendar.get(Calendar.DAY_OF_MONTH);
                            int hourS2 = calendar.get(Calendar.HOUR_OF_DAY);
                            int minuteS2 = calendar.get(Calendar.MINUTE);

                            String sampleDate2 = monthS2 + "/"
                                    + dayS2 + "/"
                                    + hourS2 + "/"
                                    + minuteS2;
                            test2.setLayoutParams(params);
                            test2.setText(sampleDate2);
                            test2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
                            test2.setTextColor(Color.WHITE);
                            linearLayout1.addView(test2);


                            for (int i = 0; i < predictionsArray.length(); i++ ) {
                                TextView tide = new TextView(getContext());
                                tide.setLayoutParams(params);
                                String tideText = getTideString(predictionsArray, i);
                                if (compareTime(predictionsArray, i))
                                    tideText += "  true";
                                else
                                    tideText += "  false";
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

    private String getTideString(JSONArray array, int index) {
        String tide = "";
        try {
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

    private String getTideDate(JSONObject object) {
        String objectsDate = "";
        try {
            objectsDate = object.getString("t");
        } catch (JSONException jsone){
            Log.wtf("failed downloading tidal information", jsone);
        }
        return "    DATE:    " + objectsDate.substring(0,10);
    }

    // returns false if tidal time before current time
    private boolean compareTime(JSONArray array, int index) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        try {
            JSONObject testObject = array.getJSONObject(index);
            String testDate = testObject.getString("t");
            int tide_month = Integer.parseInt(testDate.substring(5, 7));
            int tide_day = Integer.parseInt(testDate.substring(8, 10));
            int tide_hour = Integer.parseInt(testDate.substring(11, 13));
            int tide_minute = Integer.parseInt(testDate.substring(14, 16));
            if (tide_month > month)
                return true;
            else if (tide_month == month) {
                if (tide_day > day)
                    return true;
                else if (tide_day == day) {
                    if (tide_hour >= hour)
                        return true;
                    else if (tide_hour == hour)
                        if (tide_minute >= minute)
                            return true;
                }
            }
            return false;
        } catch (JSONException jsone) {
            Log.wtf("failed downloading tidal information", jsone);
        }
        return false;
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
