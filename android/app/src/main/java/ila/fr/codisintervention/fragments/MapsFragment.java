package ila.fr.codisintervention.fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ila.fr.codisintervention.activities.MapActivity;
import ila.fr.codisintervention.entities.SymbolKind;
import ila.fr.codisintervention.R;

public class MapsFragment extends Fragment {

    /*
    inner class for test
     */
    public class DronePoint {
        int id = 0;
        double lat;
        double lon;

        public DronePoint(int num, double lat, double lon) {
            this.id = num;
            this.lat = lat;
            this.lon = lon;
        }
    }

    DronePoint position_Drone;
    // general number of drone's points.
    // 0 : for the drone's real position
    int cpt_id = 1;
    //list of points for the drone's course
    Map<Integer, DronePoint> course = new TreeMap<Integer, DronePoint>();
    Map<String,MarkerOptions> markers = new HashMap<String,MarkerOptions>();
    MapView mMapView;
    private GoogleMap googleMap;
    private final static int LOCATION_REQ_CODE = 456;
    private static final String TAG = MapActivity.class.getSimpleName();

    /*
    Refresh the map with the drone's course
     */
    public void updateUI(GoogleMap mMap) {
        mMap.clear();
        DronePoint previous = null;
        java.util.Set<Integer> keyList = course.keySet();
        for (Integer num : keyList) {
            DronePoint point = course.get(num);
            if (!num.equals(0)) {    // specific case of the drone, itself.
                if (previous != null) {
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(previous.lat, previous.lon), new LatLng(point.lat, point.lon))
                            .width(5)
                            .color(Color.RED));
                }
                previous = point;
            }
            addMarker_Zoom(point);
        }

        for(String str : markers.keySet()){
            googleMap.addMarker(markers.get(str));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(48.115204, -1.637871)).zoom(18).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                /*
                    Manage long clic : create a point
                 */
                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                            /* add DronePoint */
                        SymbolKind symbole = getSymbolFragment();
                        if(symbole!=null) {
                            if (symbole.getIdDrawable() == R.drawable.drone_icon_map) {
                                DronePoint pt = new DronePoint(cpt_id, latLng.latitude, latLng.longitude);
                                course.put(new Integer(cpt_id), pt);   // add points in the course
                                cpt_id += 1;
                                updateUI(googleMap);                   // refresh the map
                            } else {
                                Bitmap marker = resizeBitmap(symbole.getIdDrawable(), 50, 50);
                                addCustomMarker_Zoom(latLng, marker);
                            }
                        }
                    }

                });

                    /*
                        Drag and drop a marker with a long clic
                     */
                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    Integer num;
                    String idMarker;

                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        if (!marker.getTitle().equals("")) {
                            num = new Integer(marker.getTitle());   // the id of the point = marker's title for drone's course
                        }else{
                            num=null;
                            idMarker = marker.getId();
                        }
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        if (num != null) {                        // update marker for drone
                            course.get(num).lat = marker.getPosition().latitude;
                            course.get(num).lon = marker.getPosition().longitude;
                        }
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        if (num == null) {
                            for(String str : markers.keySet()){
                                if (str.equals(idMarker)) {
                                    MarkerOptions mo = markers.get(str);
                                    mo.position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
                                    markers.put(marker.getId(), mo);
                                    markers.remove(str);
                                    //markers.get(str).position(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude));
                                }
                            }
                        }
                        updateUI(googleMap);                    // refresh the map
                    }
                });
            }
        });


        mMapView.setClickable(true);
        return rootView;
    }

    /* Get symbole from activity SymbolFragment */
    public SymbolKind getSymbolFragment(){
        return ((MapActivity)getActivity()).getSelectedSymbol();
    }

    /* method for customizing the map */
    public void styleMap(int ressource_path) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), ressource_path));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    /*method for resizing the bitmap that is used to customize a marker*/
    public Bitmap resizeBitmap(int drawablePath, int targetWidth, int targetHeight) {
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawablePath);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, targetWidth, targetHeight, false);
        return smallMarker;
    }

    /* For dropping a marker Coord at a point on the MapActivity */
    public void addCustomMarker_Zoom(LatLng Coord, Bitmap Customizer) {
        MarkerOptions mo = new MarkerOptions().position(Coord).draggable(true).title("").snippet("").icon(BitmapDescriptorFactory.fromBitmap(Customizer));
        Marker mark = googleMap.addMarker(mo);
        markers.put(mark.getId(),mo);
    }

    /* For zooming automatically to the location of the marker */
    public void ZoomOnMarker(LatLng Coord) {

        CameraPosition cameraPosition = new CameraPosition.Builder().target(Coord).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    /* Method used to add à marker and zoom on it */
    public LatLng addMarker_Zoom(DronePoint point) {
        LatLng coord = new LatLng(point.lat, point.lon);
        Marker mark = googleMap.addMarker(new MarkerOptions().position(coord).draggable(true).title("" + point.id).snippet("").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.drone_icon_map, 50, 50))));
        mark.showInfoWindow();
        return coord;
    }


    /*
        add or modify the drone position
        we store the drone position at the index 0 of parcours list
    */
    public void modifyPositionDrone(DronePoint newDrone) {
        if (course.containsKey(0)) {                // Modify
            DronePoint drone = course.get(0);
            drone.lat = newDrone.lat;
            drone.lon = newDrone.lon;
        } else {                                        // Create
            DronePoint drone = new DronePoint(0, newDrone.lat, newDrone.lon);
            course.put(0, drone);
        }
        updateUI(googleMap);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}