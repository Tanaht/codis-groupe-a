package ila.fr.codisintervention.Activities;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import ila.fr.codisintervention.Entities.Moyen;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.Services.MoyensService;
import ila.fr.codisintervention.Utils.GooglePlacesAutocompleteAdapter;
import ila.fr.codisintervention.Utils.MoyenListAdapter;

public class NewInterventionActivity extends AppCompatActivity {

    MoyenListAdapter dataAdapter;
    String inputtedAddress;
    LatLng latlngAddress;

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

           // Toast.makeText(getApplicationContext(),inputtedAddress , Toast.LENGTH_SHORT).show();
        });

        // Code Sinistre List (Liste déroulante)
        displaySpinner();

        // Moyen List
        displayListView();

        // Send intervention
        checkButtonClick();

    }

    private void displaySpinner(){
        Spinner spinnerCodes = (Spinner) findViewById(R.id.CodeList);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.InterventionCodes, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinners
        spinnerCodes.setAdapter(adapter1);
    }

    private void displayListView(){

        // Moyens Dispos List
        MoyensService ms = new MoyensService();
        ArrayList<Moyen> moyenList = ms.getMoyensDispo();

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
                String text = ((Spinner)findViewById(R.id.CodeList)).getSelectedItem().toString();
                /* TODO send :
                    inputtedAddress;
                    latlngAddress.longitude
                    latlngAddress.latitude
                    liste des moyens */
                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

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

        return resLatLng;
    }
}
