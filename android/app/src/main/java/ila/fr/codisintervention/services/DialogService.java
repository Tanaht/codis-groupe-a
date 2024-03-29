package ila.fr.codisintervention.services;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

import ila.fr.codisintervention.fragments.SymbolUpdateDialog;
import ila.fr.codisintervention.fragments.UnitUpdateDialog;
import ila.fr.codisintervention.fragments.listeners.SymbolUpdateDialogListener;
import ila.fr.codisintervention.fragments.listeners.UnitUpdateDialogListener;
import ila.fr.codisintervention.models.model.Unit;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;

/**
 * The purpose of this class is to display Dialog on demand, so he keep the logic of fragment transactions inside it.
 */
public class DialogService {
    private static final String TAG = "DialogService";
    private static final String DIALOG_TAG = "DIALOG_TAG";

    private DialogService() {}

    /**
     * Entrypoint to offer update of Symbol through Dialog this entrypoint also unsure that a listener is filled in here
     */
    public static void showSymbolUpdateDialog(FragmentManager manager, Symbol symbol, SymbolUpdateDialogListener listener) {
        if(listener == null) {
            Log.e(TAG, "An instance of SymbolUpdateDialogListener must be provided !");
        }


        String identifier = "";
        String details = "";

        if(symbol.getPayload() != null) {
            identifier = symbol.getPayload().getIdentifier();
            details = symbol.getPayload().getDetails();
        }
        FragmentTransaction ft = manager.beginTransaction();

        removePreviousDialogIfNotAlready(manager, ft);

        SymbolUpdateDialog newFragment = SymbolUpdateDialog.newInstance(identifier, details);
        newFragment.setSymbolUpdateDialogListener(listener);
        newFragment.show(ft, DIALOG_TAG);
    }

    /**
     * Entrypoint to offer update of Unit through Dialog this entrypoint also unsure that a listener is filled in here
     */
    public static void showUnitUpdateDialog(FragmentManager manager, Unit unit, UnitUpdateDialogListener listener) {
        if(listener == null) {
            Log.e(TAG, "An instance of UnitUpdateDialogListener must be provided !");
        }

        FragmentTransaction ft = manager.beginTransaction();

        removePreviousDialogIfNotAlready(manager, ft);

        UnitUpdateDialog newFragment = UnitUpdateDialog.newInstance(unit.getSymbolUnit().getColor(), unit.isMoving());
        newFragment.setUnitUpdateDialogListener(listener);
        newFragment.show(ft, DIALOG_TAG);
    }

    /**
     * method used to keep the fragment transaction safe to avoid DialogFragment to overrides in the view
     * @param manager
     * @param ft
     */
    private static void removePreviousDialogIfNotAlready(FragmentManager manager, FragmentTransaction ft) {
        Fragment prev = manager.findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
    }
}
