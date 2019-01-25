package com.example.austin.cdiprealtimeswelldata.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.austin.cdiprealtimeswelldata.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;
import static com.example.austin.cdiprealtimeswelldata.utilities.GetSwellMapUtil.getSwellMapUrl;


public class LocalTideDataFragment extends Fragment {

    private View root;
    private String location;

    public static LocalTideDataFragment newInstance(String location) {
        LocalTideDataFragment localTideDataFragment = new LocalTideDataFragment();
        localTideDataFragment.location = location;

        return localTideDataFragment;
    }


    public LocalTideDataFragment() {
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
        root = inflater.inflate(R.layout.local_tide_data_fragment, container, false);
        final LinearLayout linearLayout1 = root.findViewById(R.id.linearLayout1);
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
                            for ( int i = 0; i < 240; i+=5 ) {
                                TextView tide = new TextView(getContext());
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                params.weight = 1.0f;
                                tide.setLayoutParams(params);
                                //tide.setGravity(Gravity.CENTER);
                                tide.setText(getTideString(predictionsArray.getJSONObject(i)));
                                tide.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                                tide.setTextColor(Color.WHITE);
                                linearLayout1.addView(tide);
                            }
                        } catch (JSONException jsone){
                            Log.wtf("failed downloading tidal information", jsone);
                        }
                    }
                });
        return root;
    }

    private String getTideString(JSONObject object)
    {
        String tide = "";
        try{
            tide = object.getString("t") + "            " + object.getString("v");
        } catch (JSONException jsone){
            Log.wtf("failed downloading tidal information", jsone);
        }
        return tide.substring(0,11) + "           " + tide.substring(11);
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
            final JSONArray[] jsonPredictions = new JSONArray[1];
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
