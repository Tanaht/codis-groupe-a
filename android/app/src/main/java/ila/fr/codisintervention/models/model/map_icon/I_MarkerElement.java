package ila.fr.codisintervention.models.model.map_icon;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public interface I_MarkerElement {

    // Symbol, Unit or DronePoint
    abstract public Object getData();
    abstract public MarkerOptions getMarker();
    abstract public Marker printOnMap(GoogleMap map);
    abstract public void udpatefromDragAndDrop(Marker marker);
    abstract public void createObjectOnMap();
}
