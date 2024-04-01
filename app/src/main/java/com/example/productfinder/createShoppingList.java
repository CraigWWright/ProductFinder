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

                // a distance matrix tracks the distance between every shelf so that the distance between any 2 shelves can always
                // be calculated

                // the following code reads in the distance matrix
                // matrix has to split into 19 files as Java can only handle reading in 4061^ characters at a time. ^may be more than this but this is what it was capping out at for me
                InputStream is = getResources().openRawResource(R.raw.matrix_1);
                StringBuilder matrixString = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is2 = getResources().openRawResource(R.raw.matrix_2);
                StringBuilder matrixString2 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is2, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString2.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is3 = getResources().openRawResource(R.raw.matrix_3);
                StringBuilder matrixString3 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is3, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString3.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is4 = getResources().openRawResource(R.raw.matrix_4);
                StringBuilder matrixString4 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is4, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString4.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is5 = getResources().openRawResource(R.raw.matrix_5);
                StringBuilder matrixString5 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is5, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString5.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is6 = getResources().openRawResource(R.raw.matrix_6);
                StringBuilder matrixString6 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is6, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString6.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is7 = getResources().openRawResource(R.raw.matrix_7);
                StringBuilder matrixString7 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is7, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString7.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is8 = getResources().openRawResource(R.raw.matrix_8);
                StringBuilder matrixString8 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is8, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString8.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is9 = getResources().openRawResource(R.raw.matrix_9);
                StringBuilder matrixString9 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is9, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString9.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is10 = getResources().openRawResource(R.raw.matrix_10);
                StringBuilder matrixString10 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is10, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString10.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is11 = getResources().openRawResource(R.raw.matrix_11);
                StringBuilder matrixString11 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is11, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString11.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is12 = getResources().openRawResource(R.raw.matrix_12);
                StringBuilder matrixString12 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is12, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString12.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is13 = getResources().openRawResource(R.raw.matrix_13);
                StringBuilder matrixString13 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is13, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString13.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is14 = getResources().openRawResource(R.raw.matrix_14);
                StringBuilder matrixString14 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is14, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString14.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is15 = getResources().openRawResource(R.raw.matrix_15);
                StringBuilder matrixString15 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is15, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString15.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is16 = getResources().openRawResource(R.raw.matrix_16);
                StringBuilder matrixString16 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is16, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString16.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is17 = getResources().openRawResource(R.raw.matrix_17);
                StringBuilder matrixString17 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is17, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString17.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is18 = getResources().openRawResource(R.raw.matrix_18);
                StringBuilder matrixString18 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is18, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString18.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is19 = getResources().openRawResource(R.raw.matrix_19);
                StringBuilder matrixString19 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is19, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString19.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is20 = getResources().openRawResource(R.raw.matrix_20);
                StringBuilder matrixString20 = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is20, StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        matrixString20.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // combines all the matrix strings into one string
                String test = matrixString.toString() +  "\n" + matrixString2.toString() + "\n" + matrixString3.toString() + "\n" + matrixString4.toString() + "\n" + matrixString5.toString() + "\n" + matrixString6.toString() + "\n" + matrixString7.toString()+ "\n" + matrixString8.toString()+ "\n" + matrixString9.toString()+ "\n" + matrixString10.toString()+ "\n" + matrixString11.toString()+ "\n" + matrixString12.toString()+ "\n" + matrixString13.toString()+  "\n" +matrixString14.toString()+ "\n" + matrixString15.toString()+ "\n" + matrixString16.toString()+  "\n" +matrixString17.toString()+ "\n" + matrixString18.toString()+ "\n" + matrixString19.toString() + "\n" + matrixString20.toString();


                // creates the distance matrix
                int[][] graph = createGraph(test, 158, 158);

                // adding selected products to a list
                // creates both the unsorted and sorted shopping lists
                ArrayList<ProductClass> unsortedShoppingList = new ArrayList<ProductClass>();
                ArrayList<ProductClass> sortedShoppingList = new ArrayList<>();
                // clears list - without this it was discovered that if a user was to create a shopping list and then create another one
                // the contents of the first list would be added to the second
                unsortedShoppingList.clear();
                sortedShoppingList.clear();
                for (ProductClass productClass : MainActivity.productClassList){
                    // if the checkbox is checked then it is added to the unsorted list
                    if (productClass.isChecked()) {
                        unsortedShoppingList.add(productClass);
                    }
                }


                // the first srcShelf is always at the entrance of the shop
                int srcShelf = 0;
                int size = unsortedShoppingList.size();

                // loops through all the products in the unsorted list
                for (int i=0; i<size; i++) {
                    if (!sortedShoppingList.isEmpty()) {
                        // sets the srcShelf to whatever product was most recently added to the sorted list
                        // i.e. find the product closest to the product last visited
                        srcShelf = MainActivity.shelfClassList.get(sortedShoppingList.get(i-1).getShelfID()).getNode();
                    }
                    // finds the closest product to the srcShelf
                    ProductClass productClass = findShortest(graph, srcShelf, unsortedShoppingList);
                    // adds this product to the sorted list
                    sortedShoppingList.add(productClass);
                }



                // saves the sorted shopping list to a file for the user to access later
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), shoppingListFile));
                    for (ProductClass product : sortedShoppingList) {
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


