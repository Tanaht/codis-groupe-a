package ila.fr.codisintervention.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ila.fr.codisintervention.R;

/**
 * This activity is the main menu for a Codis User
 * it presents three buttons either to create a new intervention or to show the list of them. It can de
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class CodisMainMenu extends AppCompatActivity {
    private static final String TAG = "CodisMainMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codis_main_menu);
        setTitle(R.string.title_codis_menu);
    }


    /**
     * Show Create Intervention Form
     * to allow codis user to create new intervention
     *
     * Technically this method send an explicit intent to {@link NewInterventionActivity }
     * @param v the view
     */
    public void createIntervention(View v) {
        Intent intent = new Intent( CodisMainMenu.this, NewInterventionActivity.class);
        startActivity(intent);
    }

    /**
     * Show interventions list activity.
     *
     * Technically this method send an explicit intent to {@link InterventionsListActivity }
     *
     * @param v the view
     */
    public void showInterventionsList(View v) {
        Intent intent = new Intent( CodisMainMenu.this, InterventionsListActivity.class);
        startActivity(intent);
    }

    /**
     * Show vehicle requests list activity.
     *
     * Technically this method send an explicit intent to the activity in charge of the vehicle requests list display.
     *
     * @throws UnsupportedOperationException feature not supported yet
     * @param v the view
     */
    public void showVehicleRequestsList(View v) {
        Log.w(TAG, "Currently showVehicleRequestsList is not implemented");
        throw new UnsupportedOperationException("For Now we cannot support this request");
    }
}
