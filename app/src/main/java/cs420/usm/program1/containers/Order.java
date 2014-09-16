package cs420.usm.program1.containers;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by drs4 on 9/3/2014.
 */
public class Order implements Parcelable{

    public int id;
    public String date;

    public Order() {}   //default construction

    public Order (String date, int id) {
        this.id = id;
        this.date = date;
    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(date);
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
    }

    public String toString() {
        return String.format("%d, %s", this.id, this.date);
    }

}
