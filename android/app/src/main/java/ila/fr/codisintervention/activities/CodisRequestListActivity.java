package ila.fr.codisintervention.activities;

import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import ila.fr.codisintervention.models.model.Request;
import ila.fr.codisintervention.services.ModelServiceAware;
import ila.fr.codisintervention.services.WebSocketServiceAware;
import ila.fr.codisintervention.utils.RequestsListAdapter;

public class CodisRequestListActivity extends AppCompatActivity implements WebSocketServiceAware, ModelServiceAware{

    private static final String TAG = "CodisReqListActivity";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codis_request_list);

        //Here we call the aware interface to perform the binding at our place, we gain an instance of ServiceConnection
        Log.d(TAG, "Bind to services");
        modelServiceConnection = bindModelService();
        webSocketServiceConnection = bindWebSocketService();
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
            Request request = (Request) parent.getItemAtPosition(position);

            // TODO intent to Page de validation de demandes de moyen
        });
    }

    /**
     * Method triggered when the ModelService is connected
     */
    @Override
    public void onModelServiceConnected() {
        Log.d(TAG, "ModelService connected: " + modelService.getRequests());
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
