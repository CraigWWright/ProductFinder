package com.example.productfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        setValues();
    }

    private void getSelectedProduct() {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedProduct = MainActivity.productClassList.get(Integer.parseInt(parsedStringID)-1);
    }

    private void setValues() {

        TextView tv = (TextView) findViewById(R.id.productName);
        tv.setText(displayLocation(selectedProduct));
    }

    private String displayLocation(ProductClass selectedProduct) {
        //String location = String.valueOf(MainActivity.shelfClassList.get(selectedProduct.getShelfID()-1).getShelfNo());

        String location = selectedProduct.getProductName() + " can be found in: \nAisle: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID()-1).getAisleNo()
                + "\nSide: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID()-1).getSide() + "\nShelf: " + MainActivity.shelfClassList.get(selectedProduct.getShelfID()-1).getShelfNo()
                ;

        return location;
    }

}
