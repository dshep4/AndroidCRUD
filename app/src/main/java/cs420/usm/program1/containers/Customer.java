package cs420.usm.program1.containers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by drs4 on 9/3/2014.
 */
public class Customer implements Parcelable{
    public int id;
    public String name;
    public String address;

    public Customer(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }



    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(address);
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

    }

    public String toString() {
        return String.format("%d %s", this.id, this.name);
    }
}
