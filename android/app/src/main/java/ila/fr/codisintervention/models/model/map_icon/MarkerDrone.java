package ila.fr.codisintervention.models.model.map_icon;

import android.app.Activity;
import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ila.fr.codisintervention.models.DronePoint;
import ila.fr.codisintervention.models.model.map_icon.symbol.Payload;
import ila.fr.codisintervention.utils.MarkerUtility;

public class MarkerDrone implements I_MarkerElement {
    DronePoint data;
    Activity activity;
    boolean isDrone;


    public MarkerDrone(boolean isDrone, DronePoint point, Activity activity){
        this.data = point;
        this.activity=activity;
        this.isDrone = isDrone;
    }

    public String getResourcePath(){
        return (data.isMoving()?"drone_icon_map":"drone_marker");
    }

    @Override
    public DronePoint getData() {
        return this.data;
    }

    @Override
    public MarkerOptions getMarker() {
        Integer drawablePath = MarkerUtility.getDrawablePath(this,activity);
        Bitmap bitmap = null;
        MarkerOptions mo = null;
        String details = "";
        if (drawablePath > 0) { //the picture doesn't exist if =0
            bitmap = MarkerUtility.resizeBitmap(drawablePath, 50, 50, activity);

            details = ""+data.getId();

            LatLng coord = new LatLng(data.getLat(), data.getLon());
            mo = new MarkerOptions()
                    .position(coord)
                    .draggable(true)
                    .title(details)
                    .snippet("")
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(bitmap));
        }
        return mo;
    }

    @Override
    public Marker printOnMap(GoogleMap map) {
        MarkerOptions mo = getMarker();
        Marker mark = null;
        if (mo != null) {
            mark = map.addMarker(mo);
            mark.showInfoWindow();
        }
        return mark;
    }

    @Override
    public void udpatefromDragAndDrop(Marker marker) {

    }

    @Override
    public void createObjectOnMap() {

    }
}
