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
import java.util.regex.Pattern;

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
                shelfClass.setNode(Integer.parseInt(tokens[6]));
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

            EditText enteredFilename = findViewById(R.id.userFilename);

            submitShoppingListButton = findViewById(R.id.submitButton);
            submitShoppingListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shoppingListName = enteredFilename.getText().toString();
                    String shoppingListFile = shoppingListName + ".txt";




                    //////////////////////////
                    /////////////////////////
                    // CODE FOR SORTING SHOPPING LIST


                    InputStream is = getResources().openRawResource(R.raw.matrix_1);
                    StringBuilder matrixString = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is3, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is4, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is5, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is6, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is7, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is8, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is9, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is10, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is11, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is12, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is13, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is14, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is15, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is16, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is17, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is18, Charset.forName("UTF-8")));
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(is19, Charset.forName("UTF-8")));
                        String line;
                        while ((line = br.readLine()) != null) {
                            matrixString19.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String test = matrixString.toString() + matrixString2.toString() + matrixString3.toString() + matrixString4.toString() + matrixString5.toString() + matrixString6.toString() + matrixString7.toString()+ matrixString8.toString()+ matrixString9.toString()+ matrixString10.toString()+ matrixString11.toString()+ matrixString12.toString()+ matrixString13.toString()+ matrixString14.toString()+ matrixString15.toString()+ matrixString16.toString()+ matrixString17.toString()+ matrixString18.toString()+ matrixString19.toString();

                    int[][] graph = createGraph(test, 157, 157);


                    ArrayList<ProductClass> unsortedShoppingList = new ArrayList<ProductClass>();
                    unsortedShoppingList.clear();
                    for (ProductClass productClass : productClassList){
                        if (productClass.isChecked()) {
                            unsortedShoppingList.add(productClass);
                        }
                    }

                    for (ProductClass productClass : unsortedShoppingList) {
                        Log.d("Unsorted", productClass.getProductName());
                    }

                    int size = unsortedShoppingList.size();
                    int srcShelf = 0;
                    ArrayList<ProductClass> sortedShoppingList = new ArrayList<>();
                    sortedShoppingList.clear();

                    for (int i=0; i<size; i++) {
                        if (!sortedShoppingList.isEmpty()) {
                            //srcShelf = sortedShoppingList.get(i-1).getShelfNode();
                            srcShelf = shelfClassList.get(sortedShoppingList.get(i-1).getShelfID()).getNode();
                        }
                        ProductClass productClass = findShortest(graph, srcShelf, unsortedShoppingList);
                        sortedShoppingList.add(productClass);
                    }

                    for (ProductClass productClass : sortedShoppingList) {
                        Log.d("Message", productClass.getProductName());
                    }

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

    public static ProductClass findShortest(int[][] graph, int srcShelf, ArrayList<ProductClass> list) {
        int min  = 100;
        int counter = 0;
        for (int i = 0; i<list.size(); i++) {
            int distance = dijkstra(graph, srcShelf, shelfClassList.get(i).getNode());
            if (distance < min) {
                min = distance;
                counter = i;
            }
        }
        ProductClass node = list.get(counter);
        list.remove(counter);
        return node;
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
        ProductClass productClass = shoppingList.get(currentIndex);

        String location = productClass.getProductName() + " can be found in: \nAisle: " + shelfClassList.get(productClass.getShelfID()-1).getAisleNo()
                + "\nSide: " + shelfClassList.get(productClass.getShelfID()-1).getSide() + "\nShelf: " + shelfClassList.get(productClass.getShelfID()-1).getShelfNo()
                ;

        productDetails.setText(location);
    }

    public static int[][] createGraph(String matrixString, int rows, int cols) {
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

    private String cleanString(String input) {
        // Define a regular expression pattern to match invisible characters
        Pattern pattern = Pattern.compile("\\s+");
        // Replace any invisible characters with an empty string
        return pattern.matcher(input).replaceAll("");
    }
}