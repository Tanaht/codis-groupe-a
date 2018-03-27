package ila.fr.codisintervention.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ila.fr.codisintervention.Entities.Intervention;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.Services.InterventionService;
import ila.fr.codisintervention.Utils.InterventionListAdapter;

public class InterventionsListActivity extends AppCompatActivity {
    InterventionListAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interventions_list);
        setTitle(R.string.IntervenantListPageTitle);

        // Interventions List
        displayListView();
    }

    private void displayListView(){

        // Interventions Dispos List
        InterventionService is = new InterventionService();
        ArrayList<Intervention> interventionList = is.getInterventionList();

        //create an ArrayAdapter from the String Array
        dataAdapter = new InterventionListAdapter(this,
                R.layout.interventions_list_item_layout, interventionList);
        ListView listView = (ListView) findViewById(R.id.interventionsList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Intervention intervention = (Intervention) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on intervention with id: " + intervention.getCodeSinistre(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}