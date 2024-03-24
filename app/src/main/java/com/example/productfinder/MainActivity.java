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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<ProductClass> productClassList = new ArrayList<ProductClass>();
    public static ArrayList<ShelfClass> shelfClassList = new ArrayList<ShelfClass>();
    public static ArrayList<ProductClass> shoppingList = new ArrayList<ProductClass>();

    private ListView productListView;

    private ListView createShoppingListView;
    private ListView shoppingListFileView;
    private Button createShoppingListButton;
    private Button submitShoppingListButton;
    private int currentIndex = 0;

    private TextView productDetails;
    private Button nextButton;
    private Button previousButton;
    private TextView shoppingListCounter;
    private static final String shoppingListFileNames = "shoppingListFileNames.txt";

    static int otherTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //read in the product database on app start
        readProductData();
        //read in the shelf database on app start
        readShelfData();
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

                ProductClass productClass = new ProductClass();
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

                ShelfClass shelfClass = new ShelfClass();
                shelfClass.setShelfID(Integer.parseInt(tokens[0]));
                shelfClass.setAisleNo(Integer.parseInt(tokens[1]));
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

    private void initSearchWidgets() {
        //This method allows the user to search for a product on the product search page
        SearchView searchView = (SearchView) findViewById(R.id.productSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ProductClass> filteredProducts = new ArrayList<ProductClass>();
                for (ProductClass productClass: productClassList) {
                    //creates array of products which contain text searched by user
                    if (productClass.getProductName().toLowerCase().contains(newText.toLowerCase())){
                        filteredProducts.add(productClass);
                    }
                }

                //displays the filtered products
                ProductAdapter adapter = new ProductAdapter(getApplicationContext(), 0, filteredProducts);
                productListView.setAdapter(adapter);

                return false;
            }
        });
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
                for (ProductClass productClass: productClassList) {
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



    private void setUpList() {

        productListView = (ListView) findViewById(R.id.productListView);

        ProductAdapter adapter = new ProductAdapter(getApplicationContext(), 0, productClassList);
        productListView.setAdapter(adapter);
    }

    /*
    private void setUpFileList() {
        //don't believe this method gets used - check for safe to delete
        ListView shoppingListFileView = findViewById(R.id.shoppingListFileView);

        List<String> fileList = getFileList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
        shoppingListFileView.setAdapter(adapter);

    }



    private List<String> getFileList() {
        List<String> fileList = new ArrayList<>();
        File directory = new File(getFilesDir(), "Documents");
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileList.add(file.getName());
                }
            } else {
                Log.e("FileListActivity", "No files found in the directory.");
            }
        } else {
            Log.e("FileListActivity", "Directory does not exist.");
        }
        return fileList;
    }


     */

    private void setUpOnclickListener()
    {
        //Handles clicking on a product in the product search layout
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                //Receives the product clicked on
                ProductClass selectProduct = (ProductClass) (productListView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                //shows the DetailActivity.java which shows the products location and shop image.
                showDetail.putExtra("id",selectProduct.getProductID());
                startActivity(showDetail);
            }
        });

    }

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
            //sets view to home page
            setContentView(R.layout.activity_main);
            return true;
        } else if (itemId == R.id.ProductSearch) {
            //sets view to product search page
            setContentView(R.layout.productsearchlayout);
            ///runs methods required for product search page
            setUpList();
            setUpOnclickListener();
            initSearchWidgets();
            return true;
        } else if (itemId == R.id.ShoppingList) {
            // sets view to shopping list page
            setContentView(R.layout.shopping_list_layout);
            // runs methods required for shopping list page
            createShoppingListButton = (Button)findViewById(R.id.createShoppingListButton);
            createShoppingListButton.setOnClickListener(this);
            handleShowCreatedShoppingLists();
            handleShoppingListViewClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    private void handleFileNameClick() {
        // This code doesn't get used
        shoppingListFileView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = (String) shoppingListFileView.getItemAtPosition(position);
                File file = getApplicationContext().getFileStreamPath(filename);
                String lineFromFile;

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(filename));
                    String line="";
                    while ((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");

                        ProductClass productClass = new ProductClass();
                        productClass.setProductID(tokens[0]);
                        shoppingList.add(productClass);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (ProductClass productClass : shoppingList) {
                    Log.d("Message", productClass.getProductName());
                }
            }
        });
    }


     */
    public void onClick(View aview) {
        //handles shopping list creation
        if (aview == createShoppingListButton) {
            // when the button is clicked it sets the view to the product list view with checkboxes for products to be selected
            setContentView(R.layout.create_shopping_list_layout);

            createShoppingListView = findViewById(R.id.shoppingListView);
            ShoppingListAdapter adapter = new ShoppingListAdapter(this, 0, productClassList);
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

                    Log.d("Test", test);

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
                    for (ProductClass productClass : productClassList){
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
                            Log.d("Line 642", "Setting the shelf for:" + sortedShoppingList.get(i-1).getProductName() + " at node: " + shelfClassList.get(sortedShoppingList.get(i-1).getShelfID()).getNode());
                            srcShelf = shelfClassList.get(sortedShoppingList.get(i-1).getShelfID()).getNode();
                        }
                        // finds the closest product to the srcShelf
                        ProductClass productClass = findShortest(graph, srcShelf, unsortedShoppingList);
                        // adds this product to the sorted list
                        sortedShoppingList.add(productClass);
                    }

                    Log.d("Total for min:", String.valueOf(otherTotal));

                    for (ProductClass productClass : sortedShoppingList) {
                        Log.d("Min order:", productClass.getProductName())    ;
                    }


/*
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
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), shoppingListFileNames), true);
                        fileOutputStream.write(shoppingListName.getBytes());
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // sets page back to original shopping list layout
                    setContentView(R.layout.shopping_list_layout);
                    // updates the list view containing the shopping lists
                    handleShowCreatedShoppingLists();


 */



                }
            });
            // allows for searching for products when creating the shopping list
            initSearchWidgetsForShoppingList();

        }
    }

    /*public static ProductClass findShortest(int[][] graph, int srcShelf, ArrayList<ProductClass> list) {
        int min  = Integer.MAX_VALUE;
        int counter = 0;
        // loops through all the remaining products in the unsorted list
        // finds the closest product to the srcShelf and returns it
        for (int i = 0; i<list.size(); i++) {
            int dstnShelf = shelfClassList.get(list.get(i).getShelfID()).getNode();
            Log.d("Line 704", "Finding the distance between:" + srcShelf + " and " + dstnShelf + "for product: " + list.get(i).getProductName());
            int distance;
            distance = dijkstra(graph, srcShelf, dstnShelf);
            if (distance < min) {
                min = distance;
                Log.d("Line 708", "The distance between " + srcShelf + " and " + list.get(i).getProductName() + " is " + distance );
                counter = i;
            }
        }
        ProductClass node = list.get(counter);
        Log.d("Line 712", "The product being added to the list is:" + node.getProductName());
        // removes the product that has just been found to be the closest from the unsorted list
        list.remove(counter);
        return node;
    }
     */

    public ProductClass findShortest(int[][] graph, int srcShelf, ArrayList<ProductClass> list) {
        int min = Integer.MAX_VALUE;
        int counter = 0;
        for (int i=0; i<list.size(); i++) {
            Log.d("Line 704", "Finding the distance between:" + srcShelf + " and " + shelfClassList.get(list.get(i).getShelfID()).getNode() + "for product: " + list.get(i).getProductName());
            int distance = findDistance(graph, srcShelf, shelfClassList.get(list.get(i).getShelfID()).getNode());
            Log.d("Line 708", "The distance between " + srcShelf + " and " + list.get(i).getProductName() + " is " + distance );
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
        //int distance = graph[srcShelf][dest];
        //return distance;
    }

    public void handleShowCreatedShoppingLists() {
        // this method shows the users shopping list in list view
        // it works by reading in from a file all the shopping list file names
        String input="";
        try {
            FileInputStream fis = new FileInputStream(new File(getFilesDir(), shoppingListFileNames));
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
                // loads the shopping list
                loadShoppingList(position);
                setContentView(R.layout.shopping_list_play_layout);

                currentIndex = 0;

                // sets up buttons and text views
                productDetails = findViewById(R.id.shopping_list_product_details);
                shoppingListCounter = findViewById(R.id.shopping_list_counter);
                nextButton = findViewById(R.id.next_button);
                previousButton = findViewById(R.id.previous_button);

                // display the current product
                shoppingListDisplayProduct();
                // this shows what product the user is on out of how many they have. i.e. 5/11 products
                String counterText = (currentIndex+1) + "/" + shoppingList.size();
                shoppingListCounter.setText(counterText);

                nextButton.setOnClickListener(new View.OnClickListener() {
                    // handles the user pressing the next button
                    @Override
                    public void onClick(View v) {
                        if (currentIndex == shoppingList.size()-1) {
                            // if statement for if the next button has been pressed and all products have been collected
                            // changes the purpose of the next/finish button to a close button
                            String text = "That's it! You've collected everything on your list please head to the checkouts.";
                            productDetails.setText(text);
                            currentIndex = currentIndex + 1;
                            String btnText = "Close";
                            nextButton.setText(btnText);
                        } else if (currentIndex == shoppingList.size()) {
                            // handles the close button being pressed
                            setContentView(R.layout.shopping_list_layout);
                            handleShowCreatedShoppingLists();
                        } else {
                            // handles the user pressing the next button
                            // shows the next product in the list
                            currentIndex = (currentIndex + 1) % shoppingList.size();
                            String counterText = (currentIndex + 1) + "/" + shoppingList.size();
                            shoppingListCounter.setText(counterText);
                            shoppingListDisplayProduct();
                            // makes the previous button visible as it is not visible when on the first product
                            previousButton.setVisibility(View.VISIBLE);
                            if (currentIndex == shoppingList.size() - 1) {
                                // changes the next button to finish if on the last product
                                String btnText = "Finish";
                                nextButton.setText(btnText);
                            }
                        }
                    }
                });

                previousButton.setOnClickListener(new View.OnClickListener() {
                    // handles the user pressing the previous button
                    @Override
                    public void onClick(View v) {
                        // on click of the previous button the previous product will be shown and the counter will decrease
                        currentIndex =(currentIndex - 1) % shoppingList.size();
                        String counterText = (currentIndex+1) + "/" + shoppingList.size();
                        shoppingListCounter.setText(counterText);
                        shoppingListDisplayProduct();
                        if (currentIndex == 0) {
                            // if the previous button is pressed to go back to the first product the button will disappear
                            previousButton.setVisibility(View.GONE);
                        }
                        if (currentIndex == shoppingList.size() - 1) {
                            // if on close page and press previous it changes the text back to finish
                            // Good practice to not set text literal in setText method
                            String btnTxt = "Finish";
                            nextButton.setText(btnTxt);
                        }
                        if (currentIndex < shoppingList.size() - 1) {
                            // if on finish page and press previous it changes the text back to finish
                            String btnText = "Next";
                            nextButton.setText(btnText);
                        }
                    }
                });
            }
        });
    }

    public void loadShoppingList(int position) {
        // handles loading the shopping list that the user clicked on
        String filename =  (String) shoppingListFileView.getItemAtPosition(position);
        filename = filename + ".txt";
        // clears the shopping list
        // used in case user had previously opened another shopping list in the same run
        shoppingList.clear();

        File file = getApplicationContext().getFileStreamPath(filename);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line ="";
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");

                ProductClass productClass = new ProductClass();
                productClass.setProductID(tokens[0]);
                productClass.setProductName(tokens[1]);
                productClass.setShelfRow(tokens[2]);
                productClass.setShelfID(Integer.parseInt(tokens[3]));
                Log.d("Loading", productClass.getProductName());
                shoppingList.add(productClass);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shoppingListDisplayProduct() {
        // displays the current product in the shopping list
        ProductClass productClass = shoppingList.get(currentIndex);

        String location = productClass.getProductName() + " can be found in: \nAisle: " + shelfClassList.get(productClass.getShelfID()-1).getAisleNo()
                + "\nSide: " + shelfClassList.get(productClass.getShelfID()-1).getSide() + "\nShelf: " + shelfClassList.get(productClass.getShelfID()-1).getShelfNo()
                ;

        productDetails.setText(location);
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

    public static int dijkstra(int[][] graph, int src, int dest) {
        // dijkstra's algorithm
        int n = graph.length;
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        boolean[] visited = new boolean[n];

        for (int count = 0; count < n - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        return dist[dest];
    }

    public static int minDistance(int[] dist, boolean[] visited) {
        // finds the minimum distance between points
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] <= min) {
                min = dist[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

}