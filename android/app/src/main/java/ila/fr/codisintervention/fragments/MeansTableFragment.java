package ila.fr.codisintervention.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    private void refreshTable() {
        TableRow row = null;

        List<Unit> units = modelService.getCurrentIntervention().getUnits();

        for(int j = 0 ; j < units.size() ; j++) {
            Unit unit = units.get(j);
            row = new TableRow(getContext());
            meansTable.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            for(int i = 0 ; i < headers.size(); i++) {
                TextView text = createTextView(j == units.size() - 1, i == headers.size() - 1, i);

                switch (i) {
                    case 0:
                        text.setText(unit.getVehicle().getLabel() == null ? unit.getVehicle().getType() : unit.getVehicle().getLabel());
                        break;
                    case 1:
                        if(unit.getVehicle().getStatus() != null)
                            text.setText(unit.getVehicle().getStatus().getTranslation());
                        break;
                    case 2:
                        if(unit.getRequestDate() != null) {
                            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                            text.setText(df.format(unit.getRequestDate()));
                        }
                        break;
                    case 3:
                        if(unit.getAcceptDate() != null) {
                            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                            text.setText(df.format(unit.getAcceptDate()));
                        }
                        break;
                    case 4:
                        if(unit.getCommitedDate() != null) {
                            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                            text.setText(df.format(unit.getCommitedDate()));
                        }
                        break;
                    case 5:
                        if(unit.getReleasedDate() != null) {
                            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                            text.setText(df.format(unit.getReleasedDate()));
                        }
                        break;
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
