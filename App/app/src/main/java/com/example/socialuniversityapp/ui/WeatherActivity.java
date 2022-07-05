package com.example.socialuniversityapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.socialuniversityapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class WeatherActivity extends Fragment {

    private static final String API_KEY = "9fbf399d64f1d9eee0ec07446b12a2de";
    private static final String TAG = WeatherActivity.class.getSimpleName();


    private ImageView mImgIcon;
    private TextView mCity, mTemp, mMinTemp, mMaxTemp, mDescriptions;
    private ProgressBar mProgressBar;


    private FusedLocationProviderClient mFusedLocationClient;

    private String longValue, latValue;
    private double latitude, longitude;
    private int PERMISSION_ID = 44;

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            longValue = mLastLocation.getLongitude() + "";
            latValue = mLastLocation.getLatitude() + "";
            Log.i(TAG, "long :" + longValue + " Lat : "+ latValue);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_weather, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inflate
        mImgIcon = view.findViewById(R.id.iconWeather);
        mCity = view.findViewById(R.id.tvCity);
        mTemp = view.findViewById(R.id.tvTemp);
        mMinTemp = view.findViewById(R.id.minTemp);
        mMaxTemp = view.findViewById(R.id.maxTemp);
        mDescriptions = view.findViewById(R.id.description);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        Navigation.findNavController(view)
                .getCurrentDestination().setLabel("k");


        // Get Location
        getLastLocation();

        Log.i(TAG, "location " + longValue + "  " + latitude);


    }



    private void loadWeatherByCityName(double longitude,double latitude) {
        Log.i(TAG,longitude+"");
        Log.i(TAG,latitude+"");
        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=9fbf399d64f1d9eee0ec07446b12a2de")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        if (e != null) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        } else {
                            //convert jason response to java
                            JsonObject main = result.get("main").getAsJsonObject();
                            //temp
                            int temps = (int) main.get("temp").getAsDouble();
                            int temp = temps - 273;
                            mTemp.setText(temp + " ْ°C");

                            //maxTemp
                            int maxTemps = (int) main.get("temp_max").getAsDouble();
                            int maxTemp = maxTemps - 273;
                            mMaxTemp.setText("Max Temp:  " + maxTemp + " ْ°C");

                            //minTemp
                            int minTemps = (int) main.get("temp_min").getAsDouble();
                            int minTemp = minTemps - 273;
                            mMinTemp.setText("Min Temp:  " + minTemp + " ْ°C");

                            JsonObject sys = result.get("sys").getAsJsonObject();
                            String country = sys.get("country").getAsString();
                            String city = result.get("name").getAsString();
                            mCity.setText( city + " ' "+country);

                            // but for brevity, use the ImageView specific builder...
                            JsonArray weather = result.get("weather").getAsJsonArray();
                            String icon = weather.get(0).getAsJsonObject().get("icon").getAsString();
                            loadIcon(icon);

                            String desc = weather.get(0).getAsJsonObject().get("description").getAsString();
                            mDescriptions.setText(desc);

                            mProgressBar.setVisibility(View.INVISIBLE);

                        }
                    }


                });

    }

    private void loadIcon(String icon) {
        Ion.with(this)
                .load("http://openweathermap.org/img/w/" + icon + ".png")
                .intoImageView(mImgIcon);
    }


//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//
//    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {
            Log.i(TAG, "permisson");

            // check if location is enabled
            if (isLocationEnabled()) {
                Log.i(TAG, "location enable");

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient = LocationServices. getFusedLocationProviderClient(getActivity());

                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.i(TAG, "fused ");

                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.i(TAG, "latitude: " + latitude + " longitude " + longitude);
                            loadWeatherByCityName(longitude,latitude);


                        }
                    }


                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error trying to get last GPS location");
                                e.printStackTrace();
                            }
                        });


            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }

    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1);
        mLocationRequest.setFastestInterval(0);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());

        settingsClient.checkLocationSettings(locationSettingsRequest);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                getLastLocation();
            }
        }, Looper.myLooper());
    }


    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat
                .checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat
                        .checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    // If everything is alright then
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

}
