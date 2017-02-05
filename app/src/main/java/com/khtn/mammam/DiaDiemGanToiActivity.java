package com.khtn.mammam;

import android.icu.text.DisplayContext;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.khtn.mammam.pojo.Restaurant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DiaDiemGanToiActivity extends AppCompatActivity {

    List<Restaurant> listRest;
    private GenericTypeIndicator<List<Restaurant>> myType;
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_diem_gan_toi);

        listRest = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refRestaurant = database.getReference("restaurants");
        refRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myType = new GenericTypeIndicator<List<Restaurant>>() {
                };
                listRest = dataSnapshot.getValue(myType);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapnearby);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map=googleMap;
            }
        });

    }
}
