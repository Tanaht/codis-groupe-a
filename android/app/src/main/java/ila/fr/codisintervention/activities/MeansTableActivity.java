package ila.fr.codisintervention.activities;

import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.binders.WebSocketServiceBinder;
import ila.fr.codisintervention.fragments.AdditionalMeanFragment;
import ila.fr.codisintervention.fragments.MeansTableFragment;
import ila.fr.codisintervention.services.ModelServiceAware;
import ila.fr.codisintervention.services.WebSocketServiceAware;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class MeansTableActivity extends AppCompatActivity implements AdditionalMeanFragment.OnFragmentInteractionListener, WebSocketServiceAware, ModelServiceAware {

    private MeansTableFragment meansTableFragment;

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
    }

    @Override
    public void onWebSocketServiceConnected() {

    }

    @Override
    public void setWebSocketService(WebSocketServiceBinder.IMyServiceMethod webSocketService) {
        this.webSocketService = webSocketService;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        //not used
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindModelService(modelServiceConnexion);
        unbindWebSocketService(webSocketServiceConnection);
    }
}
