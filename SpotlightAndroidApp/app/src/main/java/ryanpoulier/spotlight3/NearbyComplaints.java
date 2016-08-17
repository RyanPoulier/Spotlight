package ryanpoulier.spotlight3;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by shamal on 8/5/16.
 */
public class NearbyComplaints extends Fragment implements OnMapReadyCallback{

    private GoogleMap map;
    Spinner smapsearch, smaprefine;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor, c;
    DBhelper DBhelper;
    String location, title, status, mapsearchterm, timestamp;
    AutoCompleteTextView mapsearch;
    private MapView mapView;

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
    public void onMapReady(GoogleMap map) {
        this.map = map;

        LatLng colombo = new LatLng(6.927546, 79.862264);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(colombo, 10);
        map.animateCamera(cameraUpdate);
    }
}
