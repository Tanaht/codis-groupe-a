package ila.fr.codisintervention.fragments;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.activities.MapActivity;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.entities.SymbolKind;
import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.model.Unit;
import ila.fr.codisintervention.models.model.map_icon.Shape;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.models.model.map_icon.symbol.SymbolUnit;
import ila.fr.codisintervention.models.DronePoint;
import ila.fr.codisintervention.models.messages.Location;
import ila.fr.codisintervention.models.model.Position;
import ila.fr.codisintervention.models.model.map_icon.drone.PathDrone;

/**
 * Fragment that contain the Map to show
 * TODO: Android Studio show severall Warnings
 */
public class MapsFragment extends Fragment {
    private static final String TAG = MapActivity.class.getSimpleName();

    /**
     * constant used for some reason
     * TODO: Apparently it's a dead code, please make sure to remove it if possible
     */
    private static final int LOCATION_REQ_CODE = 456;

    /**
     * TODO: temporary instance of a Stub object, is job is to simulate the drone position normally returned by the server
     */
    DronePoint dronePosition;


    /**
     * TODO: I don't know what this variable is ?
     * general number of drone's points.
     * 0 : for the drone's real position
     */
    int cptId = 1;

    /**
     * list of points for the drone's course
     */
    Map<Integer, DronePoint> course = new TreeMap<>();

    /**
     * list of markers that represent the drone's course on map
     */
    Map<String,MarkerOptions> markers = new HashMap<>();

    /**
     * instance of the map displayed on layout
     */
    MapView mMapView;

    /**
     * instance of Google Map API
     */
    private GoogleMap googleMap;

    /**
     * Refresh the map with the drone's course
     * @param mMap the Google Map API
     */
    public void updateUI(GoogleMap mMap) {
        mMap.clear();
        DronePoint previous = null;
        java.util.Set<Integer> keyList = course.keySet();
        for (Integer num : keyList) {
            DronePoint point = course.get(num);
            if (!num.equals(0)) {    // specific case of the drone, itself.
                if (previous != null) {
                    mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(previous.getLat(), previous.getLon()),
                                    new LatLng(point.getLat(), point.getLon()))
                            .width(5)
                            .color(Color.DKGRAY));
                }
                previous = point;
            }
            addMarkerZoom(point);
        }


        for(Map.Entry<String, MarkerOptions> entry: markers.entrySet()){
            googleMap.addMarker(entry.getValue());
        }

        if (((MapActivity)getActivity()).getModelService().getCurrentIntervention() != null) {
            if (((MapActivity)getActivity()).getModelService().getCurrentIntervention().getSymbols() != null) {
                for (Symbol s : ((MapActivity) getActivity()).getModelService().getCurrentIntervention().getSymbols()) {
                    printSymbol(s);
                }
            }
            if (((MapActivity)getActivity()).getModelService().getCurrentIntervention().getUnits() != null) {
                for (Unit u : ((MapActivity) getActivity()).getModelService().getCurrentIntervention().getUnits()) {
                    printSymbolUnit(u.getSymbolUnit());
                }
            }
        }

    }

    /**
     * Add on the googlemap a symbol or a unit from the model
     * s : Symbol to draw
     * @param s
     */
    public void printSymbol(Symbol s){
        Integer drawablePath = getDrawablepathFromSymbol(s);
        if (drawablePath > 0) { //the picture doesn't exist if =0
            Bitmap marker = resizeBitmap(drawablePath, 50, 50);
            addCustomMarkerZoom(new LatLng(s.getLocation().getLat(), s.getLocation().getLng()), marker);
        }
    }

    public void printSymbolUnit(SymbolUnit s){
        Integer drawablePath = getDrawablepathFromSymbolUnit(s);
        if (drawablePath > 0) { //the picture doesn't exist if =0
            Bitmap marker = resizeBitmap(drawablePath, 50, 50);
            addCustomMarkerZoom(new LatLng(s.getLocation().getLat(), s.getLocation().getLng()), marker);
        }
        // need to print the payload.
    }


    /**
     * Find the picture resource path of a symbol
     * If >0 : found !
     * s : Symbol to find
     * return the resource's id.
     * @param s
     * @return
     */
    public Integer getDrawablepathFromSymbol(Symbol s){
        String str = (ila.fr.codisintervention.models.model.map_icon.Color) s.getColor() + s.getShape().name();
        return getResources().getIdentifier(str.toLowerCase(), "drawable", getActivity().getPackageName());
    }
    public Integer getDrawablepathFromSymbolUnit(SymbolUnit s){
        String str = (ila.fr.codisintervention.models.model.map_icon.Color) s.getColor() + s.getShape().name();
        return getResources().getIdentifier(str.toLowerCase(), "drawable", getActivity().getPackageName());
    }

    public List<Location> send_dronePoints() {

        List <Location> dronePointList= new ArrayList<>();

        java.util.Set<Integer> keyList = course.keySet();
        for (Integer num : keyList) {
            DronePoint point = course.get(num);
            if (!num.equals(0)) {    // specific case of the drone, itself.
               dronePointList.add(new Location(point.getLat(), point.getLon()));
                }

            }
        return dronePointList;

        }


