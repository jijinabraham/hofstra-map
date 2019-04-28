package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.text.Collator;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapsActivity<withListener> extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {

    static final int POLYGON_POINTS = 5;
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    Marker marker;
    Circle circle;
    Marker marker1;
    Marker marker2;
    Polyline line;
    DrawMarker drawMarker;
    ArrayList<Marker> markers = new ArrayList<Marker>();
    Polygon shape;
    private Collator DrawRouteMaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServicesAvailable()) {
            Toast.makeText(this, "Perfect!!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);
            initMap();
        } else {
            // no google maps layout
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();

        }
        return false;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(40.71370920426692, -73.60166322969508), //Memorial Hall,Bits & Bytes,Student Financial Services
                        new LatLng(40.713995864573015, -73.60184025549006),  //Academic Advisement
                        new LatLng(40.71434570477824, -73.60163622399637),   // Hauser Hall, Department of Psychology
                        new LatLng(40.71463493439309, -73.60081230528436), // Axinn Library, The President's Office (West Wing), Honors College, bulletin Office
                        new LatLng(40.71473860101402, -73.60048835734995),  // Hammer Computer Lab
                        new LatLng(40.714940403322736, -73.59998685452882)));  // Heger Hall, Department of Religion, Department of Philosophy

        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);

        Polyline polyline2 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(40.71385270564209, -73.59981633684794), // Brower Hall
                        new LatLng(40.713004302992495, -73.60071195850378), // Adams Playhouse, The Globe Theater
                        new LatLng(40.71418308199756, -73.59929521756487),  // Spiegel Theater
                        new LatLng(40.71397598202105, -73.59997342215638),  // Calkins Hall, Department of Fine Arts, Department of Comparative Literature, department of Foreign Language
                        new LatLng(40.712898632588455, -73.60015332698822),   //Dunkin' On The Quad
                        new LatLng(40.71359843570906, -73.59923332929611)));  // Weller Hall, Business School

        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);



        polyline1.setTag("A");

        Polyline polyline3 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(40.71458813136285, -73.59937920078767),  //Mason Hall, Gallon-Wing, Department of English, Department of Women's Studies, Department of Writing Studies
                        new LatLng(40.714275044106174, -73.60008193954957), //Philips Hall, Administrative Offices
                        new LatLng(40.71404307257277, -73.6003861264611),   // Hofstra Hall, Development Office
                        new LatLng(40.71397598202105, -73.60093329710014),  // Davison Hall, Department of Anthropology, Department of Sociology
                        new LatLng(40.7132828837781, -73.6013925075531),   //Emily Lowe Hall, Emily Lowe Gallery, Department of Drama & Dance
                        new LatLng(40.71354935646173, -73.60067386175535)));   //Barnard Hall, Department of Economics, Department of Political Science

        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);

        Polyline polyline4 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(40.71308813580013, -73.59921991825104), //McEwen Hall, Department of Information Technology, Department of Telecommunications, Help Desk
                        new LatLng(40.71291024148623, -73.5990500642539), //Au Bon Pain
                        new LatLng(40.71258310439361, -73.59954073283473), //Roosevelt Hall, Department of Mathematics, Departmentof Global Studies and Geography,International Student Affairs
                        new LatLng(40.712530244017586, -73.60069140050211), //The Lawrence Herbert School of Communication, Media School
                        new LatLng(40.71223439623034, -73.59996385130682), //Bunny & Charles A. Koppelman Hall, Maurice A. Deane School of Law, Law School
                        new LatLng(40.7119700925464, -73.59949446472922))); //Kushner Hall, School of Law Library, Maurice A. Deane School of Law, Law School

        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);

        Polyline polyline5 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(40.71193857934487, -73.60060573976449),  //Breslin Hall
                        new LatLng(40.71330040059968, -73.60242175490225),  //Adams Hall, Department of Computer Science
                        new LatLng(40.71310929186988, -73.60296624333228),  //Weed Hall, Department of Engineering
                        new LatLng(40.7137850231927, -73.60264344390424),  //Bernon Hall, Admission Center
                        new LatLng(40.71368946959187, -73.60424204047712),  //Saltzman Community Services Center, Community Services Center, Hofstra University Continuing Education
                        new LatLng(40.71454548110428, -73.601107348276)));  //West Library Wing

        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);

        Polyline polyline6 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(40.71517207788174, -73.60098022454923),   //unispan
                        new LatLng(40.714141333060816, -73.60393283345525),  // netherspan
                        new LatLng(40.71290305111857, -73.60209711604841)));  //Joan & Donald Schaeffer Black Box Theater

        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);



        mGoogleMap = googleMap;
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.maptstyle));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Cant find style: Error", e);
        }

        if (mGoogleMap != null) {

            mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {

                    Geocoder gc = new Geocoder(MapsActivity.this);
                    LatLng ll = marker.getPosition();
                    double lat = ll.latitude;
                    double lng = ll.latitude;
                    List<Address> list = null;
                    try {
                        list = gc.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address add = list.get(0);
                    marker.setTitle(add.getLocality());
                    marker.showInfoWindow();


                }
            });


            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);

                    TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);

                    LatLng ll = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: " + ll.latitude);
                    tvLng.setText("Longitude: " + ll.longitude);
                    tvSnippet.setText(marker.getSnippet());


                    return v;
                }
            });

        }
        goToLocationZoom(40.71370920426692, -73.60166322969508, 15);


    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }

    public void geoLocate(View view) throws IOException {

        EditText et = (EditText) findViewById(R.id.editText);
        String location = et.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZoom(lat,lng, 15);
        setMarker(locality, lat,lng);

    }

    private void setMarker(String locality, double lat, double lng) {
        if (marker != null) {
            removeEvertything();
        }

        if (markers.size() == POLYGON_POINTS) {
            removeEverything();

        }


        MarkerOptions options = new MarkerOptions()
                .title("Adams Hall")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .position(new LatLng(lat, lng))
                .snippet("Computer Science Department");


        markers.add(mGoogleMap.addMarker(options));

        if (markers.size() == POLYGON_POINTS) {
            drawPolygon();
        }

        if (marker1 == null) {
            marker1 = mGoogleMap.addMarker(options);
        } else if (marker2 == null) {
            marker2 = mGoogleMap.addMarker(options);
            drawLine();
        } else {
            removeEvertything();
            marker1 = mGoogleMap.addMarker(options);
        }

        circle = drawCircle(new LatLng(lat, lng));

    }


    private void drawPolygon() {
        PolygonOptions options = new PolygonOptions()
                .fillColor(0x330000FF)
                .strokeWidth(3)
                .strokeColor(Color.RED);

        for (int i = 0; i < POLYGON_POINTS; i++) {
            options.add(markers.get(i).getPosition());
        }
        shape = mGoogleMap.addPolygon(options);
    }

    private void removeEverything() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
        shape.remove();
        shape = null;
    }

    private void drawLine() {

        PolylineOptions options = new PolylineOptions()
                .add(marker1.getPosition())
                .add(marker2.getPosition())
                .color(Color.BLUE)
                .width(3);

        line = mGoogleMap.addPolyline(options);
    }

    private Circle drawCircle(LatLng latLng) {

        CircleOptions options = new CircleOptions()
                .center(latLng)
                .radius(1000)
                .fillColor(0x330000FF)
                .strokeColor(Color.BLUE)
                .strokeWidth(3);

        return mGoogleMap.addCircle(options);
    }

    private void removeEvertything() {
        marker1.remove();
        marker1 = null;
        marker2.remove();
        marker2 = null;
        line.remove();
        circle.remove();
        circle = null;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    public int drawRoute(GoogleMap mMap, List<LatLng> decodedPolyLine, ArrayList<Polyline> polylineArrayList) {
        int routeDistance = 0;
        for (int i = 0; i < (decodedPolyLine.size() - 1); i++) {
            LatLng point1 = decodedPolyLine.get(i);
            LatLng point2 = decodedPolyLine.get(i + 1);

            Location location1 = new Location("1");
            location1.setLatitude(point1.latitude);
            location1.setLongitude(point1.longitude);
            Location location2 = new Location("2");
            location2.setLatitude(point2.latitude);
            location2.setLongitude(point2.longitude);

            routeDistance += location1.distanceTo(location2);
            polylineArrayList.add(mMap.addPolyline(new PolylineOptions()
                    .add(point1, point2)
                    .width(5)
                    .color(Color.RED)));
        }
        return routeDistance;
    }

    public class GeoLocation {

        private Context mContext;

        private String mLatitude;
        private String mLongtitude;
        private String mBuilding;
        private String mDepartment;

        private Location mMarkerLocation;

        public GeoLocation(Context context, String mLatitude, String mLongtitude, String mBuilding, String mDepartment) throws IOException {
            mContext = context;
            this.mLatitude = mLatitude;
            this.mLongtitude = mLongtitude;
            this.mBuilding = mBuilding;
            this.mDepartment = mDepartment;
        }

        public String getBuilding() {
            return mBuilding;
        }
        public String getDepartment() {
            return mDepartment;
        }

        public String getLatitude() {
            return mLatitude;
        }

        public String getLongtitude() {
            return mLongtitude;
        }

        private Context context;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        private double longitude;
        private double latitude;
        List<Address> addresses  = geocoder.getFromLocation(latitude,longitude, 1);

        String Building = addresses.get(0).getSubLocality();
        String Department = addresses.get(0).getAdminArea();
        String Latitude = addresses.get(0).getPostalCode();
        String Longitude = addresses.get(0).getSubAdminArea();
    }
}












