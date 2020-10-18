package com.example.a002todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {

    // the list is stored in this file (for future runs of the app as well)
    public static final String FILENAME = "listinfo.dat";

    // data will be written to the file, then file is closed
    public static void writeData(ArrayList<String> items, Context context){

        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // data will be read from the file. If file is not found, display empty array.
    public static ArrayList<String> readData (Context context){
        ArrayList<String> itemList = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            itemList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return itemList;

    }
}
