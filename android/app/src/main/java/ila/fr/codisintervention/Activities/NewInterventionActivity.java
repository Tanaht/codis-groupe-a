package ila.fr.codisintervention.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import ila.fr.codisintervention.R;

public class NewInterventionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervention);
        setTitle(R.string.NewInterventionPageTitle);

        Spinner spinnerCodes = (Spinner) findViewById(R.id.CodeList);
        Spinner spinnerMoyens = (Spinner) findViewById(R.id.CodeList);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.InterventionCodes, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.InterventionMoyens, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinners
        spinnerCodes.setAdapter(adapter1);
        spinnerMoyens.setAdapter(adapter2);
    }
}
