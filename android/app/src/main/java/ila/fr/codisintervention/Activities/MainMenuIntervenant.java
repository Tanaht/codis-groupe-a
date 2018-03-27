package ila.fr.codisintervention.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ila.fr.codisintervention.R;

public class MainMenuIntervenant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_intervenant);
        setTitle(R.string.MenuIntervenantPageTitle);

        Button buttonIntervList = ((Button) this.findViewById(R.id.textView_ListeIntervention2));
        buttonIntervList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainMenuIntervenant.this, InterventionsListActivity.class);
                startActivity(intent);
            }
        });

    }
}
