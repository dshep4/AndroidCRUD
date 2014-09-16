package cs420.usm.program1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cs420.usm.program1.R;
import cs420.usm.program1.containers.Order;

/**
 * Created by drs4 on 9/6/2014.
 */
public class CustomerHistoryAdapter extends ArrayAdapter<Order> {
    Context context;
    int layoutResourceId;
    ArrayList<Order> data = null;

    public CustomerHistoryAdapter(Context context, int layoutResourceId, ArrayList<Order> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoryHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new HistoryHolder();
            holder.date = (TextView)row.findViewById(R.id.customer_history_row_date);

            row.setTag(holder);
        } else {
            holder = (HistoryHolder)row.getTag();
        }

        holder.date.setText(data.get(position).date);

        return row;
    }

    static class HistoryHolder {
        TextView date;
    }

}


