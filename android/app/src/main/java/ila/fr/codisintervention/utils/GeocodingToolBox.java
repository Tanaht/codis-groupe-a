package ila.fr.codisintervention.utils;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ila.fr.codisintervention.R;

/**
 * A class allowing to send http request to Google Map Api to get coordinate from an adress
 */
public class GeocodingToolBox {

    private static final String TAG = "GeocodingToolBox";

    /**
     * The url to send is of the form : https://maps.googleapis.com/maps/api/geocode/outputFormat?parameters
     * Here, outputFormat is json
     * Parameters needs to be added
     */
    private static final String GOOGLE_API_URL_FORM = "https://maps.googleapis.com/maps/api/geocode/json?";
    private static final String GOOGLE_API_PARAMETER_ADDRESS = "address=";
    private static final String GOOGLE_API_PARAMETER_KEY = "&key=";

    private static final String JSON_KEY_RESULTS = "results";
    private static final String JSON_KEY_GEOMETRY = "geometry";
    private static final String JSON_KEY_LOCATION = "location";
    private static final String JSON_KEY_LAT = "lat";
    private static final String JSON_KEY_LNG = "lng";

    private Context context;

    public GeocodingToolBox(Context context){
        this.context = context;
    }

    /**
     * Construct the url for the request
     *
     * @param address needed for parameters of the request
     * @return the google api request
     */
    private String getUrlFromAdress(String address) throws UnsupportedEncodingException {
        String key = this.context.getString(R.string.google_maps_key);
        return GOOGLE_API_URL_FORM + GOOGLE_API_PARAMETER_ADDRESS + URLEncoder.encode(address, "utf-8") + GOOGLE_API_PARAMETER_KEY + key;
    }

    /**
     * Send a GoogleMapApi to get response for the given address
     * @param address
     * @param serverCallback
     */
    public void sendRequestForAddress(String address, final ServerCallback serverCallback) {
        try {
            String url = this.getUrlFromAdress(address);
            RequestQueue queue = Volley.newRequestQueue(this.context);
            //Send the request
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                if (response.length() != 0) {
                    serverCallback.onSuccess(response);
                }
            }, error -> {
                Log.e(TAG, "Error during GoogleMapApi request");
                serverCallback.onError();
            });

            queue.add(jsonObjectRequest);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage(), e);
            serverCallback.onError();
        }
    }

    /**
     * Extract location from a result given by a googleMapApi request
     * @param requestResult JSONObject
     */
    public LatLng getLocationFromGoogleApiResult(JSONObject requestResult){
        try {
            JSONArray results = requestResult.getJSONArray(JSON_KEY_RESULTS);
            if(results.length()>0){
                JSONObject result = results.getJSONObject(0);
                JSONObject geometry = result.getJSONObject(JSON_KEY_GEOMETRY);
                JSONObject location = geometry.getJSONObject(JSON_KEY_LOCATION);
                Double lat = location.getDouble(JSON_KEY_LAT);
                Double lng = location.getDouble(JSON_KEY_LNG);
                return new LatLng(lat, lng);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }
}
