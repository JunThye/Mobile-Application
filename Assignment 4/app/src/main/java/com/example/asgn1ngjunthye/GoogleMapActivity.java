package com.example.asgn1ngjunthye;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.asgn1ngjunthye.databinding.ActivityGoogleMapBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;
    public String countrySelected;
    public String categoryName;
    Geocoder geocoder;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // currently not able to use because i took out the api key
        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        countrySelected = getIntent().getExtras().getString("country","Australia");
        categoryName = getIntent().getExtras().getString("name","None");
        geocoder = new Geocoder(this, Locale.getDefault());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng sydney = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        findCountryMoveCamera();

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                String msg;
                String selectedCountry = "";

                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() == 0) {
                    msg = "No Country at this location!! Sorry";
                    runOnUiThread(() -> Toast.makeText(GoogleMapActivity.this, msg, Toast.LENGTH_SHORT).show());
                }
                else {
                    android.location.Address address = addresses.get(0);
                    selectedCountry = address.getCountryName();
                    msg = "The selected country is " + selectedCountry;
                    runOnUiThread(() -> Toast.makeText(GoogleMapActivity.this, msg, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
    private void findCountryMoveCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            /**
             * countryToFocus: String value, any string we want to search
             * maxResults: how many results to return if search was successful
             * successCallback method: if results are found, this method will be executed
             *                          runs in a background thread
             */
            geocoder.getFromLocationName(countrySelected, 1, addresses -> {
                if (!addresses.isEmpty()) {
                    runOnUiThread(() -> {
                        LatLng newAddressLocation = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude()
                        );
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));
                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(newAddressLocation)
                                        .title(categoryName)
                        );
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(GoogleMapActivity.this, "Category address not found", Toast.LENGTH_SHORT).show());
                }
            });
        }
    }
}