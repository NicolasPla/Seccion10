package com.example.seccion10.Activities.Fragments;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.seccion10.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {


    private View rootView;
    private GoogleMap gMap;
    private MapView mapView;


    private Geocoder geocoder;
    private List<Address> addresses;

    private MarkerOptions marker;

    public MapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = rootView.findViewById(R.id.map);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        this.checkIfGpsIsEnabled();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.checkIfGpsIsEnabled();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gMap.setMaxZoomPreference(20);
        gMap.setMinZoomPreference(10);

        LatLng place = new LatLng(37.38, -5.9844);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        marker = new MarkerOptions();
        marker.position(place);
        marker.title("Mi marcador");
        marker.draggable(true);
        marker.snippet("Esto es una caja de texto donde modificar los datos");
        marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));

        gMap.addMarker(marker);
        gMap.animateCamera(zoom);
        gMap.addMarker(new MarkerOptions().position(place).title("Marker in sevilla").draggable(true));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(place));

        gMap.setOnMarkerDragListener(this);

        geocoder = new Geocoder(getContext(), Locale.getDefault());
    }

    private void checkIfGpsIsEnabled(){
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == 0) {
                // El GPS no esta Activo
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

        marker.hideInfoWindow();

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;

        try {
            addresses =  geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();

        marker.setSnippet(city + ", " + country);

        marker.showInfoWindow();

        /*marker.setSnippet("address: " + addresses + "\n" +
                "address: " + "address" + "\n" +
                "city: " + "city" + "\n" +
                "state: " + "state" + "\n" +
                "country: " + "country" + "\n" +
                "postalCode: " + postalCode+ "\n");*/
/*
        Toast.makeText(getContext(), "address: " + addresses + "\n" +
                "address: " + "address" + "\n" +
                "city: " + "city" + "\n" +
                "state: " + "state" + "\n" +
                "country: " + "country" + "\n" +
                "postalCode: " + postalCode+ "\n",
                Toast.LENGTH_SHORT).show();*/
    }
}
