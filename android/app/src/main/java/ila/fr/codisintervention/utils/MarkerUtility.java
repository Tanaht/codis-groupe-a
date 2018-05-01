package ila.fr.codisintervention.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.activities.MapActivity;
import ila.fr.codisintervention.entities.SymbolKind;
import ila.fr.codisintervention.models.DronePoint;
import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.model.Unit;
import ila.fr.codisintervention.models.model.map_icon.Color;
import ila.fr.codisintervention.models.model.map_icon.I_MarkerElement;
import ila.fr.codisintervention.models.model.map_icon.MarkerDrone;
import ila.fr.codisintervention.models.model.map_icon.MarkerSymbol;
import ila.fr.codisintervention.models.model.map_icon.MarkerUnit;
import ila.fr.codisintervention.models.model.map_icon.Shape;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;

public class MarkerUtility {
    static public Integer getDrawablePath(I_MarkerElement ms, Activity activity){
        String str = "";
        if (ms instanceof MarkerSymbol)
            str = ((MarkerSymbol)ms).getData().getColor() + ((MarkerSymbol)ms).getData().getShape().name();
        else if (ms instanceof MarkerUnit)
            str = ((MarkerUnit)ms).getData().getSymbolUnit().getColor() + ((MarkerUnit)ms).getData().getSymbolUnit().getShape().name();
        else if (ms instanceof MarkerDrone)
            str = ((MarkerDrone)ms).getResourcePath();
        return activity.getResources().getIdentifier(str.toLowerCase(), "drawable", activity.getPackageName());
    }

    static public Bitmap resizeBitmap(int drawablePath, int targetWidth, int targetHeight, Activity activity) {
        BitmapDrawable bitmapdraw = (BitmapDrawable) activity.getResources().getDrawable(drawablePath);
        Bitmap b = bitmapdraw.getBitmap();
        return Bitmap.createScaledBitmap(b, targetWidth, targetHeight, false);
    }

    static public boolean createObjectOnMap(LatLng latLng, SymbolKind symbole, Activity activity){
        Color color = Color.valueOf(symbole.getColor().toUpperCase());
        Shape shape = Shape.valueOf(symbole.getId().toUpperCase());
        Location location = new Location(latLng.latitude,latLng.longitude);

        I_MarkerElement myObject = null;
        if (Shape.findAssociatedObject(shape).equals(Shape.SYMBOL)) {
            Symbol mySymbol = new Symbol(location, color, shape);
            myObject = new MarkerSymbol(mySymbol,activity);
            myObject.createObjectOnMap();
        }
        else if (Shape.findAssociatedObject(shape).equals(Shape.UNIT)) {
            //ask for a new Unit
//            Unit myUnit = new Unit(location, color, shape);
//            myObject = new MarkerUnit(myUnit,activity);
//            myObject.createObjectOnMap();
        }else if (Shape.findAssociatedObject(shape).equals(Shape.DRONE_POINT)) {
            DronePoint dp = new DronePoint(((MapActivity)activity).getCptId(), latLng.latitude,latLng.longitude);
            myObject = new MarkerDrone(false, dp, activity);
            myObject.createObjectOnMap();
            ((MapActivity)activity).increaseCptId();
        }

        return (myObject.getMarker()!=null);
    }


}
