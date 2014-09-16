package cs420.usm.program1.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cs420.usm.program1.R;
import cs420.usm.program1.adapter.CustomerHistoryAdapter;
import cs420.usm.program1.containers.Customer;
import cs420.usm.program1.containers.Order;
import cs420.usm.program1.sqlite.DBHandler;


public class CustomerDetails extends Activity {

    ArrayList<Customer> customers;
    ArrayList<Order> orders;
    int position;

    ListView orderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        System.out.println("CustomerDetails - onCreate() Position: " + position);

    }

    public void onResume() {
        super.onResume();
        System.out.println("onResume()!!");

        DBHandler db = new DBHandler(getApplicationContext());
        customers = db.getOnlyCustomers();
        orders = db.getOrderHistoryOfCustomer(customers.get(position));

        TextView name = (TextView) findViewById(R.id.customer_details_name);
        TextView address = (TextView) findViewById(R.id.customer_details_address);
        TextView id = (TextView) findViewById(R.id.customer_details_id);

        name.setText(customers.get(position).name);
        address.setText(customers.get(position).address);
        id.setText(String.format("ID: %d", customers.get(position).id));

        orderHistory = (ListView) findViewById(R.id.customer_details_order_history_list);
        CustomerHistoryAdapter customerHistoryAdapter = new CustomerHistoryAdapter(this, R.layout.customer_history, orders);
        orderHistory.setAdapter(customerHistoryAdapter);
        orderHistory.setClickable(true);
        orderHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int listPosition, long arg3) {
                Intent intent = new Intent(CustomerDetails.this, OrderDetails.class);
                intent.putExtra("position", position);
                intent.putExtra("orderId", orders.get(listPosition).id);
                startActivityForResult(intent, 0);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        System.out.println("onActivityResult!!");
        super.onActivityResult(requestCode, resultCode, intent);
        position = intent.getIntExtra("position", 0);
        CustomerHistoryAdapter customerHistoryAdapter = (CustomerHistoryAdapter) orderHistory.getAdapter();
        customerHistoryAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        else if (id == android.R.id.home) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(view.getContext(), PlaceOrder.class);
        intent.putExtra("position", position);
        startActivityForResult(intent, 0);
    }
}
