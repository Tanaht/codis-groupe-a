package ila.fr.codisintervention.Activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ila.fr.codisintervention.R;

public class TestFragmentActivity  extends FragmentActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.fragment_liste_symboles);
    }
}
