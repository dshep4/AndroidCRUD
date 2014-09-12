package cs420.usm.program1.containers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by drs4 on 9/3/2014.
 */
public class Item implements Parcelable {
    public int id;
    public String name;
    public int qtyAvailable;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item(int id, String name, int qtyAvailable) {
        this.id = id;
        this.name = name;
        this.qtyAvailable = qtyAvailable;
    }

    public void updateQtyToAmount(int i) {
        qtyAvailable = i;
    }

    public void incrementQty(int i) {
        qtyAvailable += i;
    }

    public int getQtyAvailable() {
        return qtyAvailable;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeInt(qtyAvailable);
    }

    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    private Item(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.qtyAvailable = in.readInt();
    }

    public String toString() {
        return String.format("%d %s %d", this.id, this.name, this.qtyAvailable);
    }
}
