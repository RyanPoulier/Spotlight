package ryanpoulier.spotlight2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

public class Nearby_issues extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner smapsearch, smaprefine;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor, c;
    DBhelper DBhelper;
    String location, title, status, mapsearchterm, timestamp;
    AutoCompleteTextView mapsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_issues);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        mapsearch = (AutoCompleteTextView) findViewById(R.id.mapsearch);


        smapsearch = (Spinner) findViewById(R.id.spmapsearch);
        smaprefine = (Spinner) findViewById(R.id.spmapsearchrefine);
        smaprefine.setVisibility(View.INVISIBLE);

        DBhelper = new DBhelper(getApplicationContext());
        sqLiteDatabase = DBhelper.getReadableDatabase();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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
    // code from https://www.youtube.com/watch?v=dr0zEmuDuIk
   @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Code from https://www.youtube.com/watch?v=92f4c2vHrPg and http://stackoverflow.com/questions/29868121/how-do-i-zoom-in-automatically-to-the-current-location-in-google-map-api-for-and
        mMap.setMyLocationEnabled(true);
        float zoomLevel = 10;

       cursor = DBhelper.getAllData(sqLiteDatabase);

       if (cursor.moveToFirst()) {
           do {
               title= cursor.getString(1);
               timestamp = cursor.getString(2);
               location=cursor.getString(5);
               status = cursor.getString(8);
               // code from http://stackoverflow.com/questions/3732790/android-split-string
               StringTokenizer separated = new StringTokenizer(location, ",");
               String latitude = separated.nextToken();
               String longitude = separated.nextToken();
               Double lat = Double.parseDouble(latitude);
               Double lng = Double.parseDouble(longitude);
               LatLng latlngposition = new LatLng(lat,lng);
               mMap.addMarker(new MarkerOptions().position(latlngposition).title(title).snippet("Status: " + status));
               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngposition, zoomLevel));

           }
           while (cursor.moveToNext());
       }

       mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
           @Override
           public void onInfoWindowClick(Marker marker) {
               Intent intent=new Intent (getApplicationContext(),ComplaintDetails.class);
               startActivity(intent);
           }
       });

   }

    public void ListSwitch (View view){
        Intent intent=new Intent (this,Latest_Complaints.class);
        startActivity(intent);
    }

    public void MapSearch (View view) {

        // SHORTCUT
        smaprefine.setVisibility(View.VISIBLE);

        mapsearchterm = mapsearch.getText().toString();
        Toast.makeText(Nearby_issues.this, mapsearchterm, Toast.LENGTH_SHORT).show();
        mMap.clear();

        c = DBhelper.search(sqLiteDatabase, mapsearchterm);

        if (c.moveToFirst()) {
            do {
                title = c.getString(1);
                location=c.getString(3);
            }
            while (c.moveToNext());
            // code from http://stackoverflow.com/questions/3732790/android-split-string
            StringTokenizer separated = new StringTokenizer(location, ",");
            String latitude = separated.nextToken();
            String longitude = separated.nextToken();
            Double lat = Double.parseDouble(latitude);
            Double lng = Double.parseDouble(longitude);
            LatLng searchlatlngposition = new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions().position(searchlatlngposition).title(title).snippet("Status: Notification sent to GoSL"));
            float zoomLevel = 10;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchlatlngposition, zoomLevel));
        }

    }


}
