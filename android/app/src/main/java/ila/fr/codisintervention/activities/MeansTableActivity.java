package ila.fr.codisintervention.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ila.fr.codisintervention.R;

public class MeansTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means_table);
    }

    /**
     * Show map activity.
     *
     * Technically this method send an explicit intent to {@link MapActivity }
     *
     * @param v the view
     */
    public void showMapTable(View v) {
        Intent intent = new Intent( MeansTableActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
