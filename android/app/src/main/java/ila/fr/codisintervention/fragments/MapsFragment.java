package ila.fr.codisintervention.fragments;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import java.util.LinkedHashMap;
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
import ila.fr.codisintervention.models.model.map_icon.Color;
import ila.fr.codisintervention.models.model.map_icon.I_MarkerElement;
import ila.fr.codisintervention.models.model.map_icon.MarkerDrone;
import ila.fr.codisintervention.models.model.map_icon.MarkerSymbol;
import ila.fr.codisintervention.models.model.map_icon.MarkerUnit;
import ila.fr.codisintervention.models.model.map_icon.Shape;
import ila.fr.codisintervention.models.model.map_icon.drone.PathDroneType;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.models.model.map_icon.symbol.SymbolUnit;
import ila.fr.codisintervention.models.DronePoint;
import ila.fr.codisintervention.models.model.map_icon.drone.PathDrone;
import ila.fr.codisintervention.utils.MarkerUtility;

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
    //DronePoint dronePosition;

    /**
     * this list contains all symbols, units and dronepoints with assiciated marker on the map.
     * Usefull to modify or delete markers on the map.
     */
    Map<Marker,I_MarkerElement> markerList;
    Map<Marker,I_MarkerElement> markerListDrone;

    /**
     * list of points for the drone's course
     */
    Map<Integer, DronePoint> course = new TreeMap<>();

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
        markerList = new LinkedHashMap<Marker,I_MarkerElement>();

        if (((MapActivity)getActivity()).getModelService().getCurrentIntervention() != null) {
            if (((MapActivity)getActivity()).getModelService().getCurrentIntervention().getSymbols() != null) {
                for (Symbol s : ((MapActivity) getActivity()).getModelService().getCurrentIntervention().getSymbols()) {
                    I_MarkerElement IMarker = new MarkerSymbol(s,getActivity());
                    Marker marker = IMarker.printOnMap(googleMap);
                    if (marker != null) //requested marker doesn't exit as bitmap
                        markerList.put( marker, IMarker );
                }
            }
            if (((MapActivity)getActivity()).getModelService().getCurrentIntervention().getUnits() != null) {
                for (Unit u : ((MapActivity) getActivity()).getModelService().getCurrentIntervention().getUnits()) {
                    I_MarkerElement IMarker = new MarkerUnit(u,getActivity());
                    Marker marker = IMarker.printOnMap(googleMap);
                    if (marker != null) //requested marker doesn't exit as bitmap
                        markerList.put( marker, IMarker );
                }
            }

            PathDrone path = ((MapActivity)getActivity()).getModelService().getCurrentIntervention().getPathDrone();
            updateDronePath(path);
        }

    }

    /**
     * give the drone path draw on the map
     * @return a list of Location
     */
    public List<Location> getDronePath(){
        List<Location> myPath = new ArrayList<Location>();
        for (Map.Entry<Marker,I_MarkerElement> line: markerListDrone.entrySet()){
            I_MarkerElement element = line.getValue();
            if(element instanceof MarkerDrone){
                MarkerDrone md = (MarkerDrone)element;
                if (!md.getData().isMoving())
                    myPath.add(md.getData().getLocation());
            }
        }
        return myPath;
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

    public void onModelServiceConnected(Location interventionPosition){
        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(interventionPosition.getLat(), interventionPosition.getLng())).zoom(18).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            /*
                Manage long clic : create a point
             */
            googleMap.setOnMapLongClickListener(latLng -> {
                /* add DronePoint */
                SymbolKind symbole = getSymbolFragment();
                if(symbole!=null) {

                    if (!MarkerUtility.createObjectOnMap(latLng,symbole,getActivity()))
                        Toasty.error(getActivity().getApplicationContext(), getString(R.string.error_symbol_not_found), Toast.LENGTH_SHORT, true).show();

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
                I_MarkerElement element;
                Marker markerElement;

                @Override
                public void onMarkerDragStart(Marker marker) {
                    element = null;
                    markerElement = marker;
                    if (markerList.containsKey(marker)) {
                        element = markerList.get(marker);   // Symbol or Unit
                    }else if (markerListDrone.containsKey(marker)) {
                        element = markerListDrone.get(marker);   // Drone
                    }
                }

                @Override
                public void onMarkerDrag(Marker marker) {
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    if(element != null){
                        element.udpatefromDragAndDrop(marker);
                        markerElement = marker;
                        updateView();
                    }
                }
            });
        });

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
     * For zooming automatically to the location of the marker
     * @param coord the LatLng coordinate of the marker
     */
    public void zoomOnMarker(LatLng coord) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(coord).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    /**
     * add or modify the drone position
     * we store the drone position at the index 0 of parcours list
     * @param newDrone
     */
    public void modifyDronePosition(DronePoint newDrone) {
        for (Map.Entry<Marker,I_MarkerElement> md: markerListDrone.entrySet()){
            MarkerDrone drone=null;
            if (((MarkerDrone)md).getData().isMoving()){
                drone = (MarkerDrone)md;
            }
            if (drone == null){
                DronePoint dpDrone = new DronePoint(0, newDrone.getLat(), newDrone.getLon());
                dpDrone.setMoving(true);
                drone = new MarkerDrone(true, dpDrone,getActivity());
            }else {
                drone.getData().setLat(newDrone.getLat());
                drone.getData().setLon(newDrone.getLon());
            }
        }
        updateView();
    }

    /**
     * Update all drone point on the map
     * @param path
     */
    public void updateDronePath(PathDrone path) {
        ((MapActivity) getActivity()).resetCptId();

        deleteAllDronePointOnTheMap();
        markerListDrone = new LinkedHashMap<Marker,I_MarkerElement>();

        if (path == null) {
            // TODO Think to remove : For the test !!!
            List<Location> posList = new ArrayList<Location>();
            posList.add(new Location(48.1153379, -1.6391757));
            posList.add(new Location(48.1161849, -1.6390014));
            posList.add(new Location(48.1164571, -1.6373706));
            posList.add(new Location(48.1155689, -1.6360724));
            posList.add(new Location(48.1152322, -1.6378534));
            path = new PathDrone(new ila.fr.codisintervention.models.messages.PathDrone(PathDroneType.CYCLE.name(), posList));
        }
        if (path != null) {
            // add drone's path
            Marker previousMarkerDrone = null;  // for line to draw between each point of the path
            for (Location l : path.getPoints()) {
                I_MarkerElement IMarker = new MarkerDrone(false,new DronePoint(((MapActivity) getActivity()).getCptId(),l.getLat(),l.getLng()),getActivity());
                Marker marker = IMarker.printOnMap(googleMap);
                if (marker != null) { //requested marker doesn't exit as bitmap
                    ((MapActivity) getActivity()).increaseCptId();

                    markerListDrone.put(marker, IMarker);
                    if (previousMarkerDrone != null){   // we draw each line of the path
                        googleMap.addPolyline(new PolylineOptions()
                                .add(previousMarkerDrone.getPosition(),marker.getPosition())
                                .width(5)
                                .color( android.graphics.Color.DKGRAY));
                    }
                    previousMarkerDrone = marker;
                }
            }
        }
    }

    public void deleteAllDronePointOnTheMap(){
        if (markerListDrone != null) {
            for (Map.Entry<Marker, I_MarkerElement> line : markerListDrone.entrySet()) {
                if (line != null && line.getKey() != null)
                    line.getKey().remove(); // delete the marker
            }
        }
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

}