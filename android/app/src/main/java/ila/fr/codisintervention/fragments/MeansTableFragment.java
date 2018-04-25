package ila.fr.codisintervention.fragments;

import android.content.Context;
import android.net.Uri;
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

public class MeansTableFragment extends Fragment {
    TextView title;
    private TableLayout meansTable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_means_table,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);

        title.setText("Je suis un fragment");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        meansTable = (TableLayout) getActivity().findViewById(R.id.meansTable);

        String[] titles = getResources().getStringArray(R.array.titles);

        TableRow tableRow = new TableRow(getActivity());
        meansTable.addView(tableRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        meansTable.setBackgroundColor(getResources().getColor(R.color.grey));

        tableRow.setLayoutParams(new LayoutParams(titles.length));

        int i = 0;
        for (String player : titles) {
            TextView text = createTextView(false , i == titles.length - 1);
            text.setText(player);
            text.setGravity(Gravity.CENTER);
            tableRow.addView(text, i++);
        }


        for (int j = 0; j < 10; j++) {
            tableRow = new TableRow(getActivity());
            meansTable.addView(tableRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            i = 0;
            for (String player : titles) {
                TextView text = createTextView(j==9, i == titles.length - 1);
                text.setText("123");
                tableRow.addView(text, i++);
                text.setGravity(Gravity.RIGHT);
            }
        }

    }

    private TextView createTextView(boolean endline, boolean endcolumn){
        TextView text = new TextView(getActivity(), null, R.style.meansTabHeaderCol);
        int bottom = endline ? 1 : 0;
        int right = endcolumn ? 1 :0;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.3f);
        params.setMargins(1, 1, right, bottom);
        text.setLayoutParams(params);
        text.setPadding(4, 4, 10, 4);
        text.setBackgroundColor(getResources().getColor(R.color.white));
        return text;
    }

}
