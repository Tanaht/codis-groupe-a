package ila.fr.codisintervention.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.R;

/**
 * Created by aminesoumiaa on 27/03/18.
 *
 * Used to propose to user an Autocomplete EditText to find addresses.
 * It use Google Map API
 */

public class AutocompleteAdapter extends ArrayAdapter implements Filterable {
    private static final String TAG = "AutocompleteAdapter";

    /**
     * Template URL of Rest Service Request for autocomplete Address
     * FIXME: Why do we use the rest service like that instead of using the feature natively provided by Android API of Google Map API
     * @see <a href="Google MAP Places API">https://developers.google.com/places/android-api/autocomplete?hl=fr</a>
     */
    private static final String REST_REQUEST_TPL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?key={key}&components=country:fr&input={input}";
    /**
     * API KEY used to authenticate to Google Map API
     */
    private String apiKey;

    /**
     * Results of autocompletion I guess.
     */
    private List<String> results;

    /**
     * Constructor
     * @param context the context of where the Adapter is used
     * @param textViewResourceId The TextView associated with this AutocompleteAdapter
     */
    public AutocompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        apiKey = context.getString(R.string.google_maps_key);
    }


    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public String getItem(int index) {
        return results.get(index);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    results = autocomplete(constraint.toString());
                    filterResults.values = results;
                    filterResults.count = results.size();
                }

                Log.d(TAG, "Autocomplete results for '" + constraint + "': " + filterResults.count);
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    /**
     * This method send the request to server to perform autocompletion and it parse response to build a list of addresses
     * @param input the input that will be autocompleted
     * @return a list of potential addresses
     */
    @SuppressWarnings("squid:S2093")
    private List<String> autocomplete(String input) {
        List<String> autocompleteResults = new ArrayList<>();

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            String generatedUrl = REST_REQUEST_TPL
                    .replace("{key}", apiKey)
                    .replace("{input}", URLEncoder.encode(input, "utf8"));
            URL url = new URL(generatedUrl);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());


            int read;
            // FIXME: we can't assume the size of the response like that, it's an HTTP protocol can't we get size from the HTTP header ?
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return autocompleteResults;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return autocompleteResults;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predictions = jsonObj.getJSONArray("predictions");

            autocompleteResults = new ArrayList<>(predictions.length());
            for (int i = 0; i < predictions.length(); i++) {
                String address = predictions.getJSONObject(i).getString("description");
                Log.v(TAG, "an Autocomplete possibility for '" + input + "' is '"  + address + "'");
                autocompleteResults.add(address);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Cannot parse retrieved places", e);
        }

        return autocompleteResults;
    }
}
