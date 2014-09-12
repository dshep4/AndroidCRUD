package cs420.usm.program1.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import cs420.usm.program1.R;
import cs420.usm.program1.adapter.OrderItemAdapter;
import cs420.usm.program1.containers.Customer;
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.containers.Order;


public class OrderDetails extends Activity {

    //Customer customer;
    //int position;

    ArrayList<Customer> customers;
    ArrayList<Item> items;
    int position;
    int listPosition;

    ListView orderDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Intent data = getIntent();
        //customers = data.getParcelableArrayListExtra("customers");
        //items = data.getParcelableArrayListExtra("items");
        //position = data.getIntExtra("position", 0);
        //listPosition = data.getIntExtra("listPosition", 0);
        Order order = data.getParcelableExtra("order");

        orderDetailsList = (ListView) findViewById(R.id.order_details_list);
        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(this, R.layout.order_history_details_row, order.items);
        orderDetailsList.setAdapter(orderItemAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_details, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //intent.putExtra("customers", customers);
        setResult(Activity.RESULT_OK, intent);
        finish();
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
            //intent.putExtra("customers", customers);
            setResult(Activity.RESULT_OK, intent);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
