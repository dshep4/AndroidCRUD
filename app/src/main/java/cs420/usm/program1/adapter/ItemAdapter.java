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
import cs420.usm.program1.containers.Item;

/**
 * Created by drs4 on 9/3/2014.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    Context context;
    int layoutResourceId;
    ArrayList<Item> data = null;

    public ItemAdapter(Context context, int layoutResourceId, ArrayList<Item> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemHolder();
            holder.name = (TextView)row.findViewById(R.id.inventory_row_name);
            holder.qtyAvailable = (TextView)row.findViewById(R.id.inventory_row_qty);
            holder.id = (TextView)row.findViewById(R.id.inventory_row_id);

            row.setTag(holder);
        } else {
            holder = (ItemHolder)row.getTag();
        }

        Item item = data.get(position);
        holder.name.setText(item.name);
        holder.qtyAvailable.setText(String.format("%d",item.qtyAvailable));
        holder.id.setText(String.format("%d",item.id));


        return row;
    }

    static class ItemHolder {
        TextView name;
        TextView qtyAvailable;
        TextView id;

    }
}
