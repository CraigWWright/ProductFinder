package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class shoppingList extends AppCompatActivity {

    private Button createShoppingListButton;

    private ListView shoppingListFileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_layout);

        handleShowCreatedShoppingLists();
        handleShoppingListViewClick();
        createShoppingListButton = (Button) findViewById(R.id.createShoppingListButton);
        // handles user clicking on 'Create Shopping List' button
        createShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createShoppingList = new Intent(getApplicationContext(),createShoppingList.class);
                startActivity(createShoppingList);
            }
        });
    }

    public void handleShowCreatedShoppingLists() {
        // this method shows the users shopping list in list view
        // it works by reading in from a file all the shopping list file names
        String input="";
        try {
            FileInputStream fis = new FileInputStream(new File(getFilesDir(), mainActivity.shoppingListFileNames));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
            StringBuffer stringBuffer = new StringBuffer();
            while (bufferedInputStream.available() != 0) {
                char c = (char) bufferedInputStream.read();
                stringBuffer.append(c);
            }
            bufferedInputStream.close();
            fis.close();
            input = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // uses standard ListView adapter to show list of shopping lists
        ArrayList<String> fileNamesList = new ArrayList<>(Arrays.asList(input.split(",")));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNamesList);
        shoppingListFileView = findViewById(R.id.shoppingListFileView);
        shoppingListFileView.setAdapter(adapter);
        // handles user clicking on a shopping list cell
        handleShoppingListViewClick();
    }

    public void handleShoppingListViewClick() {
        // this method handles a user clicking on a shopping list
        shoppingListFileView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename =  (String) shoppingListFileView.getItemAtPosition(position);
                // sets file to open
                filename = filename + ".txt";
                // starts activity for going through a shopping list
                Intent shoppingListPlay = new Intent(getApplicationContext(), shoppingListPlay.class);
                shoppingListPlay.putExtra("filename", filename);
                startActivity(shoppingListPlay);
            }
        });
    }

    // add the options menu to the current page
    public boolean onCreateOptionsMenu(Menu menu) {
        //creates the menu which allows for navigation
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handles menu clicks
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            Intent homePage = new Intent(getApplicationContext(), com.example.productfinder.homePage.class);
            startActivity(homePage);
            return true;
        } else if (itemId == R.id.ProductSearch) {
            Intent productSearch = new Intent(getApplicationContext(), com.example.productfinder.productSearch.class);
            startActivity(productSearch);
            return true;
        } else if (itemId == R.id.ShoppingList) {
            Intent shoppingList = new Intent(getApplicationContext(), shoppingList.class);
            startActivity(shoppingList);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
