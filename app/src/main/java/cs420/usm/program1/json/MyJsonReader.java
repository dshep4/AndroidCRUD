package cs420.usm.program1.json;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cs420.usm.program1.containers.Customer;

/**
 * Created by drs4 on 9/7/2014.
 */
public class MyJsonReader {

    public ArrayList<Customer> readCustomerJsonStream(InputStream in) throws UnsupportedEncodingException, IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        ArrayList<Customer> customers = new ArrayList<Customer>();
        //TODO -- finish json reader
        reader.beginArray();
        while(reader.hasNext()) {
            reader.beginObject();


        }

        return customers;
    }
}
