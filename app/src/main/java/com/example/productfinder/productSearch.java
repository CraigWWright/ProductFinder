package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class productSearch extends AppCompatActivity {

    private ListView productListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets view
        setContentView(R.layout.product_search_layout);

        setUpList();
        initSearchWidgets();
        setUpOnclickListener();
    }


    private void setUpList() {

        // creates ListView using productAdapter class
        productListView = (ListView) findViewById(R.id.productListView);

        productSearchAdapter adapter = new productSearchAdapter(getApplicationContext(), 0, mainActivity.productClassList);
        productListView.setAdapter(adapter);
    }

    private void setUpOnclickListener()
    {
        // Handles clicking on a product in the product search layout
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                // Receives the product clicked on
                productClass selectProduct = (productClass) (productListView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), productSearchDetail.class);
                // shows the DetailActivity.java which shows the products location and shop image.
                showDetail.putExtra("id",selectProduct.getProductID());
                startActivity(showDetail);

            }
        });

    }

    private void initSearchWidgets() {
        // This method allows the user to search for a product on the product search page
        SearchView searchView = (SearchView) findViewById(R.id.productSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<productClass> filteredProducts = new ArrayList<productClass>();
                for (com.example.productfinder.productClass productClass: mainActivity.productClassList) {
                    // creates array of products which contain text searched by user
                    if (productClass.getProductName().toLowerCase().contains(newText.toLowerCase())){
                        filteredProducts.add(productClass);
                    }
                }

                // displays the filtered products
                productSearchAdapter adapter = new productSearchAdapter(getApplicationContext(), 0, filteredProducts);
                productListView.setAdapter(adapter);

                return false;
            }
        });
    }

    // add the options menu to the current page
    public boolean onCreateOptionsMenu(Menu menu) {
        // creates the menu which allows for navigation
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handles menu clicks
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
