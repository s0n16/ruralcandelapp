package com.example.ruralcandelappf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class Contacto extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    double latitud = 39.399944;
    double longitud = -5.408922;
    float zoomLevel = 10.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng arc = new LatLng(latitud, longitud);
        mMap.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(arc).title("Candelario"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(arc));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(arc)
                .zoom(zoomLevel)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}