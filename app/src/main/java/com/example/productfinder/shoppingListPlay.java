package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class shoppingListPlay extends AppCompatActivity {

    private TextView productDetails;
    private Button nextButton;
    private Button previousButton;
    private TextView shoppingListCounter;

    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_play_layout);

        Intent previousIntent = getIntent();
        String filename = previousIntent.getStringExtra("filename");

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
        String counterText = (currentIndex+1) + "/" + MainActivity.shoppingList.size();
        shoppingListCounter.setText(counterText);

        nextButton.setOnClickListener(new View.OnClickListener() {
            // handles the user pressing the next button
            @Override
            public void onClick(View v) {
                if (currentIndex == MainActivity.shoppingList.size()-1) {
                    // if statement for if the next button has been pressed and all products have been collected
                    // changes the purpose of the next/finish button to a close button
                    String text = "That's it! You've collected everything on your list please head to the checkouts.";
                    productDetails.setText(text);
                    currentIndex = currentIndex + 1;
                    String btnText = "Close";
                    nextButton.setText(btnText);
                } else if (currentIndex == MainActivity.shoppingList.size()) {
                    // handles the close button being pressed
                    Intent shoppingList = new Intent(getApplicationContext(), shoppingList.class);
                    startActivity(shoppingList);
                } else {
                    // handles the user pressing the next button
                    // shows the next product in the list
                    currentIndex = (currentIndex + 1) % MainActivity.shoppingList.size();
                    String counterText = (currentIndex + 1) + "/" + MainActivity.shoppingList.size();
                    shoppingListCounter.setText(counterText);
                    shoppingListDisplayProduct();
                    // makes the previous button visible as it is not visible when on the first product
                    previousButton.setVisibility(View.VISIBLE);
                    if (currentIndex == MainActivity.shoppingList.size() - 1) {
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
                currentIndex =(currentIndex - 1) % MainActivity.shoppingList.size();
                String counterText = (currentIndex+1) + "/" + MainActivity.shoppingList.size();
                shoppingListCounter.setText(counterText);
                shoppingListDisplayProduct();
                if (currentIndex == 0) {
                    // if the previous button is pressed to go back to the first product the button will disappear
                    previousButton.setVisibility(View.GONE);
                }
                if (currentIndex == MainActivity.shoppingList.size() - 1) {
                    // if on close page and press previous it changes the text back to finish
                    // Good practice to not set text literal in setText method
                    String btnTxt = "Finish";
                    nextButton.setText(btnTxt);
                }
                if (currentIndex < MainActivity.shoppingList.size() - 1) {
                    // if on finish page and press previous it changes the text back to finish
                    String btnText = "Next";
                    nextButton.setText(btnText);
                }
            }
        });
    }

    public void shoppingListDisplayProduct() {
        // displays the current product in the shopping list
        ProductClass productClass = MainActivity.shoppingList.get(currentIndex);

        String location = productClass.getProductName() + " can be found in: \nAisle: " + MainActivity.shelfClassList.get(productClass.getShelfID()-1).getAisleNo()
                + "\nSide: " + MainActivity.shelfClassList.get(productClass.getShelfID()-1).getSide() + "\nShelf: " + MainActivity.shelfClassList.get(productClass.getShelfID()-1).getShelfNo()
                ;

        productDetails.setText(location);
    }


    public void loadShoppingList(String filename) {
        // handles loading the shopping list that the user clicked on
        // clears the shopping list
        // used in case user had previously opened another shopping list in the same run
        MainActivity.shoppingList.clear();

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
                MainActivity.shoppingList.add(productClass);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
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
