package ryanpoulier.spotlight2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class New_Complaint_Location extends AppCompatActivity implements OnMapReadyCallback {
    String ADDRESS, result, addressline, town, gpsCoordinates, dragresult;
    Marker marker = null;

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__complaint__location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //if (mMap != null) {
        //mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
        // @Override
        //public View getInfoWindow(Marker marker) {
        //  return null;
        // }

        // @Override
        // public View getInfoContents(Marker marker) {
        //   return null;
        //}
        // });


//         }
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

        // Code from https://www.youtube.com/watch?v=92f4c2vHrPg and http://stackoverflow.com/questions/29868121/how-do-i-zoom-in-automatically-to-the-current-location-in-google-map-api-for-and
        LatLng colombo = new LatLng(6.927546, 79.862264);
        float zoomLevel = 14;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombo, zoomLevel));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        if (mMap != null) {
            // based on https://www.youtube.com/watch?v=k253ec4m33A
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                @Override
                public void onMarkerDragStart(Marker marker) {
                }

                @Override
                public void onMarkerDrag(Marker marker) {
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    List<Address> draglist = null;
                    //Address address = draglist.get(0);

                    Geocoder gc = new Geocoder(New_Complaint_Location.this);
                    LatLng latLng = marker.getPosition();

                    try {
                        draglist = gc.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Address dragadd = draglist.get(0);
                    addressline = dragadd.getAddressLine(0);
                    town = dragadd.getSubLocality();
                    String draglat = String.valueOf(dragadd.getLatitude());
                    String draglong = String.valueOf(dragadd.getLongitude());
                    dragresult = addressline + ", " + town + "; " + draglat + "," + draglong;
                    String storedragcoord = draglat + "," + draglong;
                    gpsCoordinates = storedragcoord.toString();
                    marker.setTitle("Selected location");
                    marker.setSnippet(dragresult);
                    marker.showInfoWindow();
                }


            });
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(marker != null){
                    marker.remove();
                }
                Geocoder gc = new Geocoder(New_Complaint_Location.this);
                List<Address> alist = null;

                try {
                    alist = gc.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                Address add = alist.get(0);
                marker = mMap.addMarker(new MarkerOptions().position(new LatLng(add.getLatitude(), add.getLongitude())));
                //marker.setSnippet("Lat: " + add.getLatitude() + ", Lng: " + add.getLongitude());
                //marker.setTitle("Searched location");
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                AutoCompleteTextView location_tf = (AutoCompleteTextView) findViewById(R.id.txtSearch);
                String address = add.getAddressLine(0);
                location_tf.setText(address);
                //marker.showInfoWindow();
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng laln) {


            }
        });
    }

    // code from https://www.youtube.com/watch?v=dr0zEmuDuIk
    public void onSearch(View view) {
        AutoCompleteTextView location_tf = (AutoCompleteTextView) findViewById(R.id.txtSearch);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;


        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                Toast.makeText(New_Complaint_Location.this, "Location not found. Please check the spelling", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            // code from Zad Mit 52
            if (marker != null) {
                marker.remove();
            }

            // code from http://stackoverflow.com/questions/6922312/get-location-name-from-fetched-coordinates
            List<Address> list = null;
            try {
                list = geocoder.getFromLocation(address.getLatitude(), address.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (list != null & list.size() > 0) {
                Address add = list.get(0);
                addressline = add.getAddressLine(0);
                town = add.getSubLocality();
                String searchlat = String.valueOf(add.getLatitude());
                String searchlong = String.valueOf(add.getLongitude());
                result = addressline + ", " + town + "; " + searchlat + "," + searchlong;
                String storecoord = searchlat + "," + searchlong;
                gpsCoordinates = storecoord.toString();
            }


            //code from http://www.codeproject.com/Articles/825942/Flirting-with-Google-Maps-on-Android
            marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).flat(true).snippet(result).title("Searched location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            //marker.showInfoWindow();

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "New_Complaint_Location Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ryanpoulier.spotlight2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "New_Complaint_Location Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ryanpoulier.spotlight2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    //code from https://www.youtube.com/watch?v=8byyh8Lb_xc&index=3&list=FLsCn-tnRZVHIyKOq7o6b36Q and https://www.youtube.com/watch?v=xv_JJbjDQ3M&list=FLsCn-tnRZVHIyKOq7o6b36Q&index=2

    public void storeLocation() {

        //SharedPreferences addprefs = getSharedPreferences("address", MODE_WORLD_READABLE);
        //SharedPreferences.Editor editor= addprefs.edit();
        //editor.putString("address", result);
        //editor.apply();
        //Toast.makeText(this, "Address Saved", Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = getSharedPreferences("gpsCoordinates", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("gpsCoordinates", String.valueOf(gpsCoordinates));
        editor.apply();

        //Toast.makeText(this, "Location Saved", Toast.LENGTH_LONG).show();
    }

    public void OpenNewComplaintSuggest(View view) {
        storeLocation();
        Intent intent = new Intent(this, New_Complaint_Suggest.class);
        startActivity(intent);
    }

}



