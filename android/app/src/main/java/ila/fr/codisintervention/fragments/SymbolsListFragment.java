package ila.fr.codisintervention.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.List;

import ila.fr.codisintervention.entities.SymbolKind;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.factory.SymbolKindFactory;
import ila.fr.codisintervention.models.model.map_icon.Color;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SymbolsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SymbolsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This fragment is used to show a left panel that serve as a tool box
 */
public class SymbolsListFragment extends Fragment {
    /**
     * Retrieve all SymbolKind instance available from it's factory
     */
    private static final List<SymbolKind> list = SymbolKindFactory.getAvailableSymbols();

    /**
     * The selectedColor currently selected to create new Symbols
     */
    private String selectedColor = "red";

    /**
     * Field used to store the SymbolKind currently used to forge new Symbols
     */
    private SymbolKind currentSymbol;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SymbolsListFragment.OnFragmentInteractionListener mListener;

    /**
     * Instantiates a new Symbols list fragment.
     */
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste_symboles, container, false);

        addImageView(list, view);
        addImageViewListeners(list);
        addRadioButtonListeners(view);

        return view;
    }

    /**
     * Iterate over all SymbolKind defined in {@link SymbolKindFactory} and populate it's imageView Variable
     * @see SymbolKind#imageView
     * Add image view.
     *
     * @param list the list
     * @param view the view
     */
    public void addImageView(List<SymbolKind> list, View view) {
        for (SymbolKind symbole : list) {
            symbole.setImageView((ImageView) view.findViewById(getResources().getIdentifier(symbole.getDefaultIcon(), "id", getActivity().getPackageName())));
        }
    }

    /**
     * Add image view listeners.
     *
     * @param list the list
     */
    public void addImageViewListeners(List<SymbolKind> list) {
        for (SymbolKind symbol : list) {
            symbol.getImageView().setOnClickListener(v -> {
                symbol.getImageView().setImageResource(getResources().getIdentifier(symbol.getSelectedIcon(), "drawable", getActivity().getPackageName()));
                for (SymbolKind symbol2 : list) {
                    symbol2.setSelected(false);
                    if (symbol != symbol2) {
                        symbol2.getImageView().setImageResource(getResources().getIdentifier(symbol2.getDefaultIcon(), "drawable", getActivity().getPackageName()));
                    }
                }
                symbol.setSelected(true);
            });
        }
    }

    /**
     * decision table that associate the symbol kind with it's available selectedColor's
     * FIXME: To Refactor: Enum is a better choice I think. In any case a switch with raw String instead of final static variable is not an option
     * TODO: SonarLint said it's too complex and too repetitive
     * @return symbol kind
     */
    public SymbolKind getSelectedSymbol(){
        for(SymbolKind symbol : list){
            /**
             * TODO: It's not better to have a reference to the selected one instead of iterating over SymbolKind list ?
             * @see currentSymbol what is the purpose of this field if not to solve this issue ?
             */
            symbol.setColor(selectedColor);

            if(symbol.isSelected()){
                switch(symbol.getId()){
                    case "waterresource":
                        break;
                    case "sinister":
                        switch(selectedColor){
                            case "red":
                                symbol.setIdDrawable(R.drawable.redsinister);
                                break;
                            case "orange":
                                symbol.setIdDrawable(R.drawable.orangesinister);
                                break;
                            case "purple":
                                break;
                            case "green":
                                symbol.setIdDrawable(R.drawable.greensinister);
                                break;
                            case "blue":
                                symbol.setIdDrawable(R.drawable.bluesinister);
                                break;
                        }
                        break;
                    case "dangerdown":
                        switch(selectedColor){
                            case "red":
                                symbol.setIdDrawable(R.drawable.reddangerdown);
                                break;
                            case "orange":
                                symbol.setIdDrawable(R.drawable.orangedangerdown);
                                break;
                            case "purple":
                                break;
                            case "green":
                                symbol.setIdDrawable(R.drawable.greendangerdown);
                                break;
                            case "blue":
                                symbol.setIdDrawable(R.drawable.bluedangerdown);
                                break;
                        }
                        break;
                    case "dangerup":
                        switch(selectedColor){
                            case "red":
                                symbol.setIdDrawable(R.drawable.reddangerup);
                                break;
                            case "orange":
                                symbol.setIdDrawable(R.drawable.orangedangerup);
                                break;
                            case "purple":
                                break;
                            case "green":
                                symbol.setIdDrawable(R.drawable.greendangerup);
                                break;
                            case "blue":
                                symbol.setIdDrawable(R.drawable.bluedangerup);
                                break;
                        }
                        break;
                    case "vehicle":
                        switch(selectedColor){
                            case "red":
                                symbol.setIdDrawable(R.drawable.redvehicle);
                                break;
                            case "orange":
                                symbol.setIdDrawable(R.drawable.orangevehicle);
                                break;
                            case "purple":
                                symbol.setIdDrawable(R.drawable.purplevehicle);
                                break;
                            case "green":
                                symbol.setIdDrawable(R.drawable.greenvehicle);
                                break;
                            case "blue":
                                symbol.setIdDrawable(R.drawable.bluevehicle);
                                break;
                        }
                        break;
                    case "noteffectivevehicle":
                        switch(selectedColor){
                            case "red":
                                symbol.setIdDrawable(R.drawable.rednoteffectivevehicle);
                                break;
                            case "orange":
                                symbol.setIdDrawable(R.drawable.orangenoteffectivevehicle);
                                break;
                            case "purple":
                                symbol.setIdDrawable(R.drawable.purplenoteffectivevehicle);
                                break;
                            case "green":
                                symbol.setIdDrawable(R.drawable.greennoteffectivevehicle);
                                break;
                            case "blue":
                                symbol.setIdDrawable(R.drawable.bluenoteffectivevehicle);
                                break;
                        }
                        break;
                    case "firevehicle":
                        switch(selectedColor){
                            case "red":
                                symbol.setIdDrawable(R.drawable.redfirevehicle);
                                break;
                            case "orange":
                                break;
                            case "purple":
                                symbol.setIdDrawable(R.drawable.purplefirevehicle);
                                break;
                            case "green":
                                symbol.setIdDrawable(R.drawable.greenfirevehicle);
                                break;
                            case "blue":
                                break;
                        }
                        break;
                    case "noteffectivefirevehicle":
                        switch(selectedColor){
                            case "red":
                                symbol.setIdDrawable(R.drawable.rednoteffectivefirevehicle);
                                break;
                            case "orange":
                                break;
                            case "purple":
                                symbol.setIdDrawable(R.drawable.purplenoteffectivefirevehicle);
                                break;
                            case "green":
                                symbol.setIdDrawable(R.drawable.greennoteffectivefirevehicle);
                                break;
                            case "blue":
                                break;
                        }
                        break;
                    case "area":
                        switch(selectedColor){
                            case "red":
                                symbol.setIdDrawable(R.drawable.redarea);
                                break;
                            case "orange":
                                symbol.setIdDrawable(R.drawable.orangearea);
                                break;
                            case "purple":
                                break;
                            case "green":
                                symbol.setIdDrawable(R.drawable.greenarea);
                                break;
                            case "blue":
                                symbol.setIdDrawable(R.drawable.bluearea);
                                break;
                        }
                        break;
                    default:
                        break;
                }
                return symbol;
            }
        }
        return null;
    }

    /**
     * Gets selectedColor.
     *
     * @return the selectedColor
     */
    public String getSelectedColor() {
        return selectedColor;
    }

    /**
     * Sets selectedColor.
     *
     * @param selectedColor the selectedColor
     */
    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * Add all radio button listeners. to know the selectedColor chosen by end user
     * TODO: Here we can also use an enum for the selectedColor {@link Color}
     * TODO: you can update the layout for all radio buttons with the XML attribute android:onClick
     *
     * @param view the view
     */
    public void addRadioButtonListeners(View view) {
        RadioButton rbRouge = (RadioButton) view.findViewById(R.id.redradioButton);
        rbRouge.setOnClickListener(v -> setSelectedColor("red"));

        RadioButton rbVert = (RadioButton) view.findViewById(R.id.greenradioButton);
        rbVert.setOnClickListener(v -> setSelectedColor("green"));

        RadioButton rbBleu = (RadioButton) view.findViewById(R.id.blueradioButton);
        rbBleu.setOnClickListener(v -> setSelectedColor("blue"));

        RadioButton rbOrange = (RadioButton) view.findViewById(R.id.orangeradioButton);
        rbOrange.setOnClickListener(v -> setSelectedColor("orange"));

        RadioButton rbViolet = (RadioButton) view.findViewById(R.id.purpleradioButton);
        rbViolet.setOnClickListener(v -> setSelectedColor("purple"));

    }

    /**
     * On button pressed.
     * TODO: Is this method in use ? Cannot determine If it is called by a layout or something else, but apparently it's a dead code !
     * @param uri the uri
     */
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
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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
        /**
         * On fragment interaction.
         *
         * @param uri the uri
         */
        void onFragmentInteraction(Uri uri);
    }


}
