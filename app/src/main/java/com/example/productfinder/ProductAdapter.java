package com.example.productfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<ProductClass> {

    public ProductAdapter(Context context, int resource, List<ProductClass> productList) {
        super(context, resource, productList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductClass productClass = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_cell, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.productName);

        tv.setText(productClass.getProductName());

        return convertView;
    }
}
