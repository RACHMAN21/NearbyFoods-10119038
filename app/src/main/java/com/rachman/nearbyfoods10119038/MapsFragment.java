package com.rachman.nearbyfoods10119038;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
/*
 * Nama  : Rachman Aldiansyah
 * NIM   : 10119038
 * Kelas : IF-1
 */
public class MapsFragment extends Fragment {
    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng wrgCazerbu = new LatLng(-6.95392634685435, 107.83122536789533);
            googleMap.addMarker(new MarkerOptions().position(wrgCazerbu).title("Warung Cazerbu"));

            LatLng rmPakUjang = new LatLng(-6.9390712768240475, 107.83365333473928);
            googleMap.addMarker(new MarkerOptions().position(rmPakUjang).title("Rumah Makan Pak Ujang Rt"));

            LatLng rmMuaroTalang = new LatLng(-6.95392634685435, 107.83122536789533);
            googleMap.addMarker(new MarkerOptions().position(rmMuaroTalang).title("Rm Muaro Talang"));

            LatLng rmNasiBuAyu = new LatLng(-6.9531086464656395, 107.83124033905995);
            googleMap.addMarker(new MarkerOptions().position(rmNasiBuAyu).title("WARUNG NASI BU AYU"));

            LatLng rmDapoerTehSusi = new LatLng(-6.953084546749188, 107.82265436789528);
            googleMap.addMarker(new MarkerOptions().position(rmDapoerTehSusi).title("Dapoer Teh Susi"));

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            client = LocationServices.getFusedLocationProviderClient(getActivity());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wrgCazerbu, 13.0F));
            mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
            getCurrentLocation();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    // setting googleMaps
    private void getCurrentLocation() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            Log.e("Last Location: ", location.toString());
                            LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(currentLoc).title("Lokasi anda saat ini"));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
                        }
                    });

                }
            }
        });
    }
}