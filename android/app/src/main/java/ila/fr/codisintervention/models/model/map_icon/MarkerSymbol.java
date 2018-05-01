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
import ila.fr.codisintervention.models.model.map_icon.symbol.Payload;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.utils.MarkerUtility;

public class MarkerSymbol implements I_MarkerElement {
    Symbol data;
    Activity activity;

    public MarkerSymbol(Symbol s, Activity activity){
        this.data = s;
        this.activity = activity;
    }

    @Override
    public Symbol getData() {
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

            Payload payload = data.getPayload();
            if (payload != null)
                details = payload.getDetails();

            LatLng coord = new LatLng(data.getLocation().getLat(), data.getLocation().getLng());
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
        this.getData().setLocation(new Location(marker.getPosition().latitude,marker.getPosition().longitude));
        int id = ((MapActivity)activity).getModelService().getCurrentIntervention().getId();
        List<Symbol> maListe = new ArrayList<Symbol>();
        maListe.add(getData());
        ((MapActivity) activity).getWebSocketService().updateSymbols(id, maListe);
        // TODO see method on server side symbolsocketcontroller
    }

    @Override
    public void createObjectOnMap() {
        int id = ((MapActivity)activity).getModelService().getCurrentIntervention().getId();
        List<Symbol> maListe = new ArrayList<Symbol>();
        maListe.add(getData());
        ((MapActivity) activity).getWebSocketService().createSymbols(id, maListe);
    }
}
