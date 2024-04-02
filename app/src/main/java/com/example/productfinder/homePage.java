package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class homePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets view
        setContentView(R.layout.activity_main);
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
