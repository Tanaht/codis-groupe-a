package ila.fr.codisintervention.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ila.fr.codisintervention.R;

public class MainMenuCodis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_codis);
        setTitle(R.string.MenuCodisPageTitle);

        Button buttonNewInterv = ((Button) this.findViewById(R.id.textView_CreerIntervention));
        buttonNewInterv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainMenuCodis.this, NewInterventionActivity.class);
                startActivity(intent);
            }
        });

        Button buttonIntervList = ((Button) this.findViewById(R.id.textView_ListeIntervention));
        buttonIntervList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainMenuCodis.this, InterventionsListActivity.class);
                startActivity(intent);
            }
        });
    }
}
