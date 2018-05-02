package ila.fr.codisintervention.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.models.model.Request;

/**
 * Created by aminesoumiaa on 26/04/18.
 * Adapter used to be able to convert Model Based Vehicles List into a ListView
 */

public class RequestsListAdapter extends ArrayAdapter<Request> {
    private static final String TAG = "RequestsListAdapter";

    /**
     * Application Context
     */
    private final Context context;

    /**
     * Constructor for the adapter
     *
     * @param context                  the Application Context
     * @param listItemLayoutResourceId id of the list item layout used
     * @param requestList             List of vehicle requests to be shown in the listview
     */
    public RequestsListAdapter(Context context, int listItemLayoutResourceId, List<Request> requestList) {
        super(context, listItemLayoutResourceId, requestList);
        this.context = context;
    }

    /**
     * ViewHolder that represent elements bein displayed in a List Item.
     */
    private class ViewHolder {
        /**
         * The type of the requested vehicle (VLCG,FPT,..)
         */
        TextView vehicleType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.requests_list_item_layout, null);

            holder = new ViewHolder();
            holder.vehicleType = (TextView) convertView.findViewById(R.id.RequestVehicleType);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Request request = this.getItem(position);

        holder.vehicleType.setText(request.getVehicle().getType());

        return convertView;
    }
}
