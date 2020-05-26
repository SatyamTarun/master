package com.namo.sevahetu;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LaunchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FusedLocationProviderClient fusedLocationClient;
    private double wayLatitude=0,wayLongitude=0;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private boolean isGPS;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.center_action_bar_title);
        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        Spinner stateSpinner = (Spinner) findViewById(R.id.state_spinner);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.state_array, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        Spinner requirmentSpinner = (Spinner) findViewById(R.id.requirment_spinner);
        ArrayAdapter<CharSequence> requirmentAdapter = ArrayAdapter.createFromResource(this,
                R.array.requirment_array, android.R.layout.simple_spinner_item);
        requirmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requirmentSpinner.setAdapter(requirmentAdapter);
        stateSpinner.setOnItemSelectedListener(this);
        requirmentSpinner.setOnItemSelectedListener(this);

        Button fab = findViewById(R.id.btn_register);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(LaunchActivity.this, MapActivity.class);
                startActivity(mIntent);
            }
        });
        addFirstData();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    private void addFirstData() {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Satyam");
        user.put("last", "Sharma");
        user.put("born", 1989);
        user.put("u_id", Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));

// Add a new document with a given ID
        db.collection("users").document(Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID))
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Document Added", "DocumentSnapshot added with ID: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Not Added", "Error adding document", e);
                    }
                });
    }
}
