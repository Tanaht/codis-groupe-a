package ila.fr.codisintervention.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ila.fr.codisintervention.R;

/**
 * This activity is the main menu for a regular User
 * it presents three buttons either to create a new intervention or to show the list of them. It can de
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class UserMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        setTitle(R.string.title_user_menu);
    }

    /**
     * Show interventions list activity.
     *
     * Technically this method send an explicit intent to {@link InterventionsListActivity }
     *
     * @param v the view
     */
    public void showInterventionsList(View v) {
        Intent intent = new Intent( UserMainMenu.this, InterventionsListActivity.class);
        startActivity(intent);
    }
}
