package ila.fr.codisintervention.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.binders.WebSocketServiceBinder;
import ila.fr.codisintervention.exception.RequestNotFoundException;
import ila.fr.codisintervention.exception.VehicleNotFoundException;
import ila.fr.codisintervention.models.model.Request;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;
import ila.fr.codisintervention.services.ModelServiceAware;
import ila.fr.codisintervention.services.WebSocketServiceAware;
import ila.fr.codisintervention.services.constants.ModelConstants;
import ila.fr.codisintervention.utils.RequestsListAdapter;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class CodisRequestListActivity extends AppCompatActivity implements WebSocketServiceAware, ModelServiceAware{

    private static final String TAG = "CodisRequestListAct";

    /**
     * Service connection of the service subscribed
     */
    private ServiceConnection webSocketServiceConnection;

    /**
     * Interface delivered by WebSocketService to be used by other android Component.
     */
    private WebSocketServiceBinder.IMyServiceMethod webSocketService;

    /**
     * Service connection of the service subscribed
     */
    private ServiceConnection modelServiceConnection;

    /**
     * Binder for the model service
     */
    private ModelServiceBinder.IMyServiceMethod modelService;

    /**
     * An Adapter used to adapt the Model of a Vehicle Requests with it's representation in the interface.
     */
    RequestsListAdapter dataAdapter;

    private Request currentRequest;
    private Vehicle chosenVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codis_request_list);
        setTitle(R.string.title_requests_in_progress_list);

        //Here we call the aware interface to perform the binding at our place, we gain an instance of ServiceConnection
        webSocketServiceConnection = bindWebSocketService();
        modelServiceConnection = bindModelService();
    }

    /**
     * Used to fill the ListView of Vehicle requests according to request list sent in parameter.
     * @param requests the list of vehicle requests used to hydrate the ListView
     */
    private void displayRequestListView(List<Request> requests){
        //create an ArrayAdapter from the String Array
        dataAdapter = new RequestsListAdapter(this,
                R.layout.requests_list_item_layout, (ArrayList) requests);
        ListView listView = (ListView) findViewById(R.id.requestList);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // When clicked, show a toast with the TextView text
            this.currentRequest = (Request) parent.getItemAtPosition(position);

            // show popUp to accept or deny the request
            showPopUpToValidateRequest();
        });
    }

    /**
     * Method triggered when the ModelService is connected
     */
    @Override
    public void onModelServiceConnected() {
        initializeView();
    }

    private void initializeView() {
        if(modelService.getRequests() == null || modelService.getRequests().size() == 0){
            TextView tv = (TextView) findViewById(R.id.requestListEmptyMsg);
            tv.setText(R.string.msg_no_request_in_progress);
            Toasty.warning(getApplicationContext(),
                    getString(R.string.msg_no_request_in_progress), Toast.LENGTH_SHORT, true)
                    .show();
        } else {
            displayRequestListView(modelService.getRequests());
        }
    }

    /**
     * Method triggered when the ModelService is connected, it return the instance of the service, it's all the point of this interface.
     *
     * @param modelService the ModelService instance
     */
    @Override
    public void setModelService(ModelServiceBinder.IMyServiceMethod modelService) {
        this.modelService = modelService;
    }

    /**
     * Method triggered when the WebSocketService is connected
     */
    @Override
    public void onWebSocketServiceConnected() {

    }

    /**
     * Method triggered when the WebSocketService is connected, it return the instance of the service, it's all the point of this interface.
     *
     * @param webSocketService the WebSocketService instance
     */
    @Override
    public void setWebSocketService(WebSocketServiceBinder.IMyServiceMethod webSocketService) {
        this.webSocketService = webSocketService;
    }

    /**
     * Define BroadcoastReceiver Instance to get aware when an Intent is send to this activity among other
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null || intent.getAction() == null) {
                Log.w(TAG, "Intent is null or no action defined");
                return;
            }
            int id = intent.getIntExtra("id", -1);

            if(id == -1) {
                Log.w(TAG, "No requested ID defined in extra");
            }

            int pos = -1;

            Request request = null;

            try {
                request = modelService.getRequestById(id);
            } catch (RequestNotFoundException e) {
                Log.e(TAG, "onReceive: try to access to a vehicle request which doesn't exist");
                e.printStackTrace();
            }
            switch (intent.getAction()){
                case ModelConstants.ADD_VEHICLE_REQUEST:
                    addElement(request);
                    break;
                case ModelConstants.VALIDATE_VEHICLE_REQUEST:
                case ModelConstants.REJECT_VEHICLE_REQUEST:
                    pos = dataAdapter.getPosition(request);
                    deleteElement(pos);
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * Add new {@link Request}, and notify to the adapter that request has been added
     * @param request : the new request
     */
    private void addElement(Request request) {
        // we insert the request into the vehicles requests list linked to the adapter
        dataAdapter.add(request);
        // we notify this change to the adapter
        dataAdapter.notifyDataSetChanged();
    }


    /**
     * Delete {@link Request} , and notify to the adapter that Request has been deleted
     * @param position : the position of the Request to delete
     */
    public void deleteElement(int position) {
        // we remove the request from the list
        dataAdapter.remove(dataAdapter.getItem(position));
        // we notify this change to the adapter
        dataAdapter.notifyDataSetChanged();
    }

    private CharSequence[] getAvailableVehicleLabels(String type) {
        List<Vehicle> availableVehicles = modelService.getAvailableVehiclesByType(type);
        List<CharSequence> labels = new ArrayList<>();

        for(Vehicle vehicle : availableVehicles) {
            labels.add(vehicle.getLabel());
        }

        CharSequence[] charSequences = new CharSequence[labels.size()];
        labels.toArray(charSequences);
        return charSequences;
    }

    /**
     * Accept or deny the current request
     * @see #currentRequest
     */
    private void showPopUpToValidateRequest() {
        final CharSequence[] vehicleLabels = getAvailableVehicleLabels(currentRequest.getVehicle().getType());

        AlertDialog.Builder builder = new AlertDialog.Builder(CodisRequestListActivity.this);
        builder.setTitle(R.string.label_choose_vehicle);

        builder.setSingleChoiceItems(vehicleLabels, -1, (dialog, item) -> {
            try {
                this.chosenVehicle = modelService.getVehicleByLabel(vehicleLabels[item].toString());
            }
            catch (VehicleNotFoundException e) {
                Log.e(TAG, "Vehicle Not Found here: " + e.getMessage());
            }
        });

        builder.setPositiveButton(R.string.label_validate, (dialog, id) -> {
            Toasty.success(getApplicationContext(), "Request Accepted", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Accept " + id);
            currentRequest.setVehicle(chosenVehicle);
            webSocketService.acceptVehicleRequest(currentRequest);
        });


        builder.setNegativeButton(R.string.label_deny, (dialog, id) -> {
            Toasty.warning(getApplicationContext(), "Request Denied", Toast.LENGTH_SHORT).show();
            webSocketService.denyVehicleRequest(currentRequest);
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Subscribe to Intents that will be used to update this Activity Model.
     */
    @Override
    public void onResume(){
        super.onResume();
        IntentFilter interventionListIntentFilter = new IntentFilter();
        interventionListIntentFilter.addAction(ModelConstants.ADD_VEHICLE_REQUEST);
        interventionListIntentFilter.addAction(ModelConstants.VALIDATE_VEHICLE_REQUEST);
        interventionListIntentFilter.addAction(ModelConstants.REJECT_VEHICLE_REQUEST);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, interventionListIntentFilter);

        if(modelService != null)
            initializeView();
    }

    /**
     * Unsubscribe from Broadcoast Receiver instance
     */
    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    /**
     * We Unbind from binded service here to avoid memory leak
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //We unbind from the registered services
        if(webSocketServiceConnection != null)
            unbindService(webSocketServiceConnection);
        if(modelServiceConnection != null)
            unbindService(modelServiceConnection);
    }
}
