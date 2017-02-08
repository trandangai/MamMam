package com.khtn.mammam.utils;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sayuri Kurata on 2017/02/08.
 */

public class FirebaseUtil {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
