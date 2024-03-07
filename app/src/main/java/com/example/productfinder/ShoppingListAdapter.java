package com.example.productfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<ProductClass> {


    public ShoppingListAdapter(Context context, int resource, List<ProductClass> productList) {
        super(context, resource, productList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductClass productClass = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.create_shopping_list_cell, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        checkBox.setChecked(productClass.isChecked());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    productClass.setChecked(isChecked);
                }
            });


        TextView tv = (TextView) convertView.findViewById(R.id.productName2);
            tv.setText(productClass.getProductName());
        return convertView;
    }
}
