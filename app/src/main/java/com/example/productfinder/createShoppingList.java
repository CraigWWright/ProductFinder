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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class createShoppingList extends AppCompatActivity {

    private ListView createShoppingListView;
    private Button submitShoppingListButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_shopping_list_layout);

        createShoppingListView = findViewById(R.id.shoppingListView);
        ShoppingListAdapter adapter = new ShoppingListAdapter(this, 0, MainActivity.productClassList);
        createShoppingListView.setAdapter(adapter);

        // saves the name of the shopping list
        EditText enteredFilename = findViewById(R.id.userFilename);

        submitShoppingListButton = findViewById(R.id.submitButton);
        // handles what to do when the submit button is clicked
        submitShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shoppingListName = enteredFilename.getText().toString();
                String shoppingListFile = shoppingListName + ".txt";

                ArrayList<ProductClass> createdShoppingList = new ArrayList<ProductClass>();
                createdShoppingList.clear();
                for (ProductClass productClass : MainActivity.productClassList) {
                    if (productClass.isChecked()) {
                        createdShoppingList.add(productClass);
                    }
                }

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), shoppingListFile));
                    for (ProductClass product : createdShoppingList) {
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
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), MainActivity.shoppingListFileNames), true);
                    fileOutputStream.write(shoppingListName.getBytes());
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
                ArrayList<ProductClass> filteredProducts = new ArrayList<ProductClass>();
                for (ProductClass productClass: MainActivity.productClassList) {
                    //creates array of products which contain text searched by user
                    if (productClass.getProductName().toLowerCase().contains(newText.toLowerCase())){
                        filteredProducts.add(productClass);
                    }
                }

                //displays the filtered products
                ShoppingListAdapter adapter = new ShoppingListAdapter(getApplicationContext(), 0, filteredProducts);
                createShoppingListView.setAdapter(adapter);

                return false;
            }
        });
    }

    public static int[][] createGraph(String matrixString, int rows, int cols) {
        // used to create distance matrix
        int[][] graph = new int[rows][cols];
        String[] values = matrixString.split(",");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                graph[i][j] = Integer.parseInt(values[i * cols + j].trim());
            }
        }
        return graph;
    }

    public ProductClass findShortest(int[][] graph, int srcShelf, ArrayList<ProductClass> list) {
        int min = Integer.MAX_VALUE;
        int counter = 0;
        for (int i=0; i<list.size(); i++) {
            int distance = findDistance(graph, srcShelf, MainActivity.shelfClassList.get(list.get(i).getShelfID()).getNode());
            if (distance < min) {
                min = distance;
                counter = i;
            }
        }
        ProductClass node = list.get(counter);
        list.remove(counter);
        return node;
    }

    public int findDistance(int[][] graph, int srcShelf, int dest) {
        return graph[srcShelf][dest];
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


