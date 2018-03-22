package ila.fr.codisintervention.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ila.fr.codisintervention.Entities.Country;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.Utils.MoyenListAdapter;

public class NewInterventionActivity extends AppCompatActivity {

    MoyenListAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);
        setTitle(R.string.NewInterventionPageTitle);

        // Spinner (Liste déroulante)
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

        //Array list of countries
        ArrayList<Country> countryList = new ArrayList<Country>();
        Country country = new Country("AFG","Afghanistan",false);
        countryList.add(country);
        country = new Country("ALB","Albania",true);
        countryList.add(country);
        country = new Country("DZA","Algeria",false);
        countryList.add(country);
        country = new Country("ASM","American Samoa",true);
        countryList.add(country);
        country = new Country("AND","Andorra",true);
        countryList.add(country);
        country = new Country("AGO","Angola",false);
        countryList.add(country);
        country = new Country("AIA","Anguilla",false);
        countryList.add(country);

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MoyenListAdapter(this,
                R.layout.moyen_infos_layout, countryList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Country country = (Country) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
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

                ArrayList<Country> countryList = dataAdapter.getCountryList();
                for(int i=0;i<countryList.size();i++){
                    Country country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }
}
