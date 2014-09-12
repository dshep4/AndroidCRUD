package cs420.usm.program1.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cs420.usm.program1.R;
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.sqlite.DBHandler;


public class ItemDetails extends Activity {

    TextView name;
    TextView id;
    EditText qty;
    TextView response;

    ArrayList<Item> items;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        name = (TextView) findViewById(R.id.item_details_name);
        id = (TextView) findViewById(R.id.item_details_id);
        qty = (EditText) findViewById(R.id.item_details_qty_editable);
        response = (TextView) findViewById(R.id.item_details_response);

        Intent data = getIntent();
        items = data.getParcelableArrayListExtra("items");
        position = data.getIntExtra("position", 0);

        name.setText(items.get(position).name);
        id.setText(String.format("ID: %d",items.get(position).id));
        qty.setText(String.format("%d", items.get(position).qtyAvailable));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("items", items);
        setResult(Activity.RESULT_OK, intent);
        finish();
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_details, menu);
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
            setResult(Activity.RESULT_OK, intent);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateItem(View view) {
        if (qty.getText().toString().equals("")){
            response.setText("Quantity must not be empty");
        }
        else {
            items.get(position).updateQtyToAmount(Integer.parseInt(qty.getText().toString()));
            response.setText("Item quantity updated. \n Press back to return.");

            DBHandler db = new DBHandler(getApplicationContext());
            db.update(items.get(position));
        }
    }
}
