package ila.fr.codisintervention.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.models.model.Request;
import ila.fr.codisintervention.models.model.map_icon.vehicle.VehicleStatus;
import lombok.NoArgsConstructor;

/**
 * Fragment use to show an Alert Dialog to perform a VehicleRequest
 */
@NoArgsConstructor
public class AdditionalMeanFragment extends Fragment {
    private static final String TAG = "AdditionalMeanFragment";

    private ModelServiceBinder.IMyServiceMethod modelService;
    private String chosenVehicleType;

    private OnFragmentInteractionListener mListener;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdditionalMeanFragment.
     */
    public static AdditionalMeanFragment newInstance() {
        return new AdditionalMeanFragment();
    }

    public void setModelService(ModelServiceBinder.IMyServiceMethod modelService) {
        this.modelService = modelService;
    }

    /**
     * Show the alert dialog to choose a Vehicle type and request it to remote server
     */
    private void showPopup() {
        String[] vehicleTypes = new String[modelService.getVehicleTypes().size()];
        modelService.getVehicleTypes().toArray(vehicleTypes);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.label_choose_vehicle);

        builder.setSingleChoiceItems(vehicleTypes, -1, (dialog, item) -> {
            this.chosenVehicleType = vehicleTypes[item];
        });

        builder.setPositiveButton(R.string.label_validate, (dialog, id) -> {
            Log.d(TAG, "Create a Request for a vehicle with the given type: " + chosenVehicleType);
            Request request = new Request(chosenVehicleType);
            request.getVehicle().setStatus(VehicleStatus.CRM);
            mListener.onNewVehicleRequest(request);
        });


        builder.setNegativeButton(R.string.label_deny, (dialog, id) -> {
            Log.d(TAG, "Do not c a Request for a vehicle with the given type: " + chosenVehicleType);
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_additional_mean, container, false);

        rootView.findViewById(R.id.add_new_mean).setOnClickListener(v -> {
            showPopup();
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        void onNewVehicleRequest(Request request);
    }
}
