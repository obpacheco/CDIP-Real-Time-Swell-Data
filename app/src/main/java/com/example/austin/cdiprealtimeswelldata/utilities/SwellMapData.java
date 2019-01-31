package com.example.austin.cdiprealtimeswelldata.utilities;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.austin.cdiprealtimeswelldata.R;

public class SwellMapData {

    private final Location location;

    enum Location {
        NORTHERN_CALIFORNIA, MONTEREY, CENTRAL_COAST, SOUTHERN_CALIFORNIA
    }

    private SwellMapData() {
        this.location = Location.NORTHERN_CALIFORNIA;
    }

    public SwellMapData(String p_location) {

        if (p_location.equals("Northern California"))
            location = Location.NORTHERN_CALIFORNIA;
        else if (p_location.equals("Monterey Bay"))
            location = Location.MONTEREY;
        else if (p_location.equals("Central Coast"))
            location = Location.CENTRAL_COAST;
        else if (p_location.equals("Southern California"))
            location = Location.SOUTHERN_CALIFORNIA;
        else
            location = Location.NORTHERN_CALIFORNIA;

    }

    public String getSwellMapUrl(Context context) {
        String url = "Error location doesn't equal a resource";
        switch (location){
            case NORTHERN_CALIFORNIA:
                url = context.getString(R.string.northern_california_swell_url);
                break;
            case MONTEREY:
                url = context.getString(R.string.monterey_swell_url);
                break;
            case CENTRAL_COAST:
                url = context.getString(R.string.central_coast_swell_url);
                break;
            case SOUTHERN_CALIFORNIA:
                url = context.getString(R.string.southern_california_swell_url);
                break;
        }
        // currentTimeMillis adds the time to the string
        // forces the url to refresh the imageview every time its called
        return url + "?=" + System.currentTimeMillis();
    }

    public Bitmap getCroppedBitmap (Bitmap uncroppedBitmap){
        int x_start, y_start, width, height;
        switch (location){
            case NORTHERN_CALIFORNIA:
                x_start = 101;
                y_start = 138;
                width = 459;
                height = 625;
                break;

            case MONTEREY:
                x_start = 104;
                y_start = 106;
                width = 469;
                height = 576;
                break;

            case CENTRAL_COAST:
                x_start = 72;
                y_start = 83;
                width = 330;
                height = 585;
                break;

            case SOUTHERN_CALIFORNIA:
                x_start = 53;
                y_start = 140;
                width = 685;
                height = 635;
                break;

            default:
                x_start = 0;
                y_start = 0;
                width = uncroppedBitmap.getWidth();
                height = uncroppedBitmap.getHeight();
                break;
        }

        // making sure that the arguments dont throw IllegalArgumentException: x + width must be <= bitmap.width()
        if (x_start + width > uncroppedBitmap.getWidth()) {
            width = uncroppedBitmap.getWidth() - x_start;
        }
        if (y_start + height > uncroppedBitmap.getHeight()) {
            height = uncroppedBitmap.getHeight() - y_start;
        }


        Bitmap croppedBitmap = Bitmap.createBitmap(uncroppedBitmap, x_start, y_start, width, height);
        return croppedBitmap;
    }
}
