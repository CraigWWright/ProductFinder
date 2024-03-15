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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    //private ArrayList<String> fileNamesList = new ArrayList<>();

    private static final String shoppingListFileNames = "shoppingListFileNames.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readProductData();
        readShelfData();
    }

    private void initSearchWidgets() {
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
                    if (productClass.getProductName().toLowerCase().contains(newText.toLowerCase())){
                        filteredProducts.add(productClass);
                    }
                }

                ProductAdapter adapter = new ProductAdapter(getApplicationContext(), 0, filteredProducts);
                productListView.setAdapter(adapter);

                return false;
            }
        });
    }

    private void initSearchWidgetsForShoppingList() {
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
                    if (productClass.getProductName().toLowerCase().contains(newText.toLowerCase())){
                        filteredProducts.add(productClass);
                    }
                }

                ShoppingListAdapter adapter = new ShoppingListAdapter(getApplicationContext(), 0, filteredProducts);
                createShoppingListView.setAdapter(adapter);

                return false;
            }
        });
    }


    private void readProductData() {

        InputStream is = getResources().openRawResource(R.raw.product_database);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line="";
        try {
            while ((line = reader.readLine()) != null) {
                //Split by commas
                String[] tokens = line.split(",");

                //Read the data

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line="";
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                ShelfClass shelfClass = new ShelfClass();
                shelfClass.setShelfID(Integer.parseInt(tokens[0]));
                shelfClass.setAisleNo(Integer.parseInt(tokens[1]));
                shelfClass.setSide(tokens[2]);
                shelfClass.setShelfNo(Integer.parseInt(tokens[3]));
                shelfClass.setXpos(Integer.parseInt(tokens[4]));
                shelfClass.setYpos(Integer.parseInt(tokens[5]));
                shelfClassList.add(shelfClass);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setUpList() {

        productListView = (ListView) findViewById(R.id.productListView);

        ProductAdapter adapter = new ProductAdapter(getApplicationContext(), 0, productClassList);
        productListView.setAdapter(adapter);
    }

    private void setUpFileList() {

        ListView shoppingListFileView = findViewById(R.id.shoppingListFileView);

        List<String> fileList = getFileList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
        shoppingListFileView.setAdapter(adapter);

    }

    private void loadData() {

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


    private void setUpOnclickListener()
    {
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                ProductClass selectProduct = (ProductClass) (productListView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id",selectProduct.getProductID());
                startActivity(showDetail);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            setContentView(R.layout.activity_main);
            return true;
        } else if (itemId == R.id.ProductSearch) {
            setContentView(R.layout.productsearchlayout);
            setUpList();
            setUpOnclickListener();
            initSearchWidgets();
            return true;
        } else if (itemId == R.id.ShoppingList) {
            setContentView(R.layout.shopping_list_layout);
            createShoppingListButton = (Button)findViewById(R.id.createShoppingListButton);
            createShoppingListButton.setOnClickListener(this);
            //setUpFileList();
            //loadData();
            handleShowCreatedShoppingLists();
            //handleFileNameClick();
            handleShoppingListViewClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleFileNameClick() {
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

    public void onClick(View aview) {
        if (aview == createShoppingListButton) {
            setContentView(R.layout.create_shopping_list_layout);

            createShoppingListView = findViewById(R.id.shoppingListView);
            ShoppingListAdapter adapter = new ShoppingListAdapter(this, 0, productClassList);
            createShoppingListView.setAdapter(adapter);


            submitShoppingListButton = findViewById(R.id.submitButton);
            submitShoppingListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText enteredFilename = findViewById(R.id.userFilename);
                    String shoppingListName = enteredFilename.getText().toString();
                    String shoppingListFile = shoppingListName + ".txt";

                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), shoppingListFile));
                        for (ProductClass product : productClassList) {
                            if (product.isChecked()) {
                                fileOutputStream.write((product.getProductID() + "," + product.getProductName() + "," + product.getShelfRow() + "," + product.getShelfID() + "," +"\n").getBytes());
                                product.setChecked(false);
                            }
                        }
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    

                    shoppingListName = shoppingListName + ",";

                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), shoppingListFileNames), true);
                        fileOutputStream.write(shoppingListName.getBytes());
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    setContentView(R.layout.shopping_list_layout);
                    handleShowCreatedShoppingLists();
                }
            });
            initSearchWidgetsForShoppingList();

        }
    }

    public void handleShowCreatedShoppingLists() {
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
        shoppingListFileView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadShoppingList(position);
                setContentView(R.layout.shopping_list_play_layout);

                currentIndex = 0;

                productDetails = findViewById(R.id.shopping_list_product_details);
                shoppingListCounter = findViewById(R.id.shopping_list_counter);
                nextButton = findViewById(R.id.next_button);
                previousButton = findViewById(R.id.previous_button);

                shoppingListDisplayProduct();
                String counterText = (currentIndex+1) + "/" + shoppingList.size();
                shoppingListCounter.setText(counterText);

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentIndex == shoppingList.size()-1) {
                            String text = "That's it! You've collected everything on your list please head to the checkouts.";
                            productDetails.setText(text);
                            currentIndex = currentIndex + 1;
                            String btnText = "Close";
                            nextButton.setText(btnText);
                        } else if (currentIndex == shoppingList.size()) {
                            setContentView(R.layout.shopping_list_layout);
                            handleShowCreatedShoppingLists();
                        } else {
                            currentIndex = (currentIndex + 1) % shoppingList.size();
                            String counterText = (currentIndex + 1) + "/" + shoppingList.size();
                            shoppingListCounter.setText(counterText);
                            shoppingListDisplayProduct();
                            previousButton.setVisibility(View.VISIBLE);
                            if (currentIndex == shoppingList.size() - 1) {
                                String btnText = "Finish";
                                nextButton.setText(btnText);
                            }
                        }
                    }
                });

                previousButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentIndex =(currentIndex - 1) % shoppingList.size();
                        String counterText = (currentIndex+1) + "/" + shoppingList.size();
                        shoppingListCounter.setText(counterText);
                        shoppingListDisplayProduct();
                        if (currentIndex == 0) {
                            previousButton.setVisibility(View.GONE);
                        }
                        if (currentIndex == shoppingList.size() - 1) {
                            //Good practice to not set text literal in setText method
                            String btnTxt = "Finish";
                            nextButton.setText(btnTxt);
                        }
                        if (currentIndex < shoppingList.size() - 1) {
                            String btnText = "Next";
                            nextButton.setText(btnText);
                        }
                    }
                });
            }
        });
    }

    public void loadShoppingList(int position) {
        String filename =  (String) shoppingListFileView.getItemAtPosition(position);
        filename = filename + ".txt";

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
                shoppingList.add(productClass);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shoppingListDisplayProduct() {
        ProductClass productClass = shoppingList.get(currentIndex);

        String location = productClass.getProductName() + " can be found in: \nAisle: " + shelfClassList.get(productClass.getShelfID()-1).getAisleNo()
                + "\nSide: " + shelfClassList.get(productClass.getShelfID()-1).getSide() + "\nShelf: " + shelfClassList.get(productClass.getShelfID()-1).getShelfNo()
                ;

        productDetails.setText(location);


    }
}