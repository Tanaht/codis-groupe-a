package ila.fr.codisintervention.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ila.fr.codisintervention.R;

/**
 * Created by aminesoumiaa on 22/03/18.
 * Fragment used to show a form for intervention creation.
 * called from {@link ila.fr.codisintervention.activities.NewInterventionActivity}
 * TODO: Why this fragment even exist ? without any logic inside it ?
 */
public class InterventionFormFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intervention_form, container, false);
    }
}
