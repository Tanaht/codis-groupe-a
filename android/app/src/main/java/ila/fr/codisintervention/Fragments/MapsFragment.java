    package ila.fr.codisintervention.Fragments;

    import android.Manifest;
    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.content.pm.PackageManager;
    import android.content.res.Resources;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.drawable.BitmapDrawable;
    import android.net.Uri;
    import android.os.Bundle;
    import android.app.Fragment;
    import android.support.v4.app.ActivityCompat;
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
    import com.google.android.gms.maps.model.MarkerOptions;

    import ila.fr.codisintervention.Activities.MapActivity;
    import ila.fr.codisintervention.R;


    public class MapsFragment extends Fragment {
        MapView mMapView;
        private GoogleMap googleMap;
        private final static int LOCATION_REQ_CODE = 456;
        private static final String TAG = MapActivity.class.getSimpleName();

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
                    //styleMap(R.raw.style_json);
                    //googleMap.setMyLocationEnabled(true);

                    LatLng surprise = new LatLng(48.873756, 2.294946);
                    addMarker_Zoom(surprise);
                    //click event handler for the map
                    googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latlng) {
                            /*retrieve the coordinates got by the long click */
                            Bitmap smallMarker = resizeBitmap(R.drawable.pompier,50,50);

                            /*clear the map*/
                            //googleMap.clear();
                           addCustomMarker_Zoom(latlng,smallMarker);
                        }
                    });
                }
            });

            return rootView;
        }

        /* method for customizing the map */
        public void styleMap (int ressource_path){
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
        public Bitmap resizeBitmap (int drawablePath, int targetWidth, int targetHeight){
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(drawablePath);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, targetWidth, targetHeight, false);
            return smallMarker;
        }


        /*Method used to add a default marker */
        public void addMarker_Zoom (LatLng Coord) {

            // For dropping a marker Coord at a point on the MapActivity
            googleMap.addMarker(new MarkerOptions().position(Coord).draggable(true).title("coordinates : " + Coord.latitude + " , " + Coord.longitude).snippet("Marker Description"));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(Coord).zoom(17).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }

        /*Method used to add a marker */
        public void addCustomMarker_Zoom (LatLng Coord, Bitmap Customizer) {

            MarkerOptions marker = new MarkerOptions();
            marker.position(Coord).title("coordinates : " + Coord.latitude + " , " + Coord.longitude).isDraggable();
            marker.icon(BitmapDescriptorFactory.fromBitmap(Customizer));
            googleMap.addMarker(marker);
           /*Zoom on the newly added marker*/
            CameraPosition cameraPosition = new CameraPosition.Builder().target(Coord).zoom(17).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }

        /*Method used to zoom on a marker */
        public void ZoomOnMarker (LatLng Coord) {
            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(Coord).zoom(17).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

        /*
        //other way to move the camera
        public void moveCamera(LatLng latlng, float zoom, String title)
        {
        Log.d(TAG,"moveCamera: moving the camera to: " +latlng.latitude + ", lng: " + latlng.longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
        }
        */

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