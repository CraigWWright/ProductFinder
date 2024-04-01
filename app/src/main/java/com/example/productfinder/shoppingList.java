package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class shoppingList extends AppCompatActivity {

    private Button createShoppingListButton;

    private ListView shoppingListFileView;

    private Button nextButton;
    private Button previousButton;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_layout);

        handleShowCreatedShoppingLists();
        handleShoppingListViewClick();
        createShoppingListButton = (Button) findViewById(R.id.createShoppingListButton);
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
            FileInputStream fis = new FileInputStream(new File(getFilesDir(), MainActivity.shoppingListFileNames));
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

        ArrayList<String> fileNamesList = new ArrayList<>(Arrays.asList(input.split(",")));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNamesList);
        shoppingListFileView = findViewById(R.id.shoppingListFileView);
        shoppingListFileView.setAdapter(adapter);
        handleShoppingListViewClick();
    }

    public void handleShoppingListViewClick() {
        // this method handles a user clicking on a shopping list
        shoppingListFileView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename =  (String) shoppingListFileView.getItemAtPosition(position);
                filename = filename + ".txt";
                Intent shoppingListPlay = new Intent(getApplicationContext(), shoppingListPlay.class);
                shoppingListPlay.putExtra("filename", filename);
                startActivity(shoppingListPlay);
            }
        });
    }

    public void onClick(View view) {
        if (view == createShoppingListButton) {

        }
    }

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
            Intent homePage = new Intent(getApplicationContext(), HomePage.class);
            startActivity(homePage);
            return true;
        } else if (itemId == R.id.ProductSearch) {
            Intent productSearch = new Intent(getApplicationContext(), ProductSearch.class);
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
