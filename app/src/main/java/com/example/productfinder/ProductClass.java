package com.example.productfinder;

public class ProductClass {
    private String productID;

    private String productName;

    private String shelfRow;
    private int shelfID;
    private boolean isChecked;


    public ProductClass() {
        productID = "";
        productName = "";
        shelfRow = "";
        shelfID = 0;
        isChecked = false;
    }

    public ProductClass(String anId, String aProductName, String aShelfRow, int aShelfID) {
        productID = anId;
        productName = aProductName;
        shelfRow = aShelfRow;
        shelfID = aShelfID;
        isChecked = false;
    }

    public String getProductID() {return productID;}

    public void setProductID(String anId) {productID = anId;}

    public String getProductName() {return productName;}

    public void setProductName(String aProductName) {productName = aProductName;}

    public String getShelfRow() {return shelfRow;}

    public void setShelfRow(String aShelfRow) {shelfRow = aShelfRow;}

    public int getShelfID() {return shelfID;}

    public void setShelfID(int aShelfID) {shelfID = aShelfID;}

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
