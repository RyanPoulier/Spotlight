package ryanpoulier.spotlight2;


import android.*;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by shamal on 8/5/16.
 */
public class NearbyComplaints extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    Spinner smapsearch, smaprefine;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor, c;
    DBhelper DBhelper;
    String location, title, status, mapsearchterm, timestamp;
    AutoCompleteTextView mapsearch;
    private MapView mapView;
    MarkerOptions currentLocation;

    String ADDRESS, result, addressline, town, gpsCoordinates, dragresult;
    Marker marker = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nearbycomplaints, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        MapsInitializer.initialize(this.getActivity());
        mapView = (MapView) view.findViewById(R.id.mapp);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        this.map = map;
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub
                currentLocation = new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude()));
                //map.addMarker(currentLocation);
                new NearByIssuesTask().execute();
            }
        });
        //this.map.setMyLocationEnabled(true);
        LatLng colombo = new LatLng(6.927546, 79.862264);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(colombo, 10);
        map.animateCamera(cameraUpdate);

    }

    private class NearByIssuesTask extends AsyncTask<Void, Void, String> {

        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();


            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n > 0) {
                byte[] b = new byte[4096];
                n = in.read(b);


                if (n > 0) out.append(new String(b, 0, n));
            }


            return out.toString();
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/issues/nearby/"+currentLocation.getPosition().latitude+"/"+currentLocation.getPosition().longitude);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();


                text = getASCIIContentFromEntity(entity);
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }


            return text;
        }

        protected void onPostExecute(String results) {
            if (results!=null) {
                try {
                    JSONArray issues = new JSONArray(results);
                    for(int i = 0, count = issues.length(); i< count; i++)
                    {
                        try {
                            JSONObject jsonObject = issues.getJSONObject(i);

                            map.addMarker(new MarkerOptions().position(new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"))));
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
