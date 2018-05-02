package ila.fr.codisintervention.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.binders.WebSocketServiceBinder;
import ila.fr.codisintervention.fragments.AdditionalMeanFragment;
import ila.fr.codisintervention.fragments.MeansTableFragment;
import ila.fr.codisintervention.models.model.Request;
import ila.fr.codisintervention.services.ModelServiceAware;
import ila.fr.codisintervention.services.WebSocketServiceAware;

import static ila.fr.codisintervention.services.constants.ModelConstants.ADD_VEHICLE_REQUEST;
import static ila.fr.codisintervention.services.constants.ModelConstants.REJECT_VEHICLE_REQUEST;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_CREATE_UNIT;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_DELETE_UNIT;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_UPDATE_UNIT;
import static ila.fr.codisintervention.services.constants.ModelConstants.VALIDATE_VEHICLE_REQUEST;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class MeansTableActivity extends AppCompatActivity implements AdditionalMeanFragment.OnFragmentInteractionListener, WebSocketServiceAware, ModelServiceAware {

    private MeansTableFragment meansTableFragment;
    private AdditionalMeanFragment additionalMeanFragment;

    private ServiceConnection webSocketServiceConnection, modelServiceConnexion;

    private ModelServiceBinder.IMyServiceMethod modelService;
    private WebSocketServiceBinder.IMyServiceMethod webSocketService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_means_table);
        setContentView(R.layout.activity_means_table);

        FragmentManager manager = getSupportFragmentManager();

        meansTableFragment = (MeansTableFragment) manager.findFragmentById(R.id.means_table_fragment);
        additionalMeanFragment = (AdditionalMeanFragment) manager.findFragmentById(R.id.additional_mean_fragment);

        webSocketServiceConnection = bindWebSocketService();
        modelServiceConnexion = bindModelService();

    }

    @Override
    public void onModelServiceConnected() {
        meansTableFragment.onModelServiceConnected(modelService);
    }

    @Override
    public void setModelService(ModelServiceBinder.IMyServiceMethod modelService) {
        this.modelService = modelService;
        additionalMeanFragment.setModelService(modelService);
    }

    @Override
    public void onWebSocketServiceConnected() {

    }


    /**
     * This part can be refactored I guess, but it need a little studies about it, perhaps with an observer pattern that can be an abstraction of the complexity of Intent.
     */
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter mapIntentFilter = new IntentFilter();
        mapIntentFilter.addAction(UPDATE_INTERVENTION_CREATE_UNIT);
        mapIntentFilter.addAction(UPDATE_INTERVENTION_UPDATE_UNIT);
        mapIntentFilter.addAction(UPDATE_INTERVENTION_DELETE_UNIT);
        mapIntentFilter.addAction(ADD_VEHICLE_REQUEST);
        mapIntentFilter.addAction(REJECT_VEHICLE_REQUEST);
        mapIntentFilter.addAction(VALIDATE_VEHICLE_REQUEST);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, mapIntentFilter);
    }

    /**
     * TODO: To mutualize equally with BindToService method
     * Define BroadcoastReceiver Instance to get aware when an Intent is send to this activity among other
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null)
                return;

            if(intent.getAction() == null)
                return;

            switch(intent.getAction()) {
                case UPDATE_INTERVENTION_CREATE_UNIT:
                case UPDATE_INTERVENTION_DELETE_UNIT:
                case UPDATE_INTERVENTION_UPDATE_UNIT:
                    meansTableFragment.refreshTable();
            }
        }
    };

    @Override
    public void setWebSocketService(WebSocketServiceBinder.IMyServiceMethod webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindModelService(modelServiceConnexion);
        unbindWebSocketService(webSocketServiceConnection);
    }

    @Override
    public void onNewVehicleRequest(Request request) {
        webSocketService.requestUnit(modelService.getCurrentIntervention().getId(), request);
    }
}
