package cs420.usm.program1.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs420.usm.program1.R;
import cs420.usm.program1.containers.Customer;
import cs420.usm.program1.sqlite.DBHandler;


public class AddCustomer extends Activity {

    ArrayList<Customer> customers;
    EditText name;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        DBHandler db = new DBHandler(getApplicationContext());
        customers = db.getOnlyCustomers();

        name = (EditText) findViewById(R.id.add_customer_name);
        address = (EditText) findViewById(R.id.add_customer_address);

        name.setHint("Name");
        address.setHint("Address");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_customer, menu);
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
        } else if (id == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra("customers", customers);
            setResult(Activity.RESULT_OK, intent);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("customers", customers);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void submitNewCustomer(View view) {
        System.out.println(name.getText().toString());
        System.out.println(address.getText().toString());

        if ( name.getText().toString().equals("") || address.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if ( nameExists(name.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(), "Customer already exists!", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Customer newCustomer = new Customer(getNextId(), name.getText().toString(), address.getText().toString());
            customers.add(newCustomer);
            Toast toast = Toast.makeText(getApplicationContext(), "Customer Added!", Toast.LENGTH_LONG);
            toast.show();

            DBHandler db = new DBHandler(this.getApplicationContext());
            db.addCustomer(newCustomer);
        }
    }

    public void clearFields(View view) {
        name.setText("");
        address.setText("");
    }

    private boolean nameExists(String name) {
        for (Customer customer : customers) {
            if (customer.name.equals(name))
                return true;
        }
        return false;
    }

    //TODO - autogenerate Id values
    private int getNextId() {
        int id = 0;
        for (Customer customer : customers) {
            if (customer.id > id) {
                id = customer.id;
            }
        }
        return id+1;
    }
}
