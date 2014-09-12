package cs420.usm.program1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cs420.usm.program1.R;
import cs420.usm.program1.containers.Item;

/**
 * Created by drs4 on 9/7/2014.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    int layoutResourceId;
    ArrayList<String> data = null;

    public SpinnerAdapter (Context context, int layoutResourceId, ArrayList<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.qty = (TextView)row.findViewById(R.id.simple_spinner_text);

            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        holder.qty.setText(data.get(position));

        return row;
    }

    static class ViewHolder {
        TextView qty;
    }
}
