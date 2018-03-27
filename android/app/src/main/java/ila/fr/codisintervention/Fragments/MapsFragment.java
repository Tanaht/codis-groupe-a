    package ila.fr.codisintervention.Fragments;

    import android.Manifest;
    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.content.pm.PackageManager;
    import android.net.Uri;
    import android.os.Bundle;
    import android.app.Fragment;
    import android.support.v4.app.ActivityCompat;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.MapView;
    import com.google.android.gms.maps.MapsInitializer;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.model.CameraPosition;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.MarkerOptions;

    import ila.fr.codisintervention.R;


    public class MapsFragment extends Fragment {
        MapView mMapView;
        private GoogleMap googleMap;
        private final static int LOCATION_REQ_CODE=456;

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

                    LatLng surprise = new LatLng(48.873756, 2.294946);
                    addMarker_Zoom(surprise);
                }
            });

            return rootView;
        }

        public void addMarker_Zoom (LatLng Coord) {

            // For dropping a marker Coord at a point on the MapActivity
            googleMap.addMarker(new MarkerOptions().position(Coord).title("Marker Title").snippet("Marker Description"));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(Coord).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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