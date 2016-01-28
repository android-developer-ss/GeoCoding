package com.svs.myprojects.locationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static  LatLng CURRENT;// = new LatLng(53.558, 9.927);
    static final LatLng RJT = new LatLng(41.9201113, -88.26488971);
    private GoogleMap map;

    MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 53.558);
        double lng = intent.getDoubleExtra("lng", 9.927);
        CURRENT = new LatLng(lat, lng);
//        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));

//        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
//                .getMap();
//        Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
//                .title("Hamburg"));
//        Marker kiel = map.addMarker(new MarkerOptions()
//                .position(KIEL)
//                .title("Kiel")
//                .snippet("Kiel is cool")
//                .icon(BitmapDescriptorFactory
//                        .fromResource(R.mipmap.ic_launcher)));
//
//        // Move the camera instantly to hamburg with a zoom of 15.
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
//
//        // Zoom in, animating the camera.
//        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

//        mMapFragment = MapFragment.newInstance();
//        FragmentTransaction fragmentTransaction =
//                getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.map, mMapFragment);
//        fragmentTransaction.commit();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions().position(CURRENT)
                .title("Current Location"));
        googleMap.addMarker(new MarkerOptions()
                .position(RJT)
                .title("RJT Office")
                .snippet("RJT Android is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_launcher)));

        // Move the camera instantly to hamburg with a zoom of 15.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RJT, 15));

        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


    }
}
