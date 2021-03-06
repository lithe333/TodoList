package com.example.a002todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    // Initialising the variables
    private EditText itemET;
    //private Button btn;
    private Button btn, btn2, btn3, btn4;
    private ListView itemsList;

    // variables for reading and writings into the list
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    // variables for preview
    private ArrayList<String> itemsPreview;
    private ArrayAdapter<String> adapter2;

    // variables for editings items on the list
    private int editPosition;
    private String readItem;

    // associate the variables when opening the app
    // reading the data will store the list from previous run
    // video mentioned that adapter is to help read/write from arrays
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemET = findViewById(R.id.item_edit_text);
        btn = findViewById(R.id.add_btn);
        btn2 = findViewById(R.id.del_btn);
        btn3 = findViewById(R.id.save_btn);
        btn4 = findViewById(R.id.undo_btn);

        itemsList = findViewById((R.id.items_list));
        items = FileHelper.readData(this);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);

        // creating a copy of list for preview
        itemsPreview = new ArrayList<String>(items);
        for (int i = 0; i < items.size(); i++) {
            itemsPreview.set(i,createPreview(items.get(i)));
        }
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, itemsPreview);

        //itemsList.setAdapter(adapter);
        itemsList.setAdapter(adapter2);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        itemsList.setOnItemClickListener(this);
    }

    // when button is clicked, we add an item to list via adaptor and FileHelper
    // adaptor adds another any string to the list
    // FileHelper updates the file
    // after storing the entry we also clear the textbox
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_btn:
                String itemEnterred = itemET.getText().toString();
                adapter.add(itemEnterred);
                 adapter2.add(createPreview(itemEnterred));
                itemET.setText("");
                FileHelper.writeData(items, this);
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;
            case R.id.del_btn:
                items.remove(editPosition);
                adapter.notifyDataSetChanged();
                 itemsPreview.remove(editPosition);
                 adapter2.notifyDataSetChanged();
                itemET.setText("");
                FileHelper.writeData(items,this);
                Toast.makeText(this, "Item Removed", Toast.LENGTH_SHORT).show();
                btn.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.GONE);
                btn4.setVisibility(View.GONE);
                itemsList.setVisibility(View.VISIBLE);
                break;
            case R.id.save_btn:
                String writeItem = itemET.getText().toString();
                items.set(editPosition,writeItem);
                adapter.notifyDataSetChanged();
                 itemsPreview.set(editPosition,writeItem);
                 adapter2.notifyDataSetChanged();
                itemET.setText("");
                FileHelper.writeData(items,this);
                Toast.makeText(this, "Item Updated", Toast.LENGTH_SHORT).show();
                btn.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.GONE);
                btn4.setVisibility(View.GONE);
                itemsList.setVisibility(View.VISIBLE);
                break;
            case R.id.undo_btn:
                itemET.setText("");
                Toast.makeText(this, "Item Returned", Toast.LENGTH_SHORT).show();
                btn.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.GONE);
                btn4.setVisibility(View.GONE);
                itemsList.setVisibility(View.VISIBLE);
                break;
        }
    }

    // modifying entries to create a preview
    private String createPreview(String input) {
        int length = input.length();
        int max = 20;
        String output = "";
        int counter = 0;
        int lines = 2;

        for (int i = 0; (i<length)&&(i<max); i++) {
            if (input.charAt(i) == '\n') {
                counter++;
            }
            String temp = Character.toString((input.charAt(i)));
            if (counter<lines) {
                output = output + temp;
            } else {
                i = max;
            }
        }

        if (output.length() < length) {
            output = output + " ..";
        }
        return output;
    }

    // when list item is clicked, we remove the item
    // but it didn't update file so we need to use FileHelper.writedata again.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //items.remove(position);
        //adapter.notifyDataSetChanged();
        //FileHelper.writeData(items,this);
        //Toast.makeText(this, "Item Removed", Toast.LENGTH_SHORT).show();
        editPosition = position;
        readItem = items.get(position);
        itemET.setText(readItem);
        Toast.makeText(this, "Item Loaded", Toast.LENGTH_SHORT).show();
        btn.setVisibility(View.GONE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);
        itemsList.setVisibility(View.GONE);
    }
}