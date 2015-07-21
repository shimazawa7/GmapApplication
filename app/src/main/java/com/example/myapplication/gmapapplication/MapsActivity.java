package com.example.myapplication.gmapapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    /**
         * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
         * installed) and the map has not already been instantiated.. This will ensure that we only ever
         * call {@link #setUpMap()} once when {@link #mMap} is not null.
         * <p/>
         * If it isn't installed {@link SupportMapFragment} (and
         * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
         * install/update the Google Play services APK on their device.
         * <p/>
         * A user can return to this FragmentActivity after following the prompt and correctly
         * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
         * have been completely destroyed during this process (it is likely that it would only be
         * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
         * method in {@link #onResume()} to guarantee that it will be called.
         */

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        Intent intent = getIntent();
        String address1 = intent.getStringExtra("address");

        double latitude = 43.0675;
        double longitude = 141.350784;

        try {
            Address address = getLatLongFromLocationName(address1);
            latitude = address.getLatitude();
            longitude = address.getLongitude();
        } catch (IOException e){
            e.printStackTrace();
        }

        // 航空写真に変更
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // 渋滞状況を表示
        mMap.setTrafficEnabled(true);
        // 現在の位置情報を表示
        mMap.setMyLocationEnabled(true);

        //ここからマーカーのカスタマイズ
        // オプション設定
        MarkerOptions options = new MarkerOptions();
        // 緯度・経度
        options.position(new LatLng(0, 0));
        // タイトル・スニペット
        options.title("Title");
        options.snippet("Snippet");

        // マーカーを貼り付け
        mMap.addMarker(options);

    }
    private Address getLatLongFromLocationName(String locationName) throws IOException {
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

        List<Address> addressList = geocoder.getFromLocationName(locationName,1);
        Address address = addressList.get(0);


        return address;
    }

}