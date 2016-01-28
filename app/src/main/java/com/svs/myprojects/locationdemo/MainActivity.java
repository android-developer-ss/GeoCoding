package com.svs.myprojects.locationdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener, AddressToCoorInterface, CoordinateToAddressInterface {

    String stringProvider;
    LocationManager locationManager;
    Location location;
    TextView textProvider, textLatitude, textLongitude, textLatLong, textAddress;
    double latitude, longitude;
    EditText editAddress, editLongitude, editLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textProvider = (TextView) findViewById(R.id.text_provider);
        textLatitude = (TextView) findViewById(R.id.text_latitude);
        textLongitude = (TextView) findViewById(R.id.text_longitude);
        textLatLong = (TextView) findViewById(R.id.text_lat_long);
        textAddress = (TextView) findViewById(R.id.text_address);

        editAddress = (EditText) findViewById(R.id.edit_address);
        editLatitude = (EditText) findViewById(R.id.edit_latitude);
        editLongitude = (EditText) findViewById(R.id.edit_longitude);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Define the criteria how to select the locatioin provider -> use default
        Criteria criteria = new Criteria();
        stringProvider = locationManager.getBestProvider(criteria, enabled);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(MainActivity.this, "Check Permissions", Toast.LENGTH_LONG).show();
            return;
        }

        location = locationManager.getLastKnownLocation(stringProvider);
        if (location != null) {
            onLocationChanged(location);
        }
//        textProvider.setText(stringProvider);
//        textLatitude.setText((String)location.getLatitude());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(MainActivity.this, "Check Permissions", Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.requestLocationUpdates(stringProvider, 400, 1, MainActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(MainActivity.this, "Check Permissions", Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.removeUpdates(MainActivity.this);
    }

    @Override
    public void onLocationChanged(Location location) {
//        int latitude = (int) location.getLatitude();
//        int longitude = (int) location.getLongitude();
        textProvider.setText(stringProvider.toUpperCase());
//        textLatitude = (TextView) findViewById(R.id.text_latitude);
//        textLongitude = (TextView) findViewById(R.id.text_longitude);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        textLatitude.setText(String.valueOf(location.getLatitude()));
        textLongitude.setText(String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    public void open_maps_function(View view) {
//        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        String uriString = "geo:" + latitude + "," + longitude;
        Uri gmmIntentUri = Uri.parse(uriString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void open_custom_maps_function(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("lat",latitude);
        intent.putExtra("lng",longitude);
        startActivity(intent);
    }

    public void get_coordinates_in_async_task(View view) {
        GeoCodingAsyncTask geoCodingAsyncTask = new GeoCodingAsyncTask(Constants.ADDRESS_TO_COORDINATES, MainActivity.this);
        geoCodingAsyncTask.execute(editAddress.getText().toString());
    }

    @Override
    public void setCoordinateText(String str) {
        textLatLong.setText(str);
    }

    @Override
    public void setTextAddress(String string) {
        textAddress.setText(string);
    }

    public void get_address_in_async_task(View view) {
        GeoCodingAsyncTask geoCodingAsyncTask = new GeoCodingAsyncTask(Constants.COORDINATES_TO_ADDRESS, MainActivity.this);
        geoCodingAsyncTask.execute(editLatitude.getText().toString() + "," + editLongitude.getText().toString());
    }
}
