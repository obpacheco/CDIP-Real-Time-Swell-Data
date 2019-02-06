package com.example.austin.cdiprealtimeswelldata.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.austin.cdiprealtimeswelldata.R;

public class BuoyIdGetter {

    public static String getTideBuoyId(String location, Context context, SharedPreferences sharedPreferences) {
        if (location.equals("Northern California")) {
            switch (sharedPreferences.getInt("buoy_northern_california", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_san_francisco);
                case 1:
                    return context.getString(R.string.noaa_buoy_arena_cove);
                case 2:
                    return context.getString(R.string.noaa_buoy_bolinas);
            }
        } else if (location.equals("Monterey Bay")) {
            switch (sharedPreferences.getInt("buoy_monterey", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_monterey);
            }
        } else if (location.equals("Central Coast")) {
            switch (sharedPreferences.getInt("buoy_central_coast", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_port_san_luis);
                case 1:
                    return context.getString(R.string.noaa_buoy_pt_conception);
            }
        } else if (location.equals("Southern California")) {
            switch (sharedPreferences.getInt("buoy_southern_california", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_santa_monica);
                case 1:
                    return context.getString(R.string.noaa_buoy_santa_barbara);
                case 2:
                    return context.getString(R.string.noaa_buoy_long_beach);
                case 3:
                    return context.getString(R.string.noaa_buoy_la_jolla);
            }
        }
        return "";
    }

    public static String getWindBuoyId(String location, Context context, SharedPreferences sharedPreferences) {
        if (location.equals("Northern California")) {
            switch (sharedPreferences.getInt("buoy_northern_california", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_san_francisco);
                case 1:
                    return context.getString(R.string.noaa_buoy_arena_cove);
                case 2:
                    return context.getString(R.string.noaa_buoy_bolinas);
            }
        } else if (location.equals("Monterey Bay")) {
            switch (sharedPreferences.getInt("buoy_monterey", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_monterey);
            }
        } else if (location.equals("Central Coast")) {
            switch (sharedPreferences.getInt("buoy_central_coast", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_port_san_luis);
                case 1:
                    return context.getString(R.string.noaa_buoy_pt_conception);
            }
        } else if (location.equals("Southern California")) {
            switch (sharedPreferences.getInt("buoy_southern_california", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_santa_monica);
                case 1:
                    return context.getString(R.string.noaa_buoy_santa_barbara);
                case 2:
                    return context.getString(R.string.noaa_buoy_long_beach_wind);
                case 3:
                    return context.getString(R.string.noaa_buoy_la_jolla);
            }
        }
        return "";
    }

    public static String getNowTideBuoyId(String location, Context context, SharedPreferences sharedPreferences) {
        if (location.equals("Northern California")) {
            switch (sharedPreferences.getInt("buoy_northern_california", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_san_francisco);
                case 1:
                    return context.getString(R.string.noaa_buoy_arena_cove);
                case 2:
                    return context.getString(R.string.noaa_buoy_san_francisco);
            }
        } else if (location.equals("Monterey Bay")) {
            switch (sharedPreferences.getInt("buoy_monterey", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_monterey);
            }
        } else if (location.equals("Central Coast")) {
            switch (sharedPreferences.getInt("buoy_central_coast", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_port_san_luis);
                case 1:
                    return context.getString(R.string.noaa_buoy_pt_conception);
            }
        } else if (location.equals("Southern California")) {
            switch (sharedPreferences.getInt("buoy_southern_california", 0)) {
                case 0:
                    return context.getString(R.string.noaa_buoy_santa_monica);
                case 1:
                    return context.getString(R.string.noaa_buoy_santa_barbara);
                case 2:
                    return context.getString(R.string.noaa_buoy_long_beach);
                case 3:
                    return context.getString(R.string.noaa_buoy_la_jolla);
            }
        }
        return "";
    }



}
