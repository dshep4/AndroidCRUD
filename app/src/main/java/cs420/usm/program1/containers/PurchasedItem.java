package cs420.usm.program1.containers;

/**
 * Created by drs4 on 9/3/2014.
 * This class handles a Item that has been purchased.
 * Here we are interested in the amount purchased and not the inventory available.
 */
public class PurchasedItem {
    public int id;
    public String name;
    public int qtyPurchased;

    public PurchasedItem(int id, String name, int qtyPurchased) {
        this.id = id;
        this.name = name;
        this.qtyPurchased = qtyPurchased;
    }

    public PurchasedItem(){}

    public String toString() {
        return String.format("%d %s %d", this.id, this.name, this.qtyPurchased);
    }
}
