package ila.fr.codisintervention.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.fragments.AdditionalMeanFragment;
import ila.fr.codisintervention.fragments.MeansTableFragment;

public class MeansTableActivity extends AppCompatActivity implements AdditionalMeanFragment.OnFragmentInteractionListener {

    private MeansTableFragment meansTableFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_means_table);
        setContentView(R.layout.activity_means_table);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        //not used
    }
}
