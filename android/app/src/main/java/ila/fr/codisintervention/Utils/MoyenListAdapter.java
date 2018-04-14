package ila.fr.codisintervention.utils;

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

import ila.fr.codisintervention.entities.Vehicle;
import ila.fr.codisintervention.R;

/**
 * Created by aminesoumiaa on 22/03/18.
 */

public class MoyenListAdapter extends ArrayAdapter<Vehicle> {
    private final Context context;
    private ArrayList<Vehicle> vehiclesList;

    public MoyenListAdapter(Context context, int textViewResourceId,
                           ArrayList<Vehicle> vehiclesList) {
        super(context, textViewResourceId, vehiclesList);
        this.context = context;
        this.vehiclesList = new ArrayList<Vehicle>();
        this.vehiclesList.addAll(vehiclesList);
    }

    public ArrayList<Vehicle> getVehiclesList() {
        return vehiclesList;
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
                    Vehicle vehicle = (Vehicle) cb.getTag();
                    Toast.makeText(context.getApplicationContext(),
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    vehicle.setSelected(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Vehicle vehicle = vehiclesList.get(position);
        holder.code.setText(" (" +  vehicle.getCode() + ")");
        holder.name.setText(vehicle.getName());
        holder.name.setChecked(vehicle.isSelected());
        holder.name.setTag(vehicle);

        return convertView;
    }
}
