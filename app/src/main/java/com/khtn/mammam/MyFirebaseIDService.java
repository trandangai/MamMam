package com.khtn.mammam;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by 11620_000 on 2/1/2017.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        luuTokenVaoCSDLRieng(token);
    }

    private void luuTokenVaoCSDLRieng(String token) {
        new FireBaseIDTask().execute(token);
    }
}
