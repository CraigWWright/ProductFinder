package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class mainActivity extends AppCompatActivity {

    // Initiates global variables
    public static ArrayList<productClass> productClassList = new ArrayList<productClass>();
    public static ArrayList<shelfClass> shelfClassList = new ArrayList<shelfClass>();
    public static final String shoppingListFileNames = "shoppingListFileNames.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //read in the product database on app start
        readProductData();
        //read in the shelf database on app start
        readShelfData();
    }

    // add the options menu to the current page
    @Override
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


    private void readProductData() {

        InputStream is = getResources().openRawResource(R.raw.product_database);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line="";
        try {
            while ((line = reader.readLine()) != null) {
                //Split by commas
                String[] tokens = line.split(",");

                //Read the data into a productClass and add to list of products

                productClass productClass = new productClass();
                productClass.setProductID(tokens[0]);
                productClass.setProductName(tokens[1]);
                productClass.setShelfRow(tokens[2]);
                productClass.setShelfID(Integer.parseInt(tokens[3]));
                productClassList.add(productClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readShelfData(){
        InputStream is = getResources().openRawResource(R.raw.shelf_database);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line="";
        try {
            while ((line = reader.readLine()) != null) {
                //Split by commas
                String[] tokens = line.split(",");


                //Read the data into a shelfClass and add to the list of shelves

                shelfClass shelfClass = new shelfClass();
                shelfClass.setShelfID(Integer.parseInt(tokens[0]));
                shelfClass.setAisleNo(tokens[1]);
                shelfClass.setSide(tokens[2]);
                shelfClass.setShelfNo(Integer.parseInt(tokens[3]));
                shelfClass.setXpos(Integer.parseInt(tokens[4]));
                shelfClass.setYpos(Integer.parseInt(tokens[5]));
                shelfClass.setNode(Integer.parseInt(tokens[6]));
                shelfClassList.add(shelfClass);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}