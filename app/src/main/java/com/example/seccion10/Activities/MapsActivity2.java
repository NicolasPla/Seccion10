package com.example.seccion10.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.seccion10.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /* Limita zoom in/out*/
        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(15);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng sevilla = new LatLng(37.409, -5.990);

        mMap.addMarker(new MarkerOptions().position(sevilla).title("Marker in Sevilla!").draggable(true));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));

        CameraPosition camera = new CameraPosition.Builder()
                .target(sevilla)
                .zoom(15) // Zoom maximo -> 20
                .bearing(0) // 0 -365º Rotacion 90-> Este
                .tilt(30) // Agulo de a camara, muestra estructuras 3D
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

        // Eventos en el mapa
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(MapsActivity2.this, "OnClick: \n"
                        + "Lat: " + latLng.latitude + "\n"
                        + "Lon " + latLng.longitude , Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(MapsActivity2.this, "LongClick: \n"
                        + "Lat: " + latLng.latitude + "\n"
                        + "Lon " + latLng.longitude , Toast.LENGTH_SHORT).show();

            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Toast.makeText(MapsActivity2.this, "DragStart: \n"
                        + "Lat: " + marker.getPosition().latitude + "\n"
                        + "Lon " + marker.getPosition().longitude , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Toast.makeText(MapsActivity2.this, "Drag: \n"
                        + "Lat: " + marker.getPosition().latitude + "\n"
                        + "Lon " + marker.getPosition().longitude , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(MapsActivity2.this, "DragEnd: \n"
                        + "Lat: " + marker.getPosition().latitude + "\n"
                        + "Lon " + marker.getPosition().longitude , Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

}
