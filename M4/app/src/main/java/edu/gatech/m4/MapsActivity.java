package edu.gatech.m4;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private DBHelper dbHelper;
    private double latitude;
    private double longitude;
    private String id;
    private ArrayList<String> idList;
    private ArrayList<Double> latitudeList;
    private ArrayList<Double> longitudeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        dbHelper = new DBHelper(this);
        idList = new ArrayList<String>();
        latitudeList = new ArrayList<Double>();
        longitudeList = new ArrayList<Double>();

        String[] dates = (String[]) getIntent().getSerializableExtra("String");
        Log.d("message", dates[0]);
        Cursor cursor = dbHelper.getDateRange(dates[0], dates[1]);
        cursor.moveToFirst();
        for (int i=0; i<10; i++) {
            if (cursor.moveToNext()) {
                id = cursor.getString(cursor.getColumnIndex(DBHelper.REPORT_COLUMN_NAME));
                idList.add(id);
                latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBHelper.REPORT_COLUMN_LATITUDE)));
                latitudeList.add(latitude);
                longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBHelper.REPORT_COLUMN_LONGITUDE)));
                longitudeList.add(longitude);
            }
        }

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
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        for (int a=0; a<10; a++) {
            LatLng marker = new LatLng(latitudeList.get(a), longitudeList.get(a));
            mMap.addMarker(new MarkerOptions().position(marker).title(idList.get(a)).snippet("."));
        }
        
    }
}
