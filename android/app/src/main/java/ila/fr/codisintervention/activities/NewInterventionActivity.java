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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.binders.WebSocketServiceBinder;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;
import ila.fr.codisintervention.models.model.Position;
import ila.fr.codisintervention.models.model.InterventionModel;
import ila.fr.codisintervention.services.model.ModelService;
import ila.fr.codisintervention.services.websocket.WebsocketService;
import ila.fr.codisintervention.utils.AutocompleteAdapter;
import ila.fr.codisintervention.utils.VehiclesListAdapter;

/**
 * This activity manage the interface used by Codis User to create a new Intervention
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class NewInterventionActivity extends AppCompatActivity {
    private static final String TAG = "NewInterventionActivity";

    /**
     * An Adapter used to adapt the Model of a Vehicle with it's representation in the interface.
     */
    VehiclesListAdapter dataAdapter;

    /**
     * String that represent the address of the intervention filled by the user.
     */
    String inputAddress;

    /**
     * Tuple of Lat and Lng coordinate that reflect the address from inputAddress.
     * @see #getLocationFromAddress(String inputAddress)
     */
    LatLng latlngAddress;

    /**
     * ServiceConnection instance with the WebSocketService
     */
    private ServiceConnection webSocketServiceConnection;

    /**
     * Interface delivered by WebSocketService to be used by other android Component.
     */
    private WebSocketServiceBinder.IMyServiceMethod webSocketService;

    /**
     * ServiceConnection instance with the ModelService
     */
    private ServiceConnection modelServiceConnection;

    private ModelServiceBinder.IMyServiceMethod modelService;

    /**
     * FIXME: Bad Idea to initialize view in on create, it will be far better to do it onModelServiceConnected (because it's only when model is available that you have access to the datas)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_intervention);
        setTitle(R.string.title_add_new_intervention);

        autocompleteTextViewInitialization();

        bindToService();
    }

    /**
     * This method initialize the AutocompleteTextview it rely on {@link AutocompleteAdapter}
     */
    private void autocompleteTextViewInitialization() {
        AutoCompleteTextView autoCompView = findViewById(R.id.autocomplete_text_view);
        autoCompView.setAdapter(new AutocompleteAdapter(this, R.layout.list_item));

        autoCompView.setOnItemClickListener((parent, view, position, id) -> {
            inputAddress = (String) parent.getItemAtPosition(position);
            Log.d(TAG, "Set address to: " + inputAddress);
        });
    }

    /**
     * TODO: It could be mutualized because almost all Activities has to bind to ModelService or WebSocketService -> A separated class that do that has to be created ! like an Interface ModelServiceAware and WebsocketServiceAware, or a superclass Activity aware of services
     * Method used to bind NewInterventionActivity to WebsocketService, with that, NewInterventionActivity is aware of WebSocketService
     */
    private void bindToService() {

        modelServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                // we retrieve the modelService instance in the activity
                modelService = ((ModelServiceBinder)binder).getService();
                Log.d(TAG, "ModelService connected: " + modelService.getInterventions());

                List<String> codesSinistre = modelService.getSinisterCodes();
                displaySpinner(codesSinistre);

                List<Vehicle> vehiclesIntervention = modelService.getAvailableVehicle();
                displayListView(vehiclesIntervention);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The Service " + name + " is disconnected");
            }
        };
        //Binding Activity with ModelService
        startService(new Intent(this, ModelService.class));
        Intent intent = new Intent(this, ModelService.class);
        //launch binding of ModelService
        bindService(intent, modelServiceConnection, Context.BIND_AUTO_CREATE);


        webSocketServiceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The Service " + name + " is disconnected");
            }
            public void onServiceConnected(ComponentName arg0, IBinder binder) {

                // we retrieve the webSocketService instance in the activity
                webSocketService = ((WebSocketServiceBinder)binder).getService();
            }
        };

        startService(new Intent(getApplicationContext(), WebsocketService.class));
        intent = new Intent(getApplicationContext(), WebsocketService.class);
        // start the webSocketService binding
        bindService(intent, webSocketServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Method used to filled a dropdown list of SinisterCode send in parameter
     * @param sinisterCode the list of sinisterCode available
     */
    private void displaySpinner(List<String> sinisterCode){
        Spinner spinnerCodes = (Spinner) findViewById(R.id.CodeList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sinisterCode);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCodes.setAdapter(adapter);
    }

    /**
     * Used to fill the ListView of Vehicle according to vehicles send in parameter.
     * @param vehicles the list of vehicles used to hydrate the ListView
     */
    private void displayListView(List<Vehicle> vehicles){
        dataAdapter = new VehiclesListAdapter(this, R.layout.moyen_infos_layout, vehicles);

        ListView listView = (ListView) findViewById(R.id.vehicles_list);
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Vehicle vehicle = (Vehicle) parent.getItemAtPosition(position);
            Log.d(TAG, "VEHICLES ListView: item being clicked: " + vehicle.getLabel());
        });

    }


    /**
     * Method triggered by the button R.id.submitNewIntervention
     * this method check validity of intervention form filled by the user and send it throug the WebSocketService
     * @see WebSocketServiceBinder.IMyServiceMethod#createIntervention(InterventionModel)
     * @param v the view
     */
    public void submitNewInterventionAction(View v) {
        List<Vehicle> vehiclesList = dataAdapter.getVehiclesList();

        for(int i = 0; i< vehiclesList.size(); i++){
            Vehicle vehicle = vehiclesList.get(i);
            if(vehicle.isSelected()){
                //TODO: we donothing for now
            }
        }
        String sinisterCode = ((Spinner)findViewById(R.id.CodeList)).getSelectedItem().toString();

        // Check if address is set
        if(inputAddress.equals("")){
            Toasty.error(getApplicationContext(), getString(R.string.error_address_field_empty), Toast.LENGTH_LONG).show();
        } else {

            InterventionModel intervention = new InterventionModel();
            intervention.setAddress(inputAddress);
            intervention.setSinisterCode(sinisterCode);

            latlngAddress = getLocationFromAddress(intervention.getAddress());
            Log.d(TAG, latlngAddress == null ? "LatLng is null" : "LatLng is not null");

            if(latlngAddress != null) {
                intervention.setPosition(new Position(latlngAddress.latitude, latlngAddress.longitude));
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
