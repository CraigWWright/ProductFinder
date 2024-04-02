package com.example.productfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class productAdapter extends ArrayAdapter<productClass> {

    public productAdapter(Context context, int resource, List<productClass> productList) {
        super(context, resource, productList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // sets values of each cell in product list view
        productClass productClass = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_cell, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.productName);

        tv.setText(productClass.getProductName());

        return convertView;
    }
}
