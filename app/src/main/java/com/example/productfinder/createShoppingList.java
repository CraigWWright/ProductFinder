package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class createShoppingList extends AppCompatActivity {

    private ListView createShoppingListView;
    private Button submitShoppingListButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets view
        setContentView(R.layout.create_shopping_list_layout);

        // creates list view of  products - list view also has checkboxes
        createShoppingListView = findViewById(R.id.shoppingListView);
        shoppingListAdapter adapter = new shoppingListAdapter(this, 0, mainActivity.productClassList);
        createShoppingListView.setAdapter(adapter);

        // saves the name of the shopping list
        EditText enteredFilename = findViewById(R.id.userFilename);

        submitShoppingListButton = findViewById(R.id.submitButton);
        // handles what to do when the submit button is clicked
        submitShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gets users chosen file name from editText
                String shoppingListName = enteredFilename.getText().toString();
                String shoppingListFile = shoppingListName + ".txt";

                // creates a shopping list
                ArrayList<productClass> createdShoppingList = new ArrayList<productClass>();
                for (com.example.productfinder.productClass productClass : mainActivity.productClassList) {
                    // adds products to the list if the checkbox has been checked
                    if (productClass.isChecked()) {
                        createdShoppingList.add(productClass);
                    }
                }

                // saves all products in the shopping list to a file using the chosen file name
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), shoppingListFile));
                    for (productClass product : createdShoppingList) {
                        fileOutputStream.write((product.getProductID() + "," + product.getProductName() + "," + product.getShelfRow() + "," + product.getShelfID() + "," +"\n").getBytes());
                        product.setChecked(false);
                    }
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // the name of the file will be saved to a text file containing other file names
                // this will be used to display all the shopping lists saved in a list view
                shoppingListName = shoppingListName + ",";

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), mainActivity.shoppingListFileNames), true);
                    fileOutputStream.write(shoppingListName.getBytes());
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // clears shopping list - used in case of a user creating another shopping list in the instance
                createdShoppingList.clear();
                // sets page back to original shopping list layout
                Intent shoppingList = new Intent(getApplicationContext(), shoppingList.class);
                startActivity(shoppingList);
            }
        });
        // allows for searching for products when creating the shopping list
        initSearchWidgetsForShoppingList();
    }

    private void initSearchWidgetsForShoppingList() {
        //This method allows the user to search for a product on the shopping list creation page
        SearchView searchView = (SearchView) findViewById(R.id.shoppingListSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<productClass> filteredProducts = new ArrayList<productClass>();
                for (com.example.productfinder.productClass productClass: mainActivity.productClassList) {
                    //creates array of products which contain text searched by user
                    if (productClass.getProductName().toLowerCase().contains(newText.toLowerCase())){
                        filteredProducts.add(productClass);
                    }
                }

                //displays the filtered products
                shoppingListAdapter adapter = new shoppingListAdapter(getApplicationContext(), 0, filteredProducts);
                createShoppingListView.setAdapter(adapter);

                return false;
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


