package cs420.usm.program1.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cs420.usm.program1.R;
import cs420.usm.program1.adapter.CustomerAdapter;
import cs420.usm.program1.containers.Customer;
import cs420.usm.program1.containers.Item;


public class Customers extends Activity {

    ArrayList<Customer> customers;
    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        Intent intent = getIntent();
        customers = intent.getParcelableArrayListExtra("customers");
        items = intent.getParcelableArrayListExtra("items");
        populateList();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        System.out.println("onActivityResult!!");
        super.onActivityResult(requestCode, resultCode, intent);
        customers = intent.getParcelableArrayListExtra("customers");
        populateList();
    }

    private void populateList() {
        ListView customerList = (ListView) findViewById(R.id.customersList);
        customerList.setClickable(true);
        CustomerAdapter customerAdapter = new CustomerAdapter(this, R.layout.customer_row, customers);
        customerList.setAdapter(customerAdapter);

        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(Customers.this, CustomerDetails.class);
                intent.putExtra("items", items);
                intent.putExtra("customers", customers);
                intent.putExtra("position", position);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("customers", customers);
        setResult(Activity.RESULT_OK, intent);
        finish();
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customers, menu);
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
            intent.putExtra("customers", customers);
            setResult(0, intent);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addCustomer(View view) {

        Intent intent = new Intent(view.getContext(), AddCustomer.class);
        intent.putExtra("customers", customers);
        startActivityForResult(intent, 0);
    }

}
