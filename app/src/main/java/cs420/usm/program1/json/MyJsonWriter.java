package cs420.usm.program1.json;

import android.os.Message;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cs420.usm.program1.containers.Customer;
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.containers.Order;
import cs420.usm.program1.containers.PurchasedItem;

/**
 * Created by drs4 on 9/7/2014.
 */
public class MyJsonWriter {

    public void writeCustomersJsonStream(OutputStream out, ArrayList<Customer> customers) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeCustomerArray(writer, customers);
        writer.close();
    }

    public void writeItemsJsonStream(OutputStream out, ArrayList<Item> items) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeItemsArray(writer, items);
        writer.close();
    }

    public void writeItemsArray(JsonWriter writer, ArrayList<Item> items) throws IOException {
        writer.beginArray();
        for (Item item : items) {
            writer.beginObject();
            writer.name("id").value(item.id);
            writer.name("name").value(item.name);
            writer.name("qty").value(item.qtyAvailable);
            writer.endObject();
        }
        writer.endArray();
    }


    public void writeCustomerArray(JsonWriter writer, ArrayList<Customer> customers) throws IOException {
        writer.beginArray();
        for (Customer customer : customers) {
            writeCustomer(writer, customer);
        }
        writer.endArray();
    }

    public void writeCustomer(JsonWriter writer, Customer customer) throws IOException {
        writer.beginObject();
        writer.name("id").value(customer.id);
        writer.name("name").value(customer.name);
        writer.name("address").value(customer.address);
        writer.name("orderHistory");
        writeOrderHistory(writer, customer.orderHistory);

        writer.endObject();

    }

    public void writeOrderHistory(JsonWriter writer, ArrayList<Order> orderHistory) throws IOException{
        writer.beginArray();
        for (Order order : orderHistory) {
            writer.beginObject();
            writer.name("id").value(order.id);
            writer.name("date").value(order.date);
            writer.name("purchasedItems");
            writePurchasedItems(writer, order.items);
            writer.endObject();
        }
        writer.endArray();
    }

    public void writePurchasedItems(JsonWriter writer, ArrayList<PurchasedItem> items) throws IOException {
        writer.beginArray();
        for (PurchasedItem item : items) {
            writer.beginObject();
            writer.name("id").value(item.id);
            writer.name("name").value(item.name);
            writer.name("qtyPurchased").value(item.qtyPurchased);
            writer.endObject();
        }
        writer.endArray();

    }
}
