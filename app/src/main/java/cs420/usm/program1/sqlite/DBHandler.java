package cs420.usm.program1.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cs420.usm.program1.containers.Customer;
import cs420.usm.program1.containers.Item;
import cs420.usm.program1.containers.Order;
import cs420.usm.program1.containers.PurchasedItem;

/**
 * Created by drs4 on 9/6/2014.
 */
public class DBHandler extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "plantShopDB";
    static final int DATABASE_VERSION = 1;
    public static final String TABLE_CUSTOMERS = "customers";
    public static final String TABLE_INVENTORY = "inventory";
    public static final String TABLE_PURCHASES = "purchases";
    public static final String TABLE_ORDERS = "orders";

    //TABLE_CUSTOMERS
    public static final String COLUMN_CUSTOMERS_CUSTOMERID = "ID_C";    //PK
    public static final String COLUMN_CUSTOMERS_NAME = "NAME_C";
    public static final String COLUMN_CUSTOMERS_ADDRESS = "ADDRESS_C";

    //TABLE_INVENTORY
    public static final String COLUMN_INVENTORY_ITEMID = "ID_I";        //PK
    public static final String COLUMN_INVENTORY_NAME = "NAME_I";
    public static final String COLUMN_INVENTORY_QTY = "QTY_I";

    //TABLE_PURCHASES
    public static final String COLUMN_PURCHASES_ORDERID = "ORDERID_P";  //PK
    public static final String COLUMN_PURCHASES_ITEMID = "ITEMID_P";    //PK
    public static final String COLUMN_PURCHASES_QTYPURCHASED = "QTY_P";

    //TABLE_ORDERS
    public static final String COLUMN_ORDERS_CUSTOMERID = "CUSTOMERID_O"; //PK
    public static final String COLUMN_ORDERS_ORDERID = "ORDERID_O";       //PK
    public static final String COLUMN_ORDERS_DATE = "DATE_O";




    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMERS_TABLE = "CREATE TABLE " + TABLE_CUSTOMERS + " ("
                + COLUMN_CUSTOMERS_CUSTOMERID + " INTEGER PRIMARY KEY, "
                + COLUMN_CUSTOMERS_NAME + " TEXT, "
                + COLUMN_CUSTOMERS_ADDRESS + " TEXT)";
        db.execSQL(CREATE_CUSTOMERS_TABLE);

        String CREATE_INVENTORY_TABLE = "CREATE TABLE " + TABLE_INVENTORY + "("
                + COLUMN_INVENTORY_ITEMID + " INTEGER PRIMARY KEY,"
                + COLUMN_INVENTORY_NAME + " TEXT,"
                + COLUMN_INVENTORY_QTY + " INTEGER)";
        db.execSQL(CREATE_INVENTORY_TABLE);

        String CREATE_PURCHASES_TABLE = "CREATE TABLE " + TABLE_PURCHASES + "("
                + COLUMN_PURCHASES_ORDERID + " INTEGER PRIMARY KEY,"
                + COLUMN_PURCHASES_ITEMID + " INTEGER,"
                + COLUMN_PURCHASES_QTYPURCHASED + " INTEGER,"
                + " FOREIGN KEY( "+COLUMN_PURCHASES_ITEMID+ ") REFERENCES "
                + TABLE_INVENTORY + "(" + COLUMN_INVENTORY_ITEMID + "))";
        db.execSQL(CREATE_PURCHASES_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ORDERS_CUSTOMERID + " INTEGER,"
                + COLUMN_ORDERS_ORDERID + " INTEGER PRIMARY KEY,"
                + COLUMN_ORDERS_DATE + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_ORDERS_CUSTOMERID + ") REFERENCES "
                + TABLE_CUSTOMERS + "(" + COLUMN_CUSTOMERS_CUSTOMERID
                +"))";
        db.execSQL(CREATE_ORDERS_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public void addCustomer(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMERS_NAME, customer.name);
        values.put(COLUMN_CUSTOMERS_ADDRESS, customer.address);
        values.put(COLUMN_CUSTOMERS_CUSTOMERID, customer.id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CUSTOMERS, null, values);
        if (customer.orderHistory != null)
            addOrdersToCustomer(customer, db);

        db.close();
    }

    public void addOrdersToCustomer(Customer customer, SQLiteDatabase db) {

        for (Order order : customer.orderHistory) {
            ContentValues orderValues = new ContentValues();
            orderValues.put(COLUMN_ORDERS_CUSTOMERID, customer.id);
            orderValues.put(COLUMN_ORDERS_ORDERID, order.id);
            orderValues.put(COLUMN_ORDERS_DATE, order.date);
            for (PurchasedItem item : order.items) {
                ContentValues itemValues = new ContentValues();
                itemValues.put(COLUMN_PURCHASES_ORDERID, order.id);
                itemValues.put(COLUMN_PURCHASES_ITEMID, item.id);
                itemValues.put(COLUMN_PURCHASES_QTYPURCHASED, item.qtyPurchased);

                db.insert(TABLE_PURCHASES, null, itemValues);
            }

            db.insert(TABLE_ORDERS, null, orderValues);
        }

    }

    public void addOrderToCustomer(Order order, Customer customer) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues orderValues = new ContentValues();
        orderValues.put(COLUMN_ORDERS_CUSTOMERID, customer.id);
        orderValues.put(COLUMN_ORDERS_ORDERID, order.id);
        orderValues.put(COLUMN_ORDERS_DATE, order.date);
        for (PurchasedItem item : order.items) {
            ContentValues itemValues = new ContentValues();
            itemValues.put(COLUMN_PURCHASES_ORDERID, order.id);
            itemValues.put(COLUMN_PURCHASES_ITEMID, item.id);
            itemValues.put(COLUMN_PURCHASES_QTYPURCHASED, item.qtyPurchased);

            db.insert(TABLE_PURCHASES, null, itemValues);
        }

        db.insert(TABLE_ORDERS, null, orderValues);
    }



    public void addItemToInventory(Item item) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_INVENTORY_NAME, item.name);
        values.put(COLUMN_INVENTORY_ITEMID, item.id);
        values.put(COLUMN_INVENTORY_QTY, item.qtyAvailable);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INVENTORY, null, values);
        db.close();
    }


    public ArrayList<Customer> getCustomers() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Customer> customers = new ArrayList<Customer>();

        String queryCustomers = "SELECT "
                + COLUMN_CUSTOMERS_CUSTOMERID + ","
                + COLUMN_CUSTOMERS_NAME + ","
                + COLUMN_CUSTOMERS_ADDRESS
                + " FROM " + TABLE_CUSTOMERS;

        Cursor cursor = db.rawQuery(queryCustomers, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            Customer customer = new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            String orderQuery = "SELECT " + COLUMN_ORDERS_ORDERID + "," + COLUMN_ORDERS_DATE
                    + " FROM " + TABLE_ORDERS
                    + " WHERE " + COLUMN_ORDERS_CUSTOMERID + " = " + String.format("%d", customer.id);
            Cursor cursor1 = db.rawQuery(orderQuery, null);
            ArrayList<Order> orderHistory = new ArrayList<Order>();
            cursor1.moveToFirst();
            while (cursor1.isAfterLast() == false) {
                Order order = new Order();
                order.id = cursor1.getInt(0);
                order.date = cursor1.getString(1);

                String purchaseQuery = "SELECT " + COLUMN_PURCHASES_ITEMID + "," + COLUMN_PURCHASES_QTYPURCHASED
                        + " FROM " + TABLE_PURCHASES
                        + " WHERE " + COLUMN_PURCHASES_ORDERID + " = " + String.format("%d", order.id);
                Cursor cursor2 = db.rawQuery(purchaseQuery, null);
                cursor2.moveToFirst();
                while (cursor2.isAfterLast() == false) {
                    PurchasedItem purchasedItem = new PurchasedItem();
                    purchasedItem.id = cursor2.getInt(0);
                    purchasedItem.qtyPurchased = cursor2.getInt(1);

                    String itemQuery = "SELECT " + COLUMN_INVENTORY_NAME
                            + " FROM " + TABLE_INVENTORY
                            + " WHERE " + COLUMN_INVENTORY_ITEMID + " = " + String.format("%d", purchasedItem.id);
                    Cursor cursor3 = db.rawQuery(itemQuery, null);
                    cursor3.moveToFirst();
                    purchasedItem.name = cursor3.getString(0);

                    System.out.println(purchasedItem);
                    order.items.add(purchasedItem);
                    cursor2.moveToNext();
                }
                System.out.println(order);
                orderHistory.add(order);
                cursor1.moveToNext();
            }
            customer.orderHistory = orderHistory;
            System.out.println(customer);
            customers.add(customer);
            cursor.moveToNext();
        }

        for (Customer customer : customers) {
            if (customer.orderHistory == null) {
                customer.orderHistory = new ArrayList<Order>();
            }
        }
        return customers;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();

        String itemsQuery = "SELECT " + COLUMN_INVENTORY_ITEMID + ","
                + COLUMN_INVENTORY_NAME + "," + COLUMN_INVENTORY_QTY
                + " FROM " + TABLE_INVENTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemsQuery, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            items.add( new Item(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            cursor.moveToNext();
        }

        return items;

    }

    public void update(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        String update = "UPDATE " + TABLE_INVENTORY
                + " SET " + COLUMN_INVENTORY_ITEMID + " = " + String.format("%d", item.id) + ","
                + COLUMN_INVENTORY_NAME + " = " + "'" + item.name + "',"
                + COLUMN_INVENTORY_QTY + " = " + String.format("%d", item.qtyAvailable)
                + " WHERE " + COLUMN_INVENTORY_ITEMID + " = " + String.format("%d", item.id);

        db.rawQuery(update, null);
    }

}


