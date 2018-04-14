package ila.fr.codisintervention.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import ila.fr.codisintervention.R;

/**
 * This activity is the main menu for a Codis User
 * it presents two buttons either to create a new intervention or to show the list of them.
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class codisMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codis_main_menu);
        setTitle(R.string.title_codis_menu);

        Button buttonNewInterv = ((Button) this.findViewById(R.id.textView_CreerIntervention));
        buttonNewInterv.setOnClickListener(v -> {
            Intent intent = new Intent( codisMainMenu.this, NewInterventionActivity.class);
            startActivity(intent);
        });

        Button buttonIntervList = ((Button) this.findViewById(R.id.textView_ListeIntervention));
        buttonIntervList.setOnClickListener(v -> {
            Intent intent = new Intent( codisMainMenu.this, InterventionsListActivity.class);
            startActivity(intent);
        });
    }
}
