package cs420.usm.program1.containers;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by drs4 on 9/3/2014.
 */
public class Customer implements Parcelable{
    public int id;
    public String name;
    public String address;
    public ArrayList<Order> orderHistory;

    public Customer(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        orderHistory = new ArrayList<Order>();
    }

    public Customer() {
        orderHistory = new ArrayList<Order>();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(address);
        out.writeInt(orderHistory.size());
        for (Order order : orderHistory) {
            out.writeString(order.date);
            out.writeInt(order.id);
            out.writeInt(order.items.size());
            for (PurchasedItem item : order.items) {
                out.writeString(item.name);
                out.writeInt(item.id);
                out.writeInt(item.qtyPurchased);
            }
        }
    }

    public static final Parcelable.Creator<Customer> CREATOR
            = new Parcelable.Creator<Customer>() {
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    private Customer(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.address = in.readString();
        int stop1 = in.readInt();
        this.orderHistory = new ArrayList<Order>();
        for (int i = 0; i < stop1; i++) {
            Order order = new Order();
            order.date = in.readString();
            order.id = in.readInt();
            int stop2 = in.readInt();
            for (int k = 0; k < stop2; k++) {
                String name = in.readString();
                int id = in.readInt();
                int qty = in.readInt();
                PurchasedItem item = new PurchasedItem(id, name, qty);
                order.items.add(item);
            }
            this.orderHistory.add(order);
        }
    }

    public String toString() {
        return String.format("%d %s", this.id, this.name);
    }
}
