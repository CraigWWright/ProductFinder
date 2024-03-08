package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<ProductClass> productClassList = new ArrayList<ProductClass>();
    public static ArrayList<ShelfClass> shelfClassList = new ArrayList<ShelfClass>();
    public static ArrayList<ProductClass> shoppingList = new ArrayList<ProductClass>();

    private ListView productListView;

    private ListView createShoppingListView;
    private ListView shoppingListFileView;
    private Button createShoppingListButton;
    private Button submitShoppingListButton;

    private ArrayList<String> fileNamesList = new ArrayList<>();

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
            //Log.wtf("MyActivity", "Error reading data file on line" + line, e);
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

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNamesList);
            shoppingListFileView = findViewById(R.id.shoppingListFileView);
            shoppingListFileView.setAdapter(adapter);
            handleFileNameClick();
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
                    Log.d("MEssage", productClass.getProductName());
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
                    String filename = enteredFilename.getText().toString();
                    StringBuilder selectedProducts = new StringBuilder();
                    /*
                    for (ProductClass product : productClassList) {
                        if (product.isChecked()) {
                            selectedProducts.append(product.getProductID()).append(", ");
                        }
                    }

                     */
                    //CSVWriter.saveStringAsCSV(getApplicationContext(), String.valueOf(selectedProducts), filename);
                    try {
                        FileOutputStream file = openFileOutput(filename + ".txt", MODE_PRIVATE);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);
                        for (ProductClass product : productClassList) {
                            if (product.isChecked()) {
                                outputStreamWriter.write(product.getProductID() + "," + "\n");
                            }
                        }
                        outputStreamWriter.flush();
                        outputStreamWriter.close();

                        fileNamesList.add(filename + ".txt");

                        Toast.makeText(MainActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    setContentView(R.layout.shopping_list_layout);
                    adapter.notifyDataSetChanged();
                }
            });
            initSearchWidgetsForShoppingList();

        }
    }

}