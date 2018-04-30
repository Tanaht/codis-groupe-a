package ila.fr.codisintervention.utils;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import ila.fr.codisintervention.models.PhotoReception;


public class PhotoCommunicationToolBox {


    private Context context;

    public PhotoCommunicationToolBox(Context context) {
        this.context = context;
    }


    public void sendRequestForAddress(String address, final ServerCallback serverCallback) {
        String url = "/topic/interventions/{id}/photo";
        RequestQueue queue = Volley.newRequestQueue(this.context);
        //Send the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            if (response.length() != 0) {
                serverCallback.onSuccess(response);
            }
        }, error -> Log.e("TAG", "Error during Server request to get pictures"));
        queue.add(jsonObjectRequest);
    }


    public PhotoReception getPhotosFromServerApiResult(JSONObject requestResult) {
        Gson gson = new Gson();


        PhotoReception reception = gson.fromJson(requestResult.toString(), PhotoReception.class);
        if (reception.isPhotoReceptionEmpty()) {
            return reception;
        }
        return null;
    }
}
