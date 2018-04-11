package ila.fr.codisintervention.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ila.fr.codisintervention.entities.Moyen;
import ila.fr.codisintervention.R;

/**
 * Created by aminesoumiaa on 22/03/18.
 */

public class MoyenListAdapter extends ArrayAdapter<Moyen> {
    private final Context context;
    private ArrayList<Moyen> moyenList;

    public MoyenListAdapter(Context context, int textViewResourceId,
                           ArrayList<Moyen> moyenList) {
        super(context, textViewResourceId, moyenList);
        this.context = context;
        this.moyenList = new ArrayList<Moyen>();
        this.moyenList.addAll(moyenList);
    }

    public ArrayList<Moyen> getMoyenList() {
        return moyenList;
    }

    private class ViewHolder {
        TextView code;
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.moyen_infos_layout, null);

            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Moyen moyen = (Moyen) cb.getTag();
                    Toast.makeText(context.getApplicationContext(),
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    moyen.setSelected(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Moyen moyen = moyenList.get(position);
        holder.code.setText(" (" +  moyen.getCode() + ")");
        holder.name.setText(moyen.getName());
        holder.name.setChecked(moyen.isSelected());
        holder.name.setTag(moyen);

        return convertView;
    }
}
