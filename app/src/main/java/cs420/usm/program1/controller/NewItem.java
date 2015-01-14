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
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.sqlite.DBHandler;


public class NewItem extends Activity {

    ArrayList<Item> items;

    EditText name;
    EditText qty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        DBHandler db = new DBHandler(getApplicationContext());
        items = db.getItems();

        name = (EditText) findViewById(R.id.add_new_item_name);
        qty = (EditText) findViewById(R.id.add_new_item_qty);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("items", items);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_item, menu);
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
        }  else if (id == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra("items", items);
            setResult(Activity.RESULT_OK, intent);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submitNewItem(View view) {
        if (name.getText().toString().equals("") || qty.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (itemExists(name.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(), "Item already exists!", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Item newItem = new Item(nextId(), name.getText().toString(), Integer.parseInt(qty.getText().toString()));
            items.add(newItem);

            DBHandler db = new DBHandler(this.getApplicationContext());
            db.addItemToInventory(newItem);

            Toast toast = Toast.makeText(getApplicationContext(), "Item Added!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private boolean itemExists(String name) {
        for (Item item : items) {
            if (item.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private int nextId() {
        int nextId = 0;
        for (Item item : items) {
            if (item.id > nextId) {
                nextId = item.id;
            }
        }

        return nextId +1;
    }

    public void clearNewItemFields(View view) {
        name.setText("");
        qty.setText("");
    }
}
