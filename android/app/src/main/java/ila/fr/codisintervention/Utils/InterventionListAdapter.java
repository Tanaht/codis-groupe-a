package ila.fr.codisintervention.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.R;

/**
 * Created by aminesoumiaa on 23/03/18.
 */

public class InterventionListAdapter extends ArrayAdapter<Intervention> {
    private final Context context;

    public InterventionListAdapter(Context context, int textViewResourceId,
                            ArrayList<Intervention> interventionList) {
        super(context, textViewResourceId, interventionList);
        this.context = context;
    }

    private class ViewHolder {
        TextView id;
        TextView date;
        TextView address;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //Log.v("ConvertView", String.valueOf(position));

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
