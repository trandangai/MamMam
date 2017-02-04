package com.khtn.mammam.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by User on 2/4/2017.
 */

public class DistanceMapUtils {
    public static double GetDistance(LatLng p1, LatLng p2) {

        int R = 6378137;
        double dLat = getRad(p2.latitude - p1.latitude);
        double dLong = getRad(p2.longitude - p1.longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(getRad(p1.latitude)) * Math.cos(getRad(p2.latitude)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }

    public static double getRad(double x) {
        return x * Math.PI / 180;
    }
}
