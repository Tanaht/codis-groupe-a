package ila.fr.codisintervention.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.List;

import ila.fr.codisintervention.entities.SymbolKind;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.factory.SymbolKindFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SymbolsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SymbolsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SymbolsListFragment extends Fragment {

    //Get symbols from model
    private static List<SymbolKind> liste = SymbolKindFactory.getAvailableSymbols();

    private String couleur = "rouge";
    private SymbolKind currentSymbol;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SymbolsListFragment.OnFragmentInteractionListener mListener;

    public SymbolsListFragment() {
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
    public static SymbolsListFragment newInstance(String param1, String param2) {
        SymbolsListFragment fragment = new SymbolsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste_symboles, container, false);

        ajouterImageView(liste, view);
        ajouterImageViewListeners(liste);
        ajouterRadioButtonListeners(view);

        return view;
    }

    public void ajouterImageView(List<SymbolKind> liste, View view) {
        for (SymbolKind symbole : liste) {
            symbole.setImageView((ImageView) view.findViewById(getResources().getIdentifier(symbole.getId(), "id", getActivity().getPackageName())));
        }
    }

    public void ajouterImageViewListeners(List<SymbolKind> liste) {
        for (SymbolKind symbole : liste) {
            symbole.getImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    symbole.getImageView().setImageResource(getResources().getIdentifier(symbole.getSelectedIcon(), "drawable", getActivity().getPackageName()));
                    for (SymbolKind symbole2 : liste) {
                        symbole2.setSelected(false);
                        if (symbole != symbole2) {
                            symbole2.getImageView().setImageResource(getResources().getIdentifier(symbole2.getDefaultIcon(), "drawable", getActivity().getPackageName()));
                        }
                    }
                    symbole.setSelected(true);
                }
            });
        }
    }

    public SymbolKind getSelectedSymbol(){
        for(SymbolKind symbole : liste){
            if(symbole.isSelected()){
                switch(symbole.getId()){
                    case "ressource_eau":
                        break;
                    case "sinistre":
                        switch(couleur){
                            case "rouge":
                                symbole.setIdDrawable(R.drawable.sinistrerouge);
                                break;
                            case "orange":
                                symbole.setIdDrawable(R.drawable.sinistreorange);
                                break;
                            case "violet":
                                break;
                            case "vert":
                                symbole.setIdDrawable(R.drawable.sinistrevert);
                                break;
                            case "bleu":
                                symbole.setIdDrawable(R.drawable.sinistrebleu);
                                break;
                        }
                        break;
                    case "triangle_bas":
                        switch(couleur){
                            case "rouge":
                                symbole.setIdDrawable(R.drawable.dangerrougebas);
                                break;
                            case "orange":
                                symbole.setIdDrawable(R.drawable.dangerorangebas);
                                break;
                            case "violet":
                                break;
                            case "vert":
                                symbole.setIdDrawable(R.drawable.dangervertbas);
                                break;
                            case "bleu":
                                symbole.setIdDrawable(R.drawable.dangerbleubas);
                                break;
                        }
                        break;
                    case "triangle_haut":
                        switch(couleur){
                            case "rouge":
                                symbole.setIdDrawable(R.drawable.dangerrougehaut);
                                break;
                            case "orange":
                                symbole.setIdDrawable(R.drawable.dangerorangehaut);
                                break;
                            case "violet":
                                break;
                            case "vert":
                                symbole.setIdDrawable(R.drawable.dangerverthaut);
                                break;
                            case "bleu":
                                symbole.setIdDrawable(R.drawable.dangerbleuhaut);
                                break;
                        }
                        break;
                    case "vehicule":
                        switch(couleur){
                            case "rouge":
                                symbole.setIdDrawable(R.drawable.vehiculerouge);
                                break;
                            case "orange":
                                symbole.setIdDrawable(R.drawable.vehiculeorange);
                                break;
                            case "violet":
                                symbole.setIdDrawable(R.drawable.vehiculeviolet);
                                break;
                            case "vert":
                                symbole.setIdDrawable(R.drawable.vehiculevert);
                                break;
                            case "bleu":
                                symbole.setIdDrawable(R.drawable.vehiculebleu);
                                break;
                        }
                        break;
                    case "vehicule_non_valide":
                        switch(couleur){
                            case "rouge":
                                symbole.setIdDrawable(R.drawable.vehiculerougenonvalide);
                                break;
                            case "orange":
                                symbole.setIdDrawable(R.drawable.vehiculeorangenonvalide);
                                break;
                            case "violet":
                                symbole.setIdDrawable(R.drawable.vehiculevioletnonvalide);
                                break;
                            case "vert":
                                symbole.setIdDrawable(R.drawable.vehiculevertnonvalide);
                                break;
                            case "bleu":
                                symbole.setIdDrawable(R.drawable.vehiculebleunonvalide);
                                break;
                        }
                        break;
                    case "vehicule_pompier":
                        switch(couleur){
                            case "rouge":
                                symbole.setIdDrawable(R.drawable.vehiculepompierrouge);
                                break;
                            case "orange":
                                break;
                            case "violet":
                                symbole.setIdDrawable(R.drawable.vehiculepompierviolet);
                                break;
                            case "vert":
                                symbole.setIdDrawable(R.drawable.vehiculepompiervert);
                                break;
                            case "bleu":
                                break;
                        }
                        break;
                    case "vehicule_pompier_non_valide":
                        switch(couleur){
                            case "rouge":
                                symbole.setIdDrawable(R.drawable.vehiculepompierrougenonvalide);
                                break;
                            case "orange":
                                break;
                            case "violet":
                                symbole.setIdDrawable(R.drawable.vehiculepompiervioletnonvalide);
                                break;
                            case "vert":
                                symbole.setIdDrawable(R.drawable.vehiculepompiervertnonvalide);
                                break;
                            case "bleu":
                                break;
                        }
                        break;
                    case "zone":
                        switch(couleur){
                            case "rouge":
                                symbole.setIdDrawable(R.drawable.zoneactionrouge);
                                break;
                            case "orange":
                                symbole.setIdDrawable(R.drawable.zoneactionorange);
                                break;
                            case "violet":
                                break;
                            case "vert":
                                symbole.setIdDrawable(R.drawable.zoneactionverte);
                                break;
                            case "bleu":
                                symbole.setIdDrawable(R.drawable.zoneactionbleu);
                                break;
                        }
                        break;
                    default:
                        break;
                }
                return symbole;
            }
        }
        return null;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void ajouterRadioButtonListeners(View view) {

        RadioButton rbRouge = (RadioButton) view.findViewById(R.id.radioButtonrouge);
        rbRouge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCouleur("rouge");
            }
        });
        RadioButton rbVert = (RadioButton) view.findViewById(R.id.radioButtonvert);
        rbVert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCouleur("vert");
            }
        });
        RadioButton rbBleu = (RadioButton) view.findViewById(R.id.radioButtonbleu);
        rbBleu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCouleur("bleu");
            }
        });
        RadioButton rbOrange = (RadioButton) view.findViewById(R.id.radioButtonorange);
        rbOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCouleur("orange");
            }
        });
        RadioButton rbViolet = (RadioButton) view.findViewById(R.id.radioButtonviolet);
        rbViolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCouleur("violet");
            }
        });

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SymbolsListFragment.OnFragmentInteractionListener) {
            mListener = (SymbolsListFragment.OnFragmentInteractionListener) context;
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
        void onFragmentInteraction(Uri uri);
    }


}
