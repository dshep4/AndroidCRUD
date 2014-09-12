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
import cs420.usm.program1.containers.PurchasedItem;

/**
 * Created by drs4 on 9/6/2014.
 */
public class OrderItemAdapter  extends ArrayAdapter<PurchasedItem> {

    Context context;
    int layoutResourceId;
    ArrayList<PurchasedItem> data = null;

    public OrderItemAdapter(Context context, int layoutResourceId, ArrayList<PurchasedItem> data) {
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
            holder.name = (TextView)row.findViewById(R.id.orderHistoryDetails_name);
            holder.qty = (TextView)row.findViewById(R.id.orderHistoryDetails_qty);
            holder.id = (TextView)row.findViewById(R.id.orderHistoryDetails_id);

            row.setTag(holder);
        } else {
            holder = (ItemHolder)row.getTag();
        }

        PurchasedItem item = data.get(position);
        holder.name.setText(item.name);
        holder.qty.setText(String.format("%d",item.qtyPurchased));
        holder.id.setText(String.format("ID: %d", item.id));

        return row;
    }

    static class ItemHolder {
        TextView name;
        TextView qty;
        TextView id;

    }
}

