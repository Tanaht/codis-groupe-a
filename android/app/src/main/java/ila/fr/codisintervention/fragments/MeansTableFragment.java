package ila.fr.codisintervention.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
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

import ila.fr.codisintervention.R;

/**
 * The type Means table fragment.
 */
public class MeansTableFragment extends Fragment {
    /**
     * The Title.
     */
    TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_means_table,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        title.setText(getString(R.string.title_means_table));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TableLayout meansTable;
        meansTable = (TableLayout) getActivity().findViewById(R.id.meansTable);

        String[] headers = {
                getResources().getString(R.string.header_1),
                getResources().getString(R.string.header_2),
                getResources().getString(R.string.header_3),
                getResources().getString(R.string.header_4),
                getResources().getString(R.string.header_5),
                getResources().getString(R.string.header_6)};

        TableRow tableRow = new TableRow(getActivity());
        meansTable.addView(tableRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        meansTable.setBackgroundColor(getResources().getColor(R.color.grey));

        tableRow.setLayoutParams(new LayoutParams(headers.length));

        int i = 0;
        for (String header : headers) {
            TextView text = createTextView(false , i == headers.length - 1,0);
            text.setText(header);
            text.setBackgroundResource(R.color.colorPrimary);
            text.setTextSize(20);
            text.setTypeface(text.getTypeface(), Typeface.BOLD);
            text.setGravity(Gravity.CENTER);
            tableRow.addView(text, i++);
        }


        for (int j = 0; j < 10; j++) {
            tableRow = new TableRow(getActivity());
            meansTable.addView(tableRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            i = 0;
            for (String header : headers) {
                TextView text = createTextView(j==9, i == headers.length - 1, j);
                text.setText("123");

                tableRow.addView(text, i++);
                text.setGravity(Gravity.RIGHT);
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
