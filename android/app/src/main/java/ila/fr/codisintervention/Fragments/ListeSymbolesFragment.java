package ila.fr.codisintervention.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ila.fr.codisintervention.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListeSymbolesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListeSymbolesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeSymbolesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liste_symboles, container, false);
    }
}
