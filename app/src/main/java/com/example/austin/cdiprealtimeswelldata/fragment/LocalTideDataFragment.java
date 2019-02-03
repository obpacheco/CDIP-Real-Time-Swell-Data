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
import android.widget.TableRow;
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
        mProgressBar = root.findViewById(R.id.progress_bar);
        mProgressBar.setIndeterminate(true);

        String url = GetTideDataURL();

        Ion.with(getContext())
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            if (result != null) {
                                JSONObject json = new JSONObject(result);
                                JSONArray predictionsArray = json.getJSONArray("predictions");
                                int counter = 0;
                                // counter gets the first index that points to a tide in the future
                                while (!compareTime(predictionsArray, counter)
                                        && counter < predictionsArray.length()) {
                                    counter++;
                                }
                                TextView firstTide1 = root.findViewById(R.id.firstTideCol1);
                                firstTide1.setText(convertTimeToAMPM(predictionsArray, counter - 2));
                                TextView firstTide2 = root.findViewById(R.id.firstTideCol2);
                                firstTide2.setText(getTideString(predictionsArray, counter - 2));
                                TextView firstTide3 = root.findViewById(R.id.firstTideCol3);
                                firstTide3.setText(getTideHighLow(predictionsArray, counter - 2));

                                TextView secondTide1 = root.findViewById(R.id.secondTideCol1);
                                secondTide1.setText(convertTimeToAMPM(predictionsArray, counter - 1));
                                TextView secondTide2 = root.findViewById(R.id.secondTideCol2);
                                secondTide2.setText(getTideString(predictionsArray, counter - 1));
                                TextView secondTide3 = root.findViewById(R.id.secondTideCol3);
                                secondTide3.setText(getTideHighLow(predictionsArray, counter - 1));

                                TextView fourthTide1 = root.findViewById(R.id.fourthTideCol1);
                                fourthTide1.setText(convertTimeToAMPM(predictionsArray, counter));
                                TextView fourthTide2 = root.findViewById(R.id.fourthTideCol2);
                                fourthTide2.setText(getTideString(predictionsArray, counter));
                                TextView fourthTide3 = root.findViewById(R.id.fourthTideCol3);
                                fourthTide3.setText(getTideHighLow(predictionsArray, counter));

                                if (counter + 1 < predictionsArray.length()) {
                                    TextView fifthTide1 = root.findViewById(R.id.fifthTideCol1);
                                    fifthTide1.setText(convertTimeToAMPM(predictionsArray, counter + 1));
                                    TextView fifthTide2 = root.findViewById(R.id.fifthTideCol2);
                                    fifthTide2.setText(getTideString(predictionsArray, counter + 1));
                                    TextView fifthTide3 = root.findViewById(R.id.fifthTideCol3);
                                    fifthTide3.setText(getTideHighLow(predictionsArray, counter + 1));
                                }
                                TextView currentTide1 = root.findViewById(R.id.thirdTideCol1);
                                currentTide1.setGravity(Gravity.RIGHT);

                                mProgressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException jsone){
                            Log.wtf("failed downloading tidal information", jsone);
                        }
                    }
                });

        Ion.with(getContext())
                .load(getNowTideUrl())
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            if (result != null) {
                                JSONObject json = new JSONObject(result);
                                JSONArray dataArray = json.getJSONArray("data");
                                int last = dataArray.length() - 1;
                                TextView currentTide1 = root.findViewById(R.id.thirdTideCol1);
                                currentTide1.setText(convertTimeToAMPM(dataArray, last));
                                TextView currentTide2 = root.findViewById(R.id.thirdTideCol2);
                                currentTide2.setText(getTideString(dataArray, last));
                                TextView currentTide3 = root.findViewById(R.id.thirdTideCol3);
                                currentTide3.setText(getString(R.string.last_observed));
                                TableRow highLightRow = root.findViewById(R.id.thirdTideCol);
                                highLightRow.setBackgroundColor(getResources().getColor(R.color.highlight_trueblack));
                            }

                        } catch (JSONException jsone){
                            Log.wtf("failed downloading current tidal information", jsone);
                        }
                    }
                });

        return root;
    }


    private String getTideString(JSONArray array, int index) {
        String tide = "";
        try {
            if (index <= array.length()) {
                JSONObject nowTide = array.getJSONObject(index);
                tide = nowTide.getString("v")
                        + " ft";
            }
        } catch (JSONException jsone){
            Log.wtf("failed downloading tidal information", jsone);
        }
        return tide;
    }


    private String getTideHighLow(JSONArray array, int index) {
        String highLow = "";
        try {
            if (index <= array.length()) {
                JSONObject nowTide = array.getJSONObject(index);
                highLow = nowTide.getString("type");
            }
        } catch (JSONException jsone){
            Log.wtf("failed downloading tidal information", jsone);
        }
        return highLow;
    }

    private String convertTimeToAMPM(JSONArray jsonArray, int index)
    {
        String time = "";
        try {
            JSONObject tideData = jsonArray.getJSONObject(index);
            time = tideData.getString("t");
            time = time.substring(11);
            int hour = Integer.parseInt(time.substring(0,2));
            time = time.substring(2);
            Boolean isAM = Boolean.TRUE;
            if (hour >= 12) {
                isAM = Boolean.FALSE;
                if (hour > 12) {
                    hour -= 12;
                }
            }
            if (isAM) {
                time = hour + time + " AM";
            } else {
                time = hour + time + " PM";
            }
        } catch (JSONException jsone){
            Log.wtf("failed downloading tidal information", jsone);
        }
        return time;
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
                    if (tide_hour > hour)
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


    private String GetTideDataURL(){
        String url = "Error Finding Tide URL";
        if (location.equals("Northern California"))
            url = getContext().getString(R.string.northern_california_tide_url);
        else if (location.equals("Monterey Bay"))
            url = getContext().getString(R.string.monterey_tide_url);
        else if (location.equals("Central Coast"))
            url = getContext().getString(R.string.central_coast_tide_url);
        else if (location.equals("Southern California"))
            url = getContext().getString(R.string.southern_california_tide_url);
        return url;
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
