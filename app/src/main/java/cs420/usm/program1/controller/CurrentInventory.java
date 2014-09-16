package cs420.usm.program1.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cs420.usm.program1.R;
import cs420.usm.program1.adapter.ItemAdapter;
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.sqlite.DBHandler;


public class CurrentInventory extends Activity {

    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_inventory);

    }

    public void onResume() {
        super.onResume();
        DBHandler db = new DBHandler(getApplicationContext());
        items = db.getItems();

        populateList();
    }

    private void populateList() {
        ListView itemsList = (ListView) findViewById(R.id.current_inventory_list);
        ItemAdapter itemAdapter = new ItemAdapter(this, R.layout.inventory_table_row, items);
        itemsList.setClickable(true);
        itemsList.setAdapter(itemAdapter);
        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(CurrentInventory.this, ItemDetails.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        items = data.getParcelableArrayListExtra("items");
        populateList();
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
        getMenuInflater().inflate(R.menu.current_inventory, menu);
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
            intent.putExtra("items", items);
            setResult(1, intent);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewItem(View view) {
        Intent intent = new Intent(view.getContext(), NewItem.class);
        intent.putExtra("items", items);
        startActivity(intent);
    }
}
