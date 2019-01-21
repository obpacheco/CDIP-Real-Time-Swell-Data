package com.example.austin.cdiprealtimeswelldata.utilities;

import android.util.TypedValue;

import com.example.austin.cdiprealtimeswelldata.R;

public class GetSwellMapUtil {
    private GetSwellMapUtil(){}

    public static int getSwellMapUrl(String location)
    {
        TypedValue typedValue = new TypedValue();
        if (location.equals("Northern California"))
            return R.string.northern_california_swell_url;
        else if (location.equals("Monterey Bay"))
            return R.string.monterey_swell_url;
        else if (location.equals("Central Coast"))
            return R.string.central_coast_swell_url;
        else if (location.equals("Southern California"))
            return R.string.southern_california_swell_url;

        return -1;

    }
}
