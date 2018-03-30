package ila.fr.codisintervention.Activities;

import android.annotation.SuppressLint;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import ila.fr.codisintervention.Entities.SymboleDispo;
import ila.fr.codisintervention.Fragments.ListeSymbolesFragment;
import ila.fr.codisintervention.Fragments.MapsFragment;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.Unit;

import static ila.fr.codisintervention.services.constants.ModelConstants.ACTION_ADD_DEMANDE;
import static ila.fr.codisintervention.services.constants.ModelConstants.ACTION_UPDATE_INTERVENTION_ACCEPT_UTIL;
import static ila.fr.codisintervention.services.constants.ModelConstants.ACTION_UPDATE_INTERVENTION_CREATE_SYMBOL;
import static ila.fr.codisintervention.services.constants.ModelConstants.ACTION_UPDATE_INTERVENTION_DELETE_SYMBOL;
import static ila.fr.codisintervention.services.constants.ModelConstants.ACTION_UPDATE_INTERVENTION_DELETE_UTIL;
import static ila.fr.codisintervention.services.constants.ModelConstants.ACTION_UPDATE_INTERVENTION_UPDATE_SYMBOL;
import static ila.fr.codisintervention.services.constants.ModelConstants.ACTION_UPDATE_INTERVENTION_UPDATE_UTIL;
import static ila.fr.codisintervention.services.constants.ModelConstants.ACTION_VALIDATION_MOYEN;

public class MapActivity extends AppCompatActivity implements ListeSymbolesFragment.OnFragmentInteractionListener {

    // ServiceConnection permet de gérer l'état du lien entre l'activité et le websocketService.
    private ServiceConnection modelServiceConnection;

    //Model service
    private ModelServiceBinder.IMyServiceMethod modelService;

    private String couleur = "";

    ListeSymbolesFragment symbolFragment;
    MapsFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);

        FragmentManager manager = getSupportFragmentManager();
        symbolFragment = (ListeSymbolesFragment) manager.findFragmentById(R.id.listSymbolFragment);
        mapFragment = (MapsFragment) manager.findFragmentById(R.id.mapFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter mapIntentFilter = new IntentFilter();
        mapIntentFilter.addAction(ACTION_UPDATE_INTERVENTION_UPDATE_UTIL);
        mapIntentFilter.addAction(ACTION_UPDATE_INTERVENTION_ACCEPT_UTIL);
        mapIntentFilter.addAction(ACTION_UPDATE_INTERVENTION_DELETE_UTIL);
        mapIntentFilter.addAction(ACTION_UPDATE_INTERVENTION_UPDATE_SYMBOL);
        mapIntentFilter.addAction(ACTION_UPDATE_INTERVENTION_DELETE_SYMBOL);
        mapIntentFilter.addAction(ACTION_UPDATE_INTERVENTION_CREATE_SYMBOL);
        mapIntentFilter.addAction(ACTION_ADD_DEMANDE);
        mapIntentFilter.addAction(ACTION_VALIDATION_MOYEN);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, mapIntentFilter);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = (int) intent.getExtras().get("id");
            Symbol symbol;
            Unit unit;
            switch (intent.getAction()) {
                case ACTION_UPDATE_INTERVENTION_UPDATE_UTIL:
                    unit = modelService.getUnit(id);
                    break;
                case ACTION_UPDATE_INTERVENTION_ACCEPT_UTIL:
                    unit = modelService.getUnit(id);
                    break;
                case ACTION_UPDATE_INTERVENTION_DELETE_UTIL:
                    unit = modelService.getUnit(id);
                    break;
                case ACTION_UPDATE_INTERVENTION_UPDATE_SYMBOL:
                    symbol = modelService.getSymbol(id);
                    break;
                case ACTION_UPDATE_INTERVENTION_DELETE_SYMBOL:
                    symbol = modelService.getSymbol(id);
                    break;
                case ACTION_UPDATE_INTERVENTION_CREATE_SYMBOL:
                    symbol = modelService.getSymbol(id);
                    break;
                case ACTION_ADD_DEMANDE:
                    break;
                case ACTION_VALIDATION_MOYEN:
                    unit = modelService.getUnit(id);
                    break;
                default:
                    break;
            }
        }
    };


    //Bind activity with services
    private void bindToService() {
        modelServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                //on recupere l'instance du modelService dans l'activité
                modelService = ((ModelServiceBinder) binder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };

        //Binding Activity with ModelService
        startService(new Intent(this, ila.fr.codisintervention.services.model.ModelService.class));
        Intent intent = new Intent(this, ila.fr.codisintervention.services.model.ModelService.class);

        //lance le binding du websocketService
        bindService(intent, modelServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    /* Menu part */
    @SuppressLint("ResourceType")
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

//        double x = (double) event.getX();
//        double y = (double) event.getY();
//        SymboleDispo symbole = symbolFragment.getSelectedSymbol();
//        Bitmap marker = mapFragment.resizeBitmap(Integer.valueOf(symbole.getId()), 50, 50);
//        mapFragment.addCustomMarker_Zoom(new LatLng(x,y), marker);
        //Toast.makeText(this, "x" + x + "y" + y, Toast.LENGTH_SHORT).show();
        return true;
    }

    public SymboleDispo getSelectedSymbol(){
        return this.symbolFragment.getSelectedSymbol();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
