package cs420.usm.program1.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

import cs420.usm.program1.R;
import cs420.usm.program1.containers.Customer;
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.containers.Order;
import cs420.usm.program1.containers.PurchasedItem;
import cs420.usm.program1.sqlite.DBHandler;


public class Admin extends Activity {

    ArrayList<Customer> customers;
    ArrayList<Item> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        DBHandler db = new DBHandler(this.getApplicationContext());

        //temp list items
        Customer customer = new Customer(1, "Danno Shepard", "USM");
        Item item = new Item(1, "Sun Flowers", 5);


        Order order = new Order();
        order.items.add(new PurchasedItem(1, "Sun Flower", 3));
        order.date = new Date().toString();
        order.id = 1;

        System.out.println(order.date);

        customer.orderHistory.add(order);
        db.addCustomer(customer);
        db.addItemToInventory(item);


        customers = new ArrayList<Customer>();
        items = new ArrayList<Item>();
        customers = db.getCustomers();
        items = db.getItems();
        //customers.add(customer);
        //items.add(item);


        //TODO -- Read saved data and store into these arrayLists
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
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
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        System.out.println("onActivityResult!!");
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 0)
            customers = intent.getParcelableArrayListExtra("customers");
        else if (requestCode == 1)
            items = intent.getParcelableArrayListExtra("items");

    }

    public void displayCustomers(View view) {
        Intent intent = new Intent(view.getContext(), Customers.class);
        intent.putExtra("customers", customers);
        intent.putExtra("items", items);
        startActivityForResult(intent, 0);
    }

    public void displayInventory(View view) {
        Intent intent = new Intent(view.getContext(), CurrentInventory.class);
        intent.putExtra("items", items);
        startActivityForResult(intent, 1);
    }
}
