package cs420.usm.program1.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import cs420.usm.program1.R;
import cs420.usm.program1.adapter.ItemAdapter;
import cs420.usm.program1.adapter.PurchasedItemAdapter;
import cs420.usm.program1.adapter.SpinnerAdapter;
import cs420.usm.program1.containers.Customer;
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.containers.Order;
import cs420.usm.program1.containers.PurchaseSelection;
import cs420.usm.program1.containers.PurchasedItem;
import cs420.usm.program1.sqlite.DBHandler;


public class PlaceOrder extends Activity implements Observer  {

    ArrayList<Item> items;
    ArrayList<Customer> customers;
    int position;

    Order order;
    ArrayList<PurchasedItem> shoppingCart;

    ListView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Intent data = getIntent();
        items = data.getParcelableArrayListExtra("items");
        customers = data.getParcelableArrayListExtra("customers");
        position = data.getIntExtra("position", 0);

        orderList = (ListView) findViewById(R.id.purchaseList);
        PurchasedItemAdapter itemAdapter = new PurchasedItemAdapter(this, R.layout.purchase_table_row, items, this);
        orderList.setAdapter(itemAdapter);

        shoppingCart = new ArrayList<PurchasedItem>();
        for (int i = 0; i < orderList.getCount(); i++) {
            shoppingCart.add(new PurchasedItem(items.get(i).id, items.get(i).name, 0));
        }

    }

    public void update(Observable obs, Object obj) {
        PurchaseSelection ps = (PurchaseSelection) obj;
        System.out.println("YEAHHH! " + ps.position + ": " + ps.selection);
        shoppingCart.get(ps.position).qtyPurchased = ps.selection;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.place_order, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("order", order);
        intent.putExtra("position", position);
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
            intent.putExtra("order", order);
            intent.putExtra("position", position);
            setResult(Activity.RESULT_OK, intent);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void makePurchase(View view) {
        //TODO -- save the order to the customer to be displayed in the customer's history
        Toast toast = Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_LONG);
        toast.show();
        ArrayList<PurchasedItem> finalCart = new ArrayList<PurchasedItem>();
        for (PurchasedItem item : shoppingCart) {
            if (item.qtyPurchased > 0)
                finalCart.add(item);
        }

        order = new Order(finalCart, new Date().toString());
        order.id = getNextOrderId();

        DBHandler db = new DBHandler(getApplicationContext());
        db.addOrderToCustomer(order, customers.get(position));
        removeItemsFromInventory(finalCart);
        PurchasedItemAdapter adapter = (PurchasedItemAdapter) orderList.getAdapter();
        adapter.notifyDataSetChanged();

    }

    public void removeItemsFromInventory(ArrayList<PurchasedItem> items) {
        DBHandler db = new DBHandler(getApplicationContext());
        for (Item inventoryItem : this.items) {
            for (PurchasedItem purchasedItem : items) {
                if (inventoryItem.id == purchasedItem.id) {
                    inventoryItem.qtyAvailable -= purchasedItem.qtyPurchased;
                    db.update(inventoryItem);
                }
            }
        }
    }
    public int getNextOrderId() {
        int id = 0;
        for (Order order : customers.get(position).orderHistory) {
            if (order.id > id)
                id = order.id;
        }
        return id;
    }
}
