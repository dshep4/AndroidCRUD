package cs420.usm.program1.containers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by drs4 on 9/3/2014.
 */
public class Order implements Parcelable{

    public int id;
    public String date;
    public ArrayList<PurchasedItem> items;

    public Order (String date, int id) {
        items = new ArrayList<PurchasedItem>();
        this.id = id;
        this.date = date;
    }
    public Order(ArrayList<Item> items, ArrayList<Integer> eachQty, String date, int id) {
        for (int i = 0; i < items.size(); i++) {
            if (eachQty.get(i) > 0) {
                this.items.add(new PurchasedItem( items.get(i).id, items.get(i).name, eachQty.get(i)));
            }
        }
        this.id = id;
        this.date = date;
    }

    public Order() {
        items = new ArrayList<PurchasedItem>();
    }

    public Order(ArrayList<PurchasedItem> items, String date) {
        this.items = items;
        this.date = date;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(date);
        out.writeInt(items.size());
        for (PurchasedItem item : items) {
            out.writeInt(item.id);
            out.writeString(item.name);
            out.writeInt(item.qtyPurchased);
        }
    }

    public static final Parcelable.Creator<Order> CREATOR
            = new Parcelable.Creator<Order>() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    private Order(Parcel in) {
        this.id = in.readInt();
        this.date = in.readString();
        int stop1 = in.readInt();
        items = new ArrayList<PurchasedItem>();
        for (int i = 0; i < stop1; i++) {
            PurchasedItem item = new PurchasedItem();
            item.id = in.readInt();
            item.name = in.readString();
            item.qtyPurchased = in.readInt();
            items.add(item);
        }
    }

    public String toString() {
        return String.format("%d, %s", this.id, this.date);
    }

}
