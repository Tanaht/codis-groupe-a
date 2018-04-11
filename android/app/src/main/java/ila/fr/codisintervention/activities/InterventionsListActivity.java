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
 * This activity is used to show to the user the List of Interventions
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class InterventionsListActivity extends AppCompatActivity {
    protected static final String TAG = "InterventionsListAct";

    InterventionListAdapter dataAdapter;

    private ServiceConnection serviceConnection;
    private WebsocketServiceBinder.IMyServiceMethod service;

    private ServiceConnection modelServiceConnection;
    private ModelServiceBinder.IMyServiceMethod modelService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interventions_list);
        setTitle(R.string.IntervenantListPageTitle);

        bindToService();
    }

    private void bindToService() {
        serviceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The service " + name + " is disconnected");
            }
            public void onServiceConnected(ComponentName arg0, IBinder binder) {

                //on récupère l'instance du service dans l'activité
                service = ((WebsocketServiceBinder)binder).getService();
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
                Log.w(TAG, "The service " + name + " is disconnected");
            }
        };


        //démarre le service si il n'est pas démarré
        //Le binding du service est configuré avec "BIND_AUTO_CREATE" ce qui normalement
        //démarre le service si il n'est pas démarrer, la différence ici est que le fait de
        //démarrer le service par "startService" fait que si l'activité est détruite, le service
        //reste en vie (obligatoire pour l'application AlarmIngressStyle)
        startService(new Intent(this, WebsocketService.class));
        Intent intent = new Intent(this, WebsocketService.class);
        //lance le binding du service
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        //Binding Activity with ModelService
        startService(new Intent(this, ModelService.class));
        intent = new Intent(this, ModelService.class);
        //lance le binding du websocketService
        bindService(intent, modelServiceConnection, Context.BIND_AUTO_CREATE);
    }

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
            service.chooseIntervention(intervention.getId());
            Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(mapIntent);
        });
    }

    /**
     * Add new item , and notify to the adapter that item has been added
     * @param intervention : the new item
     */
    private void addElement(Intervention intervention) {
        // on insère l'intervention dans la liste des interventions liée à l'adapter
        dataAdapter.add(intervention);
        // on notifie à l'adapter ce changement
        dataAdapter.notifyDataSetChanged();
    }


    /**
     * Delete item , and notify to the adapter that item has been added
     * @param position : the position of the item to delete
     */
    public void deleteElement(int position) {
        // on supprime l'intervention
        dataAdapter.remove(dataAdapter.getItem(position));
        // on notifie à l'adapter ce changement
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        IntentFilter interventionListIntentFilter = new IntentFilter();
        interventionListIntentFilter.addAction(ModelConstants.ACTION_ADD_INTERVENTION);
        interventionListIntentFilter.addAction(ModelConstants.ACTION_DELETE_INTERVENTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, interventionListIntentFilter);
    }

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

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //on supprimer le binding entre l'activité et le websocketService.
        if(serviceConnection != null)
            unbindService(serviceConnection);

        if(modelServiceConnection != null)
            unbindService(modelServiceConnection);
    }
}