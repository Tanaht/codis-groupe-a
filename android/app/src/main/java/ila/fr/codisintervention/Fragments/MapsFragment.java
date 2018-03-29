package ila.fr.codisintervention.Fragments;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ila.fr.codisintervention.Activities.MapActivity;
import ila.fr.codisintervention.R;


public class MapsFragment extends Fragment {

    /*
    inner class for test
     */
    public class DronePoint {
        int numero = 0;
        double lat;
        double lon;

        public DronePoint(int num, double lat, double lon) {
            this.numero = num;
            this.lat = lat;
            this.lon = lon;
        }
    }

    DronePoint position_Drone;
    // compteur incrément numéro des point du parcours drone.
    // 0 pour la position actuelle du drone
    int cpt_numero = 1;
    //liste des points pour le parcours du drone
    Map<Integer, DronePoint> parcours = new HashMap<Integer, DronePoint>();
    List<Marker> markers = new ArrayList<Marker>();
    MapView mMapView;
    private GoogleMap googleMap;
    private final static int LOCATION_REQ_CODE = 456;
    private static final String TAG = MapActivity.class.getSimpleName();

    /*
    Raffraichir la carte avec le parcours du drone tracé
     */
    public void updateUI(GoogleMap mMap) {
        mMap.clear();
        DronePoint previous = null;
        java.util.Set<Integer> keyList = parcours.keySet();
        for (Integer num : keyList) {
            DronePoint point = parcours.get(num);
            if (num.equals(0)) {    // cas particulier du drone lui-meme
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
                        Gestion du clic long : création d'un point
                     */
                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                            /* add DronePoint */
                        DronePoint pt = new DronePoint(cpt_numero, latLng.latitude, latLng.longitude);
                        parcours.put(new Integer(cpt_numero), pt);   // ajout dans la liste des points
//                            LatLng ln = addMarker_Zoom(pt);             // ajout sur la carte
                        cpt_numero += 1;                            // incrément compteur général
                        updateUI(googleMap);                        // raffraichir la map
                    }

                });

                    /*
                        Drag and drop d'un marker sur un appui long
                     */
                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    Integer num;

                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        num = new Integer(marker.getTitle());   // numéro du marker sélectionné
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        if (num != null) {                        // update des coordonnées du marker
                            parcours.get(num).lat = marker.getPosition().latitude;
                            parcours.get(num).lon = marker.getPosition().longitude;
                        }
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        updateUI(googleMap);                    // raffraichir la map
                    }
                });
            }
        });


        mMapView.setClickable(true);
        return rootView;
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
    // For dropping a marker Coord at a point on the MapActivity
        /*Method used to add a marker */

    public void addCustomMarker_Zoom(LatLng Coord, Bitmap Customizer) {

        MarkerOptions marker = new MarkerOptions();
        marker.position(Coord).title("coordinates : " + Coord.latitude + " , " + Coord.longitude).isDraggable();
        marker.icon(BitmapDescriptorFactory.fromBitmap(Customizer));
        googleMap.addMarker(marker);
           /*Zoom on the newly added marker*/
        CameraPosition cameraPosition = new CameraPosition.Builder().target(Coord).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    /*Method used to zoom on a marker */
    public void ZoomOnMarker(LatLng Coord) {
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(Coord).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

        /*Method used to add à marker and zoom on it*/

    public LatLng addMarker_Zoom(DronePoint point) {
        LatLng coord = new LatLng(point.lat, point.lon);
        // For dropping a marker Coord at a point on the MapActivity
        Marker mark = googleMap.addMarker(new MarkerOptions().position(coord).draggable(true).title("" + point.numero).snippet(""));
        mark.showInfoWindow();
        return coord;
    }


    /*
        add or modify the drone position
        we store the drone position at the index 0 of parcours list
     */
    public void modifyPositionDrone(DronePoint newDrone) {
        if (parcours.containsKey(0)) {          // Modify
            DronePoint drone = parcours.get(0);
            drone.lat = newDrone.lat;
            drone.lon = newDrone.lon;
        } else {                                     // Create
            DronePoint drone = new DronePoint(0, newDrone.lat, newDrone.lon);
            parcours.put(0, drone);
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