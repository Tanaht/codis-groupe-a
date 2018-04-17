package ila.fr.codisintervention.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.WebSocketServiceBinder;
import ila.fr.codisintervention.entities.Vehicle;
import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.services.InterventionService;
import ila.fr.codisintervention.services.websocket.WebsocketService;
import ila.fr.codisintervention.utils.GooglePlacesAutocompleteAdapter;
import ila.fr.codisintervention.utils.MoyenListAdapter;

/**
 * This activity manage the interface used by Codis User to create a new Intervention
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class NewInterventionActivity extends AppCompatActivity {
    private static final String TAG = "NewInterventionActivity";

    /**
     * TODO: To Remove and use the ModelService that provide all we need instead.
     * Stub to get access to static datas about interventions like SinisterCodes or Vehicles
     */
    InterventionService interventionService;

    /**
     * An Adapter used to adapt the Model of a Vehicle with it's representation in the interface.
     */
    MoyenListAdapter dataAdapter;

    /**
     * String that represent the address of the intervention filled by the user.
     */
    String inputtedAddress = "";

    /**
     * Tuple of Lat and Lng coordinate that reflect the address from inputtedAddress.
     * @see #getLocationFromAddress(String inputtedAddress)
     */
    LatLng latlngAddress = null;

    /**
     * ServiceConnection instance with the WebSocketService
     */
    private ServiceConnection webSocketServiceConnection;

    /**
     * Interface delivered by WebSocketService to be used by other android Component.
     */
    private WebSocketServiceBinder.IMyServiceMethod webSocketService;

    /**
     * FIXME: Bad Idea to initialize view in on create, it will be far better to do it onModelServiceConnected (because it's only when model is available that you have access to the datas)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);
        setTitle(R.string.title_add_new_intervention);

        // AutoComplete Address
        AutoCompleteTextView autoCompView = findViewById(R.id.autoCompleteTextView);
        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));

        autoCompView.setOnItemClickListener((parent, view, position, id) -> {
            inputtedAddress = (String) parent.getItemAtPosition(position);
            Log.d(TAG, "Set address to: " + inputtedAddress);
        });

        // get codes sinistre from server
        interventionService = new InterventionService();
        ArrayList<String> codesSinistre = interventionService.getCodesSinistre();
        // Code Sinistre List (Liste déroulante)
        displaySpinner(codesSinistre);

        // get moyenList from server
        ArrayList<Vehicle> vehiclesIntervention = interventionService.getMoyensDispo();
        // Vehicle List
        displayListView(vehiclesIntervention);

        // Bind activity to websocket webSocketService
        bindToService();
    }

    /**
     * TODO: It could be mutualized because almost all Activities has to bind to ModelService or WebSocketService -> A separated class that do that has to be created ! like an Interface ModelServiceAware and WebsocketServiceAware, or a superclass Activity aware of services
     * Method used to bind NewInterventionActivity to WebsocketService, with that, NewInterventionActivity is aware of WebSocketService
     */
    private void bindToService() {
        webSocketServiceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The Service " + name + " is disconnected");
            }
            public void onServiceConnected(ComponentName arg0, IBinder binder) {

                //on récupère l'instance du webSocketService dans l'activité
                webSocketService = ((WebSocketServiceBinder)binder).getService();
            }
        };

        startService(new Intent(getApplicationContext(), WebsocketService.class));
        Intent intent = new Intent(getApplicationContext(), WebsocketService.class);
        //lance le binding du webSocketService
        bindService(intent, webSocketServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Method used to filled a dropdown list of SinisterCode send in parameter
     * @param sinisterCode the list of sinisterCode available
     */
    private void displaySpinner(List<String> sinisterCode){
        Spinner spinnerCodes = (Spinner) findViewById(R.id.CodeList);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sinisterCode);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinners
        spinnerCodes.setAdapter(adapter);
    }

    /**
     * Used to fill the ListView of Vehicle according to vehicles send in parameter.
     * @param vehicles the list of vehicles used to hydrate the ListView
     */
    private void displayListView(List<Vehicle> vehicles){

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MoyenListAdapter(this,
                R.layout.moyen_infos_layout, vehicles);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // When clicked, show a toast with the TextView text
            Vehicle vehicle = (Vehicle) parent.getItemAtPosition(position);
            Log.d(TAG, "VEHICLES ListView: item being clicked: " + vehicle.getName());
    });

    }


    /**
     * Method triggered by the button R.id.submitNewIntervention
     * this method check validity of intervention form filled by the user and send it throug the WebSocketService
     * @see WebSocketServiceBinder.IMyServiceMethod#createIntervention(Intervention)
     * @param v the view
     */
    public void submitNewInterventionAction(View v) {
        StringBuilder responseText = new StringBuilder();
        responseText.append("The following were selected...\n");

        List<Vehicle> vehiclesList = dataAdapter.getVehiclesList();
        for(int i = 0; i< vehiclesList.size(); i++){
            Vehicle vehicle = vehiclesList.get(i);
            if(vehicle.isSelected()){
                responseText.append("\n" + vehicle.getName());
            }
        }
        String sinisterCode = ((Spinner)findViewById(R.id.CodeList)).getSelectedItem().toString();

        // Check if address is set
        if(inputtedAddress.equals("")){
            Toasty.error(getApplicationContext(), getString(R.string.error_address_field_empty), Toast.LENGTH_LONG).show();
        } else {

            Intervention intervention = new Intervention();
            intervention.setAddress(inputtedAddress);
            intervention.setCode(sinisterCode);

            latlngAddress = getLocationFromAddress(intervention.getAddress());
            Log.d(TAG, latlngAddress == null ? "LatLng is null" : "LatLng is not null");

            if(latlngAddress != null) {
                intervention.setLocation(new Location(latlngAddress.latitude, latlngAddress.longitude));
                // TODO intervention.setUnit(..) when available
                // Send Intervention Details to WSS
                webSocketService.createIntervention(intervention);

                // Intent to Intervention List Activity
                Intent intent = new Intent( getApplicationContext(), CodisMainMenu.class);
                startActivity(intent);
            } else {

                Toasty.error(getApplicationContext(),getString(R.string.error_converting_address2geocode), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * TODO: Make this raise an exception instead of returning null when error.
     * Method used tor perform the geocoding from string address.
     * @param inputtedAddress the address
     * @return a LatLng instance or null if failed
     */
    private LatLng getLocationFromAddress(String inputtedAddress) {
        if(!Geocoder.isPresent()) {
            Log.w(TAG, "Geocoder Method not implemented");
        }
        Geocoder coder = new Geocoder(getApplicationContext(), Locale.getDefault());

        List<Address> address = null;
        LatLng resLatLng = null;
        try {
            // May throw an IOException

            address = coder.getFromLocationName(inputtedAddress, 1);
            if (address == null) {
                Log.w(TAG, "Address is null for '" + inputtedAddress + "'");
                return null;

            }

            if (address.isEmpty()) {
                Log.w(TAG, "There is no address for '"+ inputtedAddress + "'");
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            Log.d(TAG, "'" + inputtedAddress + "' converted to [" + location.getLatitude() + ", " + location.getLongitude() + "]");

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }

        Log.d(TAG, "getLocationFromAddress retrieved: " + resLatLng);

        return resLatLng;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webSocketServiceConnection != null)
            unbindService(webSocketServiceConnection);
    }
}
