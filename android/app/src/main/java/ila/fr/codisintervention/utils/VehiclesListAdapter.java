package ila.fr.codisintervention.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;

import static android.content.ContentValues.TAG;

/**
 * Created by aminesoumiaa on 22/03/18.
 * Adapter used to be able to convert Model Based Intervention into Intervention selectable in a ListView
 */
public class VehiclesListAdapter extends ArrayAdapter<Vehicle> {
    /**
     * Application Context
     */
    private final Context context;

    /**
     * Vehicles List
     */
    private List<Vehicle> vehiclesList;

    /**
     * Constructor for the adapter
     *
     * @param context                  the Application Context
     * @param listItemLayoutResourceId id of the list item layout used
     * @param vehiclesList             List of the vehicles to show them in the listview
     */
    public VehiclesListAdapter(Context context, int listItemLayoutResourceId,
                               List<Vehicle> vehiclesList) {
        super(context, listItemLayoutResourceId, vehiclesList);
        this.context = context;
        this.vehiclesList = new ArrayList<>();
        this.vehiclesList.addAll(vehiclesList);
    }

    /**
     * Gets vehicles list.
     *
     * @return the vehicles list
     */
    public List<Vehicle> getVehiclesList() {
        return vehiclesList;
    }

    /**
     * ViewHolder that represent elements bein displayed in a List Item.
     */
    private class ViewHolder {
        /**
         * The Code of a vehicle.
         */
        TextView code;
        /**
         * The Name of a vehicle.
         */
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.moyen_infos_layout, null);

            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.name.setOnClickListener(v -> {
                CheckBox cb = (CheckBox) v ;
                Vehicle vehicle = (Vehicle) cb.getTag();

                Log.d(TAG, "Vehicle checked: " + vehicle.getLabel());

                vehicle.setSelected(cb.isChecked());
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Vehicle vehicle = vehiclesList.get(position);
        holder.code.setText(" (" +  vehicle.getType() + ")");
        holder.name.setText(vehicle.getLabel());
        holder.name.setChecked(vehicle.isSelected());
        holder.name.setTag(vehicle);

        return convertView;
    }
}
