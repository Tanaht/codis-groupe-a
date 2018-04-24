package ila.fr.codisintervention.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.models.messages.Intervention;

/**
 * Created by aminesoumiaa on 23/03/18.
 * Adapter used to be able to convert Model Based Intervention into Intervention selectable in a ListView
 */

public class InterventionListAdapter extends ArrayAdapter<Intervention> {
    private static final String TAG = "InterventionListAdapter";
    /**
     * Application Context
     */
    private final Context context;

    /**
     * Constructor for the adapter
     * @param context the application Context
     * @param listItemLayoutResourceId id of the list item layout used
     * @param interventionList List of the interventions to show them in the listview
     */
    public InterventionListAdapter(Context context, int listItemLayoutResourceId, List<Intervention> interventionList) {
        super(context, listItemLayoutResourceId, interventionList);
        this.context = context;
    }

    /**
     * ViewHolder that represent elements bein displayed in a List Item.
     */
    private class ViewHolder {
        /**
         * The Id of an Intervention
         */
        TextView id;

        /**
         * The date of creation of an intervention
         */
        TextView date;

        /**
         * The Address of the intervention
         */
        TextView address;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.interventions_list_item_layout, null);

            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.InterventionListItemId);
            holder.date = (TextView) convertView.findViewById(R.id.InterventionListItemDate);
            holder.address = (TextView) convertView.findViewById(R.id.InterventionListItemAddress);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Intervention intervention = this.getItem(position);

        Date date = new Date(intervention.getDate());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate = df.format(date);

        holder.id.setText(intervention.getCode());
        holder.date.setText(stringDate);
        holder.address.setText(intervention.getAddress());

        return convertView;
    }
}
