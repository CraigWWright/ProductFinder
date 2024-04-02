package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class shoppingListPlay extends AppCompatActivity {

    private TextView productDetails;
    private Button nextButton;
    private Button previousButton;
    private TextView shoppingListCounter;

    private int currentIndex = 0;

    ArrayList<productClass> sortedShoppingList = new ArrayList<productClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets view
        setContentView(R.layout.shopping_list_play_layout);

        // receives name of shopping list to be opened from previous activity
        Intent previousIntent = getIntent();
        String filename = previousIntent.getStringExtra("filename");

        // loads the selected shopping list and sorts it
        loadShoppingList(filename);

        currentIndex = 0;

        // sets up buttons and text views
        productDetails = findViewById(R.id.shopping_list_product_details);
        shoppingListCounter = findViewById(R.id.shopping_list_counter);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);

        // display the current product
        shoppingListDisplayProduct();
        // this shows what product the user is on out of how many they have. i.e. 5/11 products
        String counterText = (currentIndex+1) + "/" + sortedShoppingList.size();
        shoppingListCounter.setText(counterText);

        nextButton.setOnClickListener(new View.OnClickListener() {
            // handles the user pressing the next button
            @Override
            public void onClick(View v) {
                if (currentIndex == sortedShoppingList.size()-1) {
                    // if statement for if the next button has been pressed and all products have been collected
                    // changes the purpose of the next/finish button to a close button
                    String text = "That's it! You've collected everything on your list please head to the checkouts.";
                    productDetails.setText(text);
                    currentIndex = currentIndex + 1;
                    String btnText = "Close";
                    nextButton.setText(btnText);
                } else if (currentIndex == sortedShoppingList.size()) {
                    // handles the close button being pressed
                    Intent shoppingList = new Intent(getApplicationContext(), shoppingList.class);
                    startActivity(shoppingList);
                } else {
                    // handles the user pressing the next button
                    // shows the next product in the list
                    currentIndex = (currentIndex + 1) % sortedShoppingList.size();
                    String counterText = (currentIndex + 1) + "/" + sortedShoppingList.size();
                    shoppingListCounter.setText(counterText);
                    shoppingListDisplayProduct();
                    // makes the previous button visible as it is not visible when on the first product
                    previousButton.setVisibility(View.VISIBLE);
                    if (currentIndex == sortedShoppingList.size() - 1) {
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
                currentIndex =(currentIndex - 1) % sortedShoppingList.size();
                String counterText = (currentIndex+1) + "/" + sortedShoppingList.size();
                shoppingListCounter.setText(counterText);
                shoppingListDisplayProduct();
                if (currentIndex == 0) {
                    // if the previous button is pressed to go back to the first product the button will disappear
                    previousButton.setVisibility(View.GONE);
                }
                if (currentIndex == sortedShoppingList.size() - 1) {
                    // if on close page and press previous it changes the text back to finish
                    // Good practice to not set text literal in setText method
                    String btnTxt = "Finish";
                    nextButton.setText(btnTxt);
                }
                if (currentIndex < sortedShoppingList.size() - 1) {
                    // if on finish page and press previous it changes the text back to finish
                    String btnText = "Next";
                    nextButton.setText(btnText);
                }
            }
        });
    }

    public void shoppingListDisplayProduct() {
        // displays the current product in the shopping list
        productClass productClass = sortedShoppingList.get(currentIndex);

        String location = productClass.getProductName() + " can be found in: \nAisle: " + mainActivity.shelfClassList.get(productClass.getShelfID()-1).getAisleNo()
                + "\nSide: " + mainActivity.shelfClassList.get(productClass.getShelfID()-1).getSide() + "\nShelf: " + mainActivity.shelfClassList.get(productClass.getShelfID()-1).getShelfNo()
                ;

        productDetails.setText(location);
    }


    public void loadShoppingList(String filename) {
        ArrayList<productClass> unsortedShoppingList = new ArrayList<productClass>();
        sortedShoppingList.clear();


        // reads in products from shopping list into the unsorted list
        File file = getApplicationContext().getFileStreamPath(filename);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line ="";
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");

                productClass productClass = new productClass();
                productClass.setProductID(tokens[0]);
                productClass.setProductName(tokens[1]);
                productClass.setShelfRow(tokens[2]);
                productClass.setShelfID(Integer.parseInt(tokens[3]));
                unsortedShoppingList.add(productClass);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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

        // the first srcShelf is always at the entrance of the shop
        int srcShelf = 0;
        int size = unsortedShoppingList.size();

        // loops through all the products in the unsorted list
        for (int i=0; i<size; i++) {
            if (!sortedShoppingList.isEmpty()) {
                // sets the srcShelf to whatever product was most recently added to the sorted list
                // i.e. find the product closest to the product last visited
                srcShelf = mainActivity.shelfClassList.get(sortedShoppingList.get(i-1).getShelfID()).getNode();
            }
            // finds the closest product to the srcShelf
            productClass productClass = findShortest(graph, srcShelf, unsortedShoppingList);
            // adds this product to the sorted list
            sortedShoppingList.add(productClass);
            unsortedShoppingList.clear();
        }
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

    public productClass findShortest(int[][] graph, int srcShelf, ArrayList<productClass> list) {
        // finds product closest to srcShelf
        int min = Integer.MAX_VALUE;
        int counter = 0;
        // loops through all products in unsorted list and finds minimum distance
        for (int i=0; i<list.size(); i++) {
            int distance = findDistance(graph, srcShelf, mainActivity.shelfClassList.get(list.get(i).getShelfID()).getNode());
            if (distance < min) {
                min = distance;
                counter = i;
            }
        }
        productClass node = list.get(counter);
        // removes the product that was closest from the unsorted list - it has been visited and won't be checked again
        list.remove(counter);
        return node;
    }

    public int findDistance(int[][] graph, int srcShelf, int dest) {
        return graph[srcShelf][dest];
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
