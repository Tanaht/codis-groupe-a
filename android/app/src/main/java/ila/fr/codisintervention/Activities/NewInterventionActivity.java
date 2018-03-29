package ila.fr.codisintervention.Activities;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import ila.fr.codisintervention.Entities.Moyen;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.Services.InterventionService;
import ila.fr.codisintervention.Services.MoyensService;
import ila.fr.codisintervention.Utils.GooglePlacesAutocompleteAdapter;
import ila.fr.codisintervention.Utils.MoyenListAdapter;
import ila.fr.codisintervention.binders.WebsocketServiceBinder;
import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.services.websocket.WebsocketService;

public class NewInterventionActivity extends AppCompatActivity {
    private static final String TAG = "NewInterventionActivity";

    InterventionService interventionService;
    MoyenListAdapter dataAdapter;
    String inputtedAddress = "";
    LatLng latlngAddress;

    // ServiceConnection permet de gérer l'état du lien entre l'activité et le service.
    private ServiceConnection serviceConnection;
    private WebsocketServiceBinder.IMyServiceMethod service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);
        setTitle(R.string.NewInterventionPageTitle);

        // AutoComplete Address
        AutoCompleteTextView autoCompView = findViewById(R.id.autoCompleteTextView);
        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));

        autoCompView.setOnItemClickListener((parent, view, position, id) -> {
            inputtedAddress = (String) parent.getItemAtPosition(position);
            latlngAddress = getLocationFromAddress(inputtedAddress);
        });

        // get codes sinistre from server
        interventionService = new InterventionService();
        ArrayList<String> codesSinistre = interventionService.getCodesSinistre();
        // Code Sinistre List (Liste déroulante)
        displaySpinner(codesSinistre);

        // get moyenList from server
        ArrayList<Moyen> interventionMoyens = interventionService.getMoyensDispo();
        // Moyen List
        displayListView(interventionMoyens);

        // Send intervention
        checkButtonClick();

        // Bind activity to websocket service
        bindToService();
    }

    private void bindToService() {
        serviceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {}
            public void onServiceConnected(ComponentName arg0, IBinder binder) {

                //on récupère l'instance du service dans l'activité
                service = ((WebsocketServiceBinder)binder).getService();

                //on genère l'évènement indiquant qu'on est "bindé"
                //   handler.sendEmptyMessage(ON_BIND);
            }
        };
        //démarre le service si il n'est pas démarré
        //Le binding du service est configuré avec "BIND_AUTO_CREATE" ce qui normalement
        //démarre le service si il n'est pas démarrer, la différence ici est que le fait de
        //démarrer le service par "startService" fait que si l'activité est détruite, le service
        //reste en vie (obligatoire pour l'application AlarmIngressStyle)
        startService(new Intent(getApplicationContext(), WebsocketService.class));
        Intent intent = new Intent(getApplicationContext(), WebsocketService.class);
        //lance le binding du service
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void displaySpinner(ArrayList<String> codesSinistre){
        Spinner spinnerCodes = (Spinner) findViewById(R.id.CodeList);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, codesSinistre);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinners
        spinnerCodes.setAdapter(adapter);
    }

    private void displayListView(ArrayList<Moyen> moyenList){

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MoyenListAdapter(this,
                R.layout.moyen_infos_layout, moyenList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Moyen moyen = (Moyen) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + moyen.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.SendIntervention);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Moyen> moyenList = dataAdapter.getMoyenList();
                for(int i=0;i<moyenList.size();i++){
                    Moyen moyen = moyenList.get(i);
                    if(moyen.isSelected()){
                        responseText.append("\n" + moyen.getName());
                    }
                }
                String codeSinistre = ((Spinner)findViewById(R.id.CodeList)).getSelectedItem().toString();

                // Check if address is set
                if(inputtedAddress.equals("")){
                    Toasty.error(getApplicationContext(),
                            getString(R.string.interventionNoAddress),
                            Toast.LENGTH_LONG).show();
                } else {

                    Intervention intervention = new Intervention();
                    intervention.setAddress(inputtedAddress);
                    intervention.setCode(codeSinistre);

                    latlngAddress = getLocationFromAddress(intervention.getAddress());
                    Log.d(TAG, latlngAddress == null ? "LatLng is null" : "LatLng is not null");

                    if(latlngAddress != null)
                        intervention.setLocation(new Location(latlngAddress.latitude,latlngAddress.longitude));
                    else
                        intervention.setLocation(new Location(50, 50));
                    // TODO intervention.setMoyens(..)

                    //send Intervention to WS Service
                    service.createIntervention(intervention);
                }
            }
        });
    }

    private LatLng getLocationFromAddress(String inputtedAddress) {
        Geocoder coder = new Geocoder(this, Locale.getDefault());
        List<Address> address = null;
        LatLng resLatLng = null;
        try {
            // May throw an IOException

            address = coder.getFromLocationName(inputtedAddress, 1);
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        Log.d(TAG, "LatLng retrieved: " + resLatLng);

        return resLatLng;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //on supprimer le binding entre l'activité et le websocketService.
        if(serviceConnection != null)
            unbindService(serviceConnection);
//
//        if(modelServiceConnection != null)
//            unbindService(modelServiceConnection);
    }
}
