package com.example.productfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;

public class detailActivity extends AppCompatActivity {

    ZoomageView simpleImageView;
    productClass selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets view
        setContentView(R.layout.product_search_detail);

        simpleImageView = findViewById(R.id.simpleImageView);

        getSelectedProduct();
        displayLocation();
    }

    private void getSelectedProduct() {
        // receives product from main activity
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedProduct = mainActivity.productClassList.get(Integer.parseInt(parsedStringID)-1);
    }

    private void displayLocation() {

        // displays location set by setValues method
        TextView tv = (TextView) findViewById(R.id.productName);
        tv.setText(setValues(selectedProduct));
    }

    private String setValues(productClass selectedProduct) {
        String location;

        // if statement adds shelf row if it is relevant
        if (selectedProduct.getShelfRow().equals("0")) {
            location = selectedProduct.getProductName() + " can be found in: \nAisle: " + mainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getAisleNo()
                    + "\nSide: " + mainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getSide() + "\nShelf: " + mainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getShelfNo();
        } else {
            location = selectedProduct.getProductName() + " can be found in: \nAisle: " + mainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getAisleNo()
                    + "\nSide: " + mainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getSide() + "\nShelf: " + mainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getShelfNo() + "\nShelf Row: " + selectedProduct.getShelfRow();
        }

        return location;
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
