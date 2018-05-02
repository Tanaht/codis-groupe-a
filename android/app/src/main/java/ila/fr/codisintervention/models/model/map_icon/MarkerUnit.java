package ila.fr.codisintervention.models.model.map_icon;

import android.app.Activity;
import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.activities.MapActivity;
import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.model.Unit;
import ila.fr.codisintervention.models.model.map_icon.symbol.Payload;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.utils.MarkerUtility;

public class MarkerUnit implements I_MarkerElement{
    Unit data;
    Activity activity;

    public MarkerUnit(Unit u,Activity activity){
        data = u;
        this.activity = activity;
    }

    @Override
    public Unit getData() {
        return data;
    }

    @Override
    public MarkerOptions getMarker() {
        Integer drawablePath = MarkerUtility.getDrawablePath(this,activity);
        Bitmap bitmap = null;
        MarkerOptions mo = null;
        String details = "";
        if (drawablePath > 0) { //the picture doesn't exist if =0
            bitmap = MarkerUtility.resizeBitmap(drawablePath, 50, 50, activity);

            Payload payload = data.getSymbolUnit().getPayload();
            if (payload != null)
                details = payload.getDetails();
            LatLng coord = new LatLng(data.getSymbolUnit().getLocation().getLat(), data.getSymbolUnit().getLocation().getLng());
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
        if (mo != null)
            mark = map.addMarker(mo);
        return mark;
    }

    @Override
    public void udpatefromDragAndDrop(Marker marker) {
        this.getData().getSymbolUnit().setLocation(new Location(marker.getPosition().latitude,marker.getPosition().longitude));
        int id = ((MapActivity)activity).getModelService().getCurrentIntervention().getId();
        //((MapActivity) activity).getWebSocketService().updateUnits(id, getData());
        //TODO create this method on websoketService
    }

    @Override
    public void createObjectOnMap() {
        int id = ((MapActivity)activity).getModelService().getCurrentIntervention().getId();
        //((MapActivity) activity).getWebSocketService().requestUnit(id, getData());
        //TODO create this method on websoketService
    }
}
