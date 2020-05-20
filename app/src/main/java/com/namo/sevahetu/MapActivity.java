package com.namo.sevahetu;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(5);
        LatLng banglore;
        // Add a marker in Sydney and move the camera
        if(null!=currentLocation){
            banglore = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        }else{
            banglore=new LatLng(28.6466773,76.813073);
        }
        LatLng delhi = new LatLng(28.6466773,76.813073);
//        mMap.addMarker(new MarkerOptions().position(banglore).title("Name:Satyam, Ph:7204594039,Need Help:Food")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
//        mMap.addMarker(new MarkerOptions().position(banglore).title("marker title").snippet("marker info")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_foreground)));


        mMap.addMarker(new MarkerOptions()
                .position(banglore)
                .title("Delhi"));
        mMap.addMarker(new MarkerOptions()
                .position(delhi)
                .title("Banglore"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(banglore));
    }
}