//    TODO: To refactor SonarLint said it's to complex to read, and I'm agree with it perhaps we can place hook on layout like android:onClick, if not simply create class that instanciate the appropriate listeners.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        mMapView.setClickable(true);
        return rootView;
    }

    public void onModelServiceConnected(Position interventionPosition){
        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(interventionPosition.getLatitude(), interventionPosition.getLongitude())).zoom(18).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            /*
                Manage long clic : create a point
             */
            googleMap.setOnMapLongClickListener(latLng -> {
                /* add DronePoint */
                SymbolKind symbole = getSymbolFragment();
                if(symbole!=null) {
                    Log.i(TAG, "onCreateView: symbol tap");
                    
                    if (symbole.getIdDrawable() == R.drawable.drone_icon_map) {
                        DronePoint pt = new DronePoint(cptId, latLng.latitude, latLng.longitude);
                        course.put(new Integer(cptId), pt);   // add points in the course
                        cptId += 1;
                        updateUI(googleMap);                   // refresh the map
                    } else {

                        //Bitmap marker = resizeBitmap(symbole.getIdDrawable(), 50, 50);
                        //addCustomMarkerZoom(latLng, marker);

                        // transform a SymbolKind to a Symbol and send it via websocket
                        ila.fr.codisintervention.models.model.map_icon.Color color = ila.fr.codisintervention.models.model.map_icon.Color.valueOf(symbole.getColor().toUpperCase());
                        int id = ((MapActivity)getActivity()).getModelService().getCurrentIntervention().getId();
                        Shape shape = Shape.valueOf(symbole.getId().toUpperCase());
                        Location location = new Location(latLng.latitude,latLng.longitude);
                        Symbol mySymbol = new Symbol(location, color, shape);
                        if (getDrawablepathFromSymbol(mySymbol) > 0) { // the symbol's picture exists
                            List<Symbol> maListe = new ArrayList<Symbol>();
                            maListe.add(mySymbol);
                            ((MapActivity) getActivity()).getWebSocketService().createSymbols(id, maListe);
                            //printSymbol(mySymbol); // draw it only for the test
                        }else{
                            Toasty.error(getActivity().getApplicationContext(), getString(R.string.error_symbol_not_found), Toast.LENGTH_SHORT, true).show();
                        }

                    }
                }

            });

            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback(){

                @Override
                public void onMapLoaded() {
                    updateUI( googleMap );
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
                        course.get(num).setLat(marker.getPosition().latitude);
                        course.get(num).setLon(marker.getPosition().longitude);
                    }
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    if (num == null) {
                        for(Map.Entry<String, MarkerOptions> entry : markers.entrySet()){
                            if (entry.getKey().equals(idMarker)) {
                                MarkerOptions mo = entry.getValue();
                                mo.position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
                                markers.put(marker.getId(), mo);
                                markers.remove(entry.getKey());
                                //markers.get(str).position(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude));
                            }
                        }
                    }
                    updateUI(googleMap);
                }
            });
        });

        //TODO a verifier suite au merge de la branche #43
        mMapView.setClickable(true);
        return rootView;

    }


    /**
     * Get symbol from activity {@link MapActivity}
     * @return
     */
    public SymbolKind getSymbolFragment(){
        return ((MapActivity)getActivity()).getSelectedSymbol();
    }

    /**
     * Method for customizing the map
     * @param ressourcePath the path of the resource
     */
    public void styleMap(int ressourcePath) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getActivity(), ressourcePath));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    /**
     * Method for resizing the bitmap that is used to customize a marker
     * @param drawablePath the path of the drawable resource
     * @param targetWidth the width used to resize the drawable resource
     * @param targetHeight the eight used to resize the drawable resource
     * @return a scaled bitmap created from the drawable resource
     */
    public Bitmap resizeBitmap(int drawablePath, int targetWidth, int targetHeight) {
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawablePath);
        Bitmap b = bitmapdraw.getBitmap();
        return Bitmap.createScaledBitmap(b, targetWidth, targetHeight, false);
    }

    /**
     * For dropping a marker Coord at a point on the MapActivity
     * @param coord the LatLng coordinate of the marker
     * @param customizer the marker image to show
     */
    public void addCustomMarkerZoom(LatLng coord, Bitmap customizer) {
        MarkerOptions mo = new MarkerOptions().position(coord).draggable(true).title("").snippet("").icon(BitmapDescriptorFactory.fromBitmap(customizer));
        Marker mark = googleMap.addMarker(mo);
        markers.put(mark.getId(),mo);
        Log.d(TAG,"FORM ON THE MAP : " );
    }

    /**
     * For zooming automatically to the location of the marker
     * @param coord the LatLng coordinate of the marker
     */
    public void zoomOnMarker(LatLng coord) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(coord).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * Method used to add à marker and zoom on it
     * @param point
     * @return the LatLng coordinate of the marker added
     */
    public LatLng addMarkerZoom(DronePoint point) {
        LatLng coord = new LatLng(point.getLat(), point.getLon());
        Marker mark = googleMap.addMarker(new MarkerOptions().position(coord).draggable(true)
                .title("" + point.getId()).snippet("").icon(BitmapDescriptorFactory
                        .fromBitmap(resizeBitmap(point.isMoving()?R.drawable.drone_icon_map:R.drawable.drone_marker, 50, 50))));
        mark.showInfoWindow();
        return coord;
    }


    /**
     * add or modify the drone position
     * we store the drone position at the index 0 of parcours list
     * @param newDrone
     */
    public void modifyDronePosition(DronePoint newDrone) {
        if (course.containsKey(0)) {                // Modify
            DronePoint drone = course.get(0);
            drone.setLat(newDrone.getLat());
            drone.setLon(newDrone.getLon());
        } else {                                        // Create
            DronePoint drone = new DronePoint(0, newDrone.getLat(), newDrone.getLon());
            drone.setMoving(true);
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

    /**
     * update the googleMap view from the mapActivity
     */
    public void updateView(){
        updateUI(googleMap);
    }

    public void updateDronePath(PathDrone pathDrone) {
        int dpId = 1;
        for(Location dronePoint : pathDrone.getPoints()){
            int idDrawable = R.drawable.drone_icon_map;
            DronePoint pt = new DronePoint(dpId, dronePoint.getLat(), dronePoint.getLng());
            course.put(new Integer(dpId), pt);   // add points in the course
            dpId += 1;
        }
        updateUI(googleMap);
    }
}