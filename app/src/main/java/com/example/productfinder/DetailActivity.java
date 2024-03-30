package com.example.productfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;

public class DetailActivity extends AppCompatActivity {

    ZoomageView simpleImageView;
    ProductClass selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        simpleImageView = findViewById(R.id.simpleImageView);

        getSelectedProduct();
        displayLocation();
    }

    private void getSelectedProduct() {
        // receives product from main activity
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedProduct = MainActivity.productClassList.get(Integer.parseInt(parsedStringID)-1);
    }

    private void displayLocation() {

        // displays location set by setValues method
        TextView tv = (TextView) findViewById(R.id.productName);
        tv.setText(setValues(selectedProduct));
    }

    private String setValues(ProductClass selectedProduct) {
        String location;

        // if statement adds shelf row if it is relevant
        if (selectedProduct.getShelfRow().equals("0")) {
            location = selectedProduct.getProductName() + " can be found in: \nAisle: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getAisleNo()
                    + "\nSide: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getSide() + "\nShelf: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getShelfNo();
        } else {
            location = selectedProduct.getProductName() + " can be found in: \nAisle: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getAisleNo()
                    + "\nSide: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getSide() + "\nShelf: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID() - 1).getShelfNo() + "\nShelf Row: " + selectedProduct.getShelfRow();
        }

        return location;
    }

}
