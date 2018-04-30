package ila.fr.codisintervention.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.fragments.listeners.SymbolUpdateDialogListener;

public class SymbolUpdateDialog extends DialogFragment {
    private static final String TAG = "SymbolUpdateDialog";

    /**
     * Create a new instance of SymbolUpdateDialog, providing "default value for edit texts"
     * as an argument.
     */
    public static SymbolUpdateDialog newInstance(String identifier, String details) {
        SymbolUpdateDialog f = new SymbolUpdateDialog();

        Bundle args = new Bundle();


        args.putString("identifier", identifier != null ? identifier : "");
        args.putString("details", details != null ? details : "");

        f.setArguments(args);
        return f;
    }

    /**
     * Custom listener that the owner of the DialogFragment must implement to be aware of change
     */
    private SymbolUpdateDialogListener listener;
    private EditText identifier;
    private EditText details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.title_dialog_fragment);
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);


        this.identifier = v.findViewById(R.id.editText_identifier);
        this.details = v.findViewById(R.id.editText_details);

        identifier.setText(getArguments().getString("identifier"));
        details.setText(getArguments().getString("details"));

        v.findViewById(R.id.validate_dialog_fragment).setOnClickListener(view -> {
            Log.d(TAG, "Update identifier and details with values: " + identifier.getText() + ", " + details.getText());

            String mIdentifier = this.identifier.getText() != null ? this.identifier.getText().toString() : "";
            String mDetails = this.details.getText() != null ? this.details.getText().toString() : "";

            if(listener != null)
                listener.onSymbolUpdated(mIdentifier.trim(), mDetails.trim());

            getDialog().dismiss();
        });

        v.findViewById(R.id.invalidate_dialog_fragment).setOnClickListener(view -> {
            Log.d(TAG, "Cancel update");
            getDialog().cancel();
        });
        return v;
    }

    public void setSymbolUpdateDialogListener(SymbolUpdateDialogListener listener) {
        this.listener = listener;
    }
}