package ila.fr.codisintervention.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import ila.fr.codisintervention.Activities.MainActivity;
import ila.fr.codisintervention.Activities.MainMenuCodis;
import ila.fr.codisintervention.Activities.TestFragmentActivity;
import ila.fr.codisintervention.Entities.SymboleDispo;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.Services.SymboleDispoService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListeSymbolesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListeSymbolesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeSymbolesFragment extends Fragment{

    private static List<SymboleDispo> liste = SymboleDispoService.getListeSymbolesDispo();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListeSymbolesFragment.OnFragmentInteractionListener mListener;

    public ListeSymbolesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListeSymbolesFragment newInstance(String param1, String param2) {
        ListeSymbolesFragment fragment = new ListeSymbolesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste_symboles, container, false);

        ajouterImageView(liste, view);
        ajouterImageViewListeners(liste, view);

        return view;
    }

    public void ajouterImageView(List<SymboleDispo> liste, View view){
        for (SymboleDispo symbole: liste) {
            symbole.setImageView((ImageView) view.findViewById(getResources().getIdentifier(symbole.getId(), "id", getActivity().getPackageName())));
        }
    }

    public void ajouterImageViewListeners(List<SymboleDispo> liste, View view){
        for (SymboleDispo symbole: liste){
            symbole.getImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    symbole.getImageView().setImageResource(getResources().getIdentifier(symbole.getIconeSelected(), "drawable", getActivity().getPackageName()));
                    for (SymboleDispo symbole2 : liste){
                        if(symbole != symbole2){
                            symbole2.getImageView().setImageResource(getResources().getIdentifier(symbole2.getIconeNonSelected(), "drawable", getActivity().getPackageName()));
                        }
                    }
                }
            });
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListeSymbolesFragment.OnFragmentInteractionListener) {
            mListener = (ListeSymbolesFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
