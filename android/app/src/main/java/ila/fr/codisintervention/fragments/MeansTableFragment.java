package ila.fr.codisintervention.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.models.model.Unit;

/**
 * The type Means table fragment.
 */
public class MeansTableFragment extends Fragment {
    private static final String TAG = "MeansTableFragment";
    private TableLayout meansTable;
    private List<String> headers;
    private ModelServiceBinder.IMyServiceMethod modelService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_means_table,container,false);

        meansTable = rootView.findViewById(R.id.meansTable);

        headers = Arrays.asList(getResources().getString(R.string.header_1),
                getResources().getString(R.string.header_2),
                getResources().getString(R.string.header_3),
                getResources().getString(R.string.header_4),
                getResources().getString(R.string.header_5),
                getResources().getString(R.string.header_6));

        TableRow tableRow = new TableRow(getContext());
        meansTable.addView(tableRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        meansTable.setBackgroundColor(getResources().getColor(R.color.grey));

        tableRow.setLayoutParams(new LayoutParams(headers.size()));

        int i = 0;
        for (String header : headers) {
            TextView text = createTextView(false , i == headers.size() - 1,0);
            text.setText(header);
            text.setBackgroundResource(R.color.colorPrimary);
            text.setTextSize(20);
            text.setTypeface(text.getTypeface(), Typeface.BOLD);
            text.setGravity(Gravity.CENTER);
            tableRow.addView(text, i++);
        }

        return rootView;

    }

    public void onModelServiceConnected(ModelServiceBinder.IMyServiceMethod modelService) {
            this.modelService = modelService;
            this.refreshTable();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(modelService != null)
            this.refreshTable();
    }

    /**
     * Refresh the table with data from the model
     */
    public void refreshTable() {
        int count = meansTable.getChildCount();

        Log.d(TAG, "Refresh Table requested, there is " + count + " currently in table");
        if(meansTable.getChildCount() > 1)
            meansTable.removeViews(1, meansTable.getChildCount() - 1);
        TableRow row = null;

        List<Unit> units = modelService.getCurrentIntervention().getUnits();
        Log.d(TAG, "There is " + units.size() + " units to print in meansTable");
        for(int j = 0 ; j < units.size() ; j++) {
            Unit unit = units.get(j);
            Log.d(TAG, "Add Unit " + unit.getVehicle().getLabel());
            row = new TableRow(getContext());
            meansTable.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            for(int i = 0 ; i < headers.size(); i++) {
                TextView text = createTextView(j == units.size() - 1, i == headers.size() - 1, i);
                row.addView(text, i);
                text.setGravity(Gravity.END);

                switch (i) {
                    case 0://Label/Type
                        text.setText(unit.getVehicle().getLabel() == null || unit.getVehicle().getLabel().equals("") ? unit.getVehicle().getType() : unit.getVehicle().getLabel());
                        break;
                    case 1://status
                        if(unit.getVehicle().getStatus() != null)
                            text.setText(unit.getVehicle().getStatus().getTranslation());
                        break;
                    case 2://request date
                        if(unit.getRequestDate() != null && unit.getRequestDate().getTime() != 0) {
                            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                            text.setText(df.format(unit.getRequestDate()));
                        }
                        break;
                    case 3://accept date
                        if(unit.getAcceptDate() != null && unit.getAcceptDate().getTime() != 0) {
                            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                            text.setText(df.format(unit.getAcceptDate()));
                        }
                        break;
                    case 4://commited date
                        if(unit.getCommitedDate() != null && unit.getCommitedDate().getTime() != 0) {
                            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                            text.setText(df.format(unit.getCommitedDate()));
                        }
                        break;
                    case 5://released date
                        if(unit.getReleasedDate() != null && unit.getReleasedDate().getTime() != 0) {
                            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                            text.setText(df.format(unit.getReleasedDate()));
                        }
                        break;
                        default:
                            Log.w(TAG, "No row exist here to print something");
                }
            }
        }
    }


    private TextView createTextView(boolean endline, boolean endcolumn, int line){
        TextView text = new TextView(getActivity(), null, R.style.meansTabHeaderCol);
        int bottom = endline ? 1 : 0;
        int right = endcolumn ? 1 :0;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.3f);
        params.setMargins(1, 1, right, bottom);
        text.setLayoutParams(params);
        text.setPadding(4, 4, 10, 4);
        if(line%2 == 0){
            text.setBackgroundColor(getResources().getColor(R.color.white));
        }
        else {
            text.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }

        return text;
    }

}
