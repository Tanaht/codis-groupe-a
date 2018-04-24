package ila.fr.codisintervention.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.entities.SymbolKind;
import ila.fr.codisintervention.exception.SymbolNotFoundException;
import ila.fr.codisintervention.exception.UnitNotFoundException;
import ila.fr.codisintervention.fragments.MapsFragment;
import ila.fr.codisintervention.fragments.SymbolsListFragment;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.models.model.Unit;

import static ila.fr.codisintervention.services.constants.ModelConstants.ADD_VEHICLE_REQUEST;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_CREATE_SYMBOL;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_CREATE_UNIT;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_DELETE_SYMBOL;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_DELETE_UNIT;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_UPDATE_SYMBOL;
import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_INTERVENTION_UPDATE_UNIT;
import static ila.fr.codisintervention.services.constants.ModelConstants.VALIDATE_VEHICLE_REQUEST;

/**
 * This activity is used to show the map of an intervention chosen
 * this activity intend to update the symbols and vehicles position on the map
 *
 * TODO: Please log more often
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class MapActivity extends AppCompatActivity implements SymbolsListFragment.OnFragmentInteractionListener {
    private static final String TAG = "MapActivity";

    /**
     * ServiceConnection instance with the ModelService
     */
    private ServiceConnection modelServiceConnection;

    /**
     * Interface delivered by ModelService to be used by other android Component, the purpose of this is to update the model.
     */
    private ModelServiceBinder.IMyServiceMethod modelService;

    /**
     * Fragment intended to display a list of tools to add symbols on the map
     */
    SymbolsListFragment symbolFragment;

    /**
     * Fragment used to display the map
     */
    MapsFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);

        FragmentManager manager = getSupportFragmentManager();
        symbolFragment = (SymbolsListFragment) manager.findFragmentById(R.id.listSymbolFragment);
        mapFragment = (MapsFragment) manager.findFragmentById(R.id.mapFragment);
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
        mapIntentFilter.addAction(UPDATE_INTERVENTION_CREATE_SYMBOL);
        mapIntentFilter.addAction(UPDATE_INTERVENTION_UPDATE_SYMBOL);
        mapIntentFilter.addAction(UPDATE_INTERVENTION_DELETE_SYMBOL);
        mapIntentFilter.addAction(ADD_VEHICLE_REQUEST);
        mapIntentFilter.addAction(VALIDATE_VEHICLE_REQUEST);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, mapIntentFilter);
    }

    /**
     * TODO: To mutualize equally with BindToService method
     * Define BroadcoastReceiver Instance to get aware when an Intent is send to this activity among other
     */
    //TODO la fonction ne fais rien ??
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = (int) intent.getExtras().get("id");
            Symbol symbol;
            Unit unit;

            try{

                switch (intent.getAction()) {
                    case UPDATE_INTERVENTION_UPDATE_UNIT:
                        unit = modelService.getCurrentIntervention().getUnit(id);
                        break;
                    case UPDATE_INTERVENTION_CREATE_UNIT:
                        unit = modelService.getCurrentIntervention().getUnit(id);
                        break;
                    case UPDATE_INTERVENTION_DELETE_UNIT:
                        unit = modelService.getCurrentIntervention().getUnit(id);
                        break;
                    case UPDATE_INTERVENTION_UPDATE_SYMBOL:
                        symbol = modelService.getCurrentIntervention().getSymbol(id);
                        break;
                    case UPDATE_INTERVENTION_DELETE_SYMBOL:
                        symbol = modelService.getCurrentIntervention().getSymbol(id);
                        break;
                    case UPDATE_INTERVENTION_CREATE_SYMBOL:
                        symbol = modelService.getCurrentIntervention().getSymbol(id);
                        break;
                    case ADD_VEHICLE_REQUEST:
                        break;
                    case VALIDATE_VEHICLE_REQUEST:
                        unit = modelService.getCurrentIntervention().getUnit(id);
                        break;
                    default:
                        break;
                }
            }catch (SymbolNotFoundException e){
                Log.e(TAG, "onReceive: try to get symbol who doesn't exist on current intervention selected" );
                e.printStackTrace();
            }catch (UnitNotFoundException e){
                Log.e(TAG, "onReceive: try to get unit who doesn't exist on current intervention selected" );
                e.printStackTrace();
            }
        }
    };


    /**
     * TODO: It could be mutualized because almost all Activities has to bind to ModelService or WebSocketService -> A separated class that do that has to be created ! like an Interface ModelServiceAware and WebsocketServiceAware, or a superclass Activity aware of services
     * Method used to bind MapActivity to WebsocketService and ModelService, with that, MainActivity is aware of ModelService and WebSocketService
     */
    private void bindToService() {
        modelServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                // we retrieve the modelService instance in the activity
                modelService = ((ModelServiceBinder) binder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The Service " + name + " is disconnected");
            }
        };

        //Binding Activity with ModelService
        startService(new Intent(this, ila.fr.codisintervention.services.model.ModelService.class));
        Intent intent = new Intent(this, ila.fr.codisintervention.services.model.ModelService.class);

        // start the websocketService binding
        bindService(intent, modelServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    /**
     * FIXME: Why not to place the menu_map_activity at a correct place ?
     * @SuppressLint("ResourceType")
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.menu_map_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_drone) {
            return true;
        }
        if (id == R.id.action_demande_moyen) {
            return true;
        }
        if (id == R.id.action_tableau_moyen) {
            return true;
        }
        if (id == R.id.action_deconnexion) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (modelServiceConnection != null)
            unbindService(modelServiceConnection);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        TODO: Not implemented ?
//        double x = (double) event.getX();
//        double y = (double) event.getY();
//        SymbolKind symbole = symbolFragment.getSelectedSymbol();
//        Bitmap marker = mapFragment.resizeBitmap(Integer.valueOf(symbole.getId()), 50, 50);
//        mapFragment.addCustomMarkerZoom(new LatLng(x,y), marker);
//        Toast.makeText(this, "x" + x + "y" + y, Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * getter to return an Instance of SelectedSymbol
     * @return the tool being used to create Symbol on this map
     */
    public SymbolKind getSelectedSymbol(){
        return this.symbolFragment.getSelectedSymbol();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
//        No Interaction because unnecessary
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
//        No Interaction because unnecessary
    }
}
