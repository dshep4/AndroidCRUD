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
import cs420.usm.program1.containers.Customer;

/**
 * Created by drs4 on 9/3/2014.
 */
public class CustomerAdapter extends ArrayAdapter<Customer> {

    Context context;
    int layoutResourceId;
    ArrayList<Customer> data = null;

    public CustomerAdapter(Context context, int layoutResourceId, ArrayList<Customer> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CustomerHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CustomerHolder();
            holder.name = (TextView)row.findViewById(R.id.customer_row_name);
            holder.address = (TextView)row.findViewById(R.id.customer_row_address);
            holder.id = (TextView)row.findViewById(R.id.customer_row_id);

            row.setTag(holder);
        } else {
            holder = (CustomerHolder)row.getTag();
        }

        Customer customer = data.get(position);
        holder.name.setText(customer.name);
        holder.address.setText(customer.address);
        holder.id.setText(String.format("ID: %d",customer.id));

        return row;
    }

    static class CustomerHolder {
        TextView name;
        TextView address;
        TextView id;

    }

}
