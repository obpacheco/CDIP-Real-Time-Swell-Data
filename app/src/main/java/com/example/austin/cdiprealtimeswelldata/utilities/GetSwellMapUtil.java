package com.example.austin.cdiprealtimeswelldata.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.example.austin.cdiprealtimeswelldata.R;

public class GetSwellMapUtil {
    private GetSwellMapUtil(){}

    public static String getSwellMapUrl(Context context, String location)
    {
        String url = "Error location doesn't equal a resource";
        if (location.equals("Northern California"))
            url = context.getString(R.string.northern_california_swell_url);
        else if (location.equals("Monterey Bay"))
            url = context.getString(R.string.monterey_swell_url);
        else if (location.equals("Central Coast"))
            url = context.getString(R.string.central_coast_swell_url);
        else if (location.equals("Southern California"))
            url = context.getString(R.string.southern_california_swell_url);

        // currentTimeMillis adds the time to the string
        // forces the url to refresh the imageview every time its called
        return url + "?=" + System.currentTimeMillis();

    }
}
