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

                    //googleMap.setMyLocationEnabled(true);

                    /* For customizing the map
                    try {

                        // Customise the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = googleMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        getActivity(), R.raw.style_json));

                        if (!success) {
                            Log.e(TAG, "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e(TAG, "Can't find style. Error: ", e);
                    }
                    */

                    LatLng surprise = new LatLng(48.873756, 2.294946);
                    addMarker_Zoom(surprise);
                    //click event handler for the map
                    googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latlng) {
                            /*retrieve the coordinates got by the long click */
                            /*create a marker and set up it's options */

                            MarkerOptions marker = new MarkerOptions();
                            marker.position(latlng);
                            marker.title("coordinates : " + latlng.latitude + " , " + latlng.longitude);
                            marker.isDraggable();

                            int x = 50, y = 50;
                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pompier);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, x, y, false);
                            marker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                            /*clear the map*/
                            //googleMap.clear();
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(18).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            /*add the marker on the map */
                            googleMap.addMarker(marker);
                        }
                    });
                }
            });

            return rootView;
        }


        /*
        public Bitmap resizeBitmap(String drawableName,int width, int height){
            Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(drawableName, "drawable", getPackageName()));
            return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        }
        */

        /*Method used to add à marker and zoom on it */
        public void addMarker_Zoom (LatLng Coord) {

            // For dropping a marker Coord at a point on the MapActivity
            googleMap.addMarker(new MarkerOptions().position(Coord).draggable(true).title("Marker Title").snippet("Marker Description"));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(Coord).zoom(15).build();
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