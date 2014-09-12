package cs420.usm.program1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cs420.usm.program1.R;
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.containers.PurchaseSelection;
import cs420.usm.program1.containers.PurchasedItem;

/**
 * Created by drs4 on 9/6/2014.
 */
public class PurchasedItemAdapter extends ArrayAdapter<Item> implements PropertyChangeListener {
    Context context;
    int layoutResourceId;
    ArrayList<Item> data = null;
    final Observer observer;

    public PurchasedItemAdapter(Context context, int layoutResourceId, ArrayList<Item> data, Observer observer) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.observer = observer;
    }

    public void propertyChange(PropertyChangeEvent event) {

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemHolder();
            holder.name = (TextView)row.findViewById(R.id.purchase_table_itemName);
            holder.qty = (TextView)row.findViewById(R.id.purchase_table_qty);
            holder.spinner = (Spinner) row.findViewById(R.id.purchase_table_spinner);


            row.setTag(holder);
        } else {
            holder = (ItemHolder)row.getTag();
        }

        Item item = data.get(position);
        holder.name.setText(item.name);
        holder.qty.setText(String.format("%d", item.qtyAvailable));

        int stop = data.get(position).qtyAvailable;
        ArrayList<String> stringList = new ArrayList<String>();
        for (int k = 0; k <= stop; k++) {
            stringList.add(String.format("%d", k));
        }
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this.context, R.layout.simple_spinner_item, stringList);
        holder.spinner.setAdapter(spinnerAdapter);
        final ItemHolder finalHolder = holder;
        finalHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int item = finalHolder.spinner.getSelectedItemPosition();

                PurchaseSelection ps = new PurchaseSelection();
                ps.position = position;
                ps.selection = item;
                observer.update(new Observable(), ps);
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }


        });

        return row;
    }


    static class ItemHolder {
        TextView name;
        TextView qty;
        Spinner spinner;

    }
}
