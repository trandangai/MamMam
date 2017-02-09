package com.khtn.mammam;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DisplayContext;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.khtn.mammam.pojo.Restaurant;
import com.khtn.mammam.utils.DistanceMapUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DiaDiemGanToiActivity extends AppCompatActivity implements LocationListener, GoogleMap.OnInfoWindowClickListener {

    List<Restaurant> listRest;
    private GenericTypeIndicator<List<Restaurant>> myType;
    private GoogleMap map;
    private int num = 0;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GotoMyCurrentLocation();
                CacDiaDiemXungQuanh();
            }
        }, 2000);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapnearby);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                map.getUiSettings().setZoomControlsEnabled(true);
                if (ActivityCompat.checkSelfPermission(DiaDiemGanToiActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(DiaDiemGanToiActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DiaDiemGanToiActivity.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 88);
                }
                map.setMyLocationEnabled(true);
                map.setOnInfoWindowClickListener(DiaDiemGanToiActivity.this);
            }
        });

    }

    private void GotoMyCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(DiaDiemGanToiActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            int Request_Code = 88;
            ActivityCompat.requestPermissions(DiaDiemGanToiActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code);
        }

        LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1000 * 60 * 1, DiaDiemGanToiActivity.this);
        Criteria criteria = new Criteria();
        Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (lastLocation != null) {
            LatLng CurrentPosition = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            this.currentLocation = CurrentPosition;
            MarkerOptions option = new MarkerOptions();
            option.position(CurrentPosition);
            option.title("Vị trí của bạn hiện tại").snippet("MamMam App");
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            option.alpha(0.8f);
            option.rotation(0);
            map.clear();
            Marker maker = map.addMarker(option);
            maker.showInfoWindow();
            if (num == 0) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentPosition, 14));
                num = 1;
            }
        } else {
            Toast.makeText(DiaDiemGanToiActivity.this, "null location", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        GotoMyCurrentLocation();
//        CacDiaDiemXungQuanh();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void CacDiaDiemXungQuanh() {
        ArrayList<Restaurant> listNearby = new ArrayList<>();
        for (Restaurant restaurant : listRest) {
            if (restaurant.getLatitude() != null && restaurant.getLongitude() != null) {
                LatLng latlng = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
                if (DistanceMapUtils.GetDistance(currentLocation, latlng) <= 2000) {
                    listNearby.add(restaurant);
                }
            }
        }

        for (Restaurant restaurant : listNearby) {
            MarkerOptions option = new MarkerOptions();
            option.position(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()));
            option.title(restaurant.getRestName()).snippet(restaurant.getRestAddr());
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            option.alpha(0.8f);
            option.rotation(0);
            Marker maker = map.addMarker(option);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Restaurant restaurant = null;
        int vitri =0;
        for (int i = 0; i < listRest.size(); i++) {
            if (listRest.get(i).getRestName().equals(marker.getTitle())) {
                restaurant = listRest.get(i);
                vitri = i;
                break;
            }
        }
        if (restaurant != null) {
            Intent intent = new Intent(DiaDiemGanToiActivity.this, DiaDiem_Details_Activity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("resttrans", restaurant);
            intent.putExtra("bundlerest", bundle);
            intent.putExtra("restId",vitri);
            startActivity(intent);
        } else {
            Toast.makeText(DiaDiemGanToiActivity.this, "NULL REST", Toast.LENGTH_LONG).show();
        }
    }
}
