package ila.fr.codisintervention.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
import ila.fr.codisintervention.binders.WebsocketServiceBinder;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.services.constants.ModelConstants;
import ila.fr.codisintervention.services.model.ModelService;
import ila.fr.codisintervention.services.websocket.WebsocketService;
import ila.fr.codisintervention.utils.InterventionListAdapter;

/**
 * This activity is used to show to the user the List of Interventions in progress
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class InterventionsListActivity extends AppCompatActivity {
    protected static final String TAG = "InterventionsListAct";

    /**
     * InterventionList Adapter between model and view.
     */
    InterventionListAdapter dataAdapter;

    /**
     * ServiceConnection instance with the WebSocketService
     */
    private ServiceConnection webSocketServiceConnection;

    /**
     * Interface delivered by WebSocketService to be used by other android Component.
     */
    private WebsocketServiceBinder.IMyServiceMethod webSocketService;

    /**
     * ServiceConnection instance with the ModelService
     */
    private ServiceConnection modelServiceConnection;

    /**
     * Interface delivered by ModelService to be used by other android Component, the purpose of this is to update the model.
     */
    private ModelServiceBinder.IMyServiceMethod modelService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interventions_list);
        setTitle(R.string.IntervenantListPageTitle);

        bindToService();
    }

    /**
     * TODO: It could be mutualized because almost all Activities has to bind to ModelService or WebSocketService -> A separated class that do that has to be created ! like an Interface ModelServiceAware and WebsocketServiceAware, or a superclass Activity aware of services
     * Method used to bind InterventionListActivity to WebsocketService and ModelService, with that, InterventionListActivity is aware of ModelService and WebSocketService
     */
    private void bindToService() {
        webSocketServiceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The Service " + name + " is disconnected");
            }
            public void onServiceConnected(ComponentName arg0, IBinder binder) {
                webSocketService = ((WebsocketServiceBinder)binder).getService();
            }
        };

        modelServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                //on récupère l'instance du modelService dans l'activité
                modelService = ((ModelServiceBinder)binder).getService();
                Log.d(TAG, "ModelService connected: " + modelService.getInterventions());
                if(modelService.getInterventions() == null || modelService.getInterventions().size() == 0){
                    TextView tv = (TextView) findViewById(R.id.IntvEmptyMsg);
                    tv.setText(R.string.noInterventionAvailable);
                    Toasty.warning(getApplicationContext(),
                            getString(R.string.noInterventionAvailable), Toast.LENGTH_SHORT, true)
                            .show();
                } else {
                    displayListView(modelService.getInterventions());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The Service " + name + " is disconnected");
            }
        };

        //Binding Activity with WebSocketService
        startService(new Intent(this, WebsocketService.class));
        Intent intent = new Intent(this, WebsocketService.class);
        //launch binding of WebSocketService
        bindService(intent, webSocketServiceConnection, Context.BIND_AUTO_CREATE);

        //Binding Activity with ModelService
        startService(new Intent(this, ModelService.class));
        intent = new Intent(this, ModelService.class);
        //launch binding of ModelService
        bindService(intent, modelServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Method used to display list in interface,
     * In this method we instanciate {@link InterventionListAdapter} and initialize correct ClickListener on Intervention Clicked.
     * @param interventionList list to display
     */
    private void displayListView(List<Intervention> interventionList){

        //create an ArrayAdapter from the String Array
        dataAdapter = new InterventionListAdapter(this,
                R.layout.interventions_list_item_layout, (ArrayList) interventionList);
        ListView listView = (ListView) findViewById(R.id.interventionsList);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // When clicked, show a toast with the TextView text
            Intervention intervention = (Intervention) parent.getItemAtPosition(position);

            Toasty.info(getApplicationContext(),
                    "Intervention with id:" + intervention.getId() + " has been sent to wss",
                    Toast.LENGTH_SHORT, true)
                    .show();

            // Send Intervention choice to WSS
            webSocketService.chooseIntervention(intervention.getId());
            Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(mapIntent);
        });
    }

    /**
     * Add new {@link Intervention}, and notify to the adapter that intervention has been added
     * @param intervention : the new intervention
     */
    private void addElement(Intervention intervention) {
        // on insère l'intervention dans la liste des interventions liée à l'adapter
        dataAdapter.add(intervention);
        // on notifie à l'adapter ce changement
        dataAdapter.notifyDataSetChanged();
    }


    /**
     * Delete {@link Intervention} , and notify to the adapter that Intervention has been deleted
     * @param position : the position of the Intervention to delete
     */
    public void deleteElement(int position) {
        // on supprime l'intervention
        dataAdapter.remove(dataAdapter.getItem(position));
        // on notifie à l'adapter ce changement
        dataAdapter.notifyDataSetChanged();
    }

    /**
     * Subscribe to Intents that will be used to update this Activity Model.
     */
    @Override
    public void onResume(){
        super.onResume();
        IntentFilter interventionListIntentFilter = new IntentFilter();
        interventionListIntentFilter.addAction(ModelConstants.ACTION_ADD_INTERVENTION);
        interventionListIntentFilter.addAction(ModelConstants.ACTION_DELETE_INTERVENTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, interventionListIntentFilter);
    }

    /**
     * TODO: To mutualize equally with BindToService method
     * Define BroadcoastReceiver Instance to get aware when an Intent is send to this activity among other
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = (int) intent.getExtras().get("id");
            Intervention intervention = modelService.getInterventions().get(id);
            switch (intent.getAction()){
                case ModelConstants.ACTION_ADD_INTERVENTION:
                    addElement(intervention);
                    break;
                case ModelConstants.ACTION_DELETE_INTERVENTION:
                    int position = dataAdapter.getPosition(intervention);
                    deleteElement(position);
                    break;
                default:
                    break;
            }
        }
    };

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
        //on supprimer le binding entre l'activité et le websocketService.
        if(webSocketServiceConnection != null)
            unbindService(webSocketServiceConnection);

        if(modelServiceConnection != null)
            unbindService(modelServiceConnection);
    }
}