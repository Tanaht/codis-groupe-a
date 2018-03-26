package ila.fr.codisintervention.Activities;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ila.fr.codisintervention.R;

public class TestFragmentActivity  extends FragmentActivity {

    ImageView ressourceEau;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.fragment_liste_symboles);
    }

    public void imageClick(View view){
        ressourceEau = (ImageView) view.findViewById(R.id.ressource_eau);
        ressourceEau.setImageResource(R.drawable.ressource_eau_glow);

    }
}
