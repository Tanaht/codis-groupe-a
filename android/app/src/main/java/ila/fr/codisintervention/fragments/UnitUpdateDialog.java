package ila.fr.codisintervention.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.fragments.listeners.UnitUpdateDialogListener;
import ila.fr.codisintervention.models.model.map_icon.Color;

public class UnitUpdateDialog extends DialogFragment{
    private static final String TAG = "SymbolUpdateDialog";

    /**
     * Create a new instance of UnitUpdateDialog, providing "default value for inner Form"
     * as an argument.
     */
    public static UnitUpdateDialog newInstance(Color color, boolean moving) {
        if(color == null)
            throw new RuntimeException("parameters are mandatory for this method call");

        UnitUpdateDialog f = new UnitUpdateDialog();

        Bundle args = new Bundle();

        args.putString("color", color.name());
        args.putBoolean("moving", moving);

        f.setArguments(args);
        return f;
    }

    /**
     * Custom listener that the owner of the DialogFragment must implement to be aware of change
     */
    private UnitUpdateDialogListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.title_dialog_fragment);
        View v = inflater.inflate(R.layout.unit_fragment_dialog, container, false);

        v.findViewById(R.id.validate_dialog_fragment).setOnClickListener(view -> {
            //TODO: notify listener with correct values

            if(listener != null)
                listener.onUnitUpdated(null, false);

            getDialog().dismiss();
        });

        v.findViewById(R.id.invalidate_dialog_fragment).setOnClickListener(view -> {
            Log.d(TAG, "Cancel update");
            getDialog().cancel();
        });
        return v;
    }

    public void setUnitUpdateDialogListener(UnitUpdateDialogListener listener) {
        this.listener = listener;
    }
}
